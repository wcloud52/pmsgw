package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.NodejsMobileUser;

public interface NodejsMobileUserService  {

	int insert(NodejsMobileUser record);

	int update(NodejsMobileUser record);

	int deleteById(String id);

	NodejsMobileUser selectOneById(String id);

	List<NodejsMobileUser> selectAll();

	List<NodejsMobileUser> selectListByDynamic(Map<Object, Object> paraMap);

	long selectCountByDynamic(Map<Object, Object> paraMap);

	int insertBatch(List<NodejsMobileUser> list);

	int updateBatch(List<NodejsMobileUser> list);

	
}
