package com.saas.biz.mapper.impl;

import com.saas.biz.mapper.ext.WeixinUserPurseExtMapper;
import com.saas.biz.pojo.WeixinUserPurse;

import java.util.List;
import java.util.Map;

public interface WeixinUserPurseImplMapper extends WeixinUserPurseExtMapper {
    List<Map> selectByJoin(Map map);

    long selectCountByJoin(Map map);
}