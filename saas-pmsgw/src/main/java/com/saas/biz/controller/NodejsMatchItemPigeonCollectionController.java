package com.saas.biz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

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
import com.saas.biz.pojo.NodejsMatchItemPigeonCollection;
import com.saas.biz.service.NodejsMatchItemPigeonCollectionService;

@Controller
@RequestMapping("/NodejsMatchItemPigeonCollection")
public class NodejsMatchItemPigeonCollectionController {
	protected static final Logger log = LoggerFactory.getLogger(NodejsMatchItemPigeonCollectionController.class);

	@Autowired
	protected NodejsMatchItemPigeonCollectionService sv;

	/**
	 * 插入操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertOperation(@RequestBody NodejsMatchItemPigeonCollection body) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemPigeonCollectionController.class + "/insert->" + JSON.toJSONString(body));

		NodejsMatchItemPigeonCollection item = body;

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
	public BaseResponse<Integer> updateOperation(@RequestBody NodejsMatchItemPigeonCollection body) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemPigeonCollectionController.class + "/update->" + JSON.toJSONString(body));

		NodejsMatchItemPigeonCollection item = body;

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
	public BaseResponse<List<NodejsMatchItemPigeonCollection>> queryListOperation() {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemPigeonCollectionController.class + "/list->");
		List<NodejsMatchItemPigeonCollection> list = sv.selectAll();
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
	public BaseResponse<List<NodejsMatchItemPigeonCollection>> queryListByOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemPigeonCollectionController.class + "/listBy->" + JSON.toJSONString(queryObject));

		List<NodejsMatchItemPigeonCollection> list = PagingAndSortingRepository.findList(queryObject.getJson(), new Specification<NodejsMatchItemPigeonCollection>() {

			@Override
			public List<NodejsMatchItemPigeonCollection> query(Map<Object, Object> map) {
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
	public BaseResponse<List<NodejsMatchItemPigeonCollection>> queryListExtOperation(
	   @RequestParam(required=false)  String item_collection_id,		
	   @RequestParam(required=false)  String match_id,		
	   @RequestParam(required=false)  String cote_id,		
	   @RequestParam(required=false)  String cote_name,		
	   @RequestParam(required=false)  String collection_id,		
	   @RequestParam(required=false)  String collection_ringnum,		
	   @RequestParam(required=false)  String collection_pigowner,		
	   @RequestParam(required=false)  String item_id,		
	   @RequestParam(required=false)  String parent_item_id,		
	   @RequestParam(required=false)  String item_name,		
	   @RequestParam(required=false)  Date create_time,		
	   @RequestParam(required=false)  Date modify_time		
	) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemPigeonCollectionController.class + "/query/listExt->");
		List<QueryNode> nodes = new ArrayList<QueryNode>();
	/*		
       if(StringUtils.isNotBlank(item_collection_id))
       {
         nodes.add(new QueryNode("item_collection_id", item_collection_id));
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
       if(StringUtils.isNotBlank(collection_id))
       {
         nodes.add(new QueryNode("collection_id", collection_id));
       }	
       if(StringUtils.isNotBlank(collection_ringnum))
       {
         nodes.add(new QueryNode("collection_ringnum", collection_ringnum));
       }	
       if(StringUtils.isNotBlank(collection_pigowner))
       {
         nodes.add(new QueryNode("collection_pigowner", collection_pigowner));
       }	
       if(StringUtils.isNotBlank(item_id))
       {
         nodes.add(new QueryNode("item_id", item_id));
       }	
       if(StringUtils.isNotBlank(parent_item_id))
       {
         nodes.add(new QueryNode("parent_item_id", parent_item_id));
       }	
       if(StringUtils.isNotBlank(item_name))
       {
         nodes.add(new QueryNode("item_name", item_name));
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
       if(StringUtils.isNotBlank(item_collection_id))
       {
         nodes.add(new QueryNode("item_collection_id", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), item_collection_id));
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
       if(StringUtils.isNotBlank(collection_id))
       {
         nodes.add(new QueryNode("collection_id", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), collection_id));
       }	
       if(StringUtils.isNotBlank(collection_ringnum))
       {
         nodes.add(new QueryNode("collection_ringnum", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), collection_ringnum));
       }	
       if(StringUtils.isNotBlank(collection_pigowner))
       {
         nodes.add(new QueryNode("collection_pigowner", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), collection_pigowner));
       }	
       if(StringUtils.isNotBlank(item_id))
       {
         nodes.add(new QueryNode("item_id", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), item_id));
       }	
       if(StringUtils.isNotBlank(parent_item_id))
       {
         nodes.add(new QueryNode("parent_item_id", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), parent_item_id));
       }	
       if(StringUtils.isNotBlank(item_name))
       {
         nodes.add(new QueryNode("item_name", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), item_name));
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
		List<NodejsMatchItemPigeonCollection> list = PagingAndSortingRepository.findList(queryObject, new Specification<NodejsMatchItemPigeonCollection>() {

			@Override
			public List<NodejsMatchItemPigeonCollection> query(Map<Object, Object> map) {
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
	public BaseResponse<NodejsMatchItemPigeonCollection> queryOneOperation(String item_collection_id) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemPigeonCollectionController.class + "/one->" + item_collection_id);
		NodejsMatchItemPigeonCollection t = sv.selectOneById(item_collection_id);
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
	public BaseResponse<JsonResult<List<NodejsMatchItemPigeonCollection>, Object>> queryPageOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemPigeonCollectionController.class + "/page->" + JSON.toJSONString(queryObject));

		BaseResponse<JsonResult<List<NodejsMatchItemPigeonCollection>, Object>> result = PagingAndSortingRepository.find(queryObject.getJson(), new PageSpecification<NodejsMatchItemPigeonCollection>() {
			@Override
			public List<NodejsMatchItemPigeonCollection> query(Map<Object, Object> map) {
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
	public BaseResponse<Integer> deleteOperation(@PathVariable String item_collection_id) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemPigeonCollectionController.class + "/delete/{item_collection_id}->" + item_collection_id);

		int result = sv.deleteById(item_collection_id);
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
			log.debug(NodejsMatchItemPigeonCollectionController.class + "/delete->" + JSON.toJSONString(ids));

		int result = sv.deleteByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}
}