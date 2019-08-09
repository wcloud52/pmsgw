package com.saas.def.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.saas.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saas.def.mapper.impl.DefMatchImplMapper;
import com.saas.def.pojo.DefMatch;
import com.saas.def.service.DefMatchService;

@Service
public class DefMatchServiceImpl implements DefMatchService {
	@Autowired
	private DefMatchImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(DefMatch record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(DefMatch record) {
		return implMapper.updateByPrimaryKeySelective(record);
	}
    /**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(String match_id) {
		return implMapper.deleteByPrimaryKey(match_id);
	}
    /**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public DefMatch selectOneById(String match_id) {
		return implMapper.selectByPrimaryKey(match_id);
	}

	/**
	 * 获取全部记录
	 * @return
	 */
	@Override
	public List<DefMatch> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<DefMatch> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<DefMatch>() {
			@Override
			public List<DefMatch> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	/**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	@Override
	public List<DefMatch> selectListByDynamic(Map<Object, Object> paraMap) {
		return implMapper.selectListByDynamic(paraMap);
	}
	/**
	 * 动态查询总数
	 * @param paraMap
	 * @return
	 */
	@Override
	public long selectCountByDynamic(Map<Object, Object> paraMap) {
		return implMapper.selectCountByDynamic(paraMap);
	}
	/**
	 * 根据主键列表删除
	 * @param ids
	 * @return
	 */
	@Override
	public int deleteByIds(List<String> ids) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		String idString = StringUtils.collectionToDelimitedString(ids, ",");
		//nodes.add(new QueryNode("match_id", OpEnum.IN.getName(), idString));
		int result = DynamicDelete.deleteByDynamic(nodes, new DynamicDeleteSpecification() {
			@Override
			public int deleteByDynamic(Map<Object, Object> map) {
				return implMapper.deleteByDynamic(map);
			}
		});
		return result;
	}
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	@Override
	public int insertBatch(List<DefMatch> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<DefMatch> list)
	{
		return implMapper.updateBatch(list);
	}
}
