package com.saas.biz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.security.token.SSOToken;
import com.saas.biz.pojo.NodejsMatchPigeonCollection;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.util.SnGenerator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
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
import com.saas.biz.pojo.NodejsMatchPigeonCollection;
import com.saas.biz.service.NodejsMatchPigeonCollectionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/NodejsMatchPigeonCollection")
public class NodejsMatchPigeonCollectionController {
	protected static final Logger log = LoggerFactory.getLogger(NodejsMatchPigeonCollectionController.class);

	@Autowired
	protected NodejsMatchPigeonCollectionService sv;

	/**
	 * 插入操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertOperation(@RequestBody NodejsMatchPigeonCollection body) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchPigeonCollectionController.class + "/insert->" + JSON.toJSONString(body));

		NodejsMatchPigeonCollection item = body;

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
	public BaseResponse<Integer> updateOperation(@RequestBody NodejsMatchPigeonCollection body) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchPigeonCollectionController.class + "/update->" + JSON.toJSONString(body));

		NodejsMatchPigeonCollection item = body;

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
	public BaseResponse<List<NodejsMatchPigeonCollection>> queryListOperation() {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchPigeonCollectionController.class + "/list->");
		List<NodejsMatchPigeonCollection> list = sv.selectAll();
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
	public BaseResponse<List<NodejsMatchPigeonCollection>> queryListByOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchPigeonCollectionController.class + "/listBy->" + JSON.toJSONString(queryObject));

		List<NodejsMatchPigeonCollection> list = PagingAndSortingRepository.findList(queryObject.getJson(), new Specification<NodejsMatchPigeonCollection>() {

			@Override
			public List<NodejsMatchPigeonCollection> query(Map<Object, Object> map) {
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
	 * 列表条件查询
	 * 
	 * @param sort
	 * @param fuzzyQuery
	 * @return
	 */
	@RequestMapping(value = "/query/listExt", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<List<NodejsMatchPigeonCollection>> queryListExtOperation(
	   @RequestParam(required=false)  String collection_id,		
	   @RequestParam(required=false)  String match_id,		
	   @RequestParam(required=false)  String cote_id,		
	   @RequestParam(required=false)  String cote_name,		
	   @RequestParam(required=false)  String collection_senderId,		
	   @RequestParam(required=false)  String collection_senderName,		
	   @RequestParam(required=false)  String collection_sendTime,		
	   @RequestParam(required=false)  String ringnum,		
	   @RequestParam(required=false)  String pigowner,		
	   @RequestParam(required=false)  String pigowner_num,		
	   @RequestParam(required=false)  String pigeon_color,		
	   @RequestParam(required=false)  String pigeon_ext,		
	   @RequestParam(required=false)  String fileName,		
	   @RequestParam(required=false)  String typeId,		
	   @RequestParam(required=false)  String typeName,		
	   @RequestParam(required=false)  Date create_time,		
	   @RequestParam(required=false)  Date modify_time		
	) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchPigeonCollectionController.class + "/query/listExt->");
		List<QueryNode> nodes = new ArrayList<QueryNode>();
	/*		
       if(StringUtils.isNotBlank(collection_id))
       {
         nodes.add(new QueryNode("collection_id", collection_id));
       }	
       if(StringUtils.isNotBlank(match_id))
       {
         nodes.add(new QueryNode("match_id", match_id));
       }	
       if(StringUtils.isNotBlank(cote_id))
       {
         nodes.add(new QueryNode("cote_id", cote_id));
       }	
       if(StringUtils.isNotBlank(cote_name))
       {
         nodes.add(new QueryNode("cote_name", cote_name));
       }	
       if(StringUtils.isNotBlank(collection_senderId))
       {
         nodes.add(new QueryNode("collection_senderId", collection_senderId));
       }	
       if(StringUtils.isNotBlank(collection_senderName))
       {
         nodes.add(new QueryNode("collection_senderName", collection_senderName));
       }	
       if(StringUtils.isNotBlank(collection_sendTime))
       {
         nodes.add(new QueryNode("collection_sendTime", collection_sendTime));
       }	
       if(StringUtils.isNotBlank(ringnum))
       {
         nodes.add(new QueryNode("ringnum", ringnum));
       }	
       if(StringUtils.isNotBlank(pigowner))
       {
         nodes.add(new QueryNode("pigowner", pigowner));
       }	
       if(StringUtils.isNotBlank(pigowner_num))
       {
         nodes.add(new QueryNode("pigowner_num", pigowner_num));
       }	
       if(StringUtils.isNotBlank(pigeon_color))
       {
         nodes.add(new QueryNode("pigeon_color", pigeon_color));
       }	
       if(StringUtils.isNotBlank(pigeon_ext))
       {
         nodes.add(new QueryNode("pigeon_ext", pigeon_ext));
       }	
       if(StringUtils.isNotBlank(fileName))
       {
         nodes.add(new QueryNode("fileName", fileName));
       }	
       if(StringUtils.isNotBlank(typeId))
       {
         nodes.add(new QueryNode("typeId", typeId));
       }	
       if(StringUtils.isNotBlank(typeName))
       {
         nodes.add(new QueryNode("typeName", typeName));
       }	
       if(create_time!=null)
       {
         nodes.add(new QueryNode("create_time", create_time));
       }	
       if(modify_time!=null)
       {
         nodes.add(new QueryNode("modify_time", modify_time));
       }	
	*/
       if(StringUtils.isNotBlank(collection_id))
       {
         nodes.add(new QueryNode("collection_id", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), collection_id));
       }	
       if(StringUtils.isNotBlank(match_id))
       {
         nodes.add(new QueryNode("match_id", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), match_id));
       }	
       if(StringUtils.isNotBlank(cote_id))
       {
         nodes.add(new QueryNode("cote_id", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), cote_id));
       }	
       if(StringUtils.isNotBlank(cote_name))
       {
         nodes.add(new QueryNode("cote_name", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), cote_name));
       }	
       if(StringUtils.isNotBlank(collection_senderId))
       {
         nodes.add(new QueryNode("collection_senderId", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), collection_senderId));
       }	
       if(StringUtils.isNotBlank(collection_senderName))
       {
         nodes.add(new QueryNode("collection_senderName", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), collection_senderName));
       }	
       if(StringUtils.isNotBlank(collection_sendTime))
       {
         nodes.add(new QueryNode("collection_sendTime", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), collection_sendTime));
       }	
       if(StringUtils.isNotBlank(ringnum))
       {
         nodes.add(new QueryNode("ringnum", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), ringnum));
       }	
       if(StringUtils.isNotBlank(pigowner))
       {
         nodes.add(new QueryNode("pigowner", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), pigowner));
       }	
       if(StringUtils.isNotBlank(pigowner_num))
       {
         nodes.add(new QueryNode("pigowner_num", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), pigowner_num));
       }	
       if(StringUtils.isNotBlank(pigeon_color))
       {
         nodes.add(new QueryNode("pigeon_color", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), pigeon_color));
       }	
       if(StringUtils.isNotBlank(pigeon_ext))
       {
         nodes.add(new QueryNode("pigeon_ext", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), pigeon_ext));
       }	
       if(StringUtils.isNotBlank(fileName))
       {
         nodes.add(new QueryNode("fileName", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), fileName));
       }	
       if(StringUtils.isNotBlank(typeId))
       {
         nodes.add(new QueryNode("typeId", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), typeId));
       }	
       if(StringUtils.isNotBlank(typeName))
       {
         nodes.add(new QueryNode("typeName", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), typeName));
       }	
       if(create_time!=null)
       {
         nodes.add(new QueryNode("create_time", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), create_time));
       }	
       if(modify_time!=null)
       {
         nodes.add(new QueryNode("modify_time", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), modify_time));
       }	
		
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");
		QueryObject queryObject=new QueryObject();
		queryObject.setFuzzyQuery(listNodes);
		List<NodejsMatchPigeonCollection> list = PagingAndSortingRepository.findList(queryObject, new Specification<NodejsMatchPigeonCollection>() {

			@Override
			public List<NodejsMatchPigeonCollection> query(Map<Object, Object> map) {
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
	public BaseResponse<NodejsMatchPigeonCollection> queryOneOperation(String collection_id) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchPigeonCollectionController.class + "/one->" + collection_id);
		NodejsMatchPigeonCollection t = sv.selectOneById(collection_id);
		return BaseResponse.ToJsonResult(t);
	}

	/**
	 * 查询分页
	 * 
	 * @param queryObject
	 * @return
	 */
	@RequestMapping(value = "/query/page", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<JsonResult<List<NodejsMatchPigeonCollection>, Object>> queryPageOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchPigeonCollectionController.class + "/page->" + JSON.toJSONString(queryObject));

		BaseResponse<JsonResult<List<NodejsMatchPigeonCollection>, Object>> result = PagingAndSortingRepository.find(queryObject.getJson(), new PageSpecification<NodejsMatchPigeonCollection>() {
			@Override
			public List<NodejsMatchPigeonCollection> query(Map<Object, Object> map) {
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
		return result;
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
			log.debug(NodejsMatchPigeonCollectionController.class + "/delete/{collection_id}->" + collection_id);

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
			log.debug(NodejsMatchPigeonCollectionController.class + "/delete->" + JSON.toJSONString(ids));

		int result = sv.deleteByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}

	@RequestMapping(value = "/insertBatch", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertBatcOperation(@RequestBody List<NodejsMatchPigeonCollection> list, HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchPigeonCollectionController.class + "/insertBatch->" + JSON.toJSONString(list));
		SSOToken ssoToken = SSOHelper.getSSOToken(request);

		if (ssoToken != null) {
			NodejsSysUser nodejsSysUser = JSON.parseObject(JSON.toJSONString(ssoToken.getClaims().get("nodejsSysUser")), NodejsSysUser.class);

			for (NodejsMatchPigeonCollection item : list) {
				String collection_id = SnGenerator.getLongRandSn("MP");
				item.setCollection_id(collection_id);
				item.setCote_id(nodejsSysUser.getCote_id());
				item.setCote_name(nodejsSysUser.getCote_name());
				item.setCollection_senderId(nodejsSysUser.getId());
				item.setCollection_senderName(nodejsSysUser.getUserName());
				item.setCreate_time(new Date());
				item.setModify_time(new Date());
			}
		}

		int result = sv.insertBatch(list);

		return BaseResponse.ToJsonResult(result);
	}

	@RequestMapping(value = "/selectGrpupByPigownerNum")
	@ResponseBody
	public BaseResponse<List<NodejsMatchPigeonCollection>> selectListGroupByPigownerNum (String match_id,String param){
		Map<String,Object> map=new HashedMap();
		map.put("match_id",match_id);
		map.put("param",param);
		map.put("limit",0);
		map.put("offset",10);
		List<NodejsMatchPigeonCollection> list = sv.selectListGroupByPigownerNum(map);
		return BaseResponse.ToJsonResult(list);
	}
}