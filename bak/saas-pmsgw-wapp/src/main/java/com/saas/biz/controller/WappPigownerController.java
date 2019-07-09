package com.saas.biz.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.saas.biz.pojo.WappPigowner;
import com.saas.biz.service.WappPigownerService;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;

/**
 * 我的鸽子定义
 * 
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/wappPigowner")
public class WappPigownerController {

	private static final Logger log = LoggerFactory.getLogger(WappPigownerController.class);

	@Autowired
	private WappPigownerService WappPigownerService;

	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		return "WappPigowner/list";
	}

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(String id, ModelMap model) {

		WappPigowner item = WappPigownerService.selectOneWappPigownerById(id);

		model.put("item", item);

		return "WappPigowner/edit";
	}

	/**
	 * 查看页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view.html", method = RequestMethod.GET)
	public String viewPage(String id, ModelMap model) {
		WappPigowner item = WappPigownerService.selectOneWappPigownerById(id);

		model.put("item", item);
		return "WappPigowner/view";
	}

	/**
	 * 列表
	 * 
	 * @param body
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectPage(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WappPigownerController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<WappPigowner>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<WappPigowner>() {
			@Override
			public List<WappPigowner> query(Map<Object, Object> map) {
				return WappPigownerService.selectWappPigownerListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return WappPigownerService.selectWappPigownerCountByDynamic(map);
			}
		});
		List<WappPigowner> list = restult.getData().getList();
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse<List<WappPigowner>> selectListByDynamic() throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WappPigownerController.class + "/list->");

		List<WappPigowner> list = WappPigownerService.selectAllWappPigowner();

		return BaseResponse.ToJsonResult(list);
	}

	@RequestMapping(value = "/one/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse<WappPigowner> selectOne(@PathVariable("id") String id) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WappPigownerController.class + "/one->" + id);

		WappPigowner obj = WappPigownerService.selectOneWappPigownerById(id);

		return BaseResponse.ToJsonResult(obj);
	}

	/**
	 * 插入/更新操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertOperation(@RequestHeader("X-Litemall-UserId") String userId, @RequestBody WappPigowner body) {
		if (log.isDebugEnabled())
			log.debug(WappPigownerController.class + "/save->" + JSON.toJSONString(body));

		WappPigowner item = body;
		item.setPigowner(userId);
		item.setRingnum(item.getName());
		item.setCreatedBy(userId);
		item.setCreatedDatetime(new Date());
		item.setUpdateBy(userId);
		item.setUpdateDatetime(new Date());
		int result = 0;
		if ("0".equals(item.getId())) {
			item.setId(UUID.randomUUID().toString());
			result = WappPigownerService.insertWappPigowner(item);
		} else {
			result = WappPigownerService.updateWappPigowner(item);
		}

		return BaseResponse.ToJsonResult(result);
	}

	
	/**
	 * 删除操作
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> deleteOperation(@PathVariable("id") String id) {
		if (log.isDebugEnabled())
			log.debug(WappPigownerController.class + "/delete/{id}->" + id);

		int result = WappPigownerService.deleteWappPigowner(id);
		return BaseResponse.ToJsonResult(result);
	}

}