package com.saas.biz.service;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.NodejsCrawlerCoteExtend;
import com.saas.biz.pojo.NodejsSysUser;
import com.saas.biz.pojo.NodejsWeixinUserCoteExtend;

public interface BaseService<T, PK extends Serializable> {
	/**
	 * 插入
	 * @param record
	 * @return
	 */
	int insert(T record);
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	int update(T record);
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	int deleteById(PK id);
	/**
	 * 根据主键列表删除
	 * @param ids
	 * @return
	 */
	int deleteByIds(List<PK> ids);
	/**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	T selectOneById(PK id);	
	/**
	 * 获取全部记录
	 * @return
	 */
	List<T> selectAll();
	/**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	List<T> selectListByDynamic(Map<Object, Object> paraMap);
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
	int insertBatch(List<T> list);

	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	int updateBatch(List<T> list);
	
}
