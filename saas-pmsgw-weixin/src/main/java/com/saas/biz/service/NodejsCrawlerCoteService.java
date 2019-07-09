package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.NodejsCrawlerCote;

public interface NodejsCrawlerCoteService {

	List<NodejsCrawlerCote> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	int update(NodejsCrawlerCote record);
	List<NodejsCrawlerCote> selectAll();
}
