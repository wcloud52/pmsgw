package com.saas.biz.service;

import java.util.List;

import com.saas.biz.pojo.NodejsCrawlerCoteExtend;

public interface NodejsCrawlerCoteExtendService extends BaseService<NodejsCrawlerCoteExtend,String> {

	NodejsCrawlerCoteExtend selectByCoteId(String cote_id);

	List<NodejsCrawlerCoteExtend> selectByCotestate(String cote_state);


}
