package com.saas.biz.mapper.base;

import com.saas.biz.pojo.PmsgwGameWhiteList;

public interface PmsgwGameWhiteListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsgwGameWhiteList record);

    int insertSelective(PmsgwGameWhiteList record);

    PmsgwGameWhiteList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsgwGameWhiteList record);

    int updateByPrimaryKey(PmsgwGameWhiteList record);
}