package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.WappRuleMasterMapper;
import com.saas.biz.pojo.WappRuleMaster;

public interface WappRuleMasterExtMapper   extends WappRuleMasterMapper {
	
	List<WappRuleMaster> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
}