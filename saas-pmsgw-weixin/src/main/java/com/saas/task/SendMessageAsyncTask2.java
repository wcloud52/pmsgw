package com.saas.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCustomerMessage;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.properties.WxProperties;
import com.saas.biz.service.NodejsCustomerMessageService;
import com.saas.biz.service.WeixinUserService;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Component
public class SendMessageAsyncTask2 {

	private static final Logger log = LoggerFactory.getLogger(SendMessageAsyncTask2.class);

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private WxProperties info;
	@Autowired
	private WeixinUserService weixinUserService;
	@Autowired
	private NodejsCustomerMessageService nodejsCustomerMessageService;

	/*
	 * 公棚
	 */
	private List<CustomerUser> getOpenIdWithLoft(List<WeixinUser> userList, String name) {
		List<CustomerUser> rt = new ArrayList<CustomerUser>();
		for (WeixinUser user : userList) {
			String bindName = user.getBind_name();
			if (StringUtils.isBlank(bindName)) {
				continue;
			}
			String strA = bindName.trim().replace("，", ",").replace(" ", "").replace("￥", "").replace("$", "").replace("(预)", "").replace("（预）", "").replace("*", "").replaceAll("[a-zA-Z]", "");
			String StrB = name.trim().replace(" ", "").replace("￥", "").replace("$", "").replace("(预)", "").replace("（预）", "").replace("*", "").replaceAll("[a-zA-Z]", "");
			if (StringUtils.isBlank(StrB) || StringUtils.isBlank(strA)) {
				continue;
			}
			// 包含,即多用户
			if (strA.indexOf(",") != -1) {
				strA = "," + strA + ",";
				StrB = "," + StrB + ",";

				if (strA.indexOf(StrB) != -1) {

					String game_pigowner = bindName;
					String game_receiver_openid = user.getOpenid();
					String game_receiver_nickname = user.getNickname();
					String game_receiver_headimgurl = user.getHeadimgurl();
					CustomerUser customerUser = new CustomerUser(game_pigowner, game_receiver_openid, game_receiver_nickname, game_receiver_headimgurl);

					rt.add(customerUser);
				}
			} else {
				if (strA.equals(StrB)) {

					String game_pigowner = bindName;
					String game_receiver_openid = user.getOpenid();
					String game_receiver_nickname = user.getNickname();
					String game_receiver_headimgurl = user.getHeadimgurl();
					CustomerUser customerUser = new CustomerUser(game_pigowner, game_receiver_openid, game_receiver_nickname, game_receiver_headimgurl);

					rt.add(customerUser);
				}
			}
		}
		return rt;
	}
	public List<CustomerUser> getOpenIdWithLoft(Map<String, List<CustomerUser>> map, String name) {
		
		if(map.containsKey(name))
		{
			return map.get(name);
		}
		return null;
	}
	/*
	 * 俱乐部
	 */
	private List<CustomerUser> getOpenIdWithClub(List<WeixinUser> userList, String name, String loft) {
		List<CustomerUser> rt = new ArrayList<CustomerUser>();
		for (WeixinUser user : userList) {
			String bindName = user.getBind_name();
			String bindLoft = user.getClub_bind_loft();// 棚号
			bindLoft = StringUtils.stripStart(bindLoft, "0");
			loft = StringUtils.stripStart(loft, "0");
			if (StringUtils.isBlank(bindName) || StringUtils.isBlank(bindLoft) || StringUtils.isBlank(loft)) {
				continue;
			}
			String strA = bindName.trim().replace("，", ",").replace(" ", "").replace("￥", "").replace("(预)", "").replace("（预）", "").replace("$", "").replace("*", "").replaceAll("[a-zA-Z]", "");
			String StrB = name.trim().replace(" ", "").replace("￥", "").replace("(预)", "").replace("（预）", "").replace("$", "").replace("*", "").replaceAll("[a-zA-Z]", "");
			if (StringUtils.isBlank(StrB) || StringUtils.isBlank(strA)) {
				continue;
			}
			// 包含,即多用户
			if (strA.indexOf(",") != -1) {
				strA = "," + strA + ",";
				StrB = "," + StrB + ",";

				if ((strA.indexOf(StrB) != -1) && bindLoft.equals(loft)) {
					String game_pigowner = bindName;
					String game_receiver_openid = user.getOpenid();
					String game_receiver_nickname = user.getNickname();
					String game_receiver_headimgurl = user.getHeadimgurl();
					CustomerUser customerUser = new CustomerUser(game_pigowner, game_receiver_openid, game_receiver_nickname, game_receiver_headimgurl);

					rt.add(customerUser);
				}
			} else {
				if (strA.equals(StrB) && bindLoft.equals(loft)) {
					String game_pigowner = bindName;
					String game_receiver_openid = user.getOpenid();
					String game_receiver_nickname = user.getNickname();
					String game_receiver_headimgurl = user.getHeadimgurl();
					CustomerUser customerUser = new CustomerUser(game_pigowner, game_receiver_openid, game_receiver_nickname, game_receiver_headimgurl);

					rt.add(customerUser);
				}
			}
		}
		return rt;
	}

