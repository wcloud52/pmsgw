package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.WappGameMapper;
import com.saas.biz.pojo.WappGame;

public interface WappGameExtMapper extends WappGameMapper {
	
	List<WappGame> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
}