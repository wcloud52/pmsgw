package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.saas.biz.mapper.impl.NodejsCrawlerCoteExtendImplMapper;
import com.saas.biz.pojo.NodejsCrawlerCoteExtend;
import com.saas.biz.service.NodejsCrawlerCoteExtendService;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.OpEnum;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;

@Service
public class NodejsCrawlerCoteExtendServiceImpl implements NodejsCrawlerCoteExtendService {
	@Autowired
	private NodejsCrawlerCoteExtendImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(NodejsCrawlerCoteExtend record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(NodejsCrawlerCoteExtend record) {
		return implMapper.updateByPrimaryKeySelective(record);
	}
    /**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(String cote_id) {
		return implMapper.deleteByPrimaryKey(cote_id);
	}
    /**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public NodejsCrawlerCoteExtend selectOneById(String cote_id) {
		return implMapper.selectByPrimaryKey(cote_id);
	}
	
	/**
	 * 获取全部记录
	 * @return
	 */
	@Override
	public List<NodejsCrawlerCoteExtend> selectAll() {	
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<NodejsCrawlerCoteExtend> list = DynamicQuery.selectByDynamic("sort_number ASC",nodes, new DynamicQuerySpecification<NodejsCrawlerCoteExtend>() {
			@Override
			public List<NodejsCrawlerCoteExtend> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	
	@Override
	public List<NodejsCrawlerCoteExtend> selectByCotestate(String cote_state) {
		
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("cote_state", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), cote_state));
		List<NodejsCrawlerCoteExtend> list = DynamicQuery.selectByDynamic("sort_number ASC",nodes, new DynamicQuerySpecification<NodejsCrawlerCoteExtend>() {
			@Override
			public List<NodejsCrawlerCoteExtend> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	
	@Override
	public NodejsCrawlerCoteExtend selectByCoteId(String cote_id) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("cote_id", OpEnum.EQUALS.getName(),PrependEnum.AND.getName(), cote_id));
		List<NodejsCrawlerCoteExtend> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsCrawlerCoteExtend>() {
			@Override
			public List<NodejsCrawlerCoteExtend> selectByDynamic(Map<Object, Object> map) {
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
	public List<NodejsCrawlerCoteExtend> selectListByDynamic(Map<Object, Object> paraMap) {
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
		nodes.add(new QueryNode("cote_id", OpEnum.IN.getName(), idString));
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
	public int insertBatch(List<NodejsCrawlerCoteExtend> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<NodejsCrawlerCoteExtend> list)
	{
		return implMapper.updateBatch(list);
	}
}
