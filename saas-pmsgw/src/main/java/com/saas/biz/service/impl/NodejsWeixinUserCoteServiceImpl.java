package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.OpEnum;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;


import com.saas.biz.mapper.impl.NodejsWeixinUserCoteImplMapper;
import com.saas.biz.pojo.NodejsWeixinUserCote;
import com.saas.biz.service.NodejsWeixinUserCoteService;

@Service
public class NodejsWeixinUserCoteServiceImpl implements NodejsWeixinUserCoteService {
	@Autowired
	private NodejsWeixinUserCoteImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(NodejsWeixinUserCote record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(NodejsWeixinUserCote record) {
		return implMapper.updateByPrimaryKeySelective(record);
	}
    /**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(Long id) {
		return implMapper.deleteByPrimaryKey(id);
	}
    /**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public NodejsWeixinUserCote selectOneById(Long id) {
		return implMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取全部记录
	 * @return
	 */
	@Override
	public List<NodejsWeixinUserCote> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<NodejsWeixinUserCote> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsWeixinUserCote>() {
			@Override
			public List<NodejsWeixinUserCote> selectByDynamic(Map<Object, Object> map) {
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
	public List<NodejsWeixinUserCote> selectListByDynamic(Map<Object, Object> paraMap) {
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
	public int deleteByIds(List<Long> ids) {
		/*List<QueryNode> nodes = new ArrayList<QueryNode>();
		String idString = StringUtils.collectionToDelimitedString(ids, ",");
		nodes.add(new QueryNode("id", OpEnum.IN.getName(), idString));
		int result = DynamicDelete.deleteByDynamic(nodes, new DynamicDeleteSpecification() {
			@Override
			public int deleteByDynamic(Map<Object, Object> map) {
				return implMapper.deleteByDynamic(map);
			}
		});
		return result;*/
		return -1;
	}
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	@Override
	public int insertBatch(List<NodejsWeixinUserCote> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<NodejsWeixinUserCote> list)
	{
		return implMapper.updateBatch(list);
	}
}
