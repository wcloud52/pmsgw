package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsCustomerGameMessage;

public interface NodejsCustomerGameMessageMapper {
    int deleteByPrimaryKey(String message_id);

    int insert(NodejsCustomerGameMessage record);

    int insertSelective(NodejsCustomerGameMessage record);

    NodejsCustomerGameMessage selectByPrimaryKey(String message_id);

    int updateByPrimaryKeySelective(NodejsCustomerGameMessage record);

    int updateByPrimaryKey(NodejsCustomerGameMessage record);
}