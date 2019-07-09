package com.saas.biz.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;
import com.saas.common.Specification;
import com.saas.biz.pojo.NodejsCrawlerCote;
import com.saas.biz.pojo.NodejsCrawlerCoteExtend;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.NodejsCrawlerCoteService;
import com.saas.biz.service.NodejsSysUserService;
import com.saas.biz.util.EmojiFilter;

@Controller
@RequestMapping("/nodejsSysUser")
public class NodejsSysUserController {
	protected static final Logger log = LoggerFactory.getLogger(NodejsSysUserController.class);

	@Autowired
	protected NodejsSysUserService sv;
	
	@Autowired
	protected NodejsCrawlerCoteService nodejsCrawlerCoteService;

	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(ModelMap model) {
		return "nodejsSysUser/list";
	}
	
	@RequestMapping(value = "/view.html", method = RequestMethod.GET)
	public String viewPage(ModelMap model) {
		return "nodejsSysUser/view";
	}
	
	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String editPage(String id,ModelMap model) {
		NodejsSysUser item = sv.selectOneById(id);
		if(item==null)
		{
			item=new NodejsSysUser();
		}
		model.put("item", item);
		return "nodejsSysUser/edit";
	}
	
	@RequestMapping(value = "/changePwd.html", method = RequestMethod.GET)
	public String changePwdPage(ModelMap model,HttpServletRequest request,HttpServletResponse response) {
		SSOToken ssoToken = SSOHelper.getSSOToken(request);
        if (ssoToken != null) {
       	 NodejsSysUser nodejsSysUser =JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")),NodejsSysUser.class);  			 
       	 model.put("currentUser", nodejsSysUser);
        }
		return "nodejsSysUser/changePwd";
	}
	@RequestMapping(value = "/changePwd", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> changePwdOperation(@RequestBody NodejsSysUser body) {
		if (log.isDebugEnabled())
			log.debug(NodejsSysUserController.class + "/changePwd->" + JSON.toJSONString(body));

		NodejsSysUser item = body;

		int result = sv.update(item);

		return BaseResponse.ToJsonResult(result);
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinUserController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsSysUser>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsSysUser>() {
			@Override
			public List<NodejsSysUser> query(Map<Object, Object> map) {
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
		List<NodejsSysUser> list = restult.getData().getList();
		
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
	public BaseResponse<Integer> insertOperation(@RequestBody NodejsSysUser body) {
		if (log.isDebugEnabled())
			log.debug(NodejsSysUserController.class + "/insert->" + JSON.toJSONString(body));

		NodejsSysUser item = body;
		item.setId(item.getLoginName());
		 String cote_id=item.getCote_id();
	        NodejsCrawlerCote nodejsCrawlerCote=nodejsCrawlerCoteService.selectOneById(cote_id);
	        if(nodejsCrawlerCote!=null)
	        {
	        	item.setCote_name(nodejsCrawlerCote.getCote_name());
	        	item.setPassword("123456");
	        	item.setStatus(1);
	        	item.setCreatedDatetime(new Date());
	        	item.setUpdatedDatetime(new Date());
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
	public BaseResponse<Integer> updateOperation(@RequestBody NodejsSysUser body) {
		if (log.isDebugEnabled())
			log.debug(NodejsSysUserController.class + "/update->" + JSON.toJSONString(body));

		NodejsSysUser item = body;
		String cote_id=item.getCote_id();
        NodejsCrawlerCote nodejsCrawlerCote=nodejsCrawlerCoteService.selectOneById(cote_id);
        if(nodejsCrawlerCote!=null)
        {
        	item.setCote_name(nodejsCrawlerCote.getCote_name());
        	
        	item.setUpdatedDatetime(new Date());
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
	public BaseResponse<NodejsSysUser> queryOneOperation(String id) {
		if (log.isDebugEnabled())
			log.debug(NodejsSysUserController.class + "/one->" + id);
		NodejsSysUser t = sv.selectOneById(id);
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
	public BaseResponse<Integer> deleteOperation(@PathVariable String id) {
		if (log.isDebugEnabled())
			log.debug(NodejsSysUserController.class + "/delete/{id}->" + id);

		int result = sv.deleteById(id);
		return BaseResponse.ToJsonResult(result);
	}

}