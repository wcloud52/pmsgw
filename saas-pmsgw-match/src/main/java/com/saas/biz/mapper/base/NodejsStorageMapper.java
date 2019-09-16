package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsStorage;

public interface NodejsStorageMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsStorage record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsStorage record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    NodejsStorage selectByPrimaryKey(Integer id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsStorage record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsStorage record);
}