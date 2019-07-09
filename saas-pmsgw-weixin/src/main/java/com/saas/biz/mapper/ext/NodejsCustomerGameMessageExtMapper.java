package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.mapper.base.NodejsCustomerGameMessageMapper;
import com.saas.biz.pojo.NodejsCustomerGameMessage;

public interface NodejsCustomerGameMessageExtMapper extends NodejsCustomerGameMessageMapper {
	List<NodejsCustomerGameMessage> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	List<NodejsCustomerGameMessage> selectByCoteId(@Param("game_cote_id") String game_cote_id);
}