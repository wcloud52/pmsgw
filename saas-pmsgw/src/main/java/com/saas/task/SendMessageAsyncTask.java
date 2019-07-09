package com.saas.task;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.PmsgwGameDetail;
import com.saas.biz.pojo.PmsgwPigownerGameDetail;
import com.saas.biz.pojo.WeixinMessage;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.properties.WxProperties;
import com.saas.biz.service.PmsgwGameDetailService;
import com.saas.biz.service.PmsgwPigownerGameDetailService;
import com.saas.biz.service.WeixinUserService;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Component
public class SendMessageAsyncTask {

	private static final Logger log = LoggerFactory.getLogger(SendMessageAsyncTask.class);

	@Autowired
	private WxMpService wxMpService;
	@Autowired
	private PmsgwGameDetailService pmsgwGameDetailService;
	
	@Autowired
	private PmsgwPigownerGameDetailService pmsgwPigownerGameDetailService;
	@Autowired
    private WxProperties info;
	@Autowired
	private WeixinUserService weixinUserService;

//	@Autowired
//	private NodejsCustomerMessageService nodejsCustomerMessageService;

	// 公棚
	private String getOpenIdByLoft(String name, List<WeixinUser> userList) {
		String rt = "";
		for (WeixinUser user : userList) {
			if (StringUtils.isNotBlank(user.getBind_name())) {
				String bind_nameUser = user.getBind_name().replace("，", ",");
				String[] bind_nameArray = bind_nameUser.split(",");
				for (String bind_name : bind_nameArray) {
					bind_name = bind_name.trim();
					name = name.trim();
					if (StringUtils.isNotBlank(bind_name) && name.replaceAll("[a-zA-Z]", "").equals(bind_name.replaceAll("[a-zA-Z]", ""))) {
						rt = user.getOpenid();
						return rt;
					}
				}
			}
		}
		return rt;
	}

	// 俱乐部
	private String getOpenIdByClub(String name, String loft, List<WeixinUser> userList) {
		String rt = "";
		for (WeixinUser user : userList) {
			String bind_name = user.getClub_bind_name();
			String bind_loft = user.getClub_bind_loft();
			if (StringUtils.isNotBlank(bind_name) && StringUtils.isNotBlank(bind_loft) && name.replaceAll("[a-zA-Z]", "").equals(bind_name.replaceAll("[a-zA-Z]", "")) && loft.equals(bind_loft)) {
				rt = user.getOpenid();
				return rt;
			}
		}
		return rt;
	}

