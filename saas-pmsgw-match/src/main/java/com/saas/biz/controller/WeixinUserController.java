package com.saas.biz.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.SyncInterface;
import com.saas.biz.service.WeixinUserService;
import com.saas.biz.util.EmojiFilter;
import com.saas.biz.util.WeChatSyncFollowerTools;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
/**
 * 微信用户管理
 * @author sunqiu
 *
 */
@Controller
@RequestMapping("/weixinuser")
public class WeixinUserController {

	private static final Logger log = LoggerFactory.getLogger(WeixinUserController.class);
	
	@Autowired
	private WxMpService wxMpService;
	@Autowired
	private SyncInterface syncInterface;

	@Autowired
	private WeixinUserService weixinUserService;


	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		return "weixinuser/list";
	}

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(String id, ModelMap model) {

		WeixinUser item = weixinUserService.selectOneById(id);

		model.put("item", item);
        
		return "weixinuser/edit";
	}

	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> updateOperation(@RequestBody WeixinUser body) {
		if (log.isDebugEnabled())
			log.debug(WeixinUserController.class + "/update->" + JSON.toJSONString(body));

		WeixinUser record = body;
		record.setClub_bind_time(new Date());
		record.setBind_time(new Date());
		record.setModify_time(new Date());
		int result = weixinUserService.update(record);

		return BaseResponse.ToJsonResult(result);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> save(WeixinUser body) {
		if (log.isDebugEnabled())
			log.debug(WeixinUserController.class + "/save->" + JSON.toJSONString(body));

		WeixinUser record = body;
		record.setCreate_time(new Date());
		record.setModify_time(new Date());
		// int result = weixinUserService.update(user);
		WeixinUser user = weixinUserService.selectOneById(record.getId());
		if (user != null)
			weixinUserService.update(record);
		else {
			record.setOpenid(record.getId());
			weixinUserService.insert(record);
		}

		return BaseResponse.ToJsonResult(1);
	}

	/**
	 * 列表
	 * 
	 * @param body
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinUserController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<WeixinUser>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<WeixinUser>() {
			@Override
			public List<WeixinUser> query(Map<Object, Object> map) {
				return weixinUserService.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return weixinUserService.selectCountByDynamic(map);
			}
		});
		List<WeixinUser> list = restult.getData().getList();
		for (WeixinUser obj : list) {
			if (obj.getNickname() != null && obj.getNickname().length() > 0)
				obj.setNickname(EmojiFilter.filterBase64Decode(obj.getNickname()));
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

	/**
	 * 同步
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws WxErrorException 
	 */
	@RequestMapping(value = "/synchronize", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse<String> save() throws UnsupportedEncodingException, WxErrorException {
		if (log.isDebugEnabled())
			log.debug(WeixinUserController.class + "/synchronize->");
		new WeChatSyncFollowerTools(wxMpService, syncInterface).setSyncCount(1000).synchronize();


		return BaseResponse.ToJsonResult("ok");
	}
}