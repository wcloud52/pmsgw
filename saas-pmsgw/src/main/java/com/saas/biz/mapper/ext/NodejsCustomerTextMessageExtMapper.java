package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.NodejsCustomerTextMessageMapper; 
import com.saas.biz.pojo.NodejsCustomerTextMessage;

public interface NodejsCustomerTextMessageExtMapper extends NodejsCustomerTextMessageMapper {
    /**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	List<NodejsCustomerTextMessage> selectListByDynamic(Map<Object, Object> paraMap);
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
	int insertBatch(List<NodejsCustomerTextMessage> list);
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	int updateBatch(List<NodejsCustomerTextMessage> list);
	/**
	 * 动态删除
	 * @param paraMap
	 * @return
	 */
	int deleteByDynamic(Map<Object, Object> paraMap);
}