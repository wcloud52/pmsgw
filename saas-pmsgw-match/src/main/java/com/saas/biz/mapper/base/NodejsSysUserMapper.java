package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsSysUser;

public interface NodejsSysUserMapper {
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
    int insert(NodejsSysUser record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(NodejsSysUser record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    NodejsSysUser selectByPrimaryKey(String id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(NodejsSysUser record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(NodejsSysUser record);
}