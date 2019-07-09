package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.NodejsNewsMapper; 
import com.saas.biz.pojo.NodejsNews;

public interface NodejsNewsExtMapper extends NodejsNewsMapper {
    /**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	List<NodejsNews> selectListByDynamic(Map<Object, Object> paraMap);
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
	int insertBatch(List<NodejsNews> list);
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	int updateBatch(List<NodejsNews> list);
	/**
	 * 动态删除
	 * @param paraMap
	 * @return
	 */
	int deleteByDynamic(Map<Object, Object> paraMap);
}