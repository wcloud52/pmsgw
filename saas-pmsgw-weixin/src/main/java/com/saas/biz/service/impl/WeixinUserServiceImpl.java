package com.saas.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.biz.mapper.ext.WeixinUserExtMapper;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.WeixinUserService;

@Service
public class WeixinUserServiceImpl implements WeixinUserService {
	@Autowired
	private WeixinUserExtMapper weixinUserExtMapper;
	

	@Override
	public WeixinUser selectOneById(String id) {
	
		return weixinUserExtMapper.selectByPrimaryKey(id);
	}

	@Transactional
	@Override
	public int insert(WeixinUser record) {
		return weixinUserExtMapper.insert(record);
	}

	@Override
	public int update(WeixinUser record) {
		return  weixinUserExtMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<WeixinUser> selectListByDynamic(Map<Object, Object> paraMap) {
	
		return weixinUserExtMapper.selectListByDynamic(paraMap);
	}

	@Override
	public long selectCountByDynamic(Map<Object, Object> paraMap) {
		
		return weixinUserExtMapper.selectCountByDynamic(paraMap);
	}
	 @Cacheable(cacheNames="weixin_user",key="'weixin_user_selectAll'",sync = true)
	@Override
	public List<WeixinUser> selectAll() {	
		return weixinUserExtMapper.selectAllList();
	}
	@Override
	public int delete( String id) {
		return weixinUserExtMapper.deleteByPrimaryKey(id);
	}
	@Override
	public int insertByList(List<WeixinUser> list)
	{
		return weixinUserExtMapper.insertByList(list);
	}
	@Override
	public int updateByList(List<WeixinUser> list)
	{
		return weixinUserExtMapper.updateByList(list);
	}

	
	@Override
	public int updateSubscribe()
	{
		return weixinUserExtMapper.updateSubscribe();
	}
	
}
