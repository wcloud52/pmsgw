/*
 * https://github.com/wechat-group/weixin-java-tools
 */
package com.saas.biz.service;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.util.List;

/**
 * 同步粉丝接口
 *
 * Email 253498229@qq.com
 * Created on 2018/3/28 下午10:08
 *
 * @author wangbin
 */
public interface SyncInterface {
  /**
   * 获取数据库中已存在的openId列表
   *
   * @return openId列表
   */
  List<String> getExistOpenIds();

  /**
   * 需要更新的粉丝(每次最多1w条)
   *
   * @param wxMpUsers 需更新的粉丝列表
   */
  void oldWxMpUser(List<WxMpUser> wxMpUsers);

  /**
   * 需要新增的粉丝(每次最多500条返回，可以修改这个数值)
   *
   * @param newMpUsers 需新增的粉丝列表
   */
  void newWxMpUser(List<WxMpUser> newMpUsers);

  /**
   * 取消关注的粉丝
   *
   * @param existOpenIds 需取消关注的粉丝openId列表
   */
  void unSubscribeOpenIds(List<String> existOpenIds);
  
  int deleteAll();
}
