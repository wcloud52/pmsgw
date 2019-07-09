package com.saas.biz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.saas.biz.pojo.NodejsCustomerTextMessage;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.pojo.NodejsWeixinUserCote;
import com.saas.biz.service.NodejsCustomerTextMessageService;
import com.saas.biz.util.EmojiFilter;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.OpEnum;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;

@Controller
@RequestMapping("/nodejsGroupCustomerTextMessage")
public class NodejsGroupCustomerTextMessageController {
	protected static final Logger log = LoggerFactory.getLogger(NodejsGroupCustomerTextMessageController.class);

	@Autowired
	protected NodejsCustomerTextMessageService sv;


	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		return "nodejsGroupCustomerTextMessage/list";
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
	public Map<Object, Object> selectListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(NodejsCrawlerCoteController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		
		 SSOToken ssoToken = SSOHelper.getSSOToken(request); 
		 
        if (ssoToken != null) {
       	 NodejsSysUser nodejsSysUser =JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")),NodejsSysUser.class);
       		
       	 nodes.add(new QueryNode("cote_id", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), nodejsSysUser.getCote_id()));
        }
		
        QueryNodes queryNode=QueryNodes.createQueryNodes(nodes, "and");
		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);
		queryNodes.add(queryNode);
		
		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsCustomerTextMessage>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsCustomerTextMessage>() {
			@Override
			public List<NodejsCustomerTextMessage> query(Map<Object, Object> map) {
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
		List<NodejsCustomerTextMessage> list = restult.getData().getList();
		for (NodejsCustomerTextMessage obj : list) {
			if (obj.getMessage_receiverName() != null && obj.getMessage_receiverName().length() > 0)
				obj.setMessage_receiverName(EmojiFilter.filterBase64Decode(obj.getMessage_receiverName()));
		}
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