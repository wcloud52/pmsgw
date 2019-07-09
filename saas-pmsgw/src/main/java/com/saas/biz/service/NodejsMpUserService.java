package com.saas.biz.service;

import java.util.List;

import com.saas.biz.pojo.NodejsMpUser;

public interface NodejsMpUserService extends BaseService<NodejsMpUser,Integer> {

	NodejsMpUser selectOneByUsername(String username);

	NodejsMpUser selectOneByWeixinOpenid(String weixin_openid);

	List<NodejsMpUser> selectListByUsername(String username);

	List<NodejsMpUser> selectListByMobile(String mobile);

 
}
