package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsCustomerMessagePigowner;

public interface NodejsCustomerMessagePigownerMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param openid_coteid
     */
    int deleteByPrimaryKey(String openid_coteid);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsCustomerMessagePigowner record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsCustomerMessagePigowner record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param openid_coteid
     */
    NodejsCustomerMessagePigowner selectByPrimaryKey(String openid_coteid);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsCustomerMessagePigowner record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsCustomerMessagePigowner record);
}