package com.saas.biz.service.impl;

import com.saas.biz.mapper.base.WeixinMessageMapper;
import com.saas.biz.mapper.ext.WeixinUserPurseExtMapper;
import com.saas.biz.mapper.impl.WeixinUserPurseImplMapper;
import com.saas.biz.pojo.WeixinMessage;
import com.saas.biz.pojo.WeixinUserPurse;
import com.saas.biz.service.WeixinUserPurseService;
import com.saas.biz.service.WeixinUserPurseService;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.QueryNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeixinUserPurseServiceImpl implements WeixinUserPurseService {
	@Autowired
	private WeixinUserPurseImplMapper implMapper;

	@Override
	public WeixinUserPurse selectOneById(String id) {
	
		return implMapper.selectByPrimaryKey(id);
	}

	@Transactional
	@Override
	public int insert(WeixinUserPurse record) {
		return implMapper.insert(record);
	}

	@Override
	public int update(WeixinUserPurse record) {
		return  implMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteById(String id) {
		return implMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByIds(List<String> ids) {
		return 0;
	}

	@Override
	public List<WeixinUserPurse> selectListByDynamic(Map<Object, Object> paraMap) {
	
		return implMapper.selectListByDynamic(paraMap);
	}

	@Override
	public long selectCountByDynamic(Map<Object, Object> paraMap) {
		
		return implMapper.selectCountByDynamic(paraMap);
	}
	@Override
	public List<WeixinUserPurse> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<WeixinUserPurse> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<WeixinUserPurse>() {
			@Override
			public List<WeixinUserPurse> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	@Override
	public int insertBatch(List<WeixinUserPurse> list)
	{
		return implMapper.insertByList(list);
	}
	@Override
	public int updateBatch(List<WeixinUserPurse> list)
	{
		return implMapper.updateByList(list);
	}

	@Override
	public List<WeixinUserPurse> selectByJoin(WeixinUserPurse weixinUserPurse) {
		return implMapper.selectByJoin(weixinUserPurse);
	}
}
