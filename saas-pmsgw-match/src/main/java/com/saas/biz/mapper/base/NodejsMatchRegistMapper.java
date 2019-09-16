package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsMatchRegist;

public interface NodejsMatchRegistMapper {
    int deleteByPrimaryKey(String id);

    int insert(NodejsMatchRegist record);

    int insertSelective(NodejsMatchRegist record);

    NodejsMatchRegist selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(NodejsMatchRegist record);

    int updateByPrimaryKeyWithBLOBs(NodejsMatchRegist record);

    int updateByPrimaryKey(NodejsMatchRegist record);
}