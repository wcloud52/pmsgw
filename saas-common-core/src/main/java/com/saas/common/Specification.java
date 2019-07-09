package com.saas.common;

import java.util.List;
import java.util.Map;
/**
 * 查询规则接口
 * @author tanjun
 *
 * @param <T>
 */
public interface Specification<T> 
{
	    List<T> query(Map<Object, Object> map);
	    Object queryExt(Map<Object, Object> map);
}

