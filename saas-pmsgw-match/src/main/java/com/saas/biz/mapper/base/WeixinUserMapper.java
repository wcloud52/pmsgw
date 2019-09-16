package com.saas.biz.mapper.base;

import com.saas.biz.pojo.WeixinUser;

public interface WeixinUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(WeixinUser record);

    int insertSelective(WeixinUser record);

    WeixinUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WeixinUser record);

    int updateByPrimaryKey(WeixinUser record);
}