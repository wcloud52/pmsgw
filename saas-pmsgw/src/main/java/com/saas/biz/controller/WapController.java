package com.saas.biz.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.alibaba.fastjson.JSON;
import com.saas.biz.pojo.NodejsCrawlerCoteExtend;
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;
import com.saas.biz.pojo.NodejsCustomerMessage;
import com.saas.biz.pojo.NodejsPigeonCollection;
import com.saas.biz.pojo.NodejsWeixinUserCoteExtend;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.NodejsCrawlerCoteExtendService;
import com.saas.biz.service.NodejsCrawlerService;
import com.saas.biz.service.NodejsCustomerMessageService;
import com.saas.biz.service.NodejsPigeonCollectionService;
import com.saas.biz.service.NodejsWeixinUserCoteExtendService;
import com.saas.biz.service.WeixinUserService;
import com.saas.biz.util.EmojiFilter;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.OpEnum;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;
import com.saas.common.SignEnum;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 移动端相关操作
 * 
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/wap")
public class WapController {
	private static final Logger loger = LoggerFactory.getLogger(WapController.class);

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private NodejsCustomerMessageService nodejsCustomerMessageService;
	@Autowired
	private NodejsCrawlerService nodejsCrawlerService;
	@Autowired
	private WeixinUserService weixinUserService;
	@Autowired
	protected NodejsPigeonCollectionService nodejsPigeonCollectionService;

	@RequestMapping(method = RequestMethod.GET, value = "/bindLoft")
	public ModelAndView bindLoft(HttpServletRequest req) {

		return new ModelAndView("wap/bindLoft");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/bindClub")
	public ModelAndView bindClub(HttpServletRequest req) {

		return new ModelAndView("wap/bindClub");
	}

	/**
	 * get登录页
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/bind")
	public ModelAndView bind(HttpServletRequest req) {

		return new ModelAndView("wap/bind");
	}

	/**
	 * 微信菜单-比赛页面 http://weixin.pmsgw.com/wap/page/racelist
	 * 
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/page/racelist")
	public String racelist(ModelMap model, HttpServletRequest req) {

		List<NodejsCrawlerMasterGame> item = nodejsCrawlerService.selectTop50NodejsCrawlerMasterGameList("pmsgw_weixin");
		model.put("item", item);
		return "wap/racelist";
	}

	/**
	 * 比赛成绩页面
	 * 
	 * @param model
	 * @param masterId
	 * @param req
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/page/racedetaillist")
	public String racedetaillist(ModelMap model, @RequestParam("masterId") String masterId, HttpServletRequest req) {
		NodejsCrawlerMasterGame master = nodejsCrawlerService.selectNodejsCrawlerMasterGameById("pmsgw_weixin", masterId);
		List<NodejsCrawlerDetailGame> item = nodejsCrawlerService.selectNodejsCrawlerDetailGameListByMasterId("pmsgw_weixin", masterId);
		model.put("master", master);
		model.put("item", item);
		return "wap/racedetaillist";
	}

	/**
	 * 微信菜单-我的比赛 http://weixin.pmsgw.com/wap/redirectToMyracedetaillist
	 * 
	 * @return
	 */
	@RequestMapping(value = "/redirectToMyracedetaillist", method = RequestMethod.GET)
	public String redirectToMyracedetaillist() {

		String url = "http://weixin.pmsgw.com/wap/page/myracedetaillist";
		String redirectURL = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, "1");
		return "redirect:" + redirectURL;
	}

