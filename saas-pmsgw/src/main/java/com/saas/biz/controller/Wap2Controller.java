package com.saas.biz.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.alibaba.fastjson.JSONObject;
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;
import com.saas.biz.pojo.NodejsCustomerMessage;
import com.saas.biz.pojo.NodejsCustomerMessagePigowner;
import com.saas.biz.pojo.NodejsCustomerMessagePigownerDetail;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.NodejsCrawlerService;
import com.saas.biz.service.NodejsCustomerMessageService;
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
@RequestMapping("/wap2")
public class Wap2Controller {
	private static final Logger loger = LoggerFactory.getLogger(Wap2Controller.class);

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private NodejsCustomerMessageService nodejsCustomerMessageService;
	@Autowired
	private NodejsCrawlerService nodejsCrawlerService;
	@Autowired
	private WeixinUserService weixinUserService;

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
	public String racelistPage(ModelMap model, HttpServletRequest req) {
		return "wap/racelist";
	}

	/**
	 * 微信菜单-比赛列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param sort
	 * @param fuzzyQuery
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/data/racelist", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<JsonResult<List<NodejsCrawlerMasterGame>, Object>> racelistData(
			@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize,
			@RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery)
			throws UnsupportedEncodingException {
		if (loger.isDebugEnabled())
			loger.debug(WeixinUserController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/"
					+ fuzzyQuery);

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("database", OpEnum.EQUALS.getName(), PrependEnum.NONE.getName(), "pmsgw_weixin",
				SignEnum.NONE.getName()));

		if (StringUtils.isNotBlank(fuzzyQuery)) {
			nodes.add(new QueryNode("master_text", OpEnum.LIKE.getName(), PrependEnum.AND.getName(), fuzzyQuery,
					SignEnum.YES.getName()));
		}
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort("cote_state DESC,create_time desc");
		query.setFuzzyQuery(listNodes);

		BaseResponse<JsonResult<List<NodejsCrawlerMasterGame>, Object>> result = PagingAndSortingRepository.find(query,
				new PageSpecification<NodejsCrawlerMasterGame>() {
					@Override
					public List<NodejsCrawlerMasterGame> query(Map<Object, Object> map) {
						return nodejsCrawlerService.selectNodejsCrawlerMasterGameListByDynamic(map);
					}

					@Override
					public Object queryExt(Map<Object, Object> map) {
						return null;
					}

					@Override
					public long queryCount(Map<Object, Object> map) {
						return nodejsCrawlerService.selectNodejsCrawlerMasterGameListByDynamicCount(map);
					}
				});
		return result;
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
	public String racedetaillistPage(ModelMap model, @RequestParam("masterId") String masterId,
			HttpServletRequest req) {
		NodejsCrawlerMasterGame master = nodejsCrawlerService.selectNodejsCrawlerMasterGameById("pmsgw_weixin",
				masterId);
		model.put("master", master);

		return "wap/racedetaillist";
	}

	/**
	 * 比赛成绩列表
	 * 
	 * @param model
	 * @param masterId
	 * @param pageIndex
	 * @param pageSize
	 * @param sort
	 * @param fuzzyQuery
	 * @param req
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/data/racedetaillist")
	@ResponseBody
	public BaseResponse<JsonResult<List<NodejsCrawlerDetailGame>, Object>> racedetaillistData(ModelMap model,
			@RequestParam("masterId") String masterId, @RequestParam("page") Integer pageIndex,
			@RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort,
			@RequestParam("fuzzyQuery") String fuzzyQuery, HttpServletRequest req) {

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("database", OpEnum.EQUALS.getName(), PrependEnum.NONE.getName(), "pmsgw_weixin",
				SignEnum.NONE.getName()));
		nodes.add(new QueryNode("master_Id", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), masterId,
				SignEnum.YES.getName()));

		if (StringUtils.isNotBlank(fuzzyQuery)) {
			nodes.add(new QueryNode("pigowner", OpEnum.LIKE.getName(), PrependEnum.AND.getName(), fuzzyQuery,
					SignEnum.YES.getName()));
		}
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(" rank asc,create_time asc ");
		query.setFuzzyQuery(listNodes);

		BaseResponse<JsonResult<List<NodejsCrawlerDetailGame>, Object>> result = PagingAndSortingRepository.find(query,
				new PageSpecification<NodejsCrawlerDetailGame>() {
					@Override
					public List<NodejsCrawlerDetailGame> query(Map<Object, Object> map) {
						return nodejsCrawlerService.selectNodejsCrawlerDetailGameListByDynamic(map);
					}

					@Override
					public Object queryExt(Map<Object, Object> map) {
						return null;
					}

					@Override
					public long queryCount(Map<Object, Object> map) {
						return nodejsCrawlerService.selectNodejsCrawlerDetailGameListByDynamicCount(map);
					}
				});
		return result;
	}

	/**
	 * 微信菜单-我的比赛 http://weixin.pmsgw.com/wap/redirectToMyracedetaillist
	 * 
	 * @return
	 */
	@RequestMapping(value = "/redirectToMyracedetaillist", method = RequestMethod.GET)
	public String redirectToMyracedetaillist() {

		String url = "http://weixin.pmsgw.com/wap/page/myracelist";
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
	@RequestMapping(method = RequestMethod.GET, value = "/page2/myracedetaillist")
	public String myracedetaillist(@RequestParam(name = "code") String code,
			@RequestParam(name = "state") String returnUrl, ModelMap model) throws Exception {
		if (loger.isDebugEnabled())
			loger.debug(Wap2Controller.class + "/page/myracedetaillist->" + code + "->" + returnUrl);

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

	@RequestMapping(method = RequestMethod.GET, value = "/page/myracelist2")
	public String myracelist2(@RequestParam(name = "code") String code,
			@RequestParam(name = "state") String state, ModelMap model) throws Exception {

		if (loger.isDebugEnabled())
			loger.debug(Wap2Controller.class + "/page/myracelist->" + code + "->" + state);

		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

		String openId = wxMpOAuth2AccessToken.getOpenId();
		WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
		if (wxMpUser != null && wxMpUser.getSubscribe()) {
			
			model.put("openId", openId);
			return "wap/myracelist";
		} else {
			String redirectURL = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI1NDk4NzYzMw==&scene=126#wechat_redirect";
			return "redirect:" + redirectURL;
		}

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/page/myracelist")
	public String myracelist(ModelMap model) throws Exception {

		// List<NodejsCustomerMessage> item =
		// nodejsCustomerMessageService.selectByMessageReceiverId(openId);
		model.put("openId", "ocSsDwg-giK_9vvidNLsl4vKkB8c");
		return "wap/myracelist";

	}

	@RequestMapping(value = "/data/myracelist", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<JsonResult<List<NodejsCustomerMessagePigowner>, Object>> racelistData2(
			@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize,
			@RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery)
			throws UnsupportedEncodingException {
		if (loger.isDebugEnabled())
			loger.debug(WeixinUserController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/"
					+ fuzzyQuery);

		List<QueryNode> nodes = new ArrayList<QueryNode>();

		nodes.add(new QueryNode("game_receiver_openid", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(),
				"ocSsDwg-giK_9vvidNLsl4vKkB8c", SignEnum.YES.getName()));
		/*
		 * nodes.add(new QueryNode("database", OpEnum.EQUALS.getName(),
		 * PrependEnum.NONE.getName(), "pmsgw_weixin", SignEnum.NONE.getName()));
		 * 
		 * if (StringUtils.isNotBlank(fuzzyQuery)) { nodes.add(new
		 * QueryNode("master_text", OpEnum.LIKE.getName(), PrependEnum.AND.getName(),
		 * fuzzyQuery, SignEnum.YES.getName())); }
		 */
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		// query.setSort("game_create_time desc");
		query.setFuzzyQuery(listNodes);

		BaseResponse<JsonResult<List<NodejsCustomerMessagePigowner>, Object>> result = PagingAndSortingRepository
				.find(query, new PageSpecification<NodejsCustomerMessagePigowner>() {
					@Override
					public List<NodejsCustomerMessagePigowner> query(Map<Object, Object> map) {
						return nodejsCustomerMessageService.selectNodejsCustomerMessagePigownerListByDynamic(map);
					}

					@Override
					public Object queryExt(Map<Object, Object> map) {
						return null;
					}

					@Override
					public long queryCount(Map<Object, Object> map) {
						return nodejsCustomerMessageService.selectNodejsCustomerMessagePigownerCountByDynamic(map);
					}
				});
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/page/myracedetaillist")
	public String myracedetaillistPage(

			@RequestParam("receiver_openid") String receiver_openid, @RequestParam("cote_id") String cote_id,

			ModelMap model) throws Exception {

		String openid_gameid = receiver_openid + "->" + cote_id;
		NodejsCustomerMessagePigowner master = nodejsCustomerMessageService
				.selectNodejsCustomerMessagePigownerOneById(openid_gameid);
		model.put("openId", receiver_openid);
		// model.put("masterId", master_id);
		model.put("coteId", cote_id);
		model.put("master", master);
		return "wap/myracedetaillist";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/data/myracedetaillist")
	@ResponseBody
	public BaseResponse<List<Map<String, String>>> myracedetaillistData(ModelMap model,
			@RequestParam("receiver_openid") String receiver_openid, @RequestParam("cote_id") String cote_id,
			@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize,
			@RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery, HttpServletRequest req) {

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("database", OpEnum.EQUALS.getName(), PrependEnum.NONE.getName(), "pmsgw_weixin",
				SignEnum.NONE.getName()));
		nodes.add(new QueryNode("game_cote_id", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), cote_id,
				SignEnum.YES.getName()));
		nodes.add(new QueryNode("game_receiver_openid", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(),
				receiver_openid, SignEnum.YES.getName()));
		if (StringUtils.isNotBlank(fuzzyQuery)) {
			nodes.add(new QueryNode("pigowner", OpEnum.LIKE.getName(), PrependEnum.AND.getName(), fuzzyQuery,
					SignEnum.YES.getName()));
		}
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		// query.setSort(" rank asc,create_time asc ");
		query.setFuzzyQuery(listNodes);


		List<Map<String, Object>> listMap = nodejsCustomerMessageService.selectMapByCoteIdOpenid(cote_id,
				receiver_openid);

		// 比赛列表
		List<String> allGamemastertextList = new ArrayList<String>();
		for (Map<String, Object> map : listMap) {
			String game_master_text = map.get("game_master_text").toString();
			if (!allGamemastertextList.contains(game_master_text)) {
				allGamemastertextList.add(game_master_text);
			}
		}

		// 足环列表
		List<String> allGameringnumList = new ArrayList<String>();
		for (Map<String, Object> map : listMap) {
			String game_ringnum = map.get("game_ringnum").toString();
			if (!allGameringnumList.contains(game_ringnum)) {
				allGameringnumList.add(game_ringnum);
			}
		}

		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		Map<String, String> header = new LinkedHashMap<String, String>();

		int index = 0;
		header.put("c" + index, "足环号");
		index++;
		for (String gamemastertext : allGamemastertextList) {
			header.put("c" + index, gamemastertext);
			index++;
		}
		retList.add(header);

		
		
		Map<String, String> gameMap = new LinkedHashMap<String, String>();
		for (Map<String, Object> map : listMap) {
			String game_master_text = map.get("game_master_text").toString();
			String game_ringnum = map.get("game_ringnum").toString();
			String game_rank = map.get("game_rank").toString();
			String key=game_ringnum+"->"+game_master_text;
			if(!gameMap.containsKey(key))
			{
				gameMap.put(key, game_rank);
			}
		}

		for (String gameringnum : allGameringnumList) {
			index = 0;
			Map<String, String> body = new LinkedHashMap<String, String>();
			body.put("c" + index, gameringnum);
			index++;
			for (String gamemastertext : allGamemastertextList) {
				String key=gameringnum+"->"+gamemastertext;
				String value="-";
				if(gameMap.containsKey(key))
				{
					value=gameMap.get(key);
				}
				
				body.put("c" + index, value);
				index++;
			}
			retList.add(body);
		}
		
		return BaseResponse.ToJsonResult(retList);
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
	public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl, ModelMap model)
			throws Exception {

		if (loger.isDebugEnabled())
			loger.debug(Wap2Controller.class + "/action/usersave->" + code + "->" + returnUrl);

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
	@RequestMapping(method = RequestMethod.GET, value = "/page/userbind2")
	public String userInfo2(ModelMap model) throws Exception {

		loger.info(wxMpService.getMenuService().menuGet().toJson());
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
		}
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
			loger.debug(Wap2Controller.class + "/action/usersave->" + JSON.toJSONString(body));

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
	
	
	
}
