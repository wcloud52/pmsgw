package com.saas.task;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;
import com.saas.biz.pojo.NodejsCustomerGameMessage;
import com.saas.biz.pojo.NodejsWeixinUserCote;
import com.saas.biz.properties.WxProperties;
import com.saas.biz.service.NodejsCustomerMessageService;
import com.saas.biz.service.WeixinUserService;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
/**
 * 比赛通知
 * @author tanjun
 *
 */
@Component
public class SendGameMessageAsyncTask {

	private static final Logger log = LoggerFactory.getLogger(SendGameMessageAsyncTask.class);

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private WxProperties info;
	
	@Autowired
	private NodejsCustomerMessageService nodejsCustomerMessageService;

	public void sendGameMessage() {
		List<NodejsCrawlerMasterGame> list=nodejsCustomerMessageService.selectByMasterState("0");
		String gameTemplateId=info.getGameTemplateId();
		for(NodejsCrawlerMasterGame nodejsCrawlerMasterGame:list)
		{
			nodejsCrawlerMasterGame.setMaster_state("1");
			nodejsCustomerMessageService.changeNodejsCrawlerMasterGameStatus(nodejsCrawlerMasterGame);
			sendGameMessage(nodejsCrawlerMasterGame,gameTemplateId);
			
		}
	}
	public void sendGameMessage(List<NodejsCrawlerMasterGame> list) {
		String gameTemplateId=info.getGameTemplateId();
		for(NodejsCrawlerMasterGame nodejsCrawlerMasterGame:list)
		{
			sendGameMessage(nodejsCrawlerMasterGame,gameTemplateId);
		}
	}
	public void sendGameMessage(NodejsCrawlerMasterGame nodejsCrawlerMasterGame, String gameTemplateId) {
		String coteId=nodejsCrawlerMasterGame.getCote_id();
		List<NodejsWeixinUserCote> list=nodejsCustomerMessageService.selectNodejsWeixinUserCoteByCoteId(coteId);
		for(NodejsWeixinUserCote nodejsWeixinUserCote:list)
		{
			sendGameMessage(nodejsCrawlerMasterGame,gameTemplateId,nodejsWeixinUserCote.getOpenid(),nodejsWeixinUserCote.getNickname(),
					nodejsWeixinUserCote.getHeadimgurl());
		}
	}
		public void sendGameMessage(NodejsCrawlerMasterGame pmsgwGameDetail, String gameTemplateId,
				String openid,String nickname,String headimgurl) {

			String url = pmsgwGameDetail.getMaster_href();
			String masterText = pmsgwGameDetail.getMaster_text();
			String detail_crawler_total = pmsgwGameDetail.getDetail_crawler_total();
			String master_date = pmsgwGameDetail.getMaster_date();
			
			String cote_state = pmsgwGameDetail.getCote_state();
		
			// 发送模板
			
			// 实例化模板对象
			WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
			// 设置模板ID
			wxMpTemplateMessage.setTemplateId(gameTemplateId);
			// 设置发送给哪个用户
			wxMpTemplateMessage.setToUser(openid);
			wxMpTemplateMessage.setUrl(url);

			List<WxMpTemplateData> list = Arrays.asList(
					new WxMpTemplateData("first", "平民赛鸽网提供比赛提醒", "#000000"),
					new WxMpTemplateData("keyword1", masterText, "#000000"), 
					new WxMpTemplateData("keyword2", master_date, "#000000"),
					new WxMpTemplateData("keyword3", detail_crawler_total, "#000000"),
                    new WxMpTemplateData("remark", "", "#000000"));
			// 放进模板对象。准备发送
			wxMpTemplateMessage.setData(list);

			NodejsCustomerGameMessage weixinMessage = nodejsCustomerMessageService.convertToNodejsCustomerGameMessage(pmsgwGameDetail);
			String message_id =weixinMessage.getGame_cote_id()+"_"+weixinMessage.getGame_master_id()+"_"+openid; //UUID.randomUUID().toString();
			weixinMessage.setMessage_id(message_id);
			weixinMessage.setMessage_senderId(gameTemplateId);
			weixinMessage.setMessage_senderName(gameTemplateId);
			weixinMessage.setMessage_sendTime(new Date());

			weixinMessage.setMessage_receiverId(openid);
			weixinMessage.setMessage_receiverName(openid);

			weixinMessage.setMessage_type(gameTemplateId);
			weixinMessage.setMessage_title(masterText);
			
			weixinMessage.setMessage_text(JSON.toJSONString(wxMpTemplateMessage));

			weixinMessage.setMessage_status(0);
			
			weixinMessage.setGame_receiver_openid(openid);
			weixinMessage.setGame_receiver_nickname(nickname);
			weixinMessage.setGame_receiver_headimgurl(headimgurl);
			
			weixinMessage.setMessage_create_time(new Date());
			weixinMessage.setMessage_modify_time(new Date());
			int ret = nodejsCustomerMessageService.insertNodejsCustomerGameMessage(weixinMessage);

			int message_status = 0;
			// 已经存在
			if (ret != -1) {
				boolean flag = StringUtils.equals(cote_state, "1");
				if (flag) {
					try {
						wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
						message_status = 1;// 发送成功
						//nodejsCustomerMessageService.changeStatus(message_id, message_status,"");
					} catch (WxErrorException e) {
						log.error(e.getMessage());
						message_status = -1;// 发送失败
						//nodejsCustomerMessageService.changeStatus(message_id, message_status,e.getMessage());
					}
				} 
				
				else {
					//nodejsCustomerMessageService.changeStatus(message_id, -1,"当前为不可以发送公棚");
				}			
			}
	}
	
}
