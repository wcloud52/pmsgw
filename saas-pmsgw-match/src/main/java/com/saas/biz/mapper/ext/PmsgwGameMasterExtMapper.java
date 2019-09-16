package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.mapper.base.PmsgwGameMasterMapper;
import com.saas.biz.pojo.PmsgwGameMaster;

public interface PmsgwGameMasterExtMapper extends PmsgwGameMasterMapper{
	List<PmsgwGameMaster> selectListByDynamic(Map<Object, Object> paraMap);
	long selectListByDynamicCount(Map<Object, Object> paraMap);
	int deleteByMainhref(String main_href);
	
	PmsgwGameMaster selectTopOne(@Param("main_text") String main_text);
	
	int insertCrawlerMasterFormPmsgw();
	
	int insertCrawlerDetailFormPmsgw();
}