	public Map<String, List<CustomerUser>> getWeixinUserMap(List<WeixinUser> userList) {
		Map<String, List<CustomerUser>> map = new HashMap<>();
		for (WeixinUser user : userList) {
			
			String bindName = user.getBind_name();
			if (StringUtils.isBlank(bindName)) {
				continue;
			}
			String strA = bindName.trim().replace("，", ",").replace(" ", "").replace("￥", "").replace("(预)", "").replace("（预）", "").replace("$", "").replace("*", "").replaceAll("[a-zA-Z]", "");
			
			String game_pigowner = bindName;
			String game_receiver_openid = user.getOpenid();
			String game_receiver_nickname = user.getNickname();
			String game_receiver_headimgurl = user.getHeadimgurl();
			CustomerUser customerUser = new CustomerUser(game_pigowner, game_receiver_openid, game_receiver_nickname, game_receiver_headimgurl);

			// 包含,即多用户
			if (strA.indexOf(",") != -1) {
				String[] array = strA.split(",");
				if(array!=null&&array.length>0)
				{
					List<CustomerUser> list=new ArrayList<CustomerUser>();
					list.add(customerUser);
					for (String key : array) {
						if(map.containsKey(key))
						{
							map.get(key).addAll(list);
						}
						else
						{
							map.put(key, list);
						}
					}
				}
			}
			else
			{
				List<CustomerUser> list=new ArrayList<CustomerUser>();
				list.add(customerUser);
				String key=strA;
				if(map.containsKey(key))
				{
					map.get(key).addAll(list);
				}
				else
				{
					map.put(key, list);
				}
			}
		}

		return map;
	}

	public void sendResultMessage(List<NodejsCrawlerDetailGame> pmsgwGameDetailList) {
		List<WeixinUser> userList = weixinUserService.selectAll();
		String gameTemplateId = info.getGameTemplateId();
		
		Map<String, List<CustomerUser>> map=getWeixinUserMap(userList);
		
		for (NodejsCrawlerDetailGame pmsgwGameDetail : pmsgwGameDetailList) {
			if (pmsgwGameDetail != null) {
				String name = pmsgwGameDetail.getPigowner();
				String loft = pmsgwGameDetail.getCotenum();
				String type = pmsgwGameDetail.getMaster_type();

				List<CustomerUser> openidList = new ArrayList<CustomerUser>();
				if ("loft".equals(type)) {
					openidList = getOpenIdWithLoft(map,name);//getOpenIdWithLoft(userList, name);
				} else {
					openidList = getOpenIdWithClub(userList, name, loft);
				}
				if (openidList != null && openidList.size() > 0) {

					for (CustomerUser customerUser : openidList) {
						sendResultMessage(pmsgwGameDetail, gameTemplateId, customerUser);
					}
				}
			}
		}
	}

