package com.saas.biz.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.request.WxPaySendRedpackRequest;
import com.github.binarywang.wxpay.bean.result.WxPaySendRedpackResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.properties.WxProperties;
import com.saas.biz.service.WeixinUserService;
import com.saas.biz.util.BaseWeChatService;
import com.saas.biz.util.WeChatSyncFollowerTools;
import com.saas.task.SendMessageAsyncTask;
import com.saas.task.SendMessageAsyncTask2;
import com.saas.task.SendMessageAsyncTask2.CustomerUser;
import com.saas.task.SendMobileMessageAsyncTask;

import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Controller
@RequestMapping("/weixinTest")
public class WeixinTestController {

	private static final Logger log = LoggerFactory.getLogger(WeixinTestController.class);

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private SendMessageAsyncTask sendMessageAsyncTask;

	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	/**
	 * 列表
	 * 
	 * @param body
	 * @return
	 * @throws WxErrorException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/createMenu", method = RequestMethod.GET)
	@ResponseBody
	public String createMenu() throws WxErrorException {

		WxMenuButton button11 = new WxMenuButton();
		button11.setName("新闻");
		button11.setType("miniprogram");
		button11.setUrl("http://mp.weixin.qq.com"); // 必须添加http
		button11.setAppId("wxa12d26000138625a");
		button11.setPagePath("pages/info/index");
		
		 WxMenuButton button22=new WxMenuButton();
		 button22.setName("公棚");
		
		WxMenuButton button22_1 = new WxMenuButton();
		button22_1.setName("比赛列表");
		button22_1.setType("view");
		button22_1.setUrl("http://weixin.pmsgw.com/wap/page/racelist"); 
		
		WxMenuButton button22_2 = new WxMenuButton();
		button22_2.setName("公棚列表");
		button22_2.setType("view");
		button22_2.setUrl("http://weixin.pmsgw.com/wap/redirectToLoftlist"); 
		
		List<WxMenuButton> button22_subButtons=new ArrayList<WxMenuButton>();
		button22_subButtons.add(button22_1);
		button22_subButtons.add(button22_2);
       
		button22.setSubButtons(button22_subButtons);

		 WxMenuButton button33=new WxMenuButton();
		 button33.setName("我的");
	        
		WxMenuButton button3_1 = new WxMenuButton();
		button3_1.setName("用户绑定");
		button3_1.setType("view");
		button3_1.setUrl("http://weixin.pmsgw.com/wap/redirectToUserbind"); // 必须添加http

		WxMenuButton button3_2 = new WxMenuButton();
		button3_2.setName("我的比赛");
		button3_2.setType("view");
		button3_2.setUrl("http://weixin.pmsgw.com/wap/redirectToMyracedetaillist"); // 必须添加http
		
		WxMenuButton button3_3 = new WxMenuButton();
		button3_3.setName("我的公棚");
		button3_3.setType("view");
		button3_3.setUrl("http://weixin.pmsgw.com/wap/redirectToMyLoftlist"); // 必须添加http
		
		WxMenuButton button3_4 = new WxMenuButton();
		button3_4.setName("我的收鸽");
		button3_4.setType("view");
		button3_4.setUrl("http://weixin.pmsgw.com/wap/redirectToMypigeoncollectionPage1"); // 必须添加http
		
		WxMenuButton button3_5 = new WxMenuButton();
		button3_5.setName("我的集鸽");
		button3_5.setType("view");
		button3_5.setUrl("http://weixin.pmsgw.com/wap/redirectToMypigeoncollectionPage2"); // 必须添加http
		

		List<WxMenuButton> subButtons=new ArrayList<WxMenuButton>();
        subButtons.add(button3_1);
        subButtons.add(button3_2);
        subButtons.add(button3_3);
        subButtons.add(button3_4);
        subButtons.add(button3_5);
      
        button33.setSubButtons(subButtons);
        
		List<WxMenuButton> buttons = new ArrayList<WxMenuButton>();
		buttons.add(button11);
		buttons.add(button22);
		buttons.add(button33);

		WxMenu menu = new WxMenu();
		menu.setButtons(buttons);

		wxMpService.getMenuService().menuCreate(menu);

		return "ok";
	}

	@RequestMapping(value = "/getAccessToken", method = RequestMethod.POST)
	@ResponseBody
	public String getAccessToken() throws WxErrorException {

		String token = wxMpService.getAccessToken();
		log.info(token);

		return "ok";
	}

	@RequestMapping(value = "/synchronizeUser", method = RequestMethod.POST)
	@ResponseBody
	public String synchronizeUser() throws WxErrorException {
		new WeChatSyncFollowerTools(wxMpService, new BaseWeChatService()).setSyncCount(500).synchronize();
		return "ok";
	}

	@Autowired
	private WxProperties info;

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	@ResponseBody
	public String test() throws WxErrorException {
		String gameTemplateId = info.getGameTemplateId();
		// 实例化模板对象
		WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
		// 设置模板ID
		wxMpTemplateMessage.setTemplateId(gameTemplateId);
		// 设置发送给哪个用户
		wxMpTemplateMessage.setToUser("ocSsDwjRXUH_Kn2hCZQN47z6YpnM");
		wxMpTemplateMessage.setUrl("");

		List<WxMpTemplateData> list = Arrays.asList(new WxMpTemplateData("first", "平民赛鸽网提供", "#000000"),
				new WxMpTemplateData("keyword1", "masterText", "#000000"),
				new WxMpTemplateData("keyword2", "", "#000000"),
				new WxMpTemplateData("keyword3", "pigowner", "#000000"),
				new WxMpTemplateData("keyword4", "最终成绩以公布为准", "#000000"),
				new WxMpTemplateData("keyword5", "speed" + ",名次" + "rank", "#000000"),
				new WxMpTemplateData("remark", "足环号" + "ringnum" + ",归巢时间" + "cometime", "#000000"));
		// 放进模板对象。准备发送
		wxMpTemplateMessage.setData(list);
		try {
			// 发送模板
			wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return "发送成功";
	}

	@RequestMapping(value = "/testGame", method = RequestMethod.POST)
	@ResponseBody
	public String testGame() throws WxErrorException {
		String gameTemplateId = info.getGameTemplateId();
		// 实例化模板对象
		WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
		// 设置模板ID
		wxMpTemplateMessage.setTemplateId(gameTemplateId);
		// 设置发送给哪个用户
		wxMpTemplateMessage.setToUser("ocSsDwjRXUH_Kn2hCZQN47z6YpnM");
		wxMpTemplateMessage.setUrl("");

		List<WxMpTemplateData> list = Arrays.asList(new WxMpTemplateData("first", "平民赛鸽网提供比赛提醒", "#000000"),
				new WxMpTemplateData("keyword1", "masterText", "#000000"),
				new WxMpTemplateData("keyword2", "2018-10-10", "#000000"),
				new WxMpTemplateData("keyword3", "10人", "#000000"),

				new WxMpTemplateData("remark", "比赛俱乐部", "#000000"));
		// 放进模板对象。准备发送
		wxMpTemplateMessage.setData(list);
		try {
			// 发送模板
			wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return "发送成功";
	}

	/*
	 * @RequestMapping(value = "/test2", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public List<String> test2(@RequestParam String name) throws
	 * WxErrorException {
	 * 
	 * return sendMessageAsyncTask.test(name); }
	 */
	@Autowired
	private WeixinUserService weixinUserService;

