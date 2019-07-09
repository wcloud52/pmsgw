package com.ifp.weixin.entity.jsapi;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifp.weixin.entity.base.AcessTokenManager;
import com.ifp.weixin.util.WeixinUtil;


/**
 * 微信通用接口工具类
 * 
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class JsApiManager {

	public static Logger log = LoggerFactory.getLogger(JsApiManager.class);

	/**
	 * 有效期7200秒，开发者必须在自己的服务全局缓存jsapi_ticket
	 */
	public final static String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	/**
	 * 获取ticket
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return AccessToken对象
	 */
	public static String getTicket(String appid, String appsecret,String token) {
		String requestUrl = JSAPI_TICKET.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "GET", null);
		
		String ticket="";
		// 如果请求成功
		if (null != jsonObject) {
			try {

				ticket=jsonObject.getString("ticket");
				
			} catch (JSONException e) {
				ticket = null;
				// 获取token失败
				log.error("获取ticket失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return ticket;
	}
	public static String getTicket(String appid, String appsecret) {
		String token=AcessTokenManager.getToken(appid, appsecret);
		return getTicket(appid, appsecret,token);
	}
	public static Map<String, String> sign(String appid, String appsecret, String url) {
		String jsapi_ticket=getTicket(appid,appsecret);
		return Sign.sign(jsapi_ticket,url);
	}
	public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str ="Wm3WZYTPz0wzccnW";// create_nonce_str();
        String timestamp = "1414587457";//create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }
	 private static String byteToHex(final byte[] hash) {
	        Formatter formatter = new Formatter();
	        for (byte b : hash)
	        {
	            formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
	        return result;
	    }

	    private static String create_nonce_str() {
	        return UUID.randomUUID().toString();
	    }

	    private static String create_timestamp() {
	        return Long.toString(System.currentTimeMillis() / 1000);
	    }
}