	public void sendResultMessage(NodejsCrawlerDetailGame pmsgwGameDetail, String gameTemplateId, CustomerUser customerUser) {

		String url = pmsgwGameDetail.getMaster_href();
		String masterText = pmsgwGameDetail.getMaster_text();
		String pigowner = pmsgwGameDetail.getPigowner();
		String speed = pmsgwGameDetail.getSpeed();
		Integer rank = pmsgwGameDetail.getRank();
		String ringnum = pmsgwGameDetail.getRingnum();
		String cometime = pmsgwGameDetail.getCometime();
		String cote_state = pmsgwGameDetail.getCote_state();
		String openid = customerUser.getGame_receiver_openid();
		String bindName = customerUser.getGame_pigowner();
		String nickname = customerUser.getGame_receiver_nickname();
		String headimgurl = customerUser.getGame_receiver_headimgurl();
		// 发送模板

		// 实例化模板对象
		WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
		// 设置模板ID
		wxMpTemplateMessage.setTemplateId(gameTemplateId);
		// 设置发送给哪个用户
		wxMpTemplateMessage.setToUser(openid);
		wxMpTemplateMessage.setUrl(url);

		List<WxMpTemplateData> list = Arrays.asList(new WxMpTemplateData("first", "平民赛鸽网提供", "#000000"), new WxMpTemplateData("keyword1", masterText, "#000000"), new WxMpTemplateData("keyword2", "", "#000000"), new WxMpTemplateData("keyword3", pigowner, "#000000"), new WxMpTemplateData("keyword4", "最终成绩以公布为准", "#000000"), new WxMpTemplateData("keyword5", speed + ",名次" + rank, "#000000"), new WxMpTemplateData("remark", "足环号" + ringnum + ",归巢时间" + cometime, "#000000"));
		// 放进模板对象。准备发送
		wxMpTemplateMessage.setData(list);

		NodejsCustomerMessage weixinMessage = nodejsCustomerMessageService.convert(pmsgwGameDetail);
		String message_id = weixinMessage.getGame_detail_id() + "_" + openid; // UUID.randomUUID().toString();
		weixinMessage.setMessage_id(message_id);
		weixinMessage.setMessage_senderId(gameTemplateId);
		weixinMessage.setMessage_senderName(gameTemplateId);
		weixinMessage.setMessage_sendTime(new Date());

		weixinMessage.setMessage_receiverId(openid);
		weixinMessage.setMessage_receiverName(bindName);

		weixinMessage.setMessage_type(gameTemplateId);
		weixinMessage.setMessage_title(masterText);

		weixinMessage.setMessage_text(JSON.toJSONString(wxMpTemplateMessage));

		weixinMessage.setMessage_status(0);

		weixinMessage.setGame_receiver_openid(openid);
		weixinMessage.setGame_receiver_nickname(nickname);
		weixinMessage.setGame_receiver_headimgurl(headimgurl);

		weixinMessage.setMessage_create_time(new Date());
		weixinMessage.setMessage_modify_time(new Date());
		int ret = nodejsCustomerMessageService.insert(weixinMessage);

		int message_status = 0;
		// 已经存在
		if (ret != -1) {
			boolean flag = StringUtils.equals(cote_state, "1");
			if (flag) {
				try {
					wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
					message_status = 1;// 发送成功
					//nodejsCustomerMessageService.changeStatus(message_id, message_status, "");
				} catch (WxErrorException e) {
					log.error(e.getMessage());
					message_status = -1;// 发送失败
					//nodejsCustomerMessageService.changeStatus(message_id, message_status, e.getMessage());
				}
			}

			else {
				nodejsCustomerMessageService.changeStatus(message_id, -1, "当前为不可以发送公棚");
			}
		}
	}

	public class CustomerUser {

		private String game_pigowner;

		private String game_receiver_openid;

		private String game_receiver_nickname;

		private String game_receiver_headimgurl;

		public CustomerUser(String game_pigowner, String game_receiver_openid, String game_receiver_nickname, String game_receiver_headimgurl) {
			super();
			this.game_pigowner = game_pigowner;
			this.game_receiver_openid = game_receiver_openid;
			this.game_receiver_nickname = game_receiver_nickname;
			this.game_receiver_headimgurl = game_receiver_headimgurl;
		}

		public String getGame_pigowner() {
			return game_pigowner;
		}

		public void setGame_pigowner(String game_pigowner) {
			this.game_pigowner = game_pigowner;
		}

		public String getGame_receiver_openid() {
			return game_receiver_openid;
		}

		public void setGame_receiver_openid(String game_receiver_openid) {
			this.game_receiver_openid = game_receiver_openid;
		}

		public String getGame_receiver_nickname() {
			return game_receiver_nickname;
		}

		public void setGame_receiver_nickname(String game_receiver_nickname) {
			this.game_receiver_nickname = game_receiver_nickname;
		}

		public String getGame_receiver_headimgurl() {
			return game_receiver_headimgurl;
		}

		public void setGame_receiver_headimgurl(String game_receiver_headimgurl) {
			this.game_receiver_headimgurl = game_receiver_headimgurl;
		}

	}
}
