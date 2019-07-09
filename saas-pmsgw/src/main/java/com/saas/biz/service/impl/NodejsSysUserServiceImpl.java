package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saas.common.BaseResponse;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.JsonResult;
import com.saas.common.OpEnum;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;
import com.saas.common.SignEnum;

import com.saas.biz.mapper.impl.NodejsSysUserImplMapper;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.service.NodejsSysUserService;

@Service
public class NodejsSysUserServiceImpl implements NodejsSysUserService {
	@Autowired
	private NodejsSysUserImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(NodejsSysUser record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(NodejsSysUser record) {
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
	public NodejsSysUser selectOneById(String id) {
		return implMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取全部记录
	 * @return
	 */
	@Override
	public List<NodejsSysUser> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<NodejsSysUser> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsSysUser>() {
			@Override
			public List<NodejsSysUser> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	
	@Override
	public NodejsSysUser selectOneByLoginName(String loginName) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("loginName", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), loginName));
		List<NodejsSysUser> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsSysUser>() {
			@Override
			public List<NodejsSysUser> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		if(list!=null&&list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	@Override
	public List<NodejsSysUser> selectListByDynamic(Map<Object, Object> paraMap) {
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
		/*List<QueryNode> nodes = new ArrayList<QueryNode>();
		String idString = StringUtils.collectionToDelimitedString(ids, ",");
		nodes.add(new QueryNode("Id", OpEnum.IN.getName(), idString));
		int result = DynamicDelete.deleteByDynamic(nodes, new DynamicDeleteSpecification() {
			@Override
			public int deleteByDynamic(Map<Object, Object> map) {
				return implMapper.deleteByDynamic(map);
			}
		});
		return result;*/
		return 1;
	}
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	@Override
	public int insertBatch(List<NodejsSysUser> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<NodejsSysUser> list)
	{
		return implMapper.updateBatch(list);
	}
}
