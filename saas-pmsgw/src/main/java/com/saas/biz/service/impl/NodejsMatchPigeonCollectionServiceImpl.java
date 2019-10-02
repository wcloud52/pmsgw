package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saas.common.BaseResponse;
import com.saas.common.DynamicDelete;
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

import com.saas.biz.mapper.impl.NodejsMatchPigeonCollectionImplMapper;
import com.saas.biz.pojo.NodejsMatchPigeonCollection;
import com.saas.biz.service.NodejsMatchPigeonCollectionService;

@Service
public class NodejsMatchPigeonCollectionServiceImpl implements NodejsMatchPigeonCollectionService {
	@Autowired
	private NodejsMatchPigeonCollectionImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(NodejsMatchPigeonCollection record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(NodejsMatchPigeonCollection record) {
		return implMapper.updateByPrimaryKeySelective(record);
	}
    /**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(String collection_id) {
		return implMapper.deleteByPrimaryKey(collection_id);
	}
    /**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public NodejsMatchPigeonCollection selectOneById(String collection_id) {
		return implMapper.selectByPrimaryKey(collection_id);
	}
	
	/**
	 * 获取全部记录
	 * @return
	 */
	@Override
	public List<NodejsMatchPigeonCollection> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<NodejsMatchPigeonCollection> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsMatchPigeonCollection>() {
			@Override
			public List<NodejsMatchPigeonCollection> selectByDynamic(Map<Object, Object> map) {
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
	public List<NodejsMatchPigeonCollection> selectListByDynamic(Map<Object, Object> paraMap) {
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
		nodes.add(new QueryNode("collection_id", OpEnum.IN.getName(), idString));
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
	public int insertBatch(List<NodejsMatchPigeonCollection> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<NodejsMatchPigeonCollection> list)
	{
		return implMapper.updateBatch(list);
	}

	@Override
	public List<NodejsMatchPigeonCollection> selectListGroupByPigownerNum(Map<String, Object> map) {
		return implMapper.selectListGroupByPigownerNum(map);
	}
	@Override
	public List<NodejsMatchPigeonCollection> selectGrpupByPigeonCode(Map<String, Object> map) {
		return implMapper.selectGrpupByPigeonCode(map);
	}
	@Override
	public List<NodejsMatchPigeonCollection> selectListBySign(Map<String, Object> map) {
		return implMapper.selectListBySign(map);
	}

	@Override
	public int deleteByMatchId(String match_id) {
		return implMapper.deleteByMatchId(match_id);
	}

}
