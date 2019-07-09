package com.saas.common;

import java.io.Serializable;

public class ViewQueryObject<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DispatchesDTO<QueryObject> getDto() {
		return dto;
	}

	public void setDto(DispatchesDTO<QueryObject> dto) {
		this.dto = dto;
	}

	public T getExt() {
		return ext;
	}

	public void setExt(T ext) {
		this.ext = ext;
	}

	private DispatchesDTO<QueryObject> dto;
	
	private T ext;
	
}
