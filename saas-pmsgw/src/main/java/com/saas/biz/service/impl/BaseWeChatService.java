package com.saas.biz.service.impl;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saas.biz.mapper.ext.NodejsWeixinUserExtMapper;
import com.saas.biz.pojo.NodejsWeixinUser;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.SyncInterface;
import com.saas.biz.service.WeixinUserService;
import com.saas.biz.util.EmojiFilter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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

	@Autowired
	private WeixinUserService weixinUserService;
	
	@Autowired
	private NodejsWeixinUserExtMapper nodejsWeixinUserExtMapper;
  /**
   * 从数据库中读取openId列表
   *
   * @return
   */
  @Override
  public List<String> getExistOpenIds() {
//	  List<WeixinUser> dbList = weixinUserService.selectAll();
//	  List<String> list=new ArrayList<String>();
//	  for(WeixinUser user:dbList)
//	  {
//		  list.add(user.getOpenid());
//	  }
    return null;
  }

  /**
   * 更新粉丝
   *
   * @param wxMpUsers
   */
  @Override
  public void oldWxMpUser(List<WxMpUser> wxMpUsers) {
	 // List<WeixinUser> updateList= convert(wxMpUsers);
		//weixinUserService.updateByList(updateList);	  
  }
   private List<WeixinUser> convert(List<WxMpUser> wxMpUsers)
   {
	   List<WeixinUser> list=new ArrayList<WeixinUser>();
		  for(WxMpUser user:wxMpUsers)
		  {
			  WeixinUser weixinUser=new WeixinUser();
			  weixinUser.setId(user.getOpenId());
			  weixinUser.setOpenid(user.getOpenId());
			  try {
				  if(user.getNickname()!=null)
				weixinUser.setNickname(EmojiFilter.filterBase64Encode(user.getNickname()));
			} catch (UnsupportedEncodingException e) {
			
			}
			  weixinUser.setCity(user.getCity());
			  weixinUser.setCountry(user.getCountry());
			  //weixinUser.setGroupid(Long.valueOf(user.getGroupId()));
			  weixinUser.setHeadimgurl(user.getHeadImgUrl());
			  weixinUser.setLanguage(user.getLanguage());
			  weixinUser.setProvince(user.getProvince());
			  weixinUser.setRemark(user.getRemark());
			  weixinUser.setSex(user.getSex());
			  weixinUser.setSubscribe(user.getSubscribe()?1:0);
			  weixinUser.setSubscribe_time(user.getSubscribeTime());
			  weixinUser.setUnionid(user.getUnionId());
			  weixinUser.setCreate_time(new Date());
			  weixinUser.setModify_time(new Date());
			  list.add(weixinUser);
		  }
		  return list;
   }
   
   private List<NodejsWeixinUser> convert2(List<WxMpUser> wxMpUsers)
   {
	   List<NodejsWeixinUser> list=new ArrayList<NodejsWeixinUser>();
		  for(WxMpUser user:wxMpUsers)
		  {
			  NodejsWeixinUser weixinUser=new NodejsWeixinUser();
			  weixinUser.setOpenid(user.getOpenId());
			  try {
				  if(user.getNickname()!=null)
				weixinUser.setNickname(EmojiFilter.filterBase64Encode(user.getNickname()));
			} catch (UnsupportedEncodingException e) {
			
			}
			  weixinUser.setCity(user.getCity());
			  weixinUser.setCountry(user.getCountry());
			  weixinUser.setGroupid(user.getGroupId());
			  weixinUser.setHeadimgurl(user.getHeadImgUrl());
			  weixinUser.setLanguage(user.getLanguage());
			  weixinUser.setProvince(user.getProvince());
			  weixinUser.setRemark(user.getRemark());
			  weixinUser.setSex(user.getSex());
			  weixinUser.setSubscribe(user.getSubscribe());
			  weixinUser.setSubscribe_time(user.getSubscribeTime());
			  weixinUser.setUnionid(user.getUnionId());
			  weixinUser.setCreate_time(new Date());
			  weixinUser.setModify_time(new Date());
			  list.add(weixinUser);
		  }
		  return list;
   }
  /**
   * 新建粉丝
   *
   * @param newMpUsers 需新增的粉丝列表
   */
  @Override
  public void newWxMpUser(List<WxMpUser> newMpUsers) {
	  List<NodejsWeixinUser> insertList = convert2(newMpUsers);
	  nodejsWeixinUserExtMapper.insertByList(insertList);
  }

  /**
   * 取消关注
   *
   * @param existOpenIds 需取消关注的粉丝openId列表
   */
  @Override
  public void unSubscribeOpenIds(List<String> existOpenIds) {
	  //weixinUserService.updateSubscribeByIds(existOpenIds);
  }

@Override
public int deleteAll() {
	return nodejsWeixinUserExtMapper.deleteAll();
}
}