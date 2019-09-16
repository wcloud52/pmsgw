package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsMatchItem;

public interface NodejsMatchItemMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param item_id
     */
    int deleteByPrimaryKey(String item_id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsMatchItem record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsMatchItem record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param item_id
     */
    NodejsMatchItem selectByPrimaryKey(String item_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsMatchItem record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsMatchItem record);
}