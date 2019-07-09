package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.biz.mapper.ext.WappGameExtMapper;
import com.saas.biz.pojo.WappGame;
import com.saas.biz.service.WappGameService;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.QueryNode;

@Service
public class WappGameServiceImpl implements WappGameService {
	@Autowired
	private WappGameExtMapper wappGameExtMapper;

	@Override
	public WappGame selectOneWappGameById(String id) {
		
		WappGame wappGame= wappGameExtMapper.selectByPrimaryKey(id);
		if(wappGame==null)
		{
			wappGame = new WappGame();
			wappGame.setId(UUID.randomUUID().toString());
		}
		
		return wappGame;
	}

	@Override
	public List<WappGame> selectAllWappGame() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<WappGame> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<WappGame>() {
			@Override
			public List<WappGame> selectByDynamic(Map<Object, Object> map) {
				return wappGameExtMapper.selectListByDynamic(map);
			}
		});
		return list;
	}

	@Override
	public int insertWappGame(WappGame record) {
		return wappGameExtMapper.insert(record);
	}

	@Override
	public int updateWappGame(WappGame record) {
		return wappGameExtMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteWappGame(String id) {
		return wappGameExtMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<WappGame> selectWappGameListByDynamic(Map<Object, Object> paraMap) {
		return wappGameExtMapper.selectListByDynamic(paraMap);
	}

	@Override
	public long selectWappGameCountByDynamic(Map<Object, Object> paraMap) {
		return wappGameExtMapper.selectCountByDynamic(paraMap);
	}
	@Transactional
	@Override
	public int deleteWappGameByIds(List<String> ids) {
		for (String id : ids) {
			wappGameExtMapper.deleteByPrimaryKey(id);
		}
		return 1;
	}
}
