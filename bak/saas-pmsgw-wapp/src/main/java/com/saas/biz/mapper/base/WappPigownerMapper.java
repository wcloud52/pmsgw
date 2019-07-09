package com.saas.biz.mapper.base;

import com.saas.biz.pojo.WappPigowner;

public interface WappPigownerMapper {
    int deleteByPrimaryKey(String id);

    int insert(WappPigowner record);

    int insertSelective(WappPigowner record);

    WappPigowner selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WappPigowner record);

    int updateByPrimaryKey(WappPigowner record);
}