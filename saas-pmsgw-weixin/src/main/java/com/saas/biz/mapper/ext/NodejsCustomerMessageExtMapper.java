package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.mapper.base.NodejsCustomerMessageMapper;
import com.saas.biz.pojo.NodejsCustomerMessage;

public interface NodejsCustomerMessageExtMapper   extends NodejsCustomerMessageMapper {
	
	List<NodejsCustomerMessage> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	long selectCountByMessageid(@Param("message_id") String message_id);
}