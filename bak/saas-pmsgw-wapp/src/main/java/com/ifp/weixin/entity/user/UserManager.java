package com.ifp.weixin.entity.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ifp.weixin.entity.user.UserWeiXin;
import com.ifp.weixin.util.DateFormart;
import com.ifp.weixin.util.StringUtil;
import com.ifp.weixin.util.WeixinUtil;

/**
 * 用户管理
 * 
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class UserManager {

	public static Logger log = LoggerFactory.getLogger(UserManager.class);

	public static String UPDATEREMARK_URL = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";

	/**
	 * 批量获取用户详细信息
	 */
	public static String USER_INFO_BATCHGET_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";

	/**
	 * 获取用户详细信息
	 */
	public static String USER_INFO_UNIONID_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 获取用户openid列表
	 */
	public static String USER_OPENID_LIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

	public static void updateremark(String appid, String appsecret, String openid, String token, String remark) {

		// String token = AcessTokenManager.getToken(appid, appsecret);
		String requestUrl = UPDATEREMARK_URL.replace("ACCESS_TOKEN", token);
		String data = "{\"openid\":\"" + openid + "\",\"remark\":\"" + remark + "\"}";
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST", data);
		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.get("errcode")) && jsonObject.get("errcode") != "0") {
				log.error("获取微信服务器IP地址失败失败，errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}
	}

	public static UserWeiXin getUserInfo(String appid, String appsecret, String token, String openid) {

		UserWeiXin user = null;
		if (token != null) {
			String url = USER_INFO_UNIONID_URL.replace("ACCESS_TOKEN", token).replace("OPENID", openid);
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "GET", null);

			if (null != jsonObject) {
				log.info(jsonObject.toString());
				if (StringUtil.isNotEmpty(jsonObject.get("errcode")) && jsonObject.get("errcode") != "0") {
					log.error("获取用户信息失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
				} else {
					user = new UserWeiXin();
					user = (UserWeiXin)JSONObject.toBean(jsonObject, UserWeiXin.class);
					 
					/*user.setSubscribe(jsonObject.getInt("subscribe"));
					user.setOpenid(jsonObject.getString("openid"));
					user.setNickname(jsonObject.getString("nickname"));
					user.setSex(jsonObject.getInt("sex"));
					user.setCity(jsonObject.getString("city"));
					user.setCountry(jsonObject.getString("country"));
					user.setProvince(jsonObject.getString("province"));
					user.setLanguage(jsonObject.getString("language"));
					user.setHeadimgurl(jsonObject.getString("headimgurl"));

					long subscibeTime = jsonObject.getLong("subscribe_time");
					Date st = DateFormart.paserYYYY_MM_DD_HHMMSSToDate(subscibeTime);
					user.setSubscribe_time(st);*/
				}
			}

		}
		return user;
	}

	public static List<UserWeiXin> getBatchUserInfo(String appid, String appsecret, String token, List<String> openidList) {

		List<UserWeiXin> users = new ArrayList<UserWeiXin>();
		if (token != null) {
			String url = USER_INFO_BATCHGET_URL.replace("ACCESS_TOKEN", token);
			List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
			for (String openid : openidList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("openid", openid);
				map.put("lang", "zh_CN");

				listMap.add(map);
			}
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("user_list", listMap);
			//log.info(JSONObject.fromObject(retMap).toString());
			JSONObject retJsonObject = WeixinUtil.httpsRequest(url, "POST", JSONObject.fromObject(retMap).toString());

			if (null != retJsonObject) {
				if (StringUtil.isNotEmpty(retJsonObject.get("errcode")) && retJsonObject.get("errcode") != "0") {
					log.error("获取用户信息失败 errcode:" + retJsonObject.getInt("errcode") + "，errmsg:" + retJsonObject.getString("errmsg"));
				} else {
					JSONArray user_info_list = retJsonObject.getJSONArray("user_info_list");
					if (user_info_list.size() > 0) {
						for (int i = 0; i < user_info_list.size(); i++) {
							
							JSONObject jsonObject = user_info_list.getJSONObject(i);
							UserWeiXin user = new UserWeiXin();
							user.setSubscribe(jsonObject.getInt("subscribe"));
							user.setOpenid(jsonObject.getString("openid"));
							user.setNickname(jsonObject.getString("nickname"));
							user.setSex(jsonObject.getInt("sex"));
							user.setCity(jsonObject.getString("city"));
							user.setCountry(jsonObject.getString("country"));
							user.setProvince(jsonObject.getString("province"));
							user.setLanguage(jsonObject.getString("language"));
							user.setHeadimgurl(jsonObject.getString("headimgurl"));

							long subscibeTime = jsonObject.getLong("subscribe_time");
							Date st = DateFormart.paserYYYY_MM_DD_HHMMSSToDate(subscibeTime);
							user.setSubscribe_time(st);
							
							users.add(user);
						}
					}

				}
			}

		}
		return users;
	}

	/**
	 * 获取关注者OpenID列表
	 * 
	 * @return List<String> 关注者openID列表
	 */
	public static List<String> getUserOpenIdList(String appid, String appsecret, String token) {
		// String token = AcessTokenManager.getToken(appid, appsecret);
		List<String> list = null;
		if (token != null) {
			String url = USER_OPENID_LIST_URL.replace("ACCESS_TOKEN", token).replace("NEXT_OPENID", "");

			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "GET", null);
			log.info(jsonObject.toString());
			if (null != jsonObject) {
				if (StringUtil.isNotEmpty(jsonObject.get("errcode")) && jsonObject.get("errcode") != "0") {
					log.error("获取关注用户列表失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
				} else {
					list = new ArrayList<String>();
					JSONObject data = jsonObject.getJSONObject("data");
					String openidStr = data.getString("openid");
					openidStr = openidStr.substring(1, openidStr.length() - 1);
					openidStr = openidStr.replace("\"", "");
					String openidArr[] = openidStr.split(",");
					for (int i = 0; i < openidArr.length; i++) {
						list.add(openidArr[i]);
					}
				}
			}

		}
		return list;
	}

	public static void main(String[] args) {

		/*String appid = "wx6fe226626254fab1";
		String appsecret = "5cf0ee38d874bfd860e75defa6def730";
		String token = WeixinUtil.getAccessToken(appid, appsecret).getToken();
		List<String> list = UserManager.getUserOpenIdList(appid, appsecret, token);
		System.out.println(list.size());
		
		UserWeiXin user= UserManager.getUserInfo(appid, appsecret, token,"ocSsDwlI0Bh3KF2nU2iwhWRSNxYc");
		System.out.println(JSON.toJSONString(user));*/
				 
		 List<String> list1 = new ArrayList<String>();
         List<String> list2 = new ArrayList<String>();
         //给list1赋值
         list1.add("测");
         list1.add("试");
         list1.add("一");
         list1.add("下");
         //给list2赋值
         list2.add("合");
         list2.add("并");
         list2.add("列");
         list2.add("表");
         list2.add("下");
         //将list1.list2合并
         //list1.addAll(list2);
         List<String> listAll = new ArrayList<String>();
         listAll.addAll(list1);
         listAll.addAll(list2);
         listAll = new ArrayList<String>(new LinkedHashSet<>(listAll));
         //循环输出list1 看看结果
         for (String s : listAll) {
             System.out.print(s);
         }
         
		//List<UserWeiXin> listUserWeiXin = UserManager.getBatchUserInfo(appid, appsecret, token,list.subList(0, 100));
		//System.out.println(JSON.toJSONString(listUserWeiXin));
		/*
		 * List<String> list=getUserOpenIdList("wx6fe226626254fab1",
		 * "5cf0ee38d874bfd860e75defa6def730"); System.out.println(list.toString());
		 * for(String openid :list) { UserWeiXin user=getUserInfo("wx6fe226626254fab1",
		 * "5cf0ee38d874bfd860e75defa6def730",openid);
		 * System.out.println(JSON.toJSONString(user)); }
		 */

	}
}
