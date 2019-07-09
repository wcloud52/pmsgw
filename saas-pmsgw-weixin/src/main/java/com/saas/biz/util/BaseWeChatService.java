package com.saas.biz.util;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 同步粉丝业务类
 * 
 * Email 253498229@qq.com
 * Created on 2018/3/28 下午10:08
 *
 * @author wangbin
 */
@Service
public class BaseWeChatService implements SyncInterface {

  /**
   * 从数据库中读取openId列表
   *
   * @return
   */
  @Override
  public List<String> getExistOpenIds() {
    return null;
  }

  /**
   * 更新粉丝
   *
   * @param wxMpUsers
   */
  @Override
  public void oldWxMpUser(List<WxMpUser> wxMpUsers) {
    System.out.println("有" + wxMpUsers.size() + "个粉丝被更新了");
  }

  /**
   * 新建粉丝
   *
   * @param newMpUsers 需新增的粉丝列表
   */
  @Override
  public void newWxMpUser(List<WxMpUser> newMpUsers) {
    System.out.println("有" + newMpUsers.size() + "个粉丝被新增了");

  }

  /**
   * 取消关注
   *
   * @param existOpenIds 需取消关注的粉丝openId列表
   */
  @Override
  public void unSubscribeOpenIds(List<String> existOpenIds) {
    System.out.println("有" + existOpenIds.size() + "个粉丝被取消关注了");
  }
}