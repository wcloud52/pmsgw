package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.WappPigowner;

public interface WappPigownerService {
	WappPigowner selectOneWappPigownerById(String id);
	List<WappPigowner> selectAllWappPigowner();
	int insertWappPigowner(WappPigowner record);
	int updateWappPigowner(WappPigowner record);
	int deleteWappPigowner(String id);
	List<WappPigowner> selectWappPigownerListByDynamic(Map<Object, Object> paraMap);
	long selectWappPigownerCountByDynamic(Map<Object, Object> paraMap);
	int deleteWappPigownerByIds(List<String> ids);
	
}
