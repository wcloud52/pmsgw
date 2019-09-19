package com.saas.biz.mapper.impl;

import com.saas.biz.mapper.ext.NodejsMatchExtMapper;
import com.saas.biz.pojo.NodejsMatch;

public interface NodejsMatchImplMapper extends NodejsMatchExtMapper {

    NodejsMatch selectNewMatchByCoteId(String cote_id);
}