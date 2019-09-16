package com.saas.biz.controller;

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
import com.saas.biz.pojo.PmsgwGameDetail;
import com.saas.biz.pojo.PmsgwGameMaster;
import com.saas.biz.service.PmsgwGameDetailService;
import com.saas.biz.service.PmsgwGameMasterService;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;

@Controller
@RequestMapping("/pmsgwgame")
public class PmsgwGameController {

	private static final Logger log = LoggerFactory.getLogger(PmsgwGameController.class);

	@Autowired
	private PmsgwGameMasterService pmsgwGameMasterService;

	@Autowired
	private PmsgwGameDetailService pmsgwGameDetailService;

	
	
	@RequestMapping(value = "/masterList.html", method = RequestMethod.GET)
	public String listInput(@RequestParam("website") String website,ModelMap model) {
		if(!"all".equals(website))
		model.put("website", website);
		return "pmsgwgame/masterList";
	}
	
	/**
	 * 列表
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/masterList", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectMasterListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery) {
		if (log.isDebugEnabled())
			log.debug(WeixinUserController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);
		
		BaseResponse<JsonResult<List<PmsgwGameMaster>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<PmsgwGameMaster>() {
			@Override
			public List<PmsgwGameMaster> query(Map<Object, Object> map) {
				return pmsgwGameMasterService.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return pmsgwGameMasterService.selectListByDynamicCount( map);
			}
		});
		List<PmsgwGameMaster> list = restult.getData().getList();
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
	
	@RequestMapping(value = "/detailList.html", method = RequestMethod.GET)
	public String viewInput(Long id, ModelMap model) {
		PmsgwGameMaster result = pmsgwGameMasterService.selectOneById(id);
		model.put("item", result);
		model.put("title", "test");
		return "pmsgwgame/detailList";
	}
	/**
	 * 列表
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/detailList", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectDeatilListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery) {
		if (log.isDebugEnabled())
			log.debug(WeixinUserController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);
		
		BaseResponse<JsonResult<List<PmsgwGameDetail>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<PmsgwGameDetail>() {
			@Override
			public List<PmsgwGameDetail> query(Map<Object, Object> map) {
				return pmsgwGameDetailService.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return pmsgwGameDetailService.selectListByDynamicCount( map);
			}
		});
		List<PmsgwGameDetail> list = restult.getData().getList();
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