package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saas.biz.mapper.ext.NodejsCrawlerCoteExtMapper;
import com.saas.biz.pojo.NodejsCrawlerCote;
import com.saas.biz.service.NodejsCrawlerCoteService;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.OpEnum;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.SignEnum;

@Service
public class NodejsCrawlerCoteServiceImpl implements NodejsCrawlerCoteService {
	
	@Autowired
	private NodejsCrawlerCoteExtMapper nodejsCrawlerCoteExtMapper;

	@Override
	public List<NodejsCrawlerCote> selectListByDynamic(Map<Object, Object> paraMap) {
		return nodejsCrawlerCoteExtMapper.selectListByDynamic(paraMap);
	}
	@Override
	public List<NodejsCrawlerCote> selectAll() {
		return nodejsCrawlerCoteExtMapper.selectByCoteState("1");
	}
	@Override
	public long selectCountByDynamic(Map<Object, Object> paraMap) {
		return nodejsCrawlerCoteExtMapper.selectCountByDynamic(paraMap);
	}
	@Override
	public int update(NodejsCrawlerCote record)
	{
		return nodejsCrawlerCoteExtMapper.updateByPrimaryKeySelective(record);
	}	
}
