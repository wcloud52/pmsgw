package com.saas.biz.mapper.ext;

import com.saas.biz.mapper.base.NodejsMatchRegistMapper;
import com.saas.biz.pojo.NodejsMatchRegist;

import java.util.List;
import java.util.Map;

public interface NodejsMatchRegistExtMapper extends NodejsMatchRegistMapper {
    /**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	List<NodejsMatchRegist> selectListByDynamic(Map<Object, Object> paraMap);
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
	int insertBatch(List<NodejsMatchRegist> list);
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	int updateBatch(List<NodejsMatchRegist> list);
	/**
	 * 动态删除
	 * @param paraMap
	 * @return
	 */
	int deleteByDynamic(Map<Object, Object> paraMap);
	/**
	 * 比赛结束后根据比赛结果更新报名表
	 * @param paraMap
	 * @return
	 */
	int updateRegistRank(Map<Object, Object> paraMap);


	int updateSumReward(List<NodejsMatchRegist> list);

	/**
	 * 从比赛结果表中查询结果
	 * @param paraMap
	 * @return
	 */
	List<NodejsMatchRegist> selectCrawlerDetailGame(Map<Object, Object> paraMap);
}