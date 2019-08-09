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
import com.saas.def.pojo.DefMatch;
import com.saas.def.service.DefMatchService;

@Controller
@RequestMapping("/DefMatch")
public class DefMatchController {
	protected static final Logger log = LoggerFactory.getLogger(DefMatchController.class);

	@Autowired
	protected DefMatchService sv;

	/**
	 * 插入操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertOperation(@RequestBody DefMatch body) {
		if (log.isDebugEnabled())
			log.debug(DefMatchController.class + "/insert->" + JSON.toJSONString(body));

		DefMatch item = body;

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
	public BaseResponse<Integer> updateOperation(@RequestBody DefMatch body) {
		if (log.isDebugEnabled())
			log.debug(DefMatchController.class + "/update->" + JSON.toJSONString(body));

		DefMatch item = body;

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
	public BaseResponse<List<DefMatch>> queryListOperation() {
		if (log.isDebugEnabled())
			log.debug(DefMatchController.class + "/list->");
		List<DefMatch> list = sv.selectAll();
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
	public BaseResponse<List<DefMatch>> queryListByOperation(@RequestBody DispatchesDTO <QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(DefMatchController.class + "/listBy->" + JSON.toJSONString(queryObject));

		List<DefMatch> list = PagingAndSortingRepository.findList(queryObject.getJson(), new Specification<DefMatch>() {

			@Override
			public List<DefMatch> query(Map<Object, Object> map) {
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
	public BaseResponse<DefMatch> queryOneOperation(String match_id) {
		if (log.isDebugEnabled())
			log.debug(DefMatchController.class + "/one->" + match_id);
		DefMatch t = sv.selectOneById(match_id);
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
	public BaseResponse<JsonResult<List<DefMatch>, Object>> queryPageOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(DefMatchController.class + "/page->" + JSON.toJSONString(queryObject));

		BaseResponse<JsonResult<List<DefMatch>, Object>> result = PagingAndSortingRepository.find(queryObject.getJson(), new PageSpecification<DefMatch>() {
			@Override
			public List<DefMatch> query(Map<Object, Object> map) {
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
	public BaseResponse<Integer> deleteOperation(@PathVariable String match_id) {
		if (log.isDebugEnabled())
			log.debug(DefMatchController.class + "/delete/{match_id}->" + match_id);

		int result = sv.deleteById(match_id);
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
			log.debug(DefMatchController.class + "/delete->" + JSON.toJSONString(ids));

		int result = sv.deleteByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}
}