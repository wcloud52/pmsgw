package com.saas.biz.mapper.base;

import com.saas.biz.pojo.WappGame;

public interface WappGameMapper {
    int deleteByPrimaryKey(String id);

    int insert(WappGame record);

    int insertSelective(WappGame record);

    WappGame selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WappGame record);

    int updateByPrimaryKey(WappGame record);
}