package com.saas.biz.mapper.ext;

import com.saas.biz.mapper.base.WeixinUserPurseMapper;
import com.saas.biz.pojo.WeixinUserPurse;

import java.util.List;
import java.util.Map;

public interface WeixinUserPurseExtMapper extends WeixinUserPurseMapper {
	
	List<WeixinUserPurse> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	int insertByList(List<WeixinUserPurse> list);
	int updateByList(List<WeixinUserPurse> list);
}