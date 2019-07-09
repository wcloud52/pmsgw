package com.saas.biz.util;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.saas.biz.controller.WeixinTestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步粉丝实现类
 *
 * Email 253498229@qq.com
 * Created on 2018/3/28 下午10:08
 *
 * @author wangbin
 */

public class WeChatSyncFollowerTools {
	private static final Logger log = LoggerFactory.getLogger(WeixinTestController.class);

  private WxMpService wxMpService;

  private SyncInterface syncInterface;

  /**
   * 每次同步多少条数据，默认每次500条
   */
  private int syncCount = 500;

  private WeChatSyncFollowerTools() {
  }

  /**
   * 用户自定义每次同步多少条
   */
  public WeChatSyncFollowerTools setSyncCount(int syncCount) {
    this.syncCount = syncCount;
    return this;
  }

  /**
   * 初始化工具类，需传入 wxMpService 和 同步业务类
   *
   * @param wxMpService   wxMpService
   * @param syncInterface 同步业务类
   */
  public WeChatSyncFollowerTools(WxMpService wxMpService, SyncInterface syncInterface) {
    this.wxMpService = wxMpService;
    this.syncInterface = syncInterface;
  }

  /**
   * 同步入口
   *
   * @throws WxErrorException
   */
  public void synchronize() throws WxErrorException {
    WxMpUserService userService = wxMpService.getUserService();
    boolean flag = true;
    String nextOpenId = null;
    /*
      获取当前库中所有的用户openid
     */
    List<String> existOpenIds = syncInterface.getExistOpenIds() == null ? new ArrayList<>() : syncInterface.getExistOpenIds();
    int i = 1;
    //循环方式
    while (flag) {
      /*
        从微信拉取微信粉丝
       */
      WxMpUserList wxMpUserList = userService.userList(nextOpenId);
      if (nextOpenId == null) {
        log.debug("开始同步粉丝,需同步粉丝总数:{}", wxMpUserList.getCount());
      }
      /*
        获取用户openId列表(每次最大值为10000)
       */
      List<String> openids = wxMpUserList.getOpenids();
      /*
        获取粉丝信息列表
       */
      List<WxMpUser> wxMpUsers = this.getWxMpUserList(openids);
      if (wxMpUserList.getCount() == 0 && !ObjectUtils.isEmpty(nextOpenId)) {
        wxMpUsers.add(userService.userInfo(nextOpenId));
      }
      log.debug("开始第{}次同步，本次需同步:{}条", i, wxMpUsers.size());
      i++;
      /*
        同步业务
       */
      this.batchSynchronize(wxMpUsers, existOpenIds);
      /*
        获取下次获取开始的openId
       */
      nextOpenId = wxMpUserList.getNextOpenid();
      /*
        对比nextOpenId，如果相同，就是没有下一个了
       */
      if (ObjectUtils.isEmpty(nextOpenId)) {
        //没有下一个了
        flag = false;
        log.debug("同步结束");
      }
    }
    /*
      取消关注的粉丝需要修改状态位
     */
    syncInterface.unSubscribeOpenIds(existOpenIds);
  }

  /**
   * 粉丝列表分批同步
   *
   * @param wxMpUsers 微信粉丝数据(每次最高1w人)
   */
  private void batchSynchronize(List<WxMpUser> wxMpUsers, List<String> existOpenIds) {
    /*
      获取库中已经存在的粉丝
     */
    List<WxMpUser> oldWxMpUsers = this.getOldWxMpUsers(wxMpUsers, existOpenIds);
    /*
      库中已经存在的粉丝，需要update
     */
    syncInterface.oldWxMpUser(oldWxMpUsers);
    /*
      分批次同步新粉丝
     */
    this.synchronizeNewWxMpUsersBatches(wxMpUsers, syncCount);
  }

  /**
   * 分批次同步所有不存在的粉丝
   *
   * @param wxMpUsers 微信粉丝列表
   * @param maxCount  每批最多人数，默认500
   * @return
   */
  private void synchronizeNewWxMpUsersBatches(List<WxMpUser> wxMpUsers, int maxCount) {
    List<List<WxMpUser>> newFollowerList = new ArrayList<>();
    //总数
    int count = wxMpUsers.size();
    int a = count % maxCount > 0 ? count / maxCount + 1 : count / maxCount;
    for (int i = 0; i < a; i++) {
      if (i + 1 < a) {
        List<WxMpUser> users = wxMpUsers.subList(i * maxCount, (i + 1) * maxCount);
        newFollowerList.add(users);
      } else {
        List<WxMpUser> users = wxMpUsers.subList(i * maxCount, count);
        newFollowerList.add(users);
      }
    }
    for (List<WxMpUser> mpUsers : newFollowerList) {
      /*
        库中不存在的粉丝，需要insert
        由于量可能太大，每次最多insert 500条数据
      */
      syncInterface.newWxMpUser(mpUsers);
    }
  }


  /**
   * 获取库中已有的粉丝
   *
   * @param wxMpUsers    全部粉丝列表
   * @param existOpenIds 库中openId列表
   * @return 已有的粉丝列表
   */
  private List<WxMpUser> getOldWxMpUsers(List<WxMpUser> wxMpUsers, List<String> existOpenIds) {
    List<String> excludeOpenIds = new ArrayList<>();
    List<WxMpUser> oldWxMpUsers = new ArrayList<>();
    for (WxMpUser wxMpUser : wxMpUsers) {
      if (existOpenIds.contains(wxMpUser.getOpenId())) {
        oldWxMpUsers.add(wxMpUser);
        excludeOpenIds.add(wxMpUser.getOpenId());
      }
    }
    //排除存在的openId列表
    existOpenIds.removeAll(excludeOpenIds);
    //排除存在的粉丝
    wxMpUsers.removeAll(oldWxMpUsers);
    return oldWxMpUsers;
  }

  /**
   * 分批次获取微信粉丝信息
   * 每批100人
   *
   * @param openids
   * @return
   * @throws WxErrorException
   */
  private List<WxMpUser> getWxMpUserList(List<String> openids) throws WxErrorException {
    int count = openids.size();
    if (count <= 0) {
      return new ArrayList<>();
    }
    List<WxMpUser> list = new ArrayList<>();
    WxMpUserService userService = wxMpService.getUserService();
    int a = count % 100 > 0 ? count / 100 + 1 : count / 100;
    for (int i = 0; i < a; i++) {
      if (i + 1 < a) {
        log.debug("i:{},from:{},to:{}", i, i * 100, (i + 1) * 100);
        List<WxMpUser> followers = userService.userInfoList(openids.subList(i * 100, (i + 1) * 100));
        list.addAll(followers);
      } else {
        log.debug("i:{},from:{},to:{}", i, i * 100, count - i * 100);
        List<WxMpUser> followers = userService.userInfoList(openids.subList(i * 100, count - 1));
        list.addAll(followers);
      }
    }
    return list;
  }
}