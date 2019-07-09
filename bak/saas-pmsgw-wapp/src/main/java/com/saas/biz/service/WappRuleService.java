package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.WappRuleDetail;
import com.saas.biz.pojo.WappRuleMaster;

public interface WappRuleService {
	WappRuleMaster selectOneWappRuleMasterById(String id);
	List<WappRuleMaster> selectAllWappRuleMaster();
	int insertWappRuleMaster(WappRuleMaster record);
	int updateWappRuleMaster(WappRuleMaster record);
	int deleteWappRuleMaster(String id);
	List<WappRuleMaster> selectWappRuleMasterListByDynamic(Map<Object, Object> paraMap);
	long selectWappRuleMasterCountByDynamic(Map<Object, Object> paraMap);
	int deleteWappRuleMasterByIds(List<String> ids);
	
	
	
	WappRuleDetail selectOneWappRuleDetailById(String id);
	List<WappRuleDetail> selectAllWappRuleDetail();
	int insertWappRuleDetail(WappRuleDetail record);
	int updateWappRuleDetail(WappRuleDetail record);
	int deleteWappRuleDetail(String id);
	List<WappRuleDetail> selectWappRuleDetailListByDynamic(Map<Object, Object> paraMap);
	long selectWappRuleDetailCountByDynamic(Map<Object, Object> paraMap);
	int deleteWappRuleDetailByIds(List<String> ids);
}
