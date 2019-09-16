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
import com.saas.biz.pojo.NodejsMatchItem;
import com.saas.biz.service.NodejsMatchItemService;

@Controller
@RequestMapping("/NodejsMatchItem")
public class NodejsMatchItemController {
	protected static final Logger log = LoggerFactory.getLogger(NodejsMatchItemController.class);

	@Autowired
	protected NodejsMatchItemService sv;

	/**
	 * 插入操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertOperation(@RequestBody NodejsMatchItem body) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemController.class + "/insert->" + JSON.toJSONString(body));

		NodejsMatchItem item = body;

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
	public BaseResponse<Integer> updateOperation(@RequestBody NodejsMatchItem body) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemController.class + "/update->" + JSON.toJSONString(body));

		NodejsMatchItem item = body;

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
	public BaseResponse<List<NodejsMatchItem>> queryListOperation() {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemController.class + "/list->");
		List<NodejsMatchItem> list = sv.selectAll();
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
	public BaseResponse<List<NodejsMatchItem>> queryListByOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemController.class + "/listBy->" + JSON.toJSONString(queryObject));

		List<NodejsMatchItem> list = PagingAndSortingRepository.findList(queryObject.getJson(), new Specification<NodejsMatchItem>() {

			@Override
			public List<NodejsMatchItem> query(Map<Object, Object> map) {
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
	public BaseResponse<List<NodejsMatchItem>> queryListExtOperation(
	   @RequestParam(required=false)  String item_id,		
	   @RequestParam(required=false)  String parent_item_id,		
	   @RequestParam(required=false)  String match_id,		
	   @RequestParam(required=false)  String cote_id,		
	   @RequestParam(required=false)  String cote_name,		
	   @RequestParam(required=false)  String item_type,		
	   @RequestParam(required=false)  String item_name,		
	   @RequestParam(required=false)  String item_desc,		
	   @RequestParam(required=false)  Integer item_status,		
	   @RequestParam(required=false)  Date create_time,		
	   @RequestParam(required=false)  Date modify_time		
	) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemController.class + "/query/listExt->");
		List<QueryNode> nodes = new ArrayList<QueryNode>();
	/*		
       if(StringUtils.isNotBlank(item_id))
       {
         nodes.add(new QueryNode("item_id", item_id));
       }	
       if(StringUtils.isNotBlank(parent_item_id))
       {
         nodes.add(new QueryNode("parent_item_id", parent_item_id));
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
       if(StringUtils.isNotBlank(item_type))
       {
         nodes.add(new QueryNode("item_type", item_type));
       }	
       if(StringUtils.isNotBlank(item_name))
       {
         nodes.add(new QueryNode("item_name", item_name));
       }	
       if(StringUtils.isNotBlank(item_desc))
       {
         nodes.add(new QueryNode("item_desc", item_desc));
       }	
       if(item_status!=null)
       {
         nodes.add(new QueryNode("item_status", item_status));
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
       if(StringUtils.isNotBlank(item_id))
       {
         nodes.add(new QueryNode("item_id", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), item_id));
       }	
       if(StringUtils.isNotBlank(parent_item_id))
       {
         nodes.add(new QueryNode("parent_item_id", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), parent_item_id));
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
       if(StringUtils.isNotBlank(item_type))
       {
         nodes.add(new QueryNode("item_type", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), item_type));
       }	
       if(StringUtils.isNotBlank(item_name))
       {
         nodes.add(new QueryNode("item_name", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), item_name));
       }	
       if(StringUtils.isNotBlank(item_desc))
       {
         nodes.add(new QueryNode("item_desc", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), item_desc));
       }	
       if(item_status!=null)
       {
         nodes.add(new QueryNode("item_status", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), item_status));
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
		List<NodejsMatchItem> list = PagingAndSortingRepository.findList(queryObject, new Specification<NodejsMatchItem>() {

			@Override
			public List<NodejsMatchItem> query(Map<Object, Object> map) {
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
	public BaseResponse<NodejsMatchItem> queryOneOperation(String item_id) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemController.class + "/one->" + item_id);
		NodejsMatchItem t = sv.selectOneById(item_id);
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
	public BaseResponse<JsonResult<List<NodejsMatchItem>, Object>> queryPageOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemController.class + "/page->" + JSON.toJSONString(queryObject));

		BaseResponse<JsonResult<List<NodejsMatchItem>, Object>> result = PagingAndSortingRepository.find(queryObject.getJson(), new PageSpecification<NodejsMatchItem>() {
			@Override
			public List<NodejsMatchItem> query(Map<Object, Object> map) {
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
	public BaseResponse<Integer> deleteOperation(@PathVariable String item_id) {
		if (log.isDebugEnabled())
			log.debug(NodejsMatchItemController.class + "/delete/{item_id}->" + item_id);

		int result = sv.deleteById(item_id);
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
			log.debug(NodejsMatchItemController.class + "/delete->" + JSON.toJSONString(ids));

		int result = sv.deleteByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}
}