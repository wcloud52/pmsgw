package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.PmsgwGameMaster;

public interface PmsgwGameMasterService {
	PmsgwGameMaster selectOneById(Long id);
	int insert(PmsgwGameMaster record);
	int update(PmsgwGameMaster record);
	int delete(Long id);
	List<PmsgwGameMaster> selectListByDynamic(Map<Object, Object> paraMap);
	long selectListByDynamicCount(Map<Object, Object> paraMap);
	int deleteByMainhref( String main_href);
	PmsgwGameMaster selectTopOne(String main_text);
	List<PmsgwGameMaster> selectAll();
	List<PmsgwGameMaster> selectListByWebsite(String website);
	List<PmsgwGameMaster> selectTop10ListByWebsite(String website);
	
	
	List<PmsgwGameMaster> selectListByMainhref(String main_href);
	List<PmsgwGameMaster> selectListByWebsiteCreatetime(String website);
	int insertCrawlerMasterFormPmsgw();
	int insertCrawlerDetailFormPmsgw();
	List<PmsgwGameMaster> selectTop50List();
}
