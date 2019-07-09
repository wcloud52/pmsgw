package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.WappRuleDetailMapper;
import com.saas.biz.pojo.WappRuleDetail;

public interface WappRuleDetailExtMapper   extends WappRuleDetailMapper {
	
	List<WappRuleDetail> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
}