package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.WappGame;

public interface WappGameService {
	WappGame selectOneWappGameById(String id);
	List<WappGame> selectAllWappGame();
	int insertWappGame(WappGame record);
	int updateWappGame(WappGame record);
	int deleteWappGame(String id);
	List<WappGame> selectWappGameListByDynamic(Map<Object, Object> paraMap);
	long selectWappGameCountByDynamic(Map<Object, Object> paraMap);
	int deleteWappGameByIds(List<String> ids);
	
}
