package com.saas.biz.mapper.base;

import com.saas.biz.pojo.WeixinUserPurse;

public interface WeixinUserPurseMapper {
    int deleteByPrimaryKey(String id);

    int insert(WeixinUserPurse record);

    int insertSelective(WeixinUserPurse record);

    WeixinUserPurse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WeixinUserPurse record);

    int updateByPrimaryKey(WeixinUserPurse record);
}