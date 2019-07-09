package com.saas.common;

import java.util.Map;

/**
 * 查询规则接口
 * 
 * @author tanjun
 *
 * @param <T>
 */
public interface PageSpecification<T> extends Specification<T> {
	
	long queryCount(Map<Object, Object> map);

}
