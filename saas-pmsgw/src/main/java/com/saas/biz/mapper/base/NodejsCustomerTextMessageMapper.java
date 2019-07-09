package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsCustomerTextMessage;

public interface NodejsCustomerTextMessageMapper {
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
    int insert(NodejsCustomerTextMessage record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsCustomerTextMessage record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param message_id
     */
    NodejsCustomerTextMessage selectByPrimaryKey(String message_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsCustomerTextMessage record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsCustomerTextMessage record);
}