package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;

public interface NodejsCrawlerMapper{
	List<NodejsCrawlerMasterGame> selectNodejsCrawlerMasterGameListByDynamic(Map<Object, Object> paraMap);
	long selectNodejsCrawlerMasterGameListByDynamicCount(Map<Object, Object> paraMap);
	NodejsCrawlerMasterGame selectNodejsCrawlerMasterGameById(@Param("database") String database,@Param("master_id") String master_id);
	
	List<NodejsCrawlerDetailGame> selectNodejsCrawlerDetailGameListByDynamic(Map<Object, Object> paraMap);
	List<NodejsCrawlerDetailGame> selectNodejsCrawlerDetailGameListByDynamicMatch(Map<Object, Object> paraMap);
	long selectNodejsCrawlerDetailGameListByDynamicCount(Map<Object, Object> paraMap);
}