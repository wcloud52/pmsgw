package com.ifp.weixin.entity.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifp.weixin.util.StringUtil;
import com.ifp.weixin.util.WeixinUtil;

/**
 * 微信通用接口工具类
 * 
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class IpListManager {

	public static Logger log = LoggerFactory.getLogger(IpListManager.class);

	public final static String GETCALLBACKIP = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";

	public static List<String> getIpList(String appid, String appsecret) {

		List<String> list = new ArrayList<String>();
		String token = AcessTokenManager.getToken(appid, appsecret);
		String requestUrl = GETCALLBACKIP.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.get("errcode")) && jsonObject.get("errcode") != "0") {
				log.error("获取微信服务器IP地址失败失败，errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
			} else {
				JSONArray arr = jsonObject.getJSONArray("ip_list");
				for (int i = 0; i < arr.size(); i++) {
					String group = arr.get(i).toString();
					list.add(group);
				}
			}
		}
		return list;
	}
	public static void main(String[] args) {
		/*ImageMessage textMessage = new ImageMessage();
		textMessage.setToUserName("fromUserName");
		textMessage.setFromUserName("toUserName");
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(ConstantWeixin.RESP_MESSAGE_TYPE_IMAGE);
		textMessage.setFuncFlag(0);
		textMessage.setImage(new Image("keyWord"));
		String respMessage = MessageUtil.imageMessageToXml(textMessage);
		System.out.println(respMessage);*/
		List<String> list=IpListManager.getIpList("wx0ea76454d44e1dbf","4d0fc7b7c28ab0e9b4dbdc0dde5dcd05");
		System.out.println(list.get(0));
	}
}