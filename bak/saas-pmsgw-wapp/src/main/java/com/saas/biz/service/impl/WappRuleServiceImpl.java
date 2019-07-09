package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.biz.mapper.ext.WappRuleDetailExtMapper;
import com.saas.biz.mapper.ext.WappRuleMasterExtMapper;
import com.saas.biz.pojo.WappRuleDetail;
import com.saas.biz.pojo.WappRuleMaster;
import com.saas.biz.service.WappRuleService;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.QueryNode;

@Service
public class WappRuleServiceImpl implements WappRuleService {
	@Autowired
	private WappRuleMasterExtMapper wappRuleMasterExtMapper;
	
	@Autowired
	private WappRuleDetailExtMapper wappRuleDetailExtMapper;
	
	@Override
	public WappRuleMaster selectOneWappRuleMasterById(String id) {
		WappRuleMaster wappRuleMaster= wappRuleMasterExtMapper.selectByPrimaryKey(id);
		if(wappRuleMaster==null)
		{
			wappRuleMaster = new WappRuleMaster();
			wappRuleMaster.setId(UUID.randomUUID().toString());
		}
		
		return wappRuleMaster;
	}


	@Override
	public List<WappRuleMaster> selectAllWappRuleMaster() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<WappRuleMaster> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<WappRuleMaster>() {
			@Override
			public List<WappRuleMaster> selectByDynamic(Map<Object, Object> map) {
				return wappRuleMasterExtMapper.selectListByDynamic(map);
			}
		});
		return list;
	}


	@Override
	public int insertWappRuleMaster(WappRuleMaster record) {
		return wappRuleMasterExtMapper.insert(record);
	}


	@Override
	public int updateWappRuleMaster(WappRuleMaster record) {
		return wappRuleMasterExtMapper.updateByPrimaryKey(record);
	}


	@Override
	public int deleteWappRuleMaster(String id) {
		return wappRuleMasterExtMapper.deleteByPrimaryKey(id);
	}

	@Transactional
	@Override
	public int deleteWappRuleMasterByIds(List<String> ids) {
		for (String id : ids) {
			wappRuleMasterExtMapper.deleteByPrimaryKey(id);
		}
		return 1;
	}

	@Override
	public List<WappRuleMaster> selectWappRuleMasterListByDynamic(Map<Object, Object> paraMap) {
		return wappRuleMasterExtMapper.selectListByDynamic(paraMap);
	}


	@Override
	public long selectWappRuleMasterCountByDynamic(Map<Object, Object> paraMap) {
		return wappRuleMasterExtMapper.selectCountByDynamic(paraMap);
	}


	@Override
	public WappRuleDetail selectOneWappRuleDetailById(String id) {
		WappRuleDetail wappRuleDetail= wappRuleDetailExtMapper.selectByPrimaryKey(id);
		if(wappRuleDetail==null)
		{
			wappRuleDetail = new WappRuleDetail();
			wappRuleDetail.setId(UUID.randomUUID().toString());
		}
		
		return wappRuleDetail;
	}


	@Override
	public List<WappRuleDetail> selectAllWappRuleDetail() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<WappRuleDetail> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<WappRuleDetail>() {
			@Override
			public List<WappRuleDetail> selectByDynamic(Map<Object, Object> map) {
				return wappRuleDetailExtMapper.selectListByDynamic(map);
			}
		});
		return list;
	}


	@Override
	public int insertWappRuleDetail(WappRuleDetail record) {
		return wappRuleDetailExtMapper.insert(record);
	}


	@Override
	public int updateWappRuleDetail(WappRuleDetail record) {
		return wappRuleDetailExtMapper.updateByPrimaryKey(record);
	}


	@Override
	public int deleteWappRuleDetail(String id) {
		return wappRuleDetailExtMapper.deleteByPrimaryKey(id);
	}


	@Override
	public List<WappRuleDetail> selectWappRuleDetailListByDynamic(Map<Object, Object> paraMap) {
		return wappRuleDetailExtMapper.selectListByDynamic(paraMap);
	}


	@Override
	public long selectWappRuleDetailCountByDynamic(Map<Object, Object> paraMap) {
		return wappRuleDetailExtMapper.selectCountByDynamic(paraMap);
	}


	@Override
	public int deleteWappRuleDetailByIds(List<String> ids) {
		for (String id : ids) {
			wappRuleDetailExtMapper.deleteByPrimaryKey(id);
		}
		return 1;
	}
	
	
}