	@RequestMapping(value = "/getWeixinUserByCache", method = RequestMethod.POST)
	@ResponseBody
	public int test2() throws WxErrorException {
		List<WeixinUser> userList = weixinUserService.selectAll();

		return userList.size();
	}

	@Autowired
	private SendMessageAsyncTask2 sendMessageAsyncTask2;

	@RequestMapping(value = "/test2-1", method = RequestMethod.POST)
	@ResponseBody
	public List<CustomerUser> test21(String name) throws WxErrorException {
		List<WeixinUser> userList = weixinUserService.selectAll();
		Map<String, List<CustomerUser>> map = sendMessageAsyncTask2.getWeixinUserMap(userList);
		List<CustomerUser> list = sendMessageAsyncTask2.getOpenIdWithLoft(map, name);
		return list;
	}

	@RequestMapping(value = "/test3", method = RequestMethod.POST)
	@ResponseBody
	public String test3() throws WxErrorException {
		return System.getProperty("java.io.tmpdir");
	}

	@RequestMapping(value = "/test4", method = RequestMethod.POST)
	@ResponseBody
	public String test4() throws WxErrorException {
		WxMpKefuMessage message = WxMpKefuMessage.TEXT().toUser("ocSsDwjRXUH_Kn2hCZQN47z6YpnM").content("hello world")
				.build();
		wxMpService.getKefuService().sendKefuMessage(message);
		return System.getProperty("java.io.tmpdir");
	}

