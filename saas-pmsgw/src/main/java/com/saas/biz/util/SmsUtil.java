package com.saas.biz.util;

import java.io.IOException;
import org.json.JSONException;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

public class SmsUtil {
	public static SmsSingleSenderResult sendSms(String mobile,String pigowner,String ringnum, String cote_Name) throws JSONException, HTTPException, IOException 
	{
		// 短信应用SDK AppID
		int appid = 1400077437; // 1400开头

		// 短信应用SDK AppKey
		String appkey = "1b1f77227f0b396c70b0d0aec6a45486";
		int templateId = 300647; //鸽主{1}，环号{2}。爱鸽已到公棚[{3}]
		
		// 签名
		String smsSign = "大众赛鸽";
		
		
		String[] params = { pigowner,ringnum,cote_Name};// 数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
		SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		
		SmsSingleSenderResult result = ssender.sendWithParam("86",mobile, templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
		return result;
	}
}

