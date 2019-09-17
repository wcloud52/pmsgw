package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.WeixinUser;
import org.springframework.cache.annotation.Cacheable;

public interface WeixinUserService {
	WeixinUser selectOneById(String id);
	int insert(WeixinUser record);
	int update(WeixinUser record);


    //公棚
    @Cacheable(cacheNames = "weixin_user", key = "'weixin_user_selectAllWithMapByLoftMobile'", sync = true)
    Map <String, List <WeixinUser>> selectAllWithMapByLoftMobile();

    Map<String,List<WeixinUser>> selectAllWithMapByLoft();


	Map<String,List<WeixinUser>> selectAllWithMapByClub();

	int delete(String id);
	List<WeixinUser> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	List<WeixinUser> selectAll();
	int insertByList(List<WeixinUser> list);
	int updateByList(List<WeixinUser> list);
	int updateSubscribe();
}
