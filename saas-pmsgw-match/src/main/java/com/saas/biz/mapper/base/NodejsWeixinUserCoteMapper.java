package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsWeixinUserCote;

public interface NodejsWeixinUserCoteMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsWeixinUserCote record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsWeixinUserCote record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    NodejsWeixinUserCote selectByPrimaryKey(Long id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsWeixinUserCote record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsWeixinUserCote record);
}