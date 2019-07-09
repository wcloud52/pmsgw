package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsWeixinUserCote;

public interface NodejsWeixinUserCoteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NodejsWeixinUserCote record);

    int insertSelective(NodejsWeixinUserCote record);

    NodejsWeixinUserCote selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NodejsWeixinUserCote record);

    int updateByPrimaryKey(NodejsWeixinUserCote record);
}