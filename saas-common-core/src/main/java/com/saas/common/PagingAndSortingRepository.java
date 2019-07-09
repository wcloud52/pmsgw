package com.saas.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.saas.mybatis.PageParameter;

/**
 * 分页排序处理类
 * 
 * @author tanjun
 *
 */
public class PagingAndSortingRepository {

	private static final Logger log = LoggerFactory.getLogger(PagingAndSortingRepository.class);
	

	public static <T> BaseResponse<JsonResult<List<T>, Object>> find(QueryObject queryObject,
			PageSpecification<T> spe) {

		if (log.isDebugEnabled())
			log.debug(JSON.toJSONString(queryObject));

		String sort = queryObject.getSort();

		List<QueryNodes> listNodes = queryObject.getFuzzyQuery();

		Map<String, Object> queryMap = QueryNodes.GetMap(listNodes);

		String querySql = QueryNodes.GetSql(listNodes);

		Map<Object, Object> paraMap = new HashMap<Object, Object>();
		paraMap.put("queryMap", queryMap); // 查询map
		
		paraMap.put("customQuerySegment", querySql);// 自定义语句片段
		paraMap.put("sort", sort);
		PageParameter page = new PageParameter(queryObject.getPageIndex(), queryObject.getPageSize());
		paraMap.put("page", page);

		long totalCount = spe.queryCount(paraMap);

		List<T> list = null;
		Object object = null;
		if (totalCount > 0) {
			list = spe.query(paraMap);
			object = spe.queryExt(paraMap);
		}

		JsonResult<List<T>, Object> jsonResult = new JsonResult<List<T>, Object>();
		jsonResult.setList(list != null ? list : new ArrayList<T>());
		jsonResult.setExt(object != null ? object : new Object());
		jsonResult.setTotal(totalCount);

		return BaseResponse.ToJsonResult(jsonResult);
	}

	public static <T> List<T> findList(QueryObject queryObject, Specification<T> spe) {

		if (log.isDebugEnabled())
			log.debug(JSON.toJSONString(queryObject));

		String sort = queryObject.getSort();

		List<QueryNodes> listNodes = queryObject.getFuzzyQuery();

		Map<String, Object> queryMap = QueryNodes.GetMap(listNodes);

		String querySql = QueryNodes.GetSql(listNodes);

		Map<Object, Object> paraMap = new HashMap<Object, Object>();
		paraMap.put("queryMap", queryMap); // 查询map
		paraMap.put("customQuerySegment", querySql);// 自定义语句片段
		paraMap.put("sort", sort);
		List<T> list = spe.query(paraMap);
		return list;
	}
}