	/*public void sendResultMessage(List<NodejsCrawlerDetailGame> pmsgwGameDetailList) {

		List<WeixinUser> userList = weixinUserService.selectAll();

		String gameTemplateId = info.getGameTemplateId();

		for (NodejsCrawlerDetailGame pmsgwGameDetail : pmsgwGameDetailList) {
			if (pmsgwGameDetail != null) {
				String openid = "";
				String name = pmsgwGameDetail.getPigowner();
				String loft = pmsgwGameDetail.getCotenum();
				String type = pmsgwGameDetail.getMaster_type();
				String url = pmsgwGameDetail.getMaster_href();
				String masterText = pmsgwGameDetail.getMaster_text();
				String pigowner = pmsgwGameDetail.getPigowner();
				String speed = pmsgwGameDetail.getSpeed();
				Integer rank = pmsgwGameDetail.getRank();
				String ringnum = pmsgwGameDetail.getRingnum();
				String cometime = pmsgwGameDetail.getCometime();

				if ("loft".equals(type)) {
					openid = getOpenIdByLoft(name, userList);
				} else {
					openid = getOpenIdByClub(name, loft, userList);
				}
				if (StringUtils.isNotBlank(openid)) {
					openid = "ocSsDwjRXUH_Kn2hCZQN47z6YpnM-xx";
					// 实例化模板对象
					WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
					// 设置模板ID
					wxMpTemplateMessage.setTemplateId(gameTemplateId);
					// 设置发送给哪个用户
					wxMpTemplateMessage.setToUser(openid);
					wxMpTemplateMessage.setUrl(url);

					List<WxMpTemplateData> list = Arrays.asList(new WxMpTemplateData("first", "平民赛鸽网提供", "#000000"), 
							new WxMpTemplateData("keyword1", masterText, "#000000"),
							new WxMpTemplateData("keyword2", "", "#000000"), 
							new WxMpTemplateData("keyword3", pigowner, "#000000"), 
							new WxMpTemplateData("keyword4", "最终成绩以公布为准", "#000000"),
							new WxMpTemplateData("keyword5", speed + ",名次" + rank, "#000000"), 
							new WxMpTemplateData("remark", "足环号" + ringnum + ",归巢时间" + cometime, "#000000"));
					// 放进模板对象。准备发送
					wxMpTemplateMessage.setData(list);

					NodejsCustomerMessage weixinMessage = nodejsCustomerMessageService.convert(pmsgwGameDetail);
					String message_id = UUID.randomUUID().toString();
					weixinMessage.setMessage_id(message_id);
					weixinMessage.setMessage_senderId(gameTemplateId);
					weixinMessage.setMessage_senderName(gameTemplateId);
					weixinMessage.setMessage_sendTime(new Date());

					weixinMessage.setMessage_receiverId(openid);
					weixinMessage.setMessage_receiverName(pigowner);

					weixinMessage.setMessage_type(gameTemplateId);
					weixinMessage.setMessage_title(masterText);
					weixinMessage.setMessage_text(JSON.toJSONString(wxMpTemplateMessage));
					weixinMessage.setMessage_status(0);
					weixinMessage.setMessage_create_time(new Date());
					weixinMessage.setMessage_modify_time(new Date());
					int ret = nodejsCustomerMessageService.insert(weixinMessage);

					int message_status = 0;
					// 已经存在
					if (ret != -1) {
						try {
							// 发送模板
							wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
							message_status = 1;// 发送成功
						} catch (WxErrorException e) {
							log.error(e.getMessage());
							message_status = -1;// 发送失败
						}

						nodejsCustomerMessageService.changeStatus(message_id, message_status);
					}
				}
			}
		}
	}
*/
	@Async
	public void autoSendResultMessage(List<PmsgwGameDetail> pmsgwGameDetailList, String type, String url) {

		List<WeixinUser> userList = weixinUserService.selectAll();
		String gameTemplateId = info.getGameTemplateId();
//		String appId = evn.getProperty("weixin.appId");
//		String appSecret = evn.getProperty("weixin.appSecret");
//		String gameTemplateId = evn.getProperty("weixin.result.templateId");

		//String pmsgwGameWhiteList=weixinMessageService.selectPmsgwGameWhiteList();
		for (PmsgwGameDetail pmsgwGameDetail : pmsgwGameDetailList) {
			if (pmsgwGameDetail != null) {
				String openid = "";
				String name = pmsgwGameDetail.getPigowner();
				String loft = pmsgwGameDetail.getCotenum();
				//String type = pmsgwGameDetail.getMasterType();
				//String url = pmsgwGameDetail.getMaster_href();
				String masterText = pmsgwGameDetail.getMasterText();
				String pigowner = pmsgwGameDetail.getPigowner();
				String speed = pmsgwGameDetail.getSpeed();
				Integer rank = pmsgwGameDetail.getRank();
				String ringnum = pmsgwGameDetail.getRingnum();
				String cometime = pmsgwGameDetail.getCometime();
				if ("loft".equals(type)) {
					openid = getOpenIdByLoft(name, userList);
				} else {
					openid = getOpenIdByClub(name, loft, userList);
				}
				if (StringUtils.isNotBlank(openid)) {
					
					// 实例化模板对象
					WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
					// 设置模板ID
					wxMpTemplateMessage.setTemplateId(gameTemplateId);
					// 设置发送给哪个用户
					wxMpTemplateMessage.setToUser(openid);
					wxMpTemplateMessage.setUrl(url);
					List<WxMpTemplateData> list = Arrays.asList(
							new WxMpTemplateData("first", "平民赛鸽网提供", "#000000"), 
							new WxMpTemplateData("keyword1", masterText, "#000000"),
							new WxMpTemplateData("keyword2", "", "#000000"), 
							new WxMpTemplateData("keyword3", pigowner, "#000000"), 
							new WxMpTemplateData("keyword4", "最终成绩以公布为准", "#000000"),
							new WxMpTemplateData("keyword5", speed + ",名次" + rank, "#000000"), 
							new WxMpTemplateData("remark", "足环号" + ringnum + ",归巢时间" + cometime, "#000000"));
					// 放进模板对象。准备发送
					wxMpTemplateMessage.setData(list);

					// 发送模板
					try {
						wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
					} catch (WxErrorException e) {
						
					}
					
//					TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();
//					// 根据具体模板参数组装
//					params.put("first", WechatTemplateMsg.item("平民赛鸽网提供", "#000000"));
//					params.put("keyword1", WechatTemplateMsg.item(pmsgwGameDetail.getMasterText(), "#000000"));
//					params.put("keyword2", WechatTemplateMsg.item("", "#000000"));
//					params.put("keyword3", WechatTemplateMsg.item(pmsgwGameDetail.getPigowner(), "#000000"));
//					params.put("keyword4", WechatTemplateMsg.item("最终成绩以公布为准", "#000000"));
//					if (pmsgwGameDetail.getRank() > 0)
//						params.put("keyword5", WechatTemplateMsg.item(pmsgwGameDetail.getSpeed() + ",名次" + pmsgwGameDetail.getRank(), "#000000"));
//					else {
//						params.put("keyword5", WechatTemplateMsg.item(pmsgwGameDetail.getSpeed(), "#000000"));
//					}
//					params.put("remark", WechatTemplateMsg.item("足环号" + pmsgwGameDetail.getRingnum() + ",归巢时间" + pmsgwGameDetail.getCometime(), "#000000"));

//					WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();
//					wechatTemplateMsg.setTemplate_id(gameTemplateId);
//					wechatTemplateMsg.setTouser(openid);
//					wechatTemplateMsg.setUrl(url);
//					wechatTemplateMsg.setData(params);

					/*boolean flag= pmsgwGameWhiteList.contains(pmsgwGameDetail.getMasterText());
					if(flag)
					{  log.info(pmsgwGameWhiteList+"匹配上"+pmsgwGameDetail.getMasterText());
					   WechatTemplateMsgManager.sendCustomerMessage(appId, appSecret, wechatTemplateMsg);
					}*/
					
					PmsgwPigownerGameDetail pmsgwPigownerGameDetail=pmsgwPigownerGameDetailService.convert(pmsgwGameDetail, openid, name);
					pmsgwPigownerGameDetailService.insert(pmsgwPigownerGameDetail);
					
					//WechatTemplateMsgManager.sendCustomerMessage(appId, appSecret, wechatTemplateMsg);

					WeixinMessage weixinMessage = new WeixinMessage();
					weixinMessage.setMessageId(pmsgwGameDetail.getMasterId().toString());
					weixinMessage.setSenderId(gameTemplateId);
					weixinMessage.setSenderName(gameTemplateId);
					weixinMessage.setSendTime(new Date());
					weixinMessage.setReceiverId(openid);
					weixinMessage.setReceiverName(pmsgwGameDetail.getPigowner());
					weixinMessage.setMessageTextId(pmsgwGameDetail.getId().toString());
					weixinMessage.setMessageType(gameTemplateId);
					weixinMessage.setMessageTitle(pmsgwGameDetail.getMasterText());
					weixinMessage.setMessageText(JSON.toJSONString(wxMpTemplateMessage));
					weixinMessage.setStatus(1);
					weixinMessage.setCreatedDatetime(new Date());
					weixinMessage.setModifyDatetime(new Date());
					weixinUserService.insertMessage(weixinMessage);

					pmsgwGameDetail.setFlag("1");
					pmsgwGameDetailService.update(pmsgwGameDetail);
				}
			}
		}
	}
}
