package com.ifp.weixin.entity.template;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifp.weixin.entity.base.AcessTokenManager;
import com.ifp.weixin.util.StringUtil;
import com.ifp.weixin.util.WeixinUtil;

/**
 * 微信通用接口工具类
 * 
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class WechatTemplateMsgManager {

	public static Logger log = LoggerFactory.getLogger(WechatTemplateMsgManager.class);

	
	private static String CUSTOM_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	public static boolean sendCustomerMessage(String appid, String appsecret, WechatTemplateMsg obj) {
		boolean bo = false;
		String token = AcessTokenManager.getToken(appid, appsecret);
		System.out.println(token);
		if (token != null) {
			String url = CUSTOM_SEND_URL.replace("ACCESS_TOKEN", token);
			JSONObject json = JSONObject.fromObject(obj);
			System.out.println(json);
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", json.toString());
			if (null != jsonObject) {
				if (StringUtil.isNotEmpty(jsonObject.getString("errcode")) && jsonObject.getString("errcode").equals("0")) {
					bo = true;
				}
			}
		}
		return bo;
	}
	
	public static void main(String[] args) {
		TreeMap<String,TreeMap<String,String>> params = new TreeMap<String,TreeMap<String,String>>();  
        //根据具体模板参数组装  
        params.put("first",WechatTemplateMsg.item("您的户外旅行活动订单已经支付完成，可在我的个人中心中查看", "#000000"));  
        params.put("keyword1",WechatTemplateMsg.item("8.1发现尼泊尔—人文与自然的旅行圣地", "#000000"));  
        params.put("keyword2",WechatTemplateMsg.item("5000元", "#000000"));  
        params.put("keyword3",WechatTemplateMsg.item("2017.1.2", "#000000"));  
        params.put("keyword4",WechatTemplateMsg.item("5", "#000000"));  
        params.put("remark",WechatTemplateMsg.item("请届时携带好身份证件准时到达集合地点，若临时退改将产生相应损失，敬请谅解,谢谢！", "#000000"));  
		
        WechatTemplateMsg wechatTemplateMsg = new WechatTemplateMsg();  
        wechatTemplateMsg.setTemplate_id("H0Ke6el1Ri7Ebu8EJ5slKtQNkiWNNTQnZuPKptZiI_Y");    
        wechatTemplateMsg.setTouser("ocSsDwjRXUH_Kn2hCZQN47z6YpnM");  
        //wechatTemplateMsg.setUrl("http://weixin.qq.com/download");  
        wechatTemplateMsg.setData(params);  
        
	
        WechatTemplateMsgManager.sendCustomerMessage("wx6fe226626254fab1", "5cf0ee38d874bfd860e75defa6def730", wechatTemplateMsg);
		
		//CustomerManager.getkflist("wx0ea76454d44e1dbf", "4d0fc7b7c28ab0e9b4dbdc0dde5dcd05");
		
	}
}