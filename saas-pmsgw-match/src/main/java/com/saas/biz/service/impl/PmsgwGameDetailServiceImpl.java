package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.biz.mapper.ext.PmsgwGameDetailExtMapper;
import com.saas.biz.pojo.PmsgwGameDetail;
import com.saas.biz.service.PmsgwGameDetailService;
import com.saas.common.BaseResponse;
import com.saas.common.JsonResult;
import com.saas.common.OpEnum;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;
import com.saas.common.SignEnum;

@Service
public class PmsgwGameDetailServiceImpl implements PmsgwGameDetailService {
	@Autowired
	private PmsgwGameDetailExtMapper pmsgwGameDetailExtMapper;

	@Override
	public PmsgwGameDetail selectOneById(Long id) {
	
		return pmsgwGameDetailExtMapper.selectByPrimaryKey(id);
	}

	@Transactional
	@Override
	public int insert(PmsgwGameDetail record) {
		return pmsgwGameDetailExtMapper.insert(record);
	}
	@Override
	public int insertByList(List<PmsgwGameDetail> list) {
		return pmsgwGameDetailExtMapper.insertByList(list);
	}

	@Override
	public int update(PmsgwGameDetail record) {
		
		return pmsgwGameDetailExtMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<PmsgwGameDetail> selectListByDynamic(Map<Object, Object> paraMap) {
	
		return pmsgwGameDetailExtMapper.selectListByDynamic(paraMap);
	}

	@Override
	public long selectListByDynamicCount(Map<Object, Object> paraMap) {
		
		return pmsgwGameDetailExtMapper.selectListByDynamicCount(paraMap);
	}

	@Override
	public int delete(Long id) {
		return pmsgwGameDetailExtMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByMasterId(Long masterId) {
		return pmsgwGameDetailExtMapper.deleteByMasterId(masterId);
	}

	@Override
	public List<PmsgwGameDetail> selectListByMasterId(Long masterId) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("masterId",OpEnum.EQUALS.getName(),PrependEnum.AND.getName(),masterId,SignEnum.NONE.getName()));
		
		List<QueryNodes> queryNodes = QueryNodes.createQueryNodesList(nodes,"and");
		
		QueryObject query = new QueryObject();
		query.setPageIndex(1);
		query.setPageSize(10000);
		query.setSort(" rank asc,create_time asc ");
		query.setFuzzyQuery(queryNodes);
		
		BaseResponse<JsonResult<List<PmsgwGameDetail>, Object>> restult= PagingAndSortingRepository.find(query, new PageSpecification<PmsgwGameDetail>() {

			@Override
			public List<PmsgwGameDetail> query(Map<Object, Object> map) {
				return pmsgwGameDetailExtMapper.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return 10000;
			}
		});
		return restult.getData().getList();
	}

	@Override
	public PmsgwGameDetail selectTopOne(String pigowner) {
		return pmsgwGameDetailExtMapper.selectTopOne(pigowner);
	}
	@Override
	public List<PmsgwGameDetail> selectTopMore(String pigowner) {
		return pmsgwGameDetailExtMapper.selectTopMore(pigowner);
	}
	@Override
	public List<PmsgwGameDetail> selectListByList(Long masterId, List<PmsgwGameDetail> list)
	{
		if(list!=null&&list.size()>0)
		return pmsgwGameDetailExtMapper.selectListByList(masterId,list);
		else
			return new ArrayList<PmsgwGameDetail>();
	}
}
