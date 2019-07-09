package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsWeixinUserCoteExtend;

public interface NodejsWeixinUserCoteExtendMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsWeixinUserCoteExtend record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsWeixinUserCoteExtend record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    NodejsWeixinUserCoteExtend selectByPrimaryKey(Long id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsWeixinUserCoteExtend record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsWeixinUserCoteExtend record);
}