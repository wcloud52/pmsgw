package com.saas.biz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.saas.biz.pojo.NodejsCrawlerCote;
import com.saas.biz.pojo.NodejsCrawlerCoteExtend;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.NodejsCrawlerCoteExtendService;
import com.saas.biz.service.NodejsCrawlerCoteService;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;

@Controller
@RequestMapping("/nodejsCrawlerCoteExtend")
public class NodejsCrawlerCoteExtendController {
	protected static final Logger log = LoggerFactory.getLogger(NodejsCrawlerCoteExtendController.class);

	
	@Autowired
	protected NodejsCrawlerCoteExtendService sv;
	
	@Autowired
	protected NodejsCrawlerCoteService nodejsCrawlerCoteService;

	
	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		return "nodejsCrawlerCoteExtend/list";
	}

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(String id,ModelMap model) {
		NodejsCrawlerCoteExtend item = sv.selectOneById(id);
		if(item==null)
		{
			item=new NodejsCrawlerCoteExtend();
			item.setSort_number(9);
		}
		model.put("item", item);
		return "nodejsCrawlerCoteExtend/edit";
	}

	/**
	 * 插入操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertOperation(@RequestBody NodejsCrawlerCoteExtend body) {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerCoteExtendController.class + "/insert->" + JSON.toJSONString(body));

		NodejsCrawlerCoteExtend item = body;
        String cote_id=item.getCote_id();
        NodejsCrawlerCote nodejsCrawlerCote=nodejsCrawlerCoteService.selectOneById(cote_id);
        if(nodejsCrawlerCote!=null)
        {
        	item.setCote_name(nodejsCrawlerCote.getCote_name());
        	item.setCote_website(nodejsCrawlerCote.getCote_website());
        	item.setCreate_time(new Date());
        	item.setModify_time(new Date());
        }
        
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
	public BaseResponse<Integer> updateOperation(@RequestBody NodejsCrawlerCoteExtend body) {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerCoteExtendController.class + "/update->" + JSON.toJSONString(body));

		NodejsCrawlerCoteExtend item = body;

		int result = sv.update(item);

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
			log.debug(NodejsCrawlerCoteExtendController.class + "/delete/{cote_id}->" + cote_id);

		int result = sv.deleteById(cote_id);
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
	public Map<Object, Object> selectListByDynamic(@RequestParam("page") Integer pageIndex,
			@RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort,
			@RequestParam("fuzzyQuery") String fuzzyQuery) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerCoteController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/"
					+ fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsCrawlerCoteExtend>, Object>> restult = PagingAndSortingRepository.find(query,
				new PageSpecification<NodejsCrawlerCoteExtend>() {
					@Override
					public List<NodejsCrawlerCoteExtend> query(Map<Object, Object> map) {
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
		List<NodejsCrawlerCoteExtend> list = restult.getData().getList();
		
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
	public BaseResponse<List<NodejsCrawlerCoteExtend>> queryListOperation() {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerCoteController.class + "/list->");
		List<NodejsCrawlerCoteExtend> list = sv.selectAll();
		return BaseResponse.ToJsonResult(list);
	}
}