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
import java.io.UnsupportedEncodingException;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.OpEnum;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;
import com.saas.biz.pojo.NodejsPigeonCollection;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.NodejsPigeonCollectionService;
import com.saas.biz.util.EmojiFilter;
import com.saas.biz.util.SmsUtil;
import com.saas.biz.util.SnGenerator;

@Controller
@RequestMapping("/nodejsPigeonCollection")
public class NodejsPigeonCollectionController {
	protected static final Logger log = LoggerFactory.getLogger(NodejsPigeonCollectionController.class);

	@Autowired
	protected NodejsPigeonCollectionService sv;

	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public String listInput(@RequestParam("type") String type, ModelMap model) {

		model.put("typeId", type);
		return "nodejsPigeonCollection/list";
	}

	/**
	 * 插入操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertOperation(@RequestBody NodejsPigeonCollection body) {
		if (log.isDebugEnabled())
			log.debug(NodejsPigeonCollectionController.class + "/insert->" + JSON.toJSONString(body));

		NodejsPigeonCollection item = body;

		int result = sv.insert(item);

		return BaseResponse.ToJsonResult(result);
	}

	@RequestMapping(value = "/insertBatch", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertBatcOperation(@RequestBody List<NodejsPigeonCollection> list, HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled())
			log.debug(NodejsPigeonCollectionController.class + "/insertBatch->" + JSON.toJSONString(list));
		SSOToken ssoToken = SSOHelper.getSSOToken(request);

		if (ssoToken != null) {
			NodejsSysUser nodejsSysUser = JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")), NodejsSysUser.class);

			long batchNumber = SnGenerator.getLongSn();
			int sortNumber = 0;
			for (NodejsPigeonCollection item : list) {
				String collection_id = SnGenerator.getSn(item.getTypeId() + "-");
				item.setCollection_id(collection_id);
				item.setCote_id(nodejsSysUser.getCote_id());
				item.setCote_name(nodejsSysUser.getCote_name());
				item.setCollection_senderId(nodejsSysUser.getId());
				item.setCollection_senderName(nodejsSysUser.getUserName());
				// item.setCollection_sendTime(new DateTime(new Date()).toString("yyyy-MM-dd
				// HH:mm:ss"));
				item.setStatus(0);
				item.setBatchNumber(batchNumber);
				item.setSortNumber(sortNumber);
				item.setCreate_time(new Date());
				item.setModify_time(new Date());

				sortNumber++;
			}

		}

		int result = sv.insertBatch(list);

		return BaseResponse.ToJsonResult(result);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> selectListByDynamic(@RequestParam("page") Integer pageIndex, @RequestParam("limit") Integer pageSize, @RequestParam("sort") String sort, @RequestParam("fuzzyQuery") String fuzzyQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		if (log.isDebugEnabled())
			log.debug(WeixinUserController.class + "/list->" + pageIndex + "/" + pageSize + "/" + sort + "/" + fuzzyQuery);

		List<QueryNode> nodes = new ArrayList<QueryNode>();

		SSOToken ssoToken = SSOHelper.getSSOToken(request);

		if (ssoToken != null) {
			NodejsSysUser nodejsSysUser = JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")), NodejsSysUser.class);

			nodes.add(new QueryNode("collection_senderId", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), nodejsSysUser.getId()));
		}

		QueryNodes queryNode = QueryNodes.createQueryNodes(nodes, "and");
		List<QueryNodes> queryNodes = JSON.parseArray(fuzzyQuery, QueryNodes.class);
		queryNodes.add(queryNode);

		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(sort);
		query.setFuzzyQuery(queryNodes);

		BaseResponse<JsonResult<List<NodejsPigeonCollection>, Object>> restult = PagingAndSortingRepository.find(query, new PageSpecification<NodejsPigeonCollection>() {
			@Override
			public List<NodejsPigeonCollection> query(Map<Object, Object> map) {
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
		List<NodejsPigeonCollection> list = restult.getData().getList();
		for (NodejsPigeonCollection obj : list) {
			if (obj.getNickname() != null && obj.getNickname().length() > 0)
				obj.setNickname(EmojiFilter.filterBase64Decode(obj.getNickname()));
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

	/**
	 * 单条查询
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/query/one", method = RequestMethod.GET)
	@ResponseBody
	public BaseResponse<NodejsPigeonCollection> queryOneOperation(String collection_id) {
		if (log.isDebugEnabled())
			log.debug(NodejsPigeonCollectionController.class + "/one->" + collection_id);
		NodejsPigeonCollection t = sv.selectOneById(collection_id);
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
	public BaseResponse<Integer> deleteOperation(@PathVariable String collection_id) {
		if (log.isDebugEnabled())
			log.debug(NodejsPigeonCollectionController.class + "/delete/{collection_id}->" + collection_id);

		int result = sv.deleteById(collection_id);
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
			log.debug(NodejsPigeonCollectionController.class + "/delete->" + JSON.toJSONString(ids));

		int result = sv.deleteByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}

	@RequestMapping(value = "/sendSms", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> sendSms(@RequestParam("type") String type,HttpServletRequest request, HttpServletResponse response) throws JSONException, HTTPException, IOException {
		SSOToken ssoToken = SSOHelper.getSSOToken(request);

		if (ssoToken != null) {
			NodejsSysUser nodejsSysUser = JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")), NodejsSysUser.class);
			String cote_id = nodejsSysUser.getCote_id();
			List<Map<String, String>> list = sv.selectMapByCoteId(cote_id,type);
			if (list != null && list.size() > 0) {
                for(Map<String, String> map :list)
                {
                	String pigowner=map.get("pigowner");
                	String pigowner_mobile=map.get("pigowner_mobile");
                	String ringnum=map.get("ringnum"); 
                	SmsUtil.sendSms(pigowner_mobile, pigowner, ringnum);
                }
			}
			sv.updateStatusByCoteId(cote_id,type);
		}
		return BaseResponse.ToJsonResult(1);
	}

}
