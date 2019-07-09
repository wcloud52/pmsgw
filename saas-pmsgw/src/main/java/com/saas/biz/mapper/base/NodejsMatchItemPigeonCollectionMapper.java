package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsMatchItemPigeonCollection;

public interface NodejsMatchItemPigeonCollectionMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param item_collection_id
     */
    int deleteByPrimaryKey(String item_collection_id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsMatchItemPigeonCollection record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsMatchItemPigeonCollection record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param item_collection_id
     */
    NodejsMatchItemPigeonCollection selectByPrimaryKey(String item_collection_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsMatchItemPigeonCollection record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsMatchItemPigeonCollection record);
}