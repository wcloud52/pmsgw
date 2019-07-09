package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.biz.mapper.base.WeixinMessageMapper;
import com.saas.biz.mapper.ext.WeixinUserExtMapper;
import com.saas.biz.pojo.WeixinMessage;
import com.saas.biz.pojo.WeixinUser;
import com.saas.biz.service.WeixinUserService;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.QueryNode;

@Service
public class WeixinUserServiceImpl implements WeixinUserService {
	@Autowired
	private WeixinUserExtMapper weixinUserExtMapper;
	
	@Autowired
	private WeixinMessageMapper weixinMessageMapper;

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
	@Override
	public List<WeixinUser> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<WeixinUser> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<WeixinUser>() {
			@Override
			public List<WeixinUser> selectByDynamic(Map<Object, Object> map) {
				return weixinUserExtMapper.selectListByDynamic(map);
			}
		});
		return list;
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
	public int insertMessage(WeixinMessage record) {
		return weixinMessageMapper.insert(record);
	}
	
	@Override
	public int updateSubscribe()
	{
		return weixinUserExtMapper.updateSubscribe();
	}
	@Override
	public int updateSubscribeByIds(List<String> list)
	{
		return weixinUserExtMapper.updateSubscribeByIds(list);
	}
	@Override
	public int synchronize()
	{
		return weixinUserExtMapper.synchronize();
	}
}
