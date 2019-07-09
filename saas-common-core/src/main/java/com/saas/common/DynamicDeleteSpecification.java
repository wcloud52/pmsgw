package com.saas.common;

import java.util.Map;

public interface DynamicDeleteSpecification {
	int deleteByDynamic(Map<Object, Object> map);
}
