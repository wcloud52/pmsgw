package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.WeixinMessage;
import com.saas.biz.pojo.WeixinUser;

public interface WeixinUserService {
	WeixinUser selectOneById(String id);
	int insert(WeixinUser record);
	int update(WeixinUser record);
	int delete(String id);
	List<WeixinUser> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	List<WeixinUser> selectAll();
	int insertByList(List<WeixinUser> list);
	int updateByList(List<WeixinUser> list);
	int insertMessage(WeixinMessage record);
	int updateSubscribe();
	int updateSubscribeByIds(List<String> list);
	int synchronize();
}
