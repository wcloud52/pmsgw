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
import com.saas.biz.pojo.NodejsCustomerMessage;
import com.saas.biz.service.NodejsCustomerMessageService;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;

@Controller
@RequestMapping("/nodejsCustomerMessage")
public class NodejsCustomerMessageController {

	private static final Logger log = LoggerFactory.getLogger(NodejsCustomerMessageController.class);

	@Autowired
	private NodejsCustomerMessageService nodejsCustomerMessageService;
	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		return "nodejsCustomerMessage/list";
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
			log.debug(NodejsCustomerMessageController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsCustomerMessage>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsCustomerMessage>() {
			@Override
			public List<NodejsCustomerMessage> query(Map<Object, Object> map) {
				return nodejsCustomerMessageService.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return nodejsCustomerMessageService.selectCountByDynamic(map);
			}
		});
		List<NodejsCustomerMessage> list = restult.getData().getList();
		
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