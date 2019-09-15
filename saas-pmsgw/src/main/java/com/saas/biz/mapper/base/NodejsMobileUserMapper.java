package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsMobileUser;

public interface NodejsMobileUserMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(NodejsMobileUser record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsMobileUser record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    NodejsMobileUser selectByPrimaryKey(String id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsMobileUser record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsMobileUser record);
}