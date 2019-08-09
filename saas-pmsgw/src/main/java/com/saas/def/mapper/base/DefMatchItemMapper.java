package com.saas.def.mapper.base;

import com.saas.def.pojo.DefMatchItem;

public interface DefMatchItemMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param item_id
     */
    int deleteByPrimaryKey(String item_id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(DefMatchItem record);

    /**
     * 部分插入数据库记录
     *
     * @param record
     */
    int insertSelective(DefMatchItem record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param item_id
     */
    DefMatchItem selectByPrimaryKey(String item_id);

    /**
     * 根据主键来部分更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(DefMatchItem record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(DefMatchItem record);
}