/**
 * sql查询节点类
 */
package com.saas.common;

public class QueryNode {
	
	private String prepend;
	private String field;
	private String op;
	private String sign="yes";//符号 yes存在单引号  none不存在单引号
	private Object value;
	
	public QueryNode()
	{
		
	}
	public QueryNode(String field,String op,String prepend,Object value){
		this.field = field;
		this.op = op;
		this.prepend = prepend;
		this.value = value;
	}
	public QueryNode(String field,Object value){
		this.field = field;
		this.op = "=";
		this.prepend = "none";
		this.value = value;
	}
	public QueryNode(String field,String op,String prepend,Object value,String sign){
		this.field = field;
		this.op = op;
		this.prepend = prepend;
		this.value = value;
		this.sign=sign;
	}
	public String getPrepend() {
		return prepend;
	}


	public void setPrepend(String prepend) {
		this.prepend = prepend;
	}


	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}


	public String getOp() {
		return op;
	}


	public void setOp(String op) {
		this.op = op;
	}


	public Object getValue() {
		return value;
	}


	public void setValue(Object value) {
		this.value = value;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}