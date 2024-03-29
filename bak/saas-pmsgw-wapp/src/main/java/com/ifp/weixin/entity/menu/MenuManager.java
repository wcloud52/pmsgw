package com.ifp.weixin.entity.menu;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifp.weixin.entity.base.AcessTokenManager;
import com.ifp.weixin.entity.menu.Button;
import com.ifp.weixin.entity.menu.Menu;
import com.ifp.weixin.util.WeixinUtil;

/**
 * 菜单创建
 * 
 * @author caspar.chen
 * @version 1.1
 * 
 */
public class MenuManager {

	public static Logger log = LoggerFactory.getLogger(MenuManager.class);

	/**
	 * 菜单创建（POST） 限100（次/天）
	 */
	public static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 菜单查询
	 */
	public static String MENU_GET = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	public static String MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/**
	 * 创建菜单
	 * 
	 * @param jsonMenu
	 *            json格式
	 * @return 状态 0 表示成功、其他表示失败
	 */
	public static Integer createMenu(String appid, String appsecret,String jsonMenu) {
		int result = 0;
		String token = AcessTokenManager.getToken(appid, appsecret);
		if (token != null) {
			// 拼装创建菜单的url
			String url = MENU_CREATE.replace("ACCESS_TOKEN", token);
			// 调用接口创建菜单
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", jsonMenu);

			if (null != jsonObject) {
				if (0 != jsonObject.getInt("errcode")) {
					result = jsonObject.getInt("errcode");
					String message = "创建菜单失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg");
					log.error(message);
					throw new RuntimeException(message);
				}
			}
		}
		return result;
	}

	public static Integer deleteMenu(String appid, String appsecret) {
		int result = 0;
		String token = AcessTokenManager.getToken(appid, appsecret);
		if (token != null) {
			// 拼装创建菜单的url
			String url = MENU_DELETE.replace("ACCESS_TOKEN", token);
			// 调用接口创建菜单
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "GET", null);

			if (null != jsonObject) {
				if (0 != jsonObject.getInt("errcode")) {
					result = jsonObject.getInt("errcode");
					String message = "创建菜单失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:" + jsonObject.getString("errmsg");
					log.error(message);
					throw new RuntimeException(message);
				}
			}
		}
		return result;
	}
	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @return 0表示成功，其他值表示失败
	 */
	public static Integer createMenu(String appid, String appsecret,Menu menu) {
		return createMenu(appid,appsecret,JSONObject.fromObject(menu).toString());
	}

	/**
	 * 查询菜单
	 * 
	 * @return 菜单结构json字符串
	 */
	public static JSONObject getMenuJson(String appid, String appsecret) {
		JSONObject result = null;
		String token = AcessTokenManager.getToken(appid, appsecret);
		if (token != null) {
			String url = MENU_GET.replace("ACCESS_TOKEN", token);
			result = WeixinUtil.httpsRequest(url, "GET", null);
		}
		return result;
	}

	/**
	 * 查询菜单
	 * 
	 * @return Menu 菜单对象
	 */
	public static Menu getMenu(String appid, String appsecret) {
		JSONObject json = getMenuJson(appid, appsecret).getJSONObject("menu");
		System.out.println(json);
		Menu menu = (Menu) JSONObject.toBean(json, Menu.class);
		return menu;
	}

	public static void main(String[] args) {
		// getMenu();
		Button sb2 = new Button("微客服", "click", "wchat_CustomerService_01", null, null);
		Button btn1 = new Button("微服务", "click", null, null, new Button[] { sb2 });

		Button sb3 = new Button("公司简介", "click", "23", null, null);
		Button sb4 = new Button("有问必答", "click", "45", null, null);

		Button btn2 = new Button("音智达", "click", null, null, new Button[] { sb3, sb4 });
        //wx6fe226626254fab1   5cf0ee38d874bfd860e75defa6def730
		Button sb6 = new Button("view类型", "view", null, 
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6fe226626254fab1&redirect_uri=http://www.pmsgw.com/weixinController/oauth/wx6fe226626254fab1/5cf0ee38d874bfd860e75defa6def730&response_type=code&scope=snsapi_base&state=1#wechat_redirect", null);

		Button btn3 = new Button("最新动态", "click", null, null, new Button[] { sb6 });

		Button my = new Button("用户绑定", "view",  null, "http://weixin.pmsgw.com/weixinuser/redirect", null);

		Menu menu = new Menu(new Button[] { my });
		// createMenu(menu);
		MenuManager.createMenu("wx6fe226626254fab1", "5cf0ee38d874bfd860e75defa6def730", menu);
		System.out.println(JSONObject.fromObject(menu).toString());
	}
}
