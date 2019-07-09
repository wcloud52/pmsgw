package com.saas.biz.mapper.base;

import com.saas.biz.pojo.NodejsCrawlerMasterGame;

public interface NodejsCrawlerMasterGameMapper {
    int deleteByPrimaryKey(String master_id);

    int insert(NodejsCrawlerMasterGame record);

    int insertSelective(NodejsCrawlerMasterGame record);

    NodejsCrawlerMasterGame selectByPrimaryKey(String master_id);

    int updateByPrimaryKeySelective(NodejsCrawlerMasterGame record);

    int updateByPrimaryKey(NodejsCrawlerMasterGame record);
}