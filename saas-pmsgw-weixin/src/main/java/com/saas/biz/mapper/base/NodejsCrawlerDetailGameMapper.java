package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsCrawlerDetailGame;

public interface NodejsCrawlerDetailGameMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param detail_id
     */
    int deleteByPrimaryKey(String detail_id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsCrawlerDetailGame record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsCrawlerDetailGame record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param detail_id
     */
    NodejsCrawlerDetailGame selectByPrimaryKey(String detail_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsCrawlerDetailGame record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsCrawlerDetailGame record);
}