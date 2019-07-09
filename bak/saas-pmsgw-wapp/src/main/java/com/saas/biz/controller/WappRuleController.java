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
import com.saas.biz.pojo.WappRuleDetail;
import com.saas.biz.pojo.WappRuleMaster;
import com.saas.biz.service.WappRuleService;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;

/**
 * 规则定义
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/wappRule")
public class WappRuleController {

	private static final Logger log = LoggerFactory.getLogger(WappRuleController.class);
	
	@Autowired
	private WappRuleService wappRuleService;


	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		return "wapprule/list";
	}

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(String id, ModelMap model) {

		WappRuleMaster item = wappRuleService.selectOneWappRuleMasterById(id);

		model.put("item", item);

		return "wapprule/edit";
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
		WappRuleMaster item = wappRuleService.selectOneWappRuleMasterById(id);

		model.put("item", item);
		return "wapprule/view";
	}
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse<List<WappRuleMaster>> queryOperation()
	 {
		List<WappRuleMaster> list=wappRuleService.selectAllWappRuleMaster();
		return BaseResponse.ToJsonResult(list);
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
			log.debug(WappRuleController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<WappRuleMaster>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<WappRuleMaster>() {
			@Override
			public List<WappRuleMaster> query(Map<Object, Object> map) {
				return wappRuleService.selectWappRuleMasterListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return wappRuleService.selectWappRuleMasterCountByDynamic(map);
			}
		});
		List<WappRuleMaster> list = restult.getData().getList();
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
	 * 插入操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertOperation(@RequestBody WappRuleMaster body) {
		if (log.isDebugEnabled())
			log.debug(WappRuleController.class + "/insert->" + JSON.toJSONString(body));

		WappRuleMaster item = body;
		item.setCreatedDatetime(new Date());
		item.setUpdateDatetime(new Date());
		int result = wappRuleService.insertWappRuleMaster(item);

		return BaseResponse.ToJsonResult(result);
	}

	/**
	 * 更新操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> updateOperation(@RequestBody WappRuleMaster body) {
		if (log.isDebugEnabled())
			log.debug(WappRuleController.class + "/update->" + JSON.toJSONString(body));

        WappRuleMaster item = body;
		item.setUpdateDatetime(new Date());
		int result = wappRuleService.updateWappRuleMaster(item);

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
	public BaseResponse<Integer> deleteOperation(@PathVariable String id) {
		if (log.isDebugEnabled())
			log.debug(WappRuleController.class + "/delete/{id}->" + id);

		int result = wappRuleService.deleteWappRuleMaster(id);
		return BaseResponse.ToJsonResult(result);
	}

	/**
	 * 删除操作
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> deleteListOperation(@RequestBody List<String> ids) {
		if (log.isDebugEnabled())
			log.debug(WappRuleController.class + "/delete->" + JSON.toJSONString(ids));

		int result = wappRuleService.deleteWappRuleMasterByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}

	
	/**
	 * 规则明细列表页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detailList.html", method = RequestMethod.GET)
	public String detailListPage(String id, ModelMap model) {

		WappRuleMaster item = wappRuleService.selectOneWappRuleMasterById(id);

		model.put("item", item);

		return "wapprule/detailList";
	}
	
	@RequestMapping(value = "/detailEdit.html", method = RequestMethod.GET)
	public String detailEditPage(String id,String masterId, ModelMap model) {

		WappRuleDetail item = wappRuleService.selectOneWappRuleDetailById(id);
		item.setMasterId(masterId);
		model.put("item", item);

		return "wapprule/detailEdit";
	}
	/**
	 * 查看页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detailView.html", method = RequestMethod.GET)
	public String detailViewPage(String id,String masterId, ModelMap model) {
		WappRuleDetail item = wappRuleService.selectOneWappRuleDetailById(id);
		item.setMasterId(masterId);
		model.put("item", item);
		return "wapprule/detailView";
	}
	
	@RequestMapping(value = "/detaillist", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> detaillist(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WappRuleController.class + "/detaillist->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<WappRuleDetail>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<WappRuleDetail>() {
			@Override
			public List<WappRuleDetail> query(Map<Object, Object> map) {
				return wappRuleService.selectWappRuleDetailListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return wappRuleService.selectWappRuleDetailCountByDynamic(map);
			}
		});
		List<WappRuleDetail> list = restult.getData().getList();
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
	 * 插入操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/detailinsert", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> detailinsertOperation(@RequestBody WappRuleDetail body) {
		if (log.isDebugEnabled())
			log.debug(WappRuleController.class + "/detailinsert->" + JSON.toJSONString(body));

		WappRuleDetail item = body;
		item.setCreatedDatetime(new Date());
		item.setUpdateDatetime(new Date());
		int result = wappRuleService.insertWappRuleDetail(item);

		return BaseResponse.ToJsonResult(result);
	}

	/**
	 * 更新操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/detailupdate", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> detailupdateOperation(@RequestBody WappRuleDetail body) {
		if (log.isDebugEnabled())
			log.debug(WappRuleController.class + "/detailupdate->" + JSON.toJSONString(body));

		WappRuleDetail item = body;
		item.setUpdateDatetime(new Date());
		int result = wappRuleService.updateWappRuleDetail(item);

		return BaseResponse.ToJsonResult(result);
	}
	
	/**
	 * 删除操作
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detaildelete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> detaildeleteOperation(@PathVariable String id) {
		if (log.isDebugEnabled())
			log.debug(WappRuleController.class + "/delete/{id}->" + id);

		int result = wappRuleService.deleteWappRuleDetail(id);
		return BaseResponse.ToJsonResult(result);
	}

	/**
	 * 删除操作
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/detaildelete", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> detaildeleteListOperation(@RequestBody List<String> ids) {
		if (log.isDebugEnabled())
			log.debug(WappRuleController.class + "/delete->" + JSON.toJSONString(ids));

		int result = wappRuleService.deleteWappRuleDetailByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}

}