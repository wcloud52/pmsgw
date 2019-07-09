package com.saas.biz.mapper.ext;

import java.util.List;

import com.saas.biz.mapper.base.NodejsWeixinUserMapper;
import com.saas.biz.pojo.NodejsWeixinUser;

public interface NodejsWeixinUserExtMapper extends NodejsWeixinUserMapper {
  
    int insertByList(List<NodejsWeixinUser> list);
    int deleteAll();
}