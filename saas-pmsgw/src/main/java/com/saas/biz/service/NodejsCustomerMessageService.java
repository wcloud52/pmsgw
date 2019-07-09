package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCustomerMessage;
import com.saas.biz.pojo.NodejsCustomerMessagePigowner;
import com.saas.biz.pojo.NodejsCustomerMessagePigownerDetail;

public interface NodejsCustomerMessageService {

	List<NodejsCustomerMessage> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	long selectCountByGamedetailId(String gamedetailId);
	int insert(NodejsCustomerMessage nodejsCustomerMessage);
	NodejsCustomerMessage convert(NodejsCrawlerDetailGame nodejsCrawlerDetailGame);
	int changeStatus(String message_id, Integer message_status);
	List<NodejsCustomerMessage> selectByMessageReceiverId(String message_receiverId);
	List<NodejsCustomerMessagePigownerDetail> selectNodejsCustomerMessagePigownerDetailListByDynamic(
			Map<Object, Object> paraMap);
	long selectNodejsCustomerMessagePigownerDetailCountByDynamic(Map<Object, Object> paraMap);
	List<NodejsCustomerMessagePigowner> selectNodejsCustomerMessagePigownerListByDynamic(Map<Object, Object> paraMap);
	long selectNodejsCustomerMessagePigownerCountByDynamic(Map<Object, Object> paraMap);
	NodejsCustomerMessagePigowner selectNodejsCustomerMessagePigownerOneById(String openid_gameid);
	
	List<Map<String, Object>> selectMapByCoteIdOpenid(String game_cote_id,String game_receiver_openid);
}
