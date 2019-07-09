package com.ifp.weixin.entity.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifp.weixin.constant.ConstantWeixin;
import com.ifp.weixin.util.StringUtil;
import com.ifp.weixin.util.WeixinUtil;

/**
 * 检验授权凭证
 * 
 */
public class Oauth2Manager {

	public static Logger log = LoggerFactory.getLogger(Oauth2Manager.class);

	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

	/**
	 * weixin oauth url
	 */
	public static String OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#weixin_redirect";

	public final static String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	public static Oauth2AccessToken getAccessToken(String appid, String appsecret, String code) {
		Oauth2AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new Oauth2AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				accessToken.setOpenid(jsonObject.getString("openid"));
				//accessToken.setRefreshToken(jsonObject.getString("refresh_token"));
				//accessToken.setScope(jsonObject.getString("scope"));
				//accessToken.setUnionid(jsonObject.getString("unionid"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				//log.error("获取token失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
	public static String getToken(String appid, String appsecret, String code) {
		Oauth2AccessToken at = getAccessToken( appid,  appsecret,code);
		if (null != at) {
			return at.getToken();
		} else {
			return null;
		}
	}
	public static Oauth2AccessToken refreshAccessToken(String appid, String token) {
		Oauth2AccessToken accessToken = null;

		String requestUrl = REFRESH_TOKEN_URL.replace("APPID", appid).replace("REFRESH_TOKEN", token);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new Oauth2AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				accessToken.setOpenid(jsonObject.getString("openid"));
				accessToken.setRefreshToken(jsonObject.getString("refresh_token"));
				accessToken.setScope(jsonObject.getString("scope"));

			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 获得Oauth认证的URL
	 * 
	 * @param redirectUrl
	 *            跳转的url
	 * @param charset
	 *            字符集格式
	 * @param scope
	 *            OAUTH scope
	 * @return oauth url
	 */
	public static String getOauthUrl(String redirectUrl, String charset, String scope) {
		String url = "";
		try {
			url = OAUTH_URL.replace("APPID", "wx0ea76454d44e1dbf").replace("REDIRECT_URI", URLEncoder.encode(redirectUrl, charset)).replace("SCOPE", scope);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public static UserWeiXin getUserInfo(String appid, String appsecret, String code, String openid) {
		String token = getToken(appid, appsecret,code);
		UserWeiXin user = null;
		if (token != null) {
			String url = USERINFO_URL.replace("ACCESS_TOKEN", token).replace("OPENID", openid);
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "GET", null);

			if (null != jsonObject) {
				if (StringUtil.isNotEmpty(jsonObject.get("errcode")) && jsonObject.get("errcode") != "0") {
					log.error("获取用户信息失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
				} else {
					user = new UserWeiXin();
				
					user.setOpenid(jsonObject.getString("openid"));
					user.setNickname(jsonObject.getString("nickname"));
					user.setSex(jsonObject.getInt("sex"));
					user.setCity(jsonObject.getString("city"));
					user.setCountry(jsonObject.getString("country"));
					user.setProvince(jsonObject.getString("province"));
					user.setHeadimgurl(jsonObject.getString("headimgurl"));
				}
			}

		}
		return user;
	}
}