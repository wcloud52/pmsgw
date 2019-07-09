package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saas.biz.mapper.impl.NodejsWeixinUserCoteExtendImplMapper;
import com.saas.biz.pojo.NodejsWeixinUserCoteExtend;
import com.saas.biz.service.NodejsWeixinUserCoteExtendService;
import com.saas.common.DynamicDelete;
import com.saas.common.DynamicDeleteSpecification;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.OpEnum;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;

@Service
public class NodejsWeixinUserCoteExtendServiceImpl implements NodejsWeixinUserCoteExtendService {
	@Autowired
	private NodejsWeixinUserCoteExtendImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(NodejsWeixinUserCoteExtend record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(NodejsWeixinUserCoteExtend record) {
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
	public NodejsWeixinUserCoteExtend selectOneById(Long id) {
		return implMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取全部记录
	 * @return
	 */
	@Override
	public List<NodejsWeixinUserCoteExtend> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<NodejsWeixinUserCoteExtend> list = DynamicQuery.selectByDynamic("sort_number ASC",nodes, new DynamicQuerySpecification<NodejsWeixinUserCoteExtend>() {
			@Override
			public List<NodejsWeixinUserCoteExtend> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	
	@Override
	public List<NodejsWeixinUserCoteExtend> selectByOpenid(String openid) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("openid", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), openid));
		List<NodejsWeixinUserCoteExtend> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsWeixinUserCoteExtend>() {
			@Override
			public List<NodejsWeixinUserCoteExtend> selectByDynamic(Map<Object, Object> map) {
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
	public List<NodejsWeixinUserCoteExtend> selectListByDynamic(Map<Object, Object> paraMap) {
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
		return 1;
	}
	
	@Override
	public int deleteByCoteId(String cote_id,String openid) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("cote_id", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), cote_id));
		nodes.add(new QueryNode("openid", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), openid));
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
	public int insertBatch(List<NodejsWeixinUserCoteExtend> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<NodejsWeixinUserCoteExtend> list)
	{
		return implMapper.updateBatch(list);
	}
}
