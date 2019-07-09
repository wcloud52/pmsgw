package com.saas.common;

import java.io.Serializable;
/**
 * JsonResult返回值
 * @author tanjun
 *
 * @param <T>
 */
public class JsonResult<LIST,EXT> implements Serializable{
	   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long total;
	private LIST list;
	private EXT ext;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public LIST getList() {
		return list;
	}
	public void setList(LIST list) {
		this.list = list;
	}
	public EXT getExt() {
		return ext;
	}
	public void setExt(EXT ext) {
		this.ext = ext;
	}
	
}