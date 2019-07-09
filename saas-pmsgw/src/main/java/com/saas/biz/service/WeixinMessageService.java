package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.PmsgwGameWhiteList;
import com.saas.biz.pojo.WeixinMessage;

public interface WeixinMessageService {

	List<WeixinMessage> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	
	String selectPmsgwGameWhiteList();
	
}
