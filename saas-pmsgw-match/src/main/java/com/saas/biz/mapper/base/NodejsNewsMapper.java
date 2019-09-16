package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsNews;

public interface NodejsNewsMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param news_id
     */
    int deleteByPrimaryKey(String news_id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsNews record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsNews record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param news_id
     */
    NodejsNews selectByPrimaryKey(String news_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsNews record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsNews record);
}