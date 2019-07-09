package com.ifp.weixin.entity.user;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ifp.weixin.entity.base.AcessTokenManager;
import com.ifp.weixin.entity.user.UserGroup;
import com.ifp.weixin.util.StringUtil;
import com.ifp.weixin.util.WeixinUtil;

/**
 * 分组管理
 * 
 */
public class UserGroupManager {

	public static Logger log = LoggerFactory.getLogger(UserGroupManager.class);

	/**
	 * 创建分组URL
	 */
	private static String CREATE_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";

	/**
	 * 查询所有分组
	 */
	private static String GET_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";

	/**
	 * 查询用户所在分组
	 */
	private static String GET_USER_GOUP = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";

	/**
	 * 修改分组名
	 */

	private static String UPDATE_GROUP_NAME = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";

	/**
	 * 移动用户分组
	 */

	private static String MOVE_USER_TO_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";

	/**
	 * 创建分组
	 * 
	 * @param groupName
	 *            分组名称
	 * @return 分组ID
	 */
	public static String createGroup(String appid, String appsecret,String groupName) {

		String id = null;
		String token = AcessTokenManager.getToken(appid, appsecret);
		String url = CREATE_GROUP_URL.replace("ACCESS_TOKEN", token);

		String data = "{\"group\":{\"name\":\"" + groupName + "\"}}";

		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", data);

		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.get("errcode")) && jsonObject.get("errcode") != "0") {
				log.error("创建分组失败，errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
			} else {
				id = jsonObject.getJSONObject("group").getString("id");
			}
		}
		return id;
	}

	/**
	 * 查询所有分组
	 * 
	 * @return 分组列表
	 */
	public static List<UserGroup> getGroup(String appid, String appsecret) {

		List<UserGroup> list = new ArrayList<UserGroup>();
		String token = AcessTokenManager.getToken(appid, appsecret);
		String url = GET_GROUP_URL.replace("ACCESS_TOKEN", token);

		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", null);
		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.get("errcode")) && jsonObject.get("errcode") != "0") {
				log.error("获取分组失败，errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
			} else {
				JSONArray arr = jsonObject.getJSONArray("groups");
				for (int i = 0; i < arr.size(); i++) {
					UserGroup group = new UserGroup();
					group.setId(arr.getJSONObject(i).getString("id"));
					group.setName(arr.getJSONObject(i).getString("name"));
					group.setCount(arr.getJSONObject(i).getInt("count"));
					list.add(group);
				}
			}
		}
		return list;
	}

	/**
	 * 查询用户所在分组
	 * 
	 * @param openid
	 *            用户openid
	 * @return 分组id
	 */
	public static String getGroupByOpenid(String appid, String appsecret,String openid) {

		String groupid = null;
		String token = AcessTokenManager.getToken(appid, appsecret);
		String url = GET_USER_GOUP.replace("ACCESS_TOKEN", token);

		String data = "{\"openid\":\"" + openid + "\"}";
		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", data);

		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.get("errcode")) && jsonObject.get("errcode") != "0") {
				log.error("查询用户所在分组失败，errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg"));
			} else {
				groupid = jsonObject.getString("groupid");
			}
		}
		return groupid;
	}

	/**
	 * 修改分组名称
	 * 
	 * @param id
	 *            分组id
	 * @param name
	 *            分组name
	 * @return 是否成功
	 */
	public static boolean updateGroupName(String appid, String appsecret,int id, String name) {

		boolean bo = false;
		String token = AcessTokenManager.getToken(appid, appsecret);
		String url = UPDATE_GROUP_NAME.replace("ACCESS_TOKEN", token);

		String data = "{\"group\":{\"id\":" + id + ",\"name\":\"" + name + "\"}}";

		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", data);

		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.getString("errcode")) && jsonObject.getString("errcode").equals("0")) {
				bo = true;
			}
		}
		return bo;
	}

	/**
	 * 移动用户分组
	 * 
	 * @param openid
	 *            用户openid
	 * @param groupId
	 *            分组id
	 * @return 是否成功
	 */
	public static boolean moveUserToGroup(String appid, String appsecret,String openid, int groupId) {

		boolean bo = false;
		String token = AcessTokenManager.getToken(appid, appsecret);
		String url = MOVE_USER_TO_GROUP.replace("ACCESS_TOKEN", token);

		String data = "{\"openid\":\"" + openid + "\",\"to_groupid\":" + groupId + "}";

		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", data);

		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.getString("errcode")) && jsonObject.getString("errcode").equals("0")) {
				bo = true;
			}
		}
		return bo;
	}
}
