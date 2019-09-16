package com.saas.biz.controller;

import com.alibaba.fastjson.JSON;
import com.saas.biz.config.WxConfiguration;
import com.saas.biz.pojo.*;
import com.saas.biz.service.*;
import com.saas.biz.util.EmojiFilter;
import com.saas.common.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 移动端相关操作
 * 
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/wap3")
public class Wap3Controller {
	private static final Logger loger = LoggerFactory.getLogger(Wap3Controller.class);
	@Autowired
	WxConfiguration wxConfigProvider;
	@Autowired
	private WxMpService wxMpService;
	@RequestMapping("check")
	public void checkSignature(HttpServletResponse response,String signature, String timestamp, String nonce, String echostr){
		wxMpService.setWxMpConfigStorage(wxConfigProvider.wxMpInMemoryConfigStorage());
		boolean b = wxMpService.checkSignature(timestamp, nonce, signature);
		if (b){
			PrintWriter o = null;
			try {
				o = new PrintWriter(response.getWriter());
				o.print(echostr);
			} catch (IOException e) {
			} finally {
				o.close();
			}
		}
	}

	@RequestMapping("menu")
	public void createMenu() throws WxErrorException {
		String path="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3acf38bc8ef7ef93&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		WxMenu menu=new WxMenu();
		WxMenuButton button1=new WxMenuButton();
		button1.setType(WxConsts.MenuButtonType.CLICK);
		button1.setName("工棚");
		List<WxMenuButton> list=new ArrayList<>();
		WxMenuButton btn1 = new WxMenuButton();
		btn1.setName("比赛列表");
		String url="http://wx.wcloud.top/wap/page/racelist";
		btn1.setUrl(path.replace("REDIRECT_URI",url));
		btn1.setType(WxConsts.MenuButtonType.VIEW);
		WxMenuButton btn2 = new WxMenuButton();
		btn2.setName("工棚列表");
		url="http://wx.wcloud.top/wap/page/loftlist";
		btn2.setUrl(path.replace("REDIRECT_URI",url));
		btn2.setType(WxConsts.MenuButtonType.VIEW);
		WxMenuButton btn3 = new WxMenuButton();
		btn3.setName("我的工棚");
		url="http://wx.wcloud.top/wap/page/myloftlist3";
		btn3.setUrl(path.replace("REDIRECT_URI",url));
		btn3.setType(WxConsts.MenuButtonType.VIEW);
		list.add(btn1);
		list.add(btn2);
		list.add(btn3);
		button1.setSubButtons(list);
		menu.getButtons().add(button1);
		wxMpService.setWxMpConfigStorage(wxConfigProvider.wxMpInMemoryConfigStorage());
		wxMpService.getMenuService().menuDelete();
		String s = wxMpService.getMenuService().menuCreate(menu);
	}
}
