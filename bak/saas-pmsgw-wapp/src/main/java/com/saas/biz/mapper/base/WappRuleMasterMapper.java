package com.saas.biz.mapper.base;

import com.saas.biz.pojo.WappRuleMaster;

public interface WappRuleMasterMapper {
    int deleteByPrimaryKey(String id);

    int insert(WappRuleMaster record);

    int insertSelective(WappRuleMaster record);

    WappRuleMaster selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WappRuleMaster record);

    int updateByPrimaryKey(WappRuleMaster record);
}