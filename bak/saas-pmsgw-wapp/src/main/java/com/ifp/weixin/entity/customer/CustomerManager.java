package com.ifp.weixin.entity.customer;

import java.util.ArrayList;
import java.util.List;

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
public class CustomerManager {

	public static Logger log = LoggerFactory.getLogger(CustomerManager.class);

	public final static String KFACCOUNT_ADD = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";

	private static String CUSTOM_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	private static String GETKFLIST_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";

	public static void addKfAccount(String appid, String appsecret, KfAccount kf) {
		String token = AcessTokenManager.getToken(appid, appsecret);
		if (token != null) {

			String url = KFACCOUNT_ADD.replace("ACCESS_TOKEN", token);
			String json = JSONObject.fromObject(kf).toString();
			// 调用接口创建菜单
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", json);

			if (null != jsonObject) {
				if (0 != jsonObject.getInt("errcode")) {
					String message = " errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg");
					log.error(message);

				}
			}
		}
	}

	public static boolean sendCustomerMessage(String appid, String appsecret, CustomerBaseMessage obj) {
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
	public static String getkflist(String appid, String appsecret) {

		String result = null;
		String token = AcessTokenManager.getToken(appid, appsecret);
		
		String requestUrl = GETKFLIST_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.get("errcode")) && jsonObject.get("errcode") != "0") {
				log.error("获取微信服务器IP地址失败失败，errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
			} else {
				result = jsonObject.toString();
				
			}
		}
		System.out.println(result);
		return result;
	}
	public static void main(String[] args) {
		/*KfAccount KfAccount = new KfAccount();
		KfAccount.setKf_account("gh@gh_192accce6ffa");
		KfAccount.setNickname("客服1");
		KfAccount.setPassword("96e79218965eb72c92a549dd5a330112");
		CustomerManager.addKfAccount("wx0ea76454d44e1dbf", "4d0fc7b7c28ab0e9b4dbdc0dde5dcd05", KfAccount);*/
		
		NewsMessage cust=new NewsMessage();
		News news=new News();
		Article article0=new Article();
		article0.setTitle("2018比赛");
		article0.setDescription("【山东淄博市信鸽协会】 2018比赛  (2018-01-04)");
		article0.setPicUrl("http://mmbiz.qpic.cn/mmbiz_png/wbQTpNN0RI5MGX9AZjZdnyU9ep2RycL4SEBOYrxy8DH19HWJjOycy7eSWB64avjsicOApzsvUzw0c7DZUXhvnQg/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1");
		article0.setUrl("http://speed.pigeoncn.com/speed/imfor_15026_1801044215026.html");
		
		Article article1=new Article();
		article1.setTitle("2017冬西线500公里第二站");
		article1.setDescription("【福建云霄闽翔赛鸽俱乐部】 2017冬西线500公里第二站(2018-01-04)");
		article1.setPicUrl("http://mmbiz.qpic.cn/mmbiz_png/wbQTpNN0RI5MGX9AZjZdnyU9ep2RycL4SEBOYrxy8DH19HWJjOycy7eSWB64avjsicOApzsvUzw0c7DZUXhvnQg/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1");
		article1.setUrl("http://speed.pigeoncn.com/speed/imfor_15026_1801044215026.html");
		
		Article article2=new Article();
		article2.setTitle("2017冬西线500公里第二站");
		article2.setDescription("【福建云霄闽翔赛鸽俱乐部】 2017冬西线500公里第二站 (2018-01-04)");
		article2.setPicUrl("http://mmbiz.qpic.cn/mmbiz_png/wbQTpNN0RI5MGX9AZjZdnyU9ep2RycL4SEBOYrxy8DH19HWJjOycy7eSWB64avjsicOApzsvUzw0c7DZUXhvnQg/640?wx_fmt=gif&tp=webp&wxfrom=5&wx_lazy=1");
		article2.setUrl("http://speed.pigeoncn.com/speed/imfor_15026_1801044215026.html");
		
		
		List<Article> articles=new ArrayList<Article>();
		//articles.add(article0);
		articles.add(article1);
		articles.add(article2);
		news.setArticles(articles);
		cust.setNews(news);
		cust.setMsgtype("news");
		cust.setTouser("ocSsDwjRXUH_Kn2hCZQN47z6YpnM");
		cust.setNews(news);
		CustomerManager.sendCustomerMessage("wx6fe226626254fab1", "5cf0ee38d874bfd860e75defa6def730", cust);
		
		//CustomerManager.getkflist("wx0ea76454d44e1dbf", "4d0fc7b7c28ab0e9b4dbdc0dde5dcd05");
		
	}
}