package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsCustomerGameMessage;

public interface NodejsCustomerGameMessageMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param message_id
     */
    int deleteByPrimaryKey(String message_id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsCustomerGameMessage record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsCustomerGameMessage record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param message_id
     */
    NodejsCustomerGameMessage selectByPrimaryKey(String message_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsCustomerGameMessage record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsCustomerGameMessage record);
}