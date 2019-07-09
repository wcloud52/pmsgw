package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;
import com.saas.biz.pojo.NodejsCustomerGameMessage;
import com.saas.biz.pojo.NodejsCustomerMessage;
import com.saas.biz.pojo.NodejsWeixinUserCote;

public interface NodejsCustomerMessageService {

	List<NodejsCustomerMessage> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	int insert(NodejsCustomerMessage nodejsCustomerMessage);
	NodejsCustomerMessage convert(NodejsCrawlerDetailGame nodejsCrawlerDetailGame);
	
	int changeStatus(String message_id, Integer message_status, String message_text);
	long selectCountByMessageid(String message_id);
	NodejsCustomerGameMessage convertToNodejsCustomerGameMessage(NodejsCrawlerMasterGame nodejsCrawlerDetailGame);
	int insertNodejsCustomerGameMessage(NodejsCustomerGameMessage nodejsCustomerGameMessage);
	
	List<NodejsWeixinUserCote> selectNodejsWeixinUserCoteByCoteId(String cote_id);
	List<NodejsCrawlerMasterGame> selectByMasterState(String master_state);
	int changeNodejsCrawlerMasterGameStatus(NodejsCrawlerMasterGame nodejsCrawlerMasterGame);
}
