package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsCrawlerCote;

public interface NodejsCrawlerCoteMapper {
    int deleteByPrimaryKey(String cote_id);

    int insert(NodejsCrawlerCote record);

    int insertSelective(NodejsCrawlerCote record);

    NodejsCrawlerCote selectByPrimaryKey(String cote_id);

    int updateByPrimaryKeySelective(NodejsCrawlerCote record);

    int updateByPrimaryKey(NodejsCrawlerCote record);
}