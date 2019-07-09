package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.saas.biz.mapper.impl.NodejsMobileUserImplMapper;
import com.saas.biz.pojo.NodejsMobileUser;
import com.saas.biz.service.NodejsMobileUserService;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.QueryNode;

@Service
public class NodejsMobileUserServiceImpl implements NodejsMobileUserService {
	@Autowired
	private NodejsMobileUserImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(NodejsMobileUser record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(NodejsMobileUser record) {
		return implMapper.updateByPrimaryKeySelective(record);
	}
    /**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(String id) {
		return implMapper.deleteByPrimaryKey(id);
	}
    /**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public NodejsMobileUser selectOneById(String id) {
		return implMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取全部记录
	 * @return
	 */
	 @Cacheable(cacheNames="mobile_user",key="'mobile_user_selectAll'")
	@Override
	public List<NodejsMobileUser> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<NodejsMobileUser> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsMobileUser>() {
			@Override
			public List<NodejsMobileUser> selectByDynamic(Map<Object, Object> map) {
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
	public List<NodejsMobileUser> selectListByDynamic(Map<Object, Object> paraMap) {
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
	 * 批量插入
	 * @param list
	 * @return
	 */
	@Override
	public int insertBatch(List<NodejsMobileUser> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<NodejsMobileUser> list)
	{
		return implMapper.updateBatch(list);
	}
}
