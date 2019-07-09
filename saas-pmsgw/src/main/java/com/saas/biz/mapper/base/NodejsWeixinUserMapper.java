package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsWeixinUser;

public interface NodejsWeixinUserMapper {
    int deleteByPrimaryKey(String openid);

    int insert(NodejsWeixinUser record);

    int insertSelective(NodejsWeixinUser record);

    NodejsWeixinUser selectByPrimaryKey(String openid);

    int updateByPrimaryKeySelective(NodejsWeixinUser record);

    int updateByPrimaryKey(NodejsWeixinUser record);
}