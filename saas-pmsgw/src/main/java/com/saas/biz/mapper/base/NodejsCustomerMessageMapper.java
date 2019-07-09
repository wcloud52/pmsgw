package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsCustomerMessage;

public interface NodejsCustomerMessageMapper {
    int deleteByPrimaryKey(String message_id);

    int insert(NodejsCustomerMessage record);

    int insertSelective(NodejsCustomerMessage record);

    NodejsCustomerMessage selectByPrimaryKey(String message_id);

    int updateByPrimaryKeySelective(NodejsCustomerMessage record);

    int updateByPrimaryKey(NodejsCustomerMessage record);
}