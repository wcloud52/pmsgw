package com.saas.def.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import com.saas.common.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.saas.def.pojo.DefMatchItem;
import com.saas.def.service.DefMatchItemService;

@Controller
@RequestMapping("/DefMatchItem")
public class DefMatchItemController {
	protected static final Logger log = LoggerFactory.getLogger(DefMatchItemController.class);

	@Autowired
	protected DefMatchItemService sv;

	/**
	 * 插入操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertOperation(@RequestBody DefMatchItem body) {
		if (log.isDebugEnabled())
			log.debug(DefMatchItemController.class + "/insert->" + JSON.toJSONString(body));

		DefMatchItem item = body;

		int result = sv.insert(item);

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
	public BaseResponse<Integer> updateOperation(@RequestBody DefMatchItem body) {
		if (log.isDebugEnabled())
			log.debug(DefMatchItemController.class + "/update->" + JSON.toJSONString(body));

		DefMatchItem item = body;

		int result = sv.update(item);

		return BaseResponse.ToJsonResult(result);
	}

	/**
	 * 列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/query/list", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse<List<DefMatchItem>> queryListOperation() {
		if (log.isDebugEnabled())
			log.debug(DefMatchItemController.class + "/list->");
		List<DefMatchItem> list = sv.selectAll();
		return BaseResponse.ToJsonResult(list);
	}

	/**
	 * 列表条件查询
	 * 
	 * @param sort
	 * @param fuzzyQuery
	 * @return
	 */
	@RequestMapping(value = "/query/listBy", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<List<DefMatchItem>> queryListByOperation(@RequestBody DispatchesDTO <QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(DefMatchItemController.class + "/listBy->" + JSON.toJSONString(queryObject));

		List<DefMatchItem> list = PagingAndSortingRepository.findList(queryObject.getJson(), new Specification<DefMatchItem>() {

			@Override
			public List<DefMatchItem> query(Map<Object, Object> map) {
				return sv.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

		});
		return BaseResponse.ToJsonResult(list);
	}


	/**
	 * 单条查询
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/query/one", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse<DefMatchItem> queryOneOperation(String item_id) {
		if (log.isDebugEnabled())
			log.debug(DefMatchItemController.class + "/one->" + item_id);
		DefMatchItem t = sv.selectOneById(item_id);
		return BaseResponse.ToJsonResult(t);
	}

	/**
	 * 查询分页
	 * 
	 * @param queryObject
	 * @return
	 */
	@RequestMapping(value = "/query/page", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<JsonResult<List<DefMatchItem>, Object>> queryPageOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(DefMatchItemController.class + "/page->" + JSON.toJSONString(queryObject));

		BaseResponse<JsonResult<List<DefMatchItem>, Object>> result = PagingAndSortingRepository.find(queryObject.getJson(), new PageSpecification<DefMatchItem>() {
			@Override
			public List<DefMatchItem> query(Map<Object, Object> map) {
				return sv.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return sv.selectCountByDynamic(map);
			}
		});
		return result;
	}


	/**
	 * 删除操作
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/one/{id}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> deleteOperation(@PathVariable String item_id) {
		if (log.isDebugEnabled())
			log.debug(DefMatchItemController.class + "/delete/{item_id}->" + item_id);

		int result = sv.deleteById(item_id);
		return BaseResponse.ToJsonResult(result);
	}

	/**
	 * 删除操作
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete/listBy", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> deleteListOperation(@RequestBody List<String> ids) {
		if (log.isDebugEnabled())
			log.debug(DefMatchItemController.class + "/delete->" + JSON.toJSONString(ids));

		int result = sv.deleteByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}
}