	/**
	 * 我的比赛成绩页面
	 * 
	 * @param model
	 * @param masterId
	 * @param req
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/page/myracedetaillist")
	public String myracedetaillist(@RequestParam(name = "code") String code, @RequestParam(name = "state") String returnUrl, ModelMap model) throws Exception {
		if (loger.isDebugEnabled())
			loger.debug(WapController.class + "/page/myracedetaillist->" + code + "->" + returnUrl);

		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

		String openId = wxMpOAuth2AccessToken.getOpenId();
		WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
		if (wxMpUser != null && wxMpUser.getSubscribe()) {
			List<NodejsCustomerMessage> item = nodejsCustomerMessageService.selectByMessageReceiverId(openId);
			model.put("item", item);
			return "wap/myracedetaillist";
		} else {
			String redirectURL = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI1NDk4NzYzMw==&scene=126#wechat_redirect";
			return "redirect:" + redirectURL;
		}

	}

	/**
	 * 微信菜单-用户绑定 http://weixin.pmsgw.com/wap/redirectToUserbind
	 * 
	 * @return
	 */
	@RequestMapping(value = "/redirectToUserbind", method = RequestMethod.GET)
	public String redirectToUserbind() {

		String url = "http://weixin.pmsgw.com/wap/page/userbind";
		String redirectURL = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, "1");
		return "redirect:" + redirectURL;
	}

	/**
	 * 用户绑定页
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/page/userbind")
	public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl, ModelMap model) throws Exception {

		if (loger.isDebugEnabled())
			loger.debug(WapController.class + "/action/usersave->" + code + "->" + returnUrl);

		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

		String openId = wxMpOAuth2AccessToken.getOpenId();
		WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
		if (wxMpUser != null && wxMpUser.getSubscribe()) {
			model.addAttribute("openid", openId);

			WeixinUser item = weixinUserService.selectOneById(openId);
			if (item == null) {
				item = new WeixinUser();
				item.setId(openId);
				item.setOpenid(openId);
				item.setSubscribe(1);
				item.setCreate_time(new Date());
				weixinUserService.insert(item);
			} else {
				item.setOpenid(openId);
				item.setNickname(EmojiFilter.filterBase64Encode(wxMpUser.getNickname()));
				item.setHeadimgurl(wxMpUser.getHeadImgUrl());
				item.setSubscribe(1);
				item.setModify_time(new Date());
				weixinUserService.update(item);
			}
			model.put("item", item);

			return "wap/userbind";
		} else {
			String redirectURL = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI1NDk4NzYzMw==&scene=126#wechat_redirect";
			return "redirect:" + redirectURL;
		}
	}

	// http://localhost:4000/wap/page/userbind2
	//http://weixin.pmsgw.com/wap/page/userbind2
	@RequestMapping(method = RequestMethod.GET, value = "/page/userbind2")
	public String userInfo2(ModelMap model) throws Exception {

		return "wap/userbind";
	/*	loger.info(wxMpService.getMenuService().menuGet().toJson());
		String openId = "ocSsDwjRXUH_Kn2hCZQN47z6YpnM";
		WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
		if (wxMpUser != null && wxMpUser.getSubscribe()) {
			model.addAttribute("openid", openId);

			WeixinUser item = weixinUserService.selectOneById(openId);
			if (item == null) {
				item = new WeixinUser();
				item.setId(openId);
				item.setOpenid(openId);
				item.setSubscribe(1);
				item.setCreate_time(new Date());
				weixinUserService.insert(item);
			} else {
				item.setOpenid(openId);
				item.setNickname(EmojiFilter.filterBase64Encode(wxMpUser.getNickname()));
				item.setHeadimgurl(wxMpUser.getHeadImgUrl());
				item.setSubscribe(1);
				item.setModify_time(new Date());
				weixinUserService.update(item);
			}
			model.put("item", item);

			return "wap/userbind";
		} else {
			String redirectURL = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI1NDk4NzYzMw==&scene=126#wechat_redirect";
			return "redirect:" + redirectURL;
		}*/
	}

	@RequestMapping(value = "/redirectToWeixin", method = RequestMethod.GET)
	public String redirectToWeixin() {
		// String
		// redirectURL="https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI1NDk4NzYzMw==&scene=126#wechat_redirect";
		// return "redirect:" + redirectURL;
		return "wap/error";
	}

	/**
	 * 用户绑定操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/action/usersave", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> bindSaveClub(WeixinUser body) {
		if (loger.isDebugEnabled())
			loger.debug(WapController.class + "/action/usersave->" + JSON.toJSONString(body));

		WeixinUser record = body;

		record.setModify_time(new Date());
		if (StringUtils.isNotEmpty(record.getClub_bind_address())) {
			record.setClub_bind_time(new Date());
		}
		if (StringUtils.isNotEmpty(record.getBind_address())) {
			record.setBind_time(new Date());
		}

		WeixinUser user = weixinUserService.selectOneById(record.getId());
		if (user != null)
			weixinUserService.update(record);
		else {
			record.setOpenid(record.getId());
			record.setSubscribe(1);
			record.setCreate_time(new Date());
			weixinUserService.insert(record);
		}
		return BaseResponse.ToJsonResult(1);
	}

	@Autowired
	protected NodejsCrawlerCoteExtendService nodejsCrawlerCoteExtService;

	/**
	 * 公棚列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/redirectToLoftlist", method = RequestMethod.GET)
	public String redirectToLoftlist() {

		String url = "http://weixin.pmsgw.com/wap/page/loftlist";
		String redirectURL = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, "1");
		return "redirect:" + redirectURL;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/page/loftlist")
	public String loftlist(@RequestParam(name = "code") String code, @RequestParam(name = "state") String returnUrl, ModelMap model) throws Exception {
		if (loger.isDebugEnabled())
			loger.debug(WapController.class + "/page/loftlist->" + code + "->" + returnUrl);

		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

		String openId = wxMpOAuth2AccessToken.getOpenId();
		WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
		if (wxMpUser != null && wxMpUser.getSubscribe()) {
			List<NodejsCrawlerCoteExtend> list = nodejsCrawlerCoteExtService.selectByCotestate("1");
			model.put("items", list);
			model.put("openid", openId);
			return "wap/loftlist";
		} else {
			String redirectURL = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI1NDk4NzYzMw==&scene=126#wechat_redirect";
			return "redirect:" + redirectURL;
		}

	}

	@Autowired
	protected NodejsWeixinUserCoteExtendService nodejsWeixinUserCoteExtendService;

	@RequestMapping(value = "/action/usercotesave", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> usercotesave(NodejsWeixinUserCoteExtend body) {
		if (loger.isDebugEnabled())
			loger.debug(WapController.class + "/action/usercotesave->" + JSON.toJSONString(body));

		NodejsWeixinUserCoteExtend record = body;
		NodejsCrawlerCoteExtend nodejsCrawlerCoteExtend = nodejsCrawlerCoteExtService.selectByCoteId(record.getCote_id());
		if (nodejsCrawlerCoteExtend != null) {
			record.setCote_name(nodejsCrawlerCoteExtend.getCote_name());
			record.setCote_short_name(nodejsCrawlerCoteExtend.getCote_short_name());
			record.setCote_state(nodejsCrawlerCoteExtend.getCote_state());
			record.setCote_web_url(nodejsCrawlerCoteExtend.getCote_web_url());
			record.setCote_website(nodejsCrawlerCoteExtend.getCote_website());
			record.setSort_number(nodejsCrawlerCoteExtend.getSort_number());
		}
		nodejsWeixinUserCoteExtendService.deleteByCoteId(record.getCote_id(), record.getOpenid());
		nodejsWeixinUserCoteExtendService.insert(record);

		return BaseResponse.ToJsonResult(1);
	}

	@RequestMapping(value = "/redirectToMyLoftlist", method = RequestMethod.GET)
	public String redirectToMyLoftlist() {

		String url = "http://weixin.pmsgw.com/wap/page/myloftlist";
		String redirectURL = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, "1");
		return "redirect:" + redirectURL;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/page/myloftlist")
	public String myloftlist(@RequestParam(name = "code") String code, @RequestParam(name = "state") String returnUrl, ModelMap model) throws Exception {
		if (loger.isDebugEnabled())
			loger.debug(WapController.class + "/page/myloftlist->" + code + "->" + returnUrl);

		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

		String openId = wxMpOAuth2AccessToken.getOpenId();
		WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
		if (wxMpUser != null && wxMpUser.getSubscribe()) {

			List<NodejsWeixinUserCoteExtend> list = nodejsWeixinUserCoteExtendService.selectByOpenid(openId);
			model.put("items", list);
			return "wap/myloftlist";
		} else {
			String redirectURL = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI1NDk4NzYzMw==&scene=126#wechat_redirect";
			return "redirect:" + redirectURL;
		}

	}

	// 我的收鸽
	@RequestMapping(value = "/redirectToMypigeoncollectionPage1", method = RequestMethod.GET)
	public String redirectToMypigeoncollectionPage1() {

		String url = "http://weixin.pmsgw.com/wap/page/mypigeoncollection";
		String redirectURL = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, "1");
		return "redirect:" + redirectURL;
	}

	// 我的集鸽鸽
	@RequestMapping(value = "/redirectToMypigeoncollectionPage2", method = RequestMethod.GET)
	public String redirectToMypigeoncollectionPage2() {

		String url = "http://weixin.pmsgw.com/wap/page/mypigeoncollection";
		String redirectURL = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, "2");
		return "redirect:" + redirectURL;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/page/mypigeoncollection")
	public String mypigeoncollectionPage(@RequestParam(name = "code") String code, 
			@RequestParam(name = "state") String type, ModelMap model) throws Exception {
		if (loger.isDebugEnabled())
			loger.debug(WapController.class + "/page/mypigeoncollection->" + code + "->" + type);
		
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

		String openId = wxMpOAuth2AccessToken.getOpenId();
		WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
		if (wxMpUser != null && wxMpUser.getSubscribe()) {

			List<NodejsWeixinUserCoteExtend> list = nodejsWeixinUserCoteExtendService.selectByOpenid(openId);
			model.put("items", list);
			model.put("openId", openId);
			model.put("type", type);
			return "wap/mypigeoncollection";
		} else {
			String redirectURL = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI1NDk4NzYzMw==&scene=126#wechat_redirect";
			return "redirect:" + redirectURL;
		}

	}
	@RequestMapping(method = RequestMethod.GET, value = "/page/mypigeoncollectionDetail")
	public String mypigeoncollectionPageDetail(
			@RequestParam("cote_id") String cote_id,
			@RequestParam("openid") String openid,
			@RequestParam("type") String type, 
			ModelMap model) throws Exception {
		if (loger.isDebugEnabled())
			loger.debug(WapController.class + "/page/mypigeoncollection->" + openid + "->" + type);
	
			model.put("openId", openid);
			model.put("type", type);
			model.put("cote_id", cote_id);
			return "wap/mypigeoncollectiondetail";
		
	}
	@RequestMapping(value = "/data/mypigeoncollection", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<JsonResult<List<NodejsPigeonCollection>, Object>> mypigeoncollectionData(
			@RequestParam("cote_id") String cote_id,
			@RequestParam("openid") String openid,
			@RequestParam("type") String type,
			@RequestParam("page") Integer pageIndex,
			@RequestParam("limit") Integer pageSize,
			@RequestParam("sort") String sort,
			@RequestParam("fuzzyQuery") String fuzzyQuery) throws UnsupportedEncodingException {
		if (loger.isDebugEnabled())
			loger.debug(WeixinUserController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<NodejsWeixinUserCoteExtend> list = nodejsWeixinUserCoteExtendService.selectByOpenid(openid);

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("typeId", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), type, SignEnum.YES.getName()));
		nodes.add(new QueryNode("cote_id", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), cote_id, SignEnum.YES.getName()));

		/*List<String> cote_idList = new ArrayList<String>();
		if (list != null && list.size() > 0) {
			for (NodejsWeixinUserCoteExtend coteExt : list) {
				cote_idList.add(coteExt.getCote_id().trim());
			}
		}
		if (cote_idList != null && cote_idList.size() > 0) {
			nodes.add(new QueryNode("cote_id", OpEnum.IN.getName(), PrependEnum.AND.getName(), cote_idList));
		}*/

		WeixinUser weixinUser = weixinUserService.selectOneById(openid);
		if (weixinUser != null) {
			nodes.add(new QueryNode("pigowner", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), weixinUser.getBind_name(), SignEnum.YES.getName()));
		}

		if (StringUtils.isNotBlank(fuzzyQuery)) {
			nodes.add(new QueryNode("ringnum", OpEnum.LIKE.getName(), PrependEnum.AND.getName(), fuzzyQuery, SignEnum.YES.getName()));
		}
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort("create_time desc");
		query.setFuzzyQuery(listNodes);

		BaseResponse<JsonResult<List<NodejsPigeonCollection>, Object>> result = PagingAndSortingRepository.find(query, new PageSpecification<NodejsPigeonCollection>() {
			@Override
			public List<NodejsPigeonCollection> query(Map<Object, Object> map) {
				return nodejsPigeonCollectionService.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return nodejsPigeonCollectionService.selectCountByDynamic(map);
			}
		});
		return result;
	}
}
