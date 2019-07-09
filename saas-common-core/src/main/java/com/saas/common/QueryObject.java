package com.saas.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "QueryObject实体类")
public class QueryObject implements Serializable{
	   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<QueryNode> queryNode=Arrays.asList(new QueryNode());
	
	@ApiModelProperty(value = "当前页", required = true,allowableValues="1",example="1")
	private Integer pageIndex=1;
	
	@ApiModelProperty(value = "页大小", required = true)
	private Integer pageSize=10;
	
	
	@ApiModelProperty(value = "排序", required = false)
	private String sort="";
	@ApiModelProperty(value = "data主体数据", required = true)
	private List<QueryNodes> fuzzyQuery;
	private Object ext;
	
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public List<QueryNodes> getFuzzyQuery() {
		return fuzzyQuery;
	}
	public void setFuzzyQuery(List<QueryNodes> fuzzyQuery) {
		this.fuzzyQuery = fuzzyQuery;
	}
	public Object getExt() {
		return ext;
	}
	public void setExt(Object ext) {
		this.ext = ext;
	}
	
	
}