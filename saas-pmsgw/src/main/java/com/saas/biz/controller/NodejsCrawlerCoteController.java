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
import com.saas.biz.pojo.NodejsCrawlerCote;
import com.saas.biz.service.NodejsCrawlerCoteService;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;

@Controller
@RequestMapping("/nodejsCrawlerCote")
public class NodejsCrawlerCoteController {

	private static final Logger log = LoggerFactory.getLogger(NodejsCrawlerCoteController.class);

	@Autowired
	private NodejsCrawlerCoteService nodejsCrawlerCoteService;
	
	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		return "nodejsCrawlerCote/list";
	}

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(String id,ModelMap model) {
		NodejsCrawlerCote item = nodejsCrawlerCoteService.selectOneById(id);
		if(item==null)
		{
			item=new NodejsCrawlerCote();
		}
		model.put("item", item);
		return "nodejsCrawlerCote/edit";
	}

	/**
	 * 插入操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertOperation(@RequestBody NodejsCrawlerCote body) {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerCoteController.class + "/insert->" + JSON.toJSONString(body));

		NodejsCrawlerCote item = body;
		item.setCreate_time(new Date());
		item.setModify_time(new Date());
       
		int result = nodejsCrawlerCoteService.insert(item);

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
	public BaseResponse<Integer> updateOperation(@RequestBody NodejsCrawlerCote body) {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerCoteController.class + "/update->" + JSON.toJSONString(body));

		NodejsCrawlerCote item = body;
		item.setModify_time(new Date());
		int result = nodejsCrawlerCoteService.update(item);
		
		return BaseResponse.ToJsonResult(result);
	}

	/**
	 * 删除操作
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/one/{id}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> deleteOperation(@PathVariable("id") String cote_id) {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerCoteController.class + "/delete/{cote_id}->" + cote_id);

		int result = nodejsCrawlerCoteService.deleteById(cote_id);
		return BaseResponse.ToJsonResult(result);
	}

	
	@RequestMapping(value = "/state/{cote_id}/{cote_state}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> deleteOperation(@PathVariable("cote_id") String cote_id,@PathVariable("cote_state") String cote_state) {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerCoteController.class + "/state/{cote_id}/{cote_state}->" + cote_id+"->"+cote_state);

		NodejsCrawlerCote nodejsCrawlerCote=new NodejsCrawlerCote();
		nodejsCrawlerCote.setCote_id(cote_id);
		nodejsCrawlerCote.setCote_state(cote_state);
		nodejsCrawlerCote.setModify_time(new Date());
		int result=nodejsCrawlerCoteService.update(nodejsCrawlerCote);
		return BaseResponse.ToJsonResult(result);
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
			log.debug(NodejsCrawlerCoteController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsCrawlerCote>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsCrawlerCote>() {
			@Override
			public List<NodejsCrawlerCote> query(Map<Object, Object> map) {
				return nodejsCrawlerCoteService.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return nodejsCrawlerCoteService.selectCountByDynamic(map);
			}
		});
		List<NodejsCrawlerCote> list = restult.getData().getList();
		
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
	 * 列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/query/list", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse<List<NodejsCrawlerCote>> queryListOperation() {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerCoteController.class + "/list->");
		List<NodejsCrawlerCote> list = nodejsCrawlerCoteService.selectAll();
		return BaseResponse.ToJsonResult(list);
	}
}