	@RequestMapping(value = "/test5", method = RequestMethod.POST)
	@ResponseBody
	public String test5() throws WxErrorException {
		String text = "001203400";
		String s = StringUtils.stripStart(text, "0");
		return s;
	}

	@RequestMapping(value = "/test6", method = RequestMethod.POST)
	@ResponseBody
	public String test6() throws HTTPException, JSONException, IOException {
		String[] mobileList = {

				"18701122859","18611906315"
		
		};
		// 短信应用SDK AppID
		int appid = 1400077437; // 1400开头

		// 短信应用SDK AppKey
		String appkey = "1b1f77227f0b396c70b0d0aec6a45486";
		int templateId = 203166; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
		// templateId7839对应的内容是"您的验证码是: {1}"
		// 签名
		String smsSign = "大众赛鸽";
		// {1},参赛者{2},比赛成绩{3},名次{4},足环号{5},归巢时间{6}
		//"180公里\",\"金纺鸽业+张亚宁\",\"1317.53479922\",\"1\",\"2018-03-1808985\",\"2018-11-24 10:47:48.120\"

		String[] params = { "180公里", "金纺鸽业+张亚宁", "1317.53479922", "1", "2018-03-1808985", "2018-11-24 10:47:48.120" };// 数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
		SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		for(String mobile:mobileList)
		{
			if(mobile.length()>0)
			{
				SmsSingleSenderResult result = ssender.sendWithParam("86",mobile, templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
				System.out.println(result);
			}
		
		}

		return smsSign;
	}

	@Autowired
	private SendMobileMessageAsyncTask sendMobileMessageAsyncTask;
	@RequestMapping(value = "/test7", method = RequestMethod.POST)
	@ResponseBody
	public String test7(
			@RequestParam	String mobile,@RequestParam	String masterText,@RequestParam	String cote_name,@RequestParam	int
		rank,@RequestParam	String ringnum,@RequestParam	String cometime) throws JSONException, HTTPException, IOException{
		
		sendMobileMessageAsyncTask.sendSms(mobile, masterText, cote_name, rank, ringnum, cometime);
		return "OK";
	}
	@Autowired
	private WxPayService wxService;
	/**
	 * 发送红包
	 * @param request
	 * @return
	 * @throws WxPayException
	 */
	  @PostMapping("/sendRedpack")
	  public WxPaySendRedpackResult sendRedpack() throws WxPayException {
		 WxPaySendRedpackRequest req=new WxPaySendRedpackRequest();
		  req.setSign("111");
		  req.setMchBillNo(UUID.randomUUID().toString()); // 商户订单号
	      req.setReOpenid("ocSsDwjRXUH_Kn2hCZQN47z6YpnM"); // 用户的openid
	      req.setSendName("大众赛鸽"); // 红包发送者名称
	      req.setTotalAmount(100);// 付款金额 限制 大于100 小于200*100，单位分
	      req.setTotalNum(1); // 红包发放人数
	      req.setWishing("恭喜发财"); // 包祝福语
	      req.setActName("测试"); // 活动名称
	      req.setRemark("测试"); // 备注
	      //req.setSceneId(product); // 场景id
	      req.setClientIp("36.110.19.90"); // 请求的客户端Ip地址
	     
	      // {"actName":"测试","clientIp":"36.110.19.90","mchBillNo":"1fe6bd3f-da80-4161-a549-c023a604e65a","reOpenid":"ocSsDwjRXUH_Kn2hCZQN47z6YpnM","remark":"测试","sendName":"大众赛鸽","sign":"111","totalAmount":100,"totalNum":1,"wishing":"恭喜发财"}

         log.info(JSON.toJSONString(req));
	    return wxService.sendRedpack(req);
	  }
	/*
	 * @RequestMapping(value = "/test7", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public String test7() throws WxErrorException { List
	 * list=redisTemplate.opsForList().range("weixin_user::weixin_user_selectAll",0,
	 * -1); Object os="test"; redisTemplate.opsForValue().set("vv", "ok");
	 * 
	 * return (String) redisTemplate.opsForValue().get("name"); }
	 */
}