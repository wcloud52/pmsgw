package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;

public interface NodejsCrawlerService {
	List<NodejsCrawlerMasterGame> selectNodejsCrawlerMasterGameListByDynamic(Map<Object, Object> paraMap);
	long selectNodejsCrawlerMasterGameListByDynamicCount(Map<Object, Object> paraMap);
	NodejsCrawlerMasterGame selectNodejsCrawlerMasterGameById(String database, String master_id);
	
	List<NodejsCrawlerDetailGame> selectNodejsCrawlerDetailGameListByDynamic(Map<Object, Object> paraMap);
	long selectNodejsCrawlerDetailGameListByDynamicCount(Map<Object, Object> paraMap);
	List<NodejsCrawlerMasterGame> selectTop50NodejsCrawlerMasterGameList(String database);

	List<NodejsCrawlerMasterGame> selectTop50NodejsCrawlerMasterGameListByCoteId(String database, String cote_id);

	List<NodejsCrawlerDetailGame> selectNodejsCrawlerDetailGameListByMasterId(String database, String masterId);
	List<NodejsCrawlerDetailGame> selectNodejsCrawlerDetailGameListByDynamicMatch(String database, String masterId,String cote_id);

	List<NodejsCrawlerDetailGame> selectNodejsCrawlerDetailGameListByMasterId(String database, String pigowner, String masterId, int pageIndex, int pageSize);
}