package com.saas.task;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCustomerMessage;
import com.saas.biz.pojo.NodejsMobileUser;
import com.saas.biz.service.NodejsCustomerMessageService;
import com.saas.biz.service.NodejsMobileUserService;

@Component
public class SendMobileMessageAsyncTask {

	private static final Logger log = LoggerFactory.getLogger(SendMobileMessageAsyncTask.class);
	@Autowired
	private NodejsMobileUserService nodejsMobileUserService;
	@Autowired
	private NodejsCustomerMessageService nodejsCustomerMessageService;


	private CustomerUser getCustomerUser(List<NodejsMobileUser> userList, String name,String coteId) {
		CustomerUser rt = null;
		for (NodejsMobileUser user : userList) {
			//excel上传维护
			String excelBindName = user.getPigowner();
			String excelBindCoteId=user.getCote_id ();
			String excelShortBindName=user.getCote_name();
			String excelMobile=user.getMobile();
			String state=user.getState();
			if(
					state.equals("1") &&
					coteId.equals(excelBindCoteId)	&&
			(
					name.equals(excelBindName)||(name.length()>=9&&excelBindName.length()>=9&&name.substring(0,8).equals(excelBindName.substring(0,8)))
			)
			)
			{

				rt=new CustomerUser(excelBindName,excelMobile,excelShortBindName,excelBindCoteId);
			}
		}
		return rt;
	}
	@Async
	public void sendResultMessage(List<NodejsCrawlerDetailGame> pmsgwGameDetailList) throws JSONException, HTTPException, IOException {

		List<NodejsMobileUser> userList = nodejsMobileUserService.selectAll();
		for (NodejsCrawlerDetailGame pmsgwGameDetail : pmsgwGameDetailList) {
			if (pmsgwGameDetail != null)
			{

				String name = pmsgwGameDetail.getPigowner();
				String coteId=pmsgwGameDetail.getCote_id();


				CustomerUser customerUser=getCustomerUser(userList,name,coteId);
				if (customerUser != null) 
				{
					sendResultMessage(pmsgwGameDetail, customerUser);				
				}
			}
		}
	}
	public SmsSingleSenderResult sendSms(String mobile,String masterText,String cote_name,String short_cote_name,Integer rank,String ringnum,String cometime) throws JSONException, HTTPException, IOException
	{
		// 短信应用SDK AppID
		int appid = 1400077437; // 1400开头

		// 短信应用SDK AppKey
		String appkey = "1b1f77227f0b396c70b0d0aec6a45486";
		int templateId = 240194; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
		// templateId7839对应的内容是"您的验证码是: {1}"
		// 签名
		String smsSign = "大众赛鸽";
		// 环号{1}获得第{2}名,({3})归巢时间{4}
		ringnum=StringUtils.removeStart(ringnum,"20");
		cote_name=short_cote_name;
		masterText=masterText+"<"+cote_name+">";
		
		String suffix = cometime.substring(cometime.lastIndexOf(".") );
		cometime=cometime.replace(suffix, "");
		
		String[] params = { ringnum, rank.toString(),masterText,cometime };// 数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
		SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		
		SmsSingleSenderResult result = ssender.sendWithParam("86",mobile, templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
		return result;
	}
	public void sendResultMessage(NodejsCrawlerDetailGame pmsgwGameDetail, CustomerUser customerUser) throws JSONException, HTTPException, IOException {

		String url = pmsgwGameDetail.getMaster_href();
		String masterText = pmsgwGameDetail.getMaster_text();
		String pigowner = pmsgwGameDetail.getPigowner();
		String speed = pmsgwGameDetail.getSpeed();
		Integer rank = pmsgwGameDetail.getRank();
		String ringnum = pmsgwGameDetail.getRingnum();
		String cometime = pmsgwGameDetail.getCometime();
		String cote_state = pmsgwGameDetail.getCote_state();
		String cote_id = pmsgwGameDetail.getCote_id();
		String cote_name = pmsgwGameDetail.getCote_name();
        String openid=customerUser.getGame_mobile();
        String mobile=customerUser.getGame_mobile();
        String bindName=customerUser.getGame_pigowner();
        String nickname=customerUser.getGame_pigowner();

		String excelCoteId=customerUser.game_cote_id;
		String excelShortName=customerUser.game_short_cote_name;

        String headimgurl="";

		NodejsCustomerMessage weixinMessage = nodejsCustomerMessageService.convert(pmsgwGameDetail);
		String message_id =weixinMessage.getGame_detail_id()+"_"+openid; //UUID.randomUUID().toString();
		weixinMessage.setMessage_id(message_id);
		weixinMessage.setMessage_senderId(openid);
		weixinMessage.setMessage_senderName(openid);
		weixinMessage.setMessage_sendTime(new Date());

		weixinMessage.setMessage_receiverId(openid);
		weixinMessage.setMessage_receiverName(bindName);

		weixinMessage.setMessage_type("mobile");
		weixinMessage.setMessage_title(masterText);
		
		weixinMessage.setMessage_text("环号{1}获得第{2}名,({3})归巢时间{4}");

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
			boolean flag = StringUtils.equals(cote_id, excelCoteId);
			if (flag) {
				SmsSingleSenderResult smsRt=sendSms( mobile, masterText, cote_name,excelShortName,  rank, ringnum, cometime);
				message_status = 1;// 发送成功
				nodejsCustomerMessageService.changeStatus(message_id, message_status,JSON.toJSONString(smsRt));
			} 		
			else {
				nodejsCustomerMessageService.changeStatus(message_id, -1,"当前为不可以发送公棚");
			}			
		}
	}
	
	class CustomerUser {
	   
	    private String game_pigowner;

	    private String game_mobile;
		private String game_cote_id;

		private String game_short_cote_name;

	  
		public CustomerUser(String game_pigowner, String game_mobile,String game_short_cote_name,String game_cote_id) {
			super();
			this.game_pigowner = game_pigowner;
			this.setGame_mobile(game_mobile);
			this.game_short_cote_name=game_short_cote_name;
			this.game_cote_id=game_cote_id;
		}

		public String getGame_pigowner() {
			return game_pigowner;
		}

		public void setGame_pigowner(String game_pigowner) {
			this.game_pigowner = game_pigowner;
		}

		public String getGame_mobile() {
			return game_mobile;
		}

		public void setGame_mobile(String game_mobile) {
			this.game_mobile = game_mobile;
		}

		public String getGame_short_cote_name() {
			return game_short_cote_name;
		}

		public void setGame_short_cote_name(String game_short_cote_name) {
			this.game_short_cote_name = game_short_cote_name;
		}

		public String getGame_cote_id() {
			return game_cote_id;
		}

		public void setGame_cote_id(String game_cote_id) {
			this.game_cote_id = game_cote_id;
		}
	}
}
