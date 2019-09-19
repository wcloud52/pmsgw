package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.NodejsMatchPigeonCollectionMapper; 
import com.saas.biz.pojo.NodejsMatchPigeonCollection;

public interface NodejsMatchPigeonCollectionExtMapper extends NodejsMatchPigeonCollectionMapper {
    /**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	List<NodejsMatchPigeonCollection> selectListByDynamic(Map<Object, Object> paraMap);
	/**
	 * 动态查询总数
	 * @param paraMap
	 * @return
	 */
	long selectCountByDynamic(Map<Object, Object> paraMap);
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int insertBatch(List<NodejsMatchPigeonCollection> list);
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	int updateBatch(List<NodejsMatchPigeonCollection> list);
	/**
	 * 动态删除
	 * @param paraMap
	 * @return
	 */
	int deleteByDynamic(Map<Object, Object> paraMap);

	/**
	 * 查询某比赛上传的集鸽，根据会员编号分组
	 * @param map
	 * @return
	 */
	List<NodejsMatchPigeonCollection> selectListGroupByPigownerNum(Map<String, Object> map);

}