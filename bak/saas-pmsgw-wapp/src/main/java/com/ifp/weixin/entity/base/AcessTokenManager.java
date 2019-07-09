package com.ifp.weixin.entity.base;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.ifp.weixin.util.WeixinUtil;


/**
 * 微信通用接口工具类
 * 
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class AcessTokenManager {

	public static Logger log = LoggerFactory.getLogger(AcessTokenManager.class);

	/**
	 * 获取access_token的接口地址（GET） 限200（次/天）
	 */
	public final static String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取access_token对象
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return AccessToken对象
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
	public static String getToken(String appid, String appsecret) {
		AccessToken at = getAccessToken( appid,  appsecret);
		if (null != at) {
			return at.getToken();
		} else {
			return null;
		}
	}
}