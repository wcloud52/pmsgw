package com.saas.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class DynamicQuery<T> {
	
	public static <T> List<T> selectByDynamic(List<QueryNode> nodes,DynamicQuerySpecification<T> spec) {
		return selectByDynamic(null,nodes,spec);
	}
	
	public static <T> List<T> selectByDynamic(String sort,List<QueryNode> nodes,DynamicQuerySpecification<T> spec) {
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");

		Map<String, Object> queryMap = QueryNodes.GetMap(listNodes);
		String querySql = QueryNodes.GetSql(listNodes);
		
		Map<Object, Object> paraMap = new HashMap<Object, Object>();
		paraMap.put("queryMap", queryMap); // 查询map
		paraMap.put("customQuerySegment", querySql);// 自定义语句片段
		paraMap.put("sort", sort);
		return spec.selectByDynamic(paraMap);
	}
}