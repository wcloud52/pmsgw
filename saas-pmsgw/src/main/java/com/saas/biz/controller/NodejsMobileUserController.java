package com.saas.biz.controller;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.math.BigDecimal;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.saas.biz.pojo.NodejsPigeonCollection;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.util.EmojiFilter;
import com.saas.biz.util.SnGenerator;
import com.saas.common.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.saas.biz.pojo.NodejsMobileUser;
import com.saas.biz.service.NodejsMobileUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/nodejsMobileUser")
public class NodejsMobileUserController {
	protected static final Logger log = LoggerFactory.getLogger(NodejsMobileUserController.class);

	@Autowired
	protected NodejsMobileUserService sv;

	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput( ModelMap model) {
		return "nodejsMobileUser/list";
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinUserController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNode> nodes = new ArrayList<QueryNode>();

	/*	SSOToken ssoToken = SSOHelper.getSSOToken(request);

		if (ssoToken != null) {
			NodejsSysUser nodejsSysUser = JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")), NodejsSysUser.class);

			nodes.add(new QueryNode("collection_senderId", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), nodejsSysUser.getId()));
		}*/

		QueryNodes queryNode = QueryNodes.createQueryNodes(nodes, "and");
		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);
		queryNodes.add(queryNode);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsMobileUser>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsMobileUser>() {
			@Override
			public List<NodejsMobileUser> query(Map<Object, Object> map) {
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
		List<NodejsMobileUser> list = restult.getData().getList();
		/*for (NodejsMobileUser obj : list) {
			if (obj.getNickname() != null && obj.getNickname().length() > 0)
				obj.setNickname(EmojiFilter.filterBase64Decode(obj.getNickname()));
		}*/
		long count = restult.getData().getTotal();
		int code = restult.getCode();
		String msg = restult.getMessage();
		Map<Object, Object> map = new HashMap <Object, Object> ();
		map.put("code", code);
		map.put("msg", msg);
		map.put("count", count);
		map.put("data", list);
		return map;
	}

	@RequestMapping(value = "/insertBatch", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertBatcOperation(@RequestBody List<NodejsMobileUser> list, HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled())
			log.debug(NodejsPigeonCollectionController.class + "/insertBatch->" + JSON.toJSONString(list));

			long batchNumber = SnGenerator.getLongSn();
			int sortNumber = 0;

			String coteId="";
			for (NodejsMobileUser item : list) {
				String id = SnGenerator.getSn( "");
				item.setId (id);

				item.setState ("1");
				item.setBatchNumber(batchNumber);
				item.setSortNumber(sortNumber);
				item.setCreate_time(new Date());
				item.setModify_time(new Date());

				sortNumber++;

				coteId=item.getCote_id ();
			}

        sv.deleteByCoteId (coteId);
		int result = sv.insertBatch(list);

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
			log.debug(NodejsMobileUserController.class + "/delete->" + JSON.toJSONString(ids));

		int result = sv.deleteByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}
}