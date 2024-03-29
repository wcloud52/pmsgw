package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsPigeonCollection;

public interface NodejsPigeonCollectionMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param collection_id
     */
    int deleteByPrimaryKey(String collection_id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsPigeonCollection record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsPigeonCollection record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param collection_id
     */
    NodejsPigeonCollection selectByPrimaryKey(String collection_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsPigeonCollection record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsPigeonCollection record);
}