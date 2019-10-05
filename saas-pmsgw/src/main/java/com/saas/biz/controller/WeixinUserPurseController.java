package com.saas.biz.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.pojo.WeixinUserPurse;
import com.saas.biz.service.SyncInterface;
import com.saas.biz.service.WeixinUserPurseService;
import com.saas.biz.util.EmojiFilter;
import com.saas.biz.util.SnGenerator;
import com.saas.biz.util.WeChatSyncFollowerTools;
import com.saas.common.*;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信用户管理
 * @author sunqiu
 *
 */
@Controller
@RequestMapping("/weixinuserpurse")
public class WeixinUserPurseController {

	private static final Logger log = LoggerFactory.getLogger(WeixinUserPurseController.class);
	
	@Autowired
	private WxMpService wxMpService;
	@Autowired
	private SyncInterface syncInterface;

	@Autowired
	private WeixinUserPurseService weixinUserPurseService;


	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		return "weixinuserpurse/list";
	}

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(String id, ModelMap model) {

		WeixinUserPurse item = weixinUserPurseService.selectOneById(id);

		model.put("item", item);
        
		return "weixinuserpurse/edit";
	}

	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Map> updateOperation(WeixinUserPurse body) {
		if (log.isDebugEnabled())
			log.debug(WeixinUserPurseController.class + "/update->" + JSON.toJSONString(body));
		int result=0;
		Map map=new HashMap();
		WeixinUserPurse record = body;
		if (record.getId()!=null&&!record.getId().equals("")){
			record.setModify_time(new Date());
			WeixinUserPurse purse = weixinUserPurseService.selectOneById(record.getId());
			map.put("money",purse.getMoney().add(record.getMoney()));
			purse.setMoney(purse.getMoney().add(record.getMoney()));
			result= weixinUserPurseService.update(purse);
		}else {
			record.setCreate_time(new Date());
			record.setId(SnGenerator.getLongRandSn("P"));
			map.put("money",record.getMoney());
			result=weixinUserPurseService.insert(record);
		}
		map.put("code",result);
		return BaseResponse.ToJsonResult(map);
	}

	/**
	 * 列表
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("fuzzyQuery") String fuzzyQuery, HttpServletRequest request) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinUserPurseController.class + "/list->" + pageIndex + "/" + pageSize  + "/" + fuzzyQuery);
		SSOToken ssoToken = SSOHelper.getSSOToken(request);
		String cote_id="";
		if (ssoToken != null) {
			NodejsSysUser nodejsSysUser = JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")), NodejsSysUser.class);
			cote_id=nodejsSysUser.getCote_id();
		}
		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);
		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setFuzzyQuery(queryNodes);
		String finalCote_id = cote_id;
		BaseResponse<JsonResult<List<Map>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<Map>() {
			@Override
			public List<Map> query(Map<Object, Object> map) {
				map.put("cote_id", finalCote_id);
				return weixinUserPurseService.selectByJoin(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				map.put("cote_id", finalCote_id);
				return weixinUserPurseService.selectCountByJoin(map);
			}
		});
		List<Map> list = restult.getData().getList();
		for (Map obj : list) {
			if (obj.get("nickname") != null && (obj.get("nickname")+"").length() > 0)
				obj.put("nickname",EmojiFilter.filterBase64Decode(obj.get("nickname")+""));
		}
		long count = restult.getData().getTotal();
		int code = restult.getCode();
		String msg = restult.getMessage();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("code", code);
		map.put("msg", msg);
		map.put("count", count);
		map.put("data", list);
		return map;
	}
}