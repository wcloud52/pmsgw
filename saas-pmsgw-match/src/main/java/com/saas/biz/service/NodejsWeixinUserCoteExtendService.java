package com.saas.biz.service;

import java.util.List;

import com.saas.biz.pojo.NodejsWeixinUserCoteExtend;

public interface NodejsWeixinUserCoteExtendService extends BaseService<NodejsWeixinUserCoteExtend,Long> {

	List<NodejsWeixinUserCoteExtend> selectByOpenid(String openid);

	
	int deleteByCoteId(String cote_id, String openid);

}
