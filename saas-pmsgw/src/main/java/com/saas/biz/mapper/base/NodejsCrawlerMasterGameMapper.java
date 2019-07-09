package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsCrawlerMasterGame;

public interface NodejsCrawlerMasterGameMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param master_id
     */
    int deleteByPrimaryKey(String master_id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsCrawlerMasterGame record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsCrawlerMasterGame record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param master_id
     */
    NodejsCrawlerMasterGame selectByPrimaryKey(String master_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsCrawlerMasterGame record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsCrawlerMasterGame record);
}