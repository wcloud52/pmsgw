package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsMatch;

public interface NodejsMatchMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param match_id
     */
    int deleteByPrimaryKey(String match_id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsMatch record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsMatch record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param match_id
     */
    NodejsMatch selectByPrimaryKey(String match_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsMatch record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsMatch record);
}