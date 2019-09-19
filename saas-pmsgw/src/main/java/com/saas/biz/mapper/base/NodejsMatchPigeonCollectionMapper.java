package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsMatchPigeonCollection;

import java.util.List;
import java.util.Map;

public interface NodejsMatchPigeonCollectionMapper {
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
    int insert(NodejsMatchPigeonCollection record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsMatchPigeonCollection record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param collection_id
     */
    NodejsMatchPigeonCollection selectByPrimaryKey(String collection_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsMatchPigeonCollection record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsMatchPigeonCollection record);


}