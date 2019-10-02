package com.saas.biz.mapper.impl;

import com.saas.biz.mapper.ext.WeixinUserPurseExtMapper;
import com.saas.biz.pojo.WeixinUserPurse;

import java.util.List;

public interface WeixinUserPurseImplMapper extends WeixinUserPurseExtMapper {
    List<WeixinUserPurse> selectByJoin(WeixinUserPurse weixinUserPurse);
}