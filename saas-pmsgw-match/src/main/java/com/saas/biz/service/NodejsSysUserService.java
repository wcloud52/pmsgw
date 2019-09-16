package com.saas.biz.service;

import com.saas.biz.pojo.NodejsSysUser;

public interface NodejsSysUserService extends BaseService<NodejsSysUser,String> {

	NodejsSysUser selectOneByLoginName(String loginName);

}
