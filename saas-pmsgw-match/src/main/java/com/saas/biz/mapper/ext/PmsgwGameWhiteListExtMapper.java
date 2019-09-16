package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.PmsgwGameWhiteListMapper;
import com.saas.biz.pojo.PmsgwGameWhiteList;

public interface PmsgwGameWhiteListExtMapper extends PmsgwGameWhiteListMapper {
	List<PmsgwGameWhiteList> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
}