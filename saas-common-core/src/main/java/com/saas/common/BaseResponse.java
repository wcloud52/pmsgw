package com.saas.common;

import java.io.Serializable;
/**
 * BaseResponse返回值
 * @author tanjun
 *
 * @param <T>
 */
public class BaseResponse<T> implements Serializable{
	   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String message;
	private T data;
	public static <T> BaseResponse<T> ToJsonResult (T obj) {
        return new BaseResponse<T>(0,"",obj);
    }
    
    public static  BaseResponse<String> ToCustomError (int code,String msg) {
        
        return new BaseResponse<String>(code,msg,"");
    }
    public static <T> BaseResponse<T> ToCustomError (int code,String msg, T data) {
        
        return new BaseResponse<T>(code,msg,data);
    }
    public static BaseResponse<String> ToError(String msg) {
       return ToCustomError(-1, msg);
    }
	public BaseResponse(Integer code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}