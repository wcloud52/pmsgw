package com.saas.biz.mapper.base;

import com.saas.biz.pojo.WappRuleDetail;

public interface WappRuleDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(WappRuleDetail record);

    int insertSelective(WappRuleDetail record);

    WappRuleDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WappRuleDetail record);

    int updateByPrimaryKey(WappRuleDetail record);
}