package com.saas.biz.component;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.saas.biz.mapper.ext.NodejsWeixinUserExtMapper;
import com.saas.biz.pojo.NodejsWeixinUser;
import com.saas.biz.util.EmojiFilter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 同步微信用户
 * 
 * @author tanjun
 *
 */
@Component
public class WeixinUserSync {
	private static final Logger log = LoggerFactory.getLogger(WeixinUserSync.class);
	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private NodejsWeixinUserExtMapper nodejsWeixinUserExtMapper;
	/**
	 * 同步入口
	 *
	 * @throws WxErrorException
	 */
	public void synchronize() throws WxErrorException {

		WxMpUserService userService = wxMpService.getUserService();
		boolean flag = true;
		String nextOpenId = null;

		int i = 1;
		// 循环方式
		while (flag) {
			/*
			 * 从微信拉取
			 */
			WxMpUserList wxMpUserList = userService.userList(nextOpenId);

			log.debug("开始第{}次从微信爬取用户,用户数:{}", i, wxMpUserList.getCount());

			List<String> openids = wxMpUserList.getOpenids();

			List<WxMpUser> wxMpUsers = getWxMpUserList(openids, 100);
			if(wxMpUsers!=null&wxMpUsers.size()>0)
			{
				List<NodejsWeixinUser> insertList = convert2(wxMpUsers);
				 nodejsWeixinUserExtMapper.insertByList(insertList);
			}
			
			log.debug("开始第{}次同步，本次需同步:{}条", i, wxMpUsers.size());
			i++;
			nextOpenId = wxMpUserList.getNextOpenid();

			if (StringUtils.isEmpty(nextOpenId)) {

				flag = false;
				log.debug("同步结束");
			}
		}
	}

	/**
	 * 分批次获取微信粉丝信息 每批size人
	 *
	 * @param openids
	 * @param size
	 * @return
	 * @throws WxErrorException
	 */
	private List<WxMpUser> getWxMpUserList(List<String> openids, int size) throws WxErrorException {
		int count = openids.size();
		if (count <= 0) {
			return new ArrayList<>();
		}
		List<WxMpUser> list = new ArrayList<>();
		WxMpUserService userService = wxMpService.getUserService();
		int page = count % size > 0 ? count / size + 1 : count / size;
		for (int i = 0; i < page; i++) {
			if (i + 1 < page) {
				log.debug("i:{},from:{},to:{}", i, i * size, (i + 1) * size);
				List<WxMpUser> followers = userService.userInfoList(openids.subList(i * size, (i + 1) * size));
				list.addAll(followers);
			} else {
				log.debug("i:{},from:{},to:{}", i, i * size, count);
				List<WxMpUser> followers = userService.userInfoList(openids.subList(i * size, count));
				list.addAll(followers);
			}
		}
		return list;
	}

	private List<NodejsWeixinUser> convert2(List<WxMpUser> wxMpUsers) {
		List<NodejsWeixinUser> list = new ArrayList<NodejsWeixinUser>();
		for (WxMpUser user : wxMpUsers) {
			NodejsWeixinUser weixinUser = new NodejsWeixinUser();
			weixinUser.setOpenid(user.getOpenId());
			weixinUser.setNickname(user.getNickname());

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
}