/**
 * sql查询节点类
 */
package com.saas.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.StringUtils;

public class QueryNodes {

	private String prepend;
	private List<QueryNode> nodes;

	public String getPrepend() {
		return prepend;
	}

	public void setPrepend(String prepend) {
		this.prepend = prepend;
	}

	public List<QueryNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<QueryNode> nodes) {
		this.nodes = nodes;
	}

	public QueryNodes() {

	}

	public QueryNodes(List<QueryNode> nodes, String prepend) {
		this.nodes = nodes;
		this.prepend = prepend;
	}

	/**
	 * 创建QueryNodes
	 * 
	 * @param nodes
	 * @param prepend
	 * @return
	 */
	public static QueryNodes createQueryNodes(List<QueryNode> nodes, String prepend) {
		return new QueryNodes(nodes, prepend);
	}

	public static List<QueryNodes> createQueryNodesList(List<QueryNode> nodes, String prepend) {
		return Arrays.asList(createQueryNodes(nodes, "and"));
	}

	/**
	 * 获取条件拼接sql
	 * 
	 * @param list
	 * @return
	 */
	public static String GetSql(List<QueryNodes> list) {
		fiterByPrepend(list);
		String sql = "";
		if (list != null && list.size() > 0) {
			if (list.size() > 1)
				sql = GetSqlByQueryNodes(list);
			else
				sql = GetSqlByQueryNode(list.get(0).nodes);
		}
		return sql;
	}
	public static void fiterByPrepend(List<QueryNodes> list) {
		
		if (list != null && list.size() > 0) {
			for (QueryNodes node : list) {
				
				List<QueryNode> qnodeList=new ArrayList<QueryNode>();
				if (node.nodes != null && node.nodes.size() > 0)
				{	
					for (QueryNode qnode : node.nodes) {
						if (qnode.getPrepend().equalsIgnoreCase("none")) {
							
						}
						else
						{
							qnodeList.add(qnode);
						}
					}
				}
				node.nodes.clear();
				if(qnodeList.size()>0)
					node.nodes.addAll(qnodeList);
			}
		}
	}

   /* public static List<QueryNode>  fiterQueryNodeByPrepend(List<QueryNode> list) {
		
		return list.stream().filter(item->!item.getPrepend().equalsIgnoreCase("none"))
				.collect(Collectors.toList());		
	}*/
	/**
	 * 获取条件map
	 * 
	 * @param list
	 * @return
	 */
	public static Map<String, Object> GetMap(List<QueryNodes> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (list != null && list.size() > 0) {
			for (QueryNodes node : list) {
				if (node.nodes != null && node.nodes.size() > 0)
					for (QueryNode qnode : node.nodes) {
						if (qnode.getPrepend().equalsIgnoreCase("none")) {
							String field = qnode.getField();
							Object value = qnode.getValue();
							field = field.replace(".", "_");
							if (qnode.getOp().equalsIgnoreCase("between_and")) {

								List<String> array = quotesBracketToList(StringUtils.trim(qnode.getValue().toString()));
								value = array;

							} else if (qnode.getOp().equalsIgnoreCase("in")) {
								List<String> array = quotesBracketToList(StringUtils.trim(qnode.getValue().toString()));
								value = array;
							}
							else if (qnode.getOp().equalsIgnoreCase("not in")) {
								List<String> array = quotesBracketToList(StringUtils.trim(qnode.getValue().toString()));
								value = array;
							}
							if (!map.containsKey(field)) {
								map.put(field, value);
							}
						}
					}
			}
		}
		return map;
	}

	public static List<String> getQuotesList(String value, String type) {
		List<String> array = quotesBracketToList(StringUtils.trim(value));
		array = addQuotes(array, type);
		return array;
	}

	private static String GetSqlByQueryNodes(List<QueryNodes> list) {
		SQL sql = new SQL();
		for (QueryNodes qnode : list) {
			if (qnode.getPrepend().equalsIgnoreCase("and")&&qnode.nodes!=null&&qnode.nodes.size()>0) {
				sql.AND();
				sql.WHERE(GetSqlByQueryNode(qnode.getNodes()));
			} else if (qnode.getPrepend().equalsIgnoreCase("or")&&qnode.nodes!=null&&qnode.nodes.size()>0) {
				sql.OR();
				sql.WHERE(GetSqlByQueryNode(qnode.getNodes()));
			}
		}
		return sql.toString();
	}

	private static String GetSqlByQueryNode(List<QueryNode> list) {
		SQL sql = new SQL();
		if (list != null && list.size() > 0)
			for (QueryNode qnode : list) {
				if (qnode.getPrepend().equalsIgnoreCase("and")) {
					sql.AND();
					GetSqlByOp(sql, qnode);
				} else if (qnode.getPrepend().equalsIgnoreCase("or")) {
					sql.OR();
					GetSqlByOp(sql, qnode);
				}
			}
		return sql.toString();
	}

	private static void GetSqlByOp(SQL sql, QueryNode qnode) {
		 if (qnode.getOp().equalsIgnoreCase("like")) {
			sql.WHERE(" CAST(" + qnode.getField() + " AS CHAR) " + " " + qnode.getOp() + " " + addQuotes(StringUtils.trim(qnode.getValue().toString()), qnode.getSign(), true));
			// sql.WHERE(qnode.getField() + " " + qnode.getOp() + " " +
			// addQuotes(qnode.getValue().toString(),qnode.getSign(),true));
		} else if (qnode.getOp().equalsIgnoreCase("BETWEEN_AND")) {
			List<String> array = quotesBracketToList(StringUtils.trim(qnode.getValue().toString()));
			array = addQuotes(array, qnode.getSign());
			if (array != null && array.size() == 2) {
				sql.WHERE(qnode.getField() + " BETWEEN " + addQuotes(StringUtils.trim(array.get(0)), qnode.getSign()) + " AND " + addQuotes(StringUtils.trim(array.get(1)), qnode.getSign()));
			}
		} else if (qnode.getOp().equalsIgnoreCase("in")) {

			List<String> array = quotesBracketToList(StringUtils.trim(qnode.getValue().toString()));
			array = addQuotes(array, qnode.getSign());
			String value = StringUtils.join(array.toArray(), ",");
			sql.WHERE(qnode.getField() + " in (" + StringUtils.trim(value) + ")");
		} 
		else if (qnode.getOp().equalsIgnoreCase("not in")) {

			List<String> array = quotesBracketToList(StringUtils.trim(qnode.getValue().toString()));
			array = addQuotes(array, qnode.getSign());
			String value = StringUtils.join(array.toArray(), ",");
			sql.WHERE(qnode.getField() + " not in (" + StringUtils.trim(value) + ")");
		} 
		else {
			sql.WHERE(qnode.getField() + " " + qnode.getOp() + " " + addQuotes(StringUtils.trim(qnode.getValue().toString()), qnode.getSign()));
		}
	}

	private static String addQuotes(String text, String type, boolean islike) {
		String result = text;
		if (type.equalsIgnoreCase("none")) {

		} else {
			result = addQuotes(text, islike);
		}
		return result;
	}

	private static String addQuotes(String text, String type) {
		String result = text;
		if (type.equalsIgnoreCase("none")) {

		} else {
			result = addQuotes(text);
		}
		return result;
	}

	private static List<String> addQuotes(List<String> list, String type) {
		List<String> result = list;
		if (type.equalsIgnoreCase("none")) {

		} else {
			result = addQuotes(list);
		}
		return result;
	}
	private static String addQuotes(String text, boolean islike) {
		String result = StringUtils.removeEnd(StringUtils.removeStart(text, "'"), "'");
		if (StringUtils.startsWith(text, "%"))// 开始包含
		{
			result = StringUtils.removeEnd(StringUtils.removeStart(result, "%"), "%");
			result = "'%" + result + "'";
		} else if (StringUtils.endsWith(text, "%"))// 结尾包含
		{
			result = StringUtils.removeEnd(StringUtils.removeStart(result, "%"), "%");
			result = "'" + result + "%'";
		} else {
			result = StringUtils.removeEnd(StringUtils.removeStart(result, "%"), "%");
			result = "'%" + result + "%'";
		}
		return result;
	}
	
	private static List<String> quotesBracketToList(String text) {
		String result = StringUtils.removeEnd(StringUtils.removeStart(text, "["), "]");
		String[] arary = result.split(",");
		List<String> list = new ArrayList<String>();
		for (String s : arary) {
			s=StringUtils.trim(s);
			list.add(StringUtils.removeEnd(StringUtils.removeStart(s, "\""), "\""));
		}
		return list;
	}

	/**
	 * 加'号
	 * 
	 * @param text
	 * @return
	 */
	private static String addQuotes(String text) {
		String result = StringUtils.removeEnd(StringUtils.removeStart(text, "'"), "'");
		result = StringUtils.removeEnd(StringUtils.removeStart(result, "\""), "\"");
		result = "'" + result + "'";
		return result;
	}

	/**
	 * 加'号
	 * 
	 * @param list
	 * @return
	 */
	private static List<String> addQuotes(List<String> list) {
		@SuppressWarnings("unchecked")
		List<String> result = (List<String>) CollectionUtils.collect(list, new Transformer() {

			@Override
			public Object transform(Object arg0) {

				return addQuotes(arg0.toString());
			}
		});
		return result;
	}
}