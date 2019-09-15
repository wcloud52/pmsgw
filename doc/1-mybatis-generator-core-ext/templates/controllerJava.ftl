package ${implMapperPackageName?replace('mapper.impl','controller' )};

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
import com.zichan100.common.BaseResponse;
import com.zichan100.common.JsonResult;
import com.zichan100.common.domain.DispatchesDTO;
import com.zichan100.common.enums.OpEnum;
import com.zichan100.common.enums.PrependEnum;
import com.zichan100.common.repository.PageSpecification;
import com.zichan100.common.repository.PagingAndSortingRepository;
import com.zichan100.common.repository.QueryNode;
import com.zichan100.common.repository.QueryNodes;
import com.zichan100.common.repository.QueryObject;
import com.zichan100.common.repository.Specification;
import ${pojoPackageName}.${pojoName};
import ${implMapperPackageName?replace('mapper.impl','service' )}.${implMapperName?replace('ImplMapper', 'Service')};

@Controller
@RequestMapping("/${pojoName}")
public class ${pojoName}Controller {
	protected static final Logger log = LoggerFactory.getLogger(${pojoName}Controller.class);

	@Autowired
	protected ${pojoName}Service sv;

	/**
	 * 插入操作
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponse<Integer> insertOperation(@RequestBody ${pojoName} body) {
		if (log.isDebugEnabled())
			log.debug(${pojoName}Controller.class + "/insert->" + JSON.toJSONString(body));

		${pojoName} item = body;

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
	public BaseResponse<Integer> updateOperation(@RequestBody ${pojoName} body) {
		if (log.isDebugEnabled())
			log.debug(${pojoName}Controller.class + "/update->" + JSON.toJSONString(body));

		${pojoName} item = body;

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
	public BaseResponse<List<${pojoName}>> queryListOperation() {
		if (log.isDebugEnabled())
			log.debug(${pojoName}Controller.class + "/list->");
		List<${pojoName}> list = sv.selectAll();
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
	public BaseResponse<List<${pojoName}>> queryListByOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(${pojoName}Controller.class + "/listBy->" + JSON.toJSONString(queryObject));

		List<${pojoName}> list = PagingAndSortingRepository.findList(queryObject.getJson(), new Specification<${pojoName}>() {

			@Override
			public List<${pojoName}> query(Map<Object, Object> map) {
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
	public BaseResponse<List<${pojoName}>> queryListExtOperation(
	 <#list table.allColumns as column>  
	   @RequestParam(required=false)  ${column.fullyQualifiedJavaType.shortName} ${column.javaProperty}<#if column_has_next>,</#if>		
	</#list>
	) {
		if (log.isDebugEnabled())
			log.debug(${pojoName}Controller.class + "/query/listExt->");
		List<QueryNode> nodes = new ArrayList<QueryNode>();
	/*		
    <#list table.allColumns as column>  
     <#if column.fullyQualifiedJavaType.shortName == "String" >
       if(StringUtils.isNotBlank(${column.javaProperty}))
       {
         nodes.add(new QueryNode("${column.actualColumnName}", ${column.javaProperty}));
       }	
     </#if>	
     <#if column.fullyQualifiedJavaType.shortName != "String" >
       if(${column.javaProperty}!=null)
       {
         nodes.add(new QueryNode("${column.actualColumnName}", ${column.javaProperty}));
       }	
     </#if>	
	</#list>	
	*/
     <#list table.allColumns as column>  
     <#if column.fullyQualifiedJavaType.shortName == "String" >
       if(StringUtils.isNotBlank(${column.javaProperty}))
       {
         nodes.add(new QueryNode("${column.actualColumnName}", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), ${column.javaProperty}));
       }	
     </#if>	
     <#if column.fullyQualifiedJavaType.shortName != "String" >
       if(${column.javaProperty}!=null)
       {
         nodes.add(new QueryNode("${column.actualColumnName}", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), ${column.javaProperty}));
       }	
     </#if>	
	</#list>	
		
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");
		QueryObject queryObject=new QueryObject();
		queryObject.setFuzzyQuery(listNodes);
		List<${pojoName}> list = PagingAndSortingRepository.findList(queryObject, new Specification<${pojoName}>() {

			@Override
			public List<${pojoName}> query(Map<Object, Object> map) {
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
	public BaseResponse<${pojoName}> queryOneOperation(${table.primaryKeyColumns[0].fullyQualifiedJavaType.shortName} ${table.primaryKeyColumns[0].javaProperty}) {
		if (log.isDebugEnabled())
			log.debug(${pojoName}Controller.class + "/one->" + ${table.primaryKeyColumns[0].javaProperty});
		${pojoName} t = sv.selectOneById(${table.primaryKeyColumns[0].javaProperty});
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
	public BaseResponse<JsonResult<List<${pojoName}>, Object>> queryPageOperation(@RequestBody DispatchesDTO<QueryObject> queryObject) {
		if (log.isDebugEnabled())
			log.debug(${pojoName}Controller.class + "/page->" + JSON.toJSONString(queryObject));

		BaseResponse<JsonResult<List<${pojoName}>, Object>> result = PagingAndSortingRepository.findPage(queryObject.getJson(), new PageSpecification<${pojoName}>() {
			@Override
			public List<${pojoName}> query(Map<Object, Object> map) {
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
	public BaseResponse<Integer> deleteOperation(@PathVariable ${table.primaryKeyColumns[0].fullyQualifiedJavaType.shortName} ${table.primaryKeyColumns[0].javaProperty}) {
		if (log.isDebugEnabled())
			log.debug(${pojoName}Controller.class + "/delete/{${table.primaryKeyColumns[0].javaProperty}}->" + ${table.primaryKeyColumns[0].javaProperty});

		int result = sv.deleteById(${table.primaryKeyColumns[0].javaProperty});
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
	public BaseResponse<Integer> deleteListOperation(@RequestBody List<${table.primaryKeyColumns[0].fullyQualifiedJavaType.shortName}> ids) {
		if (log.isDebugEnabled())
			log.debug(${pojoName}Controller.class + "/delete->" + JSON.toJSONString(ids));

		int result = sv.deleteByIds(ids);
		return BaseResponse.ToJsonResult(result);
	}
}