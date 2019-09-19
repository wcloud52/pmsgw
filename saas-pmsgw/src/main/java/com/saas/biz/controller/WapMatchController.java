package com.saas.biz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.saas.biz.pojo.*;
import com.saas.biz.service.*;
import com.saas.biz.util.EmojiFilter;
import com.saas.common.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

/**
 * 移动端相关操作
 * 
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/wap")
public class WapMatchController {
	private static final Logger loger = LoggerFactory.getLogger(WapMatchController.class);

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
	@Autowired
	protected NodejsMatchRegistService nodejsMatchRegistService;
	@Autowired
	protected NodejsMatchService nodejsMatchService;

	@RequestMapping(value = "page/resultOne",method = RequestMethod.GET)
	public String resultOne(Model model, @RequestParam("match_id") String match_id, @RequestParam("pigeon_code") String pigeon_code, @RequestParam("pigowner") String pigowner){
		//根据id获取比赛
		NodejsMatch nodejsMatch = nodejsMatchService.selectOneById(match_id);
		Map queryMap = new HashedMap();
		queryMap.put("match_id", match_id);
		queryMap.put("cote_id",nodejsMatch.getCote_id());
		queryMap.put("start_time",nodejsMatch.getStart_time());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		queryMap.put("end_time",nodejsMatch.getEnd_time()==null?sdf.format(new Date()):nodejsMatch.getEnd_time());
		queryMap.put("database","pmsgw_weixin");
		List<NodejsMatchRegist> detailGameList = nodejsMatchRegistService.selectCrawlerDetailGame(queryMap);
		JSONArray rules = JSONObject.parseArray(nodejsMatch.getRule());
		int reward=0;
		List<Map<String,Object>> result=new ArrayList<>();
		if (detailGameList.size()>0){
			List<NodejsMatchRegist> collect = detailGameList.stream().filter(item -> item.getPigeon_code().equals(pigeon_code)).collect(Collectors.toList());
			String regist = collect.get(0).getRegist();
			JSONObject registJson = JSONObject.parseObject(regist);
			for (String key:registJson.keySet() ) {
				List<NodejsMatchRegist> filterList = detailGameList.stream().filter(item -> item.getRegist().contains(key)).collect(Collectors.toList());
				filterList.stream().forEach(item -> {
					if (item.getRank() <= 0) {
						item.setRank(999999999);
					}
				});
				filterList = filterList.stream().sorted((b1, b2) -> b1.getRank() - b2.getRank()).collect(Collectors.toList());
				String[] split = key.split("-");
				for (int i = 0; i < rules.size(); i++) {
					JSONObject rule = rules.getJSONObject(i).getJSONObject("rule");
					if (rule.getString("code").equals(split[1])){
						result.add(nodejsMatchRegistService.echoOneResult(rule,key,filterList,pigeon_code));
					}
				}
			}
			result.sort((a,b)->(a.get("name")+"").compareTo(b.get("name")+""));
			double sum = result.stream().collect(Collectors.summingDouble(item -> (Float) item.get("reward")));
			Map all=new HashedMap();
			all.put("name","合计");
			all.put("reward",(int)sum);
			result.add(all);
			model.addAttribute("result",result);
			model.addAttribute("pigeon_code",pigeon_code);
			model.addAttribute("pigowner",pigowner);
		}
		return "wap/resultOne";
	}
}
