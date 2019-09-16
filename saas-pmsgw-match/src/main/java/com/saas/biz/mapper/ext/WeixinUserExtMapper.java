package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.WeixinUserMapper;
import com.saas.biz.pojo.WeixinUser;

public interface WeixinUserExtMapper   extends WeixinUserMapper {
	
	List<WeixinUser> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	
	int insertByList(List<WeixinUser> list);
	int updateByList(List<WeixinUser> list);
	int updateSubscribe();
	
	int updateSubscribeByIds(List<String> list);
	
	int synchronize();
}