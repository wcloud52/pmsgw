package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsCrawlerCote;

public interface NodejsCrawlerCoteMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param cote_id
     */
    int deleteByPrimaryKey(String cote_id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsCrawlerCote record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsCrawlerCote record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param cote_id
     */
    NodejsCrawlerCote selectByPrimaryKey(String cote_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsCrawlerCote record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsCrawlerCote record);
}