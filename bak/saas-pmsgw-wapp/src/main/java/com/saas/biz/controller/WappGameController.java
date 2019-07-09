package com.saas.biz.controller;

import java.io.UnsupportedEncodingException;
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
import com.saas.biz.pojo.WappGame;
import com.saas.biz.pojo.WappRuleMaster;
import com.saas.biz.service.WappGameService;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;

/**
 * 比赛定义
 * @author tanjun
 *
 */
@Controller
@RequestMapping("/wappGame")
public class WappGameController {

	private static final Logger log = LoggerFactory.getLogger(WappGameController.class);
	
	@Autowired
	private WappGameService wappGameService;


	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		return "wappgame/list";
	}

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(String id, ModelMap model) {

		WappGame item = wappGameService.selectOneWappGameById(id);

		model.put("item", item);

		return "wappgame/edit";
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
		WappGame item = wappGameService.selectOneWappGameById(id);

		model.put("item", item);
		return "wappgame/view";
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
			log.debug(WappGameController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<WappGame>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<WappGame>() {
			@Override
			public List<WappGame> query(Map<Object, Object> map) {
				return wappGameService.selectWappGameListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return wappGameService.selectWappGameCountByDynamic(map);
			}
		});
		List<WappGame> list = restult.getData().getList();
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
	public BaseResponse<Integer> insertOperation(@RequestBody WappGame body) {
		if (log.isDebugEnabled())
			log.debug(WappGameController.class + "/insert->" + JSON.toJSONString(body));

		WappGame item = body;
		
		int result = wappGameService.insertWappGame(item);

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
	public BaseResponse<Integer> updateOperation(@RequestBody WappGame body) {
		if (log.isDebugEnabled())
			log.debug(WappGameController.class + "/update->" + JSON.toJSONString(body));

		WappGame item = body;
		
		int result = wappGameService.updateWappGame(item);
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
			log.debug(WappGameController.class + "/delete/{id}->" + id);

		int result = wappGameService.deleteWappGame(id);
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
			log.debug(WappGameController.class + "/delete->" + JSON.toJSONString(ids));

		int result = wappGameService.deleteWappGameByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}

}