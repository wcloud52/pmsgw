package com.ifp.weixin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifp.weixin.entity.base.AccessToken;
import com.ifp.weixin.entity.base.AcessTokenManager;


/**
 * 微信通用接口工具类
 * 
 */
public class AcessTokenService {

	public static Logger log = LoggerFactory.getLogger(AcessTokenService.class);

	/**
	 * 获取access_token对象
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return AccessToken对象
	 */
	public static AccessToken  getAccessToken(String appid, String appsecret) {
		return AcessTokenManager.getAccessToken(appid, appsecret);
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