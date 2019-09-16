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
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;
import com.saas.biz.service.NodejsCrawlerService;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;
import com.saas.task.CrawlerTask;

@Controller
@RequestMapping("/nodejsCrawler")
public class NodejsCrawlerController {

	private static final Logger log = LoggerFactory.getLogger(NodejsCrawlerController.class);

	@Autowired
	private NodejsCrawlerService nodejsCrawlerService;

	@Autowired
	private CrawlerTask crawlerTask;
	
	/**
	 * 比赛列表页面
	 * @param database
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/masterList.html", method = RequestMethod.GET)
	public String listInput(@RequestParam("database") String database,ModelMap model) {
		
		model.put("database", database);
		return "nodejsCrawler/masterList";
	}
	/**
	 * 比赛列表数据
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/masterList", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectMasterListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery) {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerController.class + "/masterList->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);
		
		BaseResponse<JsonResult<List<NodejsCrawlerMasterGame>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsCrawlerMasterGame>() {
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
				return nodejsCrawlerService.selectNodejsCrawlerMasterGameListByDynamicCount( map);
			}
		});
		List<NodejsCrawlerMasterGame> list = restult.getData().getList();
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
	 * 比赛成绩列表页面
	 * @param database
	 * @param master_id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detailList.html", method = RequestMethod.GET)
	public String viewInput(@RequestParam("database") String database,@RequestParam("master_id") String master_id, ModelMap model) {
		NodejsCrawlerMasterGame result = nodejsCrawlerService.selectNodejsCrawlerMasterGameById(database, master_id);
		model.put("item", result);
		model.put("database", database);
		return "nodejsCrawler/detailList";
	}
	/**
	 * 比赛成绩列表
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/detailList", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectDeatilListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery) {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerController.class + "/detailList->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);
		
		BaseResponse<JsonResult<List<NodejsCrawlerDetailGame>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsCrawlerDetailGame>() {
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
				return nodejsCrawlerService.selectNodejsCrawlerDetailGameListByDynamicCount( map);
			}
		});
		List<NodejsCrawlerDetailGame> list = restult.getData().getList();
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
	 * 抓取数据
	 * @param database
	 * @return
	 */
	@RequestMapping(value = "/crawler", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse<String> crawler(@RequestParam("crawler") String crawler)
	{
		crawlerTask.crawler(crawler);
		
		return BaseResponse.ToJsonResult("ok");
	}
}