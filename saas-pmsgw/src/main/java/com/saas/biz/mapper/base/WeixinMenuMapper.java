package com.saas.biz.mapper.base;

import com.saas.biz.pojo.WeixinMenu;

public interface WeixinMenuMapper {
    int deleteByPrimaryKey(String id);

    int insert(WeixinMenu record);

    int insertSelective(WeixinMenu record);

    WeixinMenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WeixinMenu record);

    int updateByPrimaryKey(WeixinMenu record);
}