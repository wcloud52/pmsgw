package com.saas.def.mapper.base;

import com.saas.def.pojo.DefMatch;

public interface DefMatchMapper {
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
    int insert(DefMatch record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(DefMatch record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param match_id
     */
    DefMatch selectByPrimaryKey(String match_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(DefMatch record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(DefMatch record);
}