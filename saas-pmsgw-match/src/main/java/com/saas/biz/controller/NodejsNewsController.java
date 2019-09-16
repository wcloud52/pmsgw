package com.saas.biz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.OpEnum;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;
import com.saas.biz.pojo.NodejsNews;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.service.NodejsNewsService;

@Controller
@RequestMapping("/nodejsNews")
public class NodejsNewsController {
	protected static final Logger log = LoggerFactory.getLogger(NodejsNewsController.class);

	@Autowired
	protected NodejsNewsService sv;

	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(@RequestParam("type") String type,ModelMap model) {
		model.put("news_type", type);
		return "nodejsNews/list";
	}
	
	@RequestMapping(value = "/view.html", method = RequestMethod.GET)
	public String viewPage(ModelMap model) {
		return "nodejsNews/view";
	}
	
	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(@RequestParam("type") String type,@RequestParam("id") String id,ModelMap model) {
		NodejsNews item = sv.selectOneById(id);
		if(item==null)
		{
			item=new NodejsNews();
			item.setNews_type(type);
		}
		model.put("item", item);
		return "nodejsNews/edit";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinUserController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNode> nodes = new ArrayList<QueryNode>();
		
		 SSOToken ssoToken = SSOHelper.getSSOToken(request); 
		 
	        if (ssoToken != null) {
	       	 NodejsSysUser nodejsSysUser =JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")),NodejsSysUser.class);
	       		
	       	 nodes.add(new QueryNode("news_senderId", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), nodejsSysUser.getId()));
	        }
			
	        QueryNodes queryNode=QueryNodes.createQueryNodes(nodes, "and");
			List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);
			queryNodes.add(queryNode);
			
		
		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsNews>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsNews>() {
			@Override
			public List<NodejsNews> query(Map<Object, Object> map) {
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
		List<NodejsNews> list = restult.getData().getList();
		
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
	public BaseResponse<Integer> insertOperation(@RequestBody NodejsNews body, HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled())
			log.debug(NodejsNewsController.class + "/insert->" + JSON.toJSONString(body));

		NodejsNews item = body;
       SSOToken ssoToken = SSOHelper.getSSOToken(request); 
		 
         if (ssoToken != null) {
        	 NodejsSysUser nodejsSysUser =JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")),NodejsSysUser.class);
        	 item.setNews_id(UUID.randomUUID().toString());
        	item.setCote_id(nodejsSysUser.getCote_id());
        	item.setCote_name(nodejsSysUser.getCote_name());
        	item.setNews_senderId(nodejsSysUser.getId());
        	item.setNews_senderName(nodejsSysUser.getUserName());
        	item.setNews_sendTime(new Date());
        	item.setNews_status(1);
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
	public BaseResponse<Integer> updateOperation(@RequestBody NodejsNews body, HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled())
			log.debug(NodejsNewsController.class + "/update->" + JSON.toJSONString(body));

		NodejsNews item = body;
       SSOToken ssoToken = SSOHelper.getSSOToken(request); 
		 
         if (ssoToken != null) {
        	 NodejsSysUser nodejsSysUser =JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")),NodejsSysUser.class);
        	
        	item.setCote_id(nodejsSysUser.getCote_id());
        	item.setCote_name(nodejsSysUser.getCote_name());
        	item.setNews_senderId(nodejsSysUser.getId());
        	item.setNews_senderName(nodejsSysUser.getUserName());
        	item.setNews_sendTime(new Date());
        	item.setNews_status(1);
        	item.setModify_time(new Date());
         }
		int result = sv.update(item);

		return BaseResponse.ToJsonResult(result);
	}

	
	
	/**
	 * 单条查询
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/query/one", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse<NodejsNews> queryOneOperation(String news_id) {
		if (log.isDebugEnabled())
			log.debug(NodejsNewsController.class + "/one->" + news_id);
		NodejsNews t = sv.selectOneById(news_id);
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
	public BaseResponse<Integer> deleteOperation(@PathVariable("id") String news_id) {
		if (log.isDebugEnabled())
			log.debug(NodejsNewsController.class + "/delete/{news_id}->" + news_id);

		int result = sv.deleteById(news_id);
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
			log.debug(NodejsNewsController.class + "/delete->" + JSON.toJSONString(ids));

		int result = sv.deleteByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}
	

}