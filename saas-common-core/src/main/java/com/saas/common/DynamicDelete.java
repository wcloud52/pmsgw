package com.saas.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class DynamicDelete<T> {

	public static int deleteByDynamic(List<QueryNode> nodes,DynamicDeleteSpecification spec) {
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");

		Map<String, Object> queryMap = QueryNodes.GetMap(listNodes);
		String querySql = QueryNodes.GetSql(listNodes);
		
		Map<Object, Object> paraMap = new HashMap<Object, Object>();
		paraMap.put("queryMap", queryMap); // 查询map
		paraMap.put("customQuerySegment", querySql);// 自定义语句片段
		
		return spec.deleteByDynamic(paraMap);
	}
}