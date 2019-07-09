package com.saas.common;

import java.io.Serializable;

/**
 * 接收参数实体类
 * 
 * @author tanjun
 *
 * @param <T>
 */
public class DispatchesDTO<T> implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String currentUserCode;
	private String currentUserName;
	private String currentUserAccount;
	private Integer currentUserType;
	private String tenantId;
	private T json;

	public String getCurrentUserCode() {
		return currentUserCode;
	}

	public void setCurrentUserCode(String currentUserCode) {
		this.currentUserCode = currentUserCode;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public String getCurrentUserAccount() {
		return currentUserAccount;
	}

	public void setCurrentUserAccount(String currentUserAccount) {
		this.currentUserAccount = currentUserAccount;
	}

	public Integer getCurrentUserType() {
		return currentUserType;
	}

	public void setCurrentUserType(Integer currentUserType) {
		this.currentUserType = currentUserType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public T getJson() {
		return json;
	}

	public void setJson(T json) {
		this.json = json;
	};

}
