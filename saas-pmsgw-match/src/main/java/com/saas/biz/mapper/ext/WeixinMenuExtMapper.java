package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.WeixinMenuMapper;
import com.saas.biz.pojo.WeixinMenu;

public interface WeixinMenuExtMapper  extends WeixinMenuMapper {
	
	List<WeixinMenu> selectListByDynamic(Map<Object, Object> paraMap);
	long selectListByDynamicCount(Map<Object, Object> paraMap);
	
}