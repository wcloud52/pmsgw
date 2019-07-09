package com.saas.common;

import java.util.List;
import java.util.Map;

public interface DynamicQuerySpecification<T> {
	List<T> selectByDynamic(Map<Object, Object> map);
}
