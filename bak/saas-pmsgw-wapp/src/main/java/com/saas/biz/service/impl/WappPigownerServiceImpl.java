package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.biz.mapper.ext.WappPigownerExtMapper;
import com.saas.biz.pojo.WappPigowner;
import com.saas.biz.service.WappPigownerService;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.QueryNode;

@Service
public class WappPigownerServiceImpl implements WappPigownerService {
	@Autowired
	private WappPigownerExtMapper WappPigownerExtMapper;

	@Override
	public WappPigowner selectOneWappPigownerById(String id) {
		
		WappPigowner WappPigowner= WappPigownerExtMapper.selectByPrimaryKey(id);
		if(WappPigowner==null)
		{
			WappPigowner = new WappPigowner();
			WappPigowner.setId(UUID.randomUUID().toString());
		}
		
		return WappPigowner;
	}

	@Override
	public List<WappPigowner> selectAllWappPigowner() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<WappPigowner> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<WappPigowner>() {
			@Override
			public List<WappPigowner> selectByDynamic(Map<Object, Object> map) {
				return WappPigownerExtMapper.selectListByDynamic(map);
			}
		});
		return list;
	}

	@Override
	public int insertWappPigowner(WappPigowner record) {
		return WappPigownerExtMapper.insert(record);
	}

	@Override
	public int updateWappPigowner(WappPigowner record) {
		return WappPigownerExtMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteWappPigowner(String id) {
		return WappPigownerExtMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<WappPigowner> selectWappPigownerListByDynamic(Map<Object, Object> paraMap) {
		return WappPigownerExtMapper.selectListByDynamic(paraMap);
	}

	@Override
	public long selectWappPigownerCountByDynamic(Map<Object, Object> paraMap) {
		return WappPigownerExtMapper.selectCountByDynamic(paraMap);
	}
	@Transactional
	@Override
	public int deleteWappPigownerByIds(List<String> ids) {
		for (String id : ids) {
			WappPigownerExtMapper.deleteByPrimaryKey(id);
		}
		return 1;
	}
}
