package com.saas.biz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.saas.common.BaseResponse;
import com.saas.common.DispatchesDTO;
import com.saas.common.JsonResult;
import com.saas.common.OpEnum;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;
import com.saas.common.Specification;
import com.saas.biz.pojo.NodejsMatch;
import com.saas.biz.pojo.NodejsNews;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.service.NodejsMatchService;
import com.saas.biz.util.SnGenerator;

@Controller
@RequestMapping("/nodejsMatch")
public class NodejsMatchController {
	protected static final Logger log = LoggerFactory.getLogger(NodejsMatchController.class);

	@Autowired
	protected NodejsMatchService sv;

	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		
		return "nodejsMatch/list";
	}
	
	@RequestMapping(value = "/view.html", method = RequestMethod.GET)
	public String viewPage(ModelMap model) {
		return "nodejsMatch/view";
	}
	
	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(@RequestParam("id") String id,ModelMap model) {
		NodejsMatch item = sv.selectOneById(id);
		if(item==null)
		{
			item=new NodejsMatch();
			
		}
		model.put("item", item);
		return "nodejsMatch/edit";
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		
		 SSOToken ssoToken = SSOHelper.getSSOToken(request); 
		 
	        if (ssoToken != null) {
	       	 NodejsSysUser nodejsSysUser =JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")),NodejsSysUser.class);
	       		
	       	 nodes.add(new QueryNode("match_senderId", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), nodejsSysUser.getId()));
	        }
			
	        QueryNodes queryNode=QueryNodes.createQueryNodes(nodes, "and");
			List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);
			queryNodes.add(queryNode);
			
		
		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsMatch>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsMatch>() {
			@Override
			public List<NodejsMatch> query(Map<Object, Object> map) {
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
		List<NodejsMatch> list = restult.getData().getList();
		
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
	public BaseResponse<Integer> insertOperation(@RequestBody NodejsMatch body, HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchController.class + "/insert->" + JSON.toJSONString(body));

		NodejsMatch item = body;
 SSOToken ssoToken = SSOHelper.getSSOToken(request); 
		 
         if (ssoToken != null) {
        	 NodejsSysUser nodejsSysUser =JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")),NodejsSysUser.class);
        	 String match_id=SnGenerator.getSn("M");
			item.setMatch_id(match_id);
        	item.setCote_id(nodejsSysUser.getCote_id());
        	item.setCote_name(nodejsSysUser.getCote_name());
        	item.setMatch_senderId(nodejsSysUser.getId());
        	item.setMatch_senderName(nodejsSysUser.getUserName());
        	item.setMatch_sendTime(new Date());
        	item.setMatch_status(1);
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
	public BaseResponse<Integer> updateOperation(@RequestBody NodejsMatch body, HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchController.class + "/update->" + JSON.toJSONString(body));

		NodejsMatch item = body;
		 SSOToken ssoToken = SSOHelper.getSSOToken(request); 
				 
		         if (ssoToken != null) {
		        	 NodejsSysUser nodejsSysUser =JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")),NodejsSysUser.class);
		        	 String match_id=SnGenerator.getSn("M");
					item.setMatch_id(match_id);
		        	item.setCote_id(nodejsSysUser.getCote_id());
		        	item.setCote_name(nodejsSysUser.getCote_name());
		        	item.setMatch_senderId(nodejsSysUser.getId());
		        	item.setMatch_senderName(nodejsSysUser.getUserName());
		        	item.setMatch_sendTime(new Date());
		        	item.setMatch_status(1);
		        	
		        	item.setModify_time(new Date());
		         }

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
	public BaseResponse<List<NodejsMatch>> queryListOperation() {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchController.class + "/list->");
		List<NodejsMatch> list = sv.selectAll();
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
	public BaseResponse<List<NodejsMatch>> queryListByOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchController.class + "/listBy->" + JSON.toJSONString(queryObject));

		List<NodejsMatch> list = PagingAndSortingRepository.findList(queryObject.getJson(), new Specification<NodejsMatch>() {

			@Override
			public List<NodejsMatch> query(Map<Object, Object> map) {
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
	public BaseResponse<NodejsMatch> queryOneOperation(String match_id) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchController.class + "/one->" + match_id);
		NodejsMatch t = sv.selectOneById(match_id);
		return BaseResponse.ToJsonResult(t);
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
			log.debug(NodejsMatchController.class + "/delete/{match_id}->" + match_id);

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
			log.debug(NodejsMatchController.class + "/delete->" + JSON.toJSONString(ids));

		int result = sv.deleteByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}
}