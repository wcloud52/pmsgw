package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.OpEnum;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;

import com.saas.biz.mapper.impl.NodejsMpUserImplMapper;
import com.saas.biz.pojo.NodejsMpUser;
import com.saas.biz.pojo.NodejsStorage;
import com.saas.biz.service.NodejsMpUserService;

@Service
public class NodejsMpUserServiceImpl implements NodejsMpUserService {
	@Autowired
	private NodejsMpUserImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(NodejsMpUser record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(NodejsMpUser record) {
		return implMapper.updateByPrimaryKeySelective(record);
	}
    /**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(Integer id) {
		return implMapper.deleteByPrimaryKey(id);
	}
    /**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public NodejsMpUser selectOneById(Integer id) {
		return implMapper.selectByPrimaryKey(id);
	}
	@Override
	public NodejsMpUser selectOneByUsername(String username) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("username", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), username));
		List<NodejsMpUser> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsMpUser>() {
			@Override
			public List<NodejsMpUser> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		if(list!=null&&list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
	@Override
	public List<NodejsMpUser> selectListByUsername(String username) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("username", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), username));
		List<NodejsMpUser> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsMpUser>() {
			@Override
			public List<NodejsMpUser> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		
		return list;
	}
	@Override
	public List<NodejsMpUser> selectListByMobile(String mobile) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("mobile", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), mobile));
		List<NodejsMpUser> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsMpUser>() {
			@Override
			public List<NodejsMpUser> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		
		return list;
	}
	@Override
	public NodejsMpUser selectOneByWeixinOpenid(String weixin_openid) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("weixin_openid", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), weixin_openid));
		List<NodejsMpUser> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsMpUser>() {
			@Override
			public List<NodejsMpUser> selectByDynamic(Map<Object, Object> map) {
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
	 * 获取全部记录
	 * @return
	 */
	@Override
	public List<NodejsMpUser> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<NodejsMpUser> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsMpUser>() {
			@Override
			public List<NodejsMpUser> selectByDynamic(Map<Object, Object> map) {
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
	public List<NodejsMpUser> selectListByDynamic(Map<Object, Object> paraMap) {
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
	public int deleteByIds(List<Integer> ids) {
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
		return 1;
	}
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	@Override
	public int insertBatch(List<NodejsMpUser> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<NodejsMpUser> list)
	{
		return implMapper.updateBatch(list);
	}
}
