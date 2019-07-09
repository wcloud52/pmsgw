package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.WappPigownerMapper;
import com.saas.biz.pojo.WappPigowner;

public interface WappPigownerExtMapper extends WappPigownerMapper {
	
    List<WappPigowner> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
}