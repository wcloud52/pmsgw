package com.saas.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.biz.mapper.base.NodejsCustomerGameMessageMapper;
import com.saas.biz.mapper.ext.NodejsCrawlerMasterGameExtMapper;
import com.saas.biz.mapper.ext.NodejsCustomerGameMessageExtMapper;
import com.saas.biz.mapper.ext.NodejsCustomerMessageExtMapper;
import com.saas.biz.mapper.ext.NodejsWeixinUserCoteExtMapper;
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;
import com.saas.biz.pojo.NodejsCustomerGameMessage;
import com.saas.biz.pojo.NodejsCustomerMessage;
import com.saas.biz.pojo.NodejsWeixinUserCote;
import com.saas.biz.service.NodejsCustomerMessageService;

@Service
public class NodejsCustomerMessageServiceImpl implements NodejsCustomerMessageService {
	
	@Autowired
	private NodejsCustomerMessageExtMapper nodejsCustomerMessageExtMapper;
	
	@Autowired
	private NodejsCustomerGameMessageExtMapper nodejsCustomerGameMessageExtMapper;
	
	@Autowired
	private NodejsWeixinUserCoteExtMapper nodejsWeixinUserCoteExtMapper;
	
	@Autowired
	private NodejsCrawlerMasterGameExtMapper nodejsCrawlerMasterGameExtMapper;

	@Override
	public List<NodejsCustomerMessage> selectListByDynamic(Map<Object, Object> paraMap) {
		return nodejsCustomerMessageExtMapper.selectListByDynamic(paraMap);
	}

	@Override
	public long selectCountByDynamic(Map<Object, Object> paraMap) {
		return nodejsCustomerMessageExtMapper.selectCountByDynamic(paraMap);
	}

	@Override
	public long selectCountByMessageid(String message_id) {
		return nodejsCustomerMessageExtMapper.selectCountByMessageid(message_id);
	}
	@Override
	public int insertNodejsCustomerGameMessage(NodejsCustomerGameMessage nodejsCustomerGameMessage) {
		return nodejsCustomerGameMessageExtMapper.insertSelective(nodejsCustomerGameMessage);
	}
	@Override
	public List<NodejsWeixinUserCote>  selectNodejsWeixinUserCoteByCoteId(String cote_id)
	{
		return nodejsWeixinUserCoteExtMapper.selectByCoteId(cote_id);
	}
	@Override
	public List<NodejsCrawlerMasterGame>  selectByMasterState(String master_state)
	{
		return nodejsCrawlerMasterGameExtMapper.selectByMasterState(master_state);
	}
	@Override
	public int changeNodejsCrawlerMasterGameStatus(NodejsCrawlerMasterGame nodejsCrawlerMasterGame) {
		return nodejsCrawlerMasterGameExtMapper.updateByPrimaryKeySelective(nodejsCrawlerMasterGame);
	}
	@Transactional
	@Override
	public //synchronized 
	int insert(NodejsCustomerMessage nodejsCustomerMessage) {
		String message_id=nodejsCustomerMessage.getGame_detail_id()+"_"+nodejsCustomerMessage.getMessage_receiverId();
		long count=0;//selectCountByMessageid(message_id);
		int result=0;
		if(count==0)
		{
			try
			{
			result=nodejsCustomerMessageExtMapper.insertSelective(nodejsCustomerMessage);
			}
			catch (Exception ex)
			{
				result=-1;
			}
		}
		else
		{
			result=-1;
		}
		return result;
	}
	@Override
	public int changeStatus(String message_id,Integer message_status,String message_text) {
		NodejsCustomerMessage nodejsCustomerMessage=new NodejsCustomerMessage();
		nodejsCustomerMessage.setMessage_id(message_id);
		nodejsCustomerMessage.setMessage_status(message_status);
		if(message_text!=null&&message_text.length()>0)
		{
			nodejsCustomerMessage.setMessage_text(message_text);
		}
		return nodejsCustomerMessageExtMapper.updateByPrimaryKeySelective(nodejsCustomerMessage);
	}
	@Override
	public NodejsCustomerMessage convert(NodejsCrawlerDetailGame nodejsCrawlerDetailGame) {
		NodejsCustomerMessage nodejsCustomerMessage=new NodejsCustomerMessage();
		nodejsCustomerMessage.setGame_detail_id(nodejsCrawlerDetailGame.getDetail_id());
		nodejsCustomerMessage.setGame_cote_id(nodejsCrawlerDetailGame.getCote_id());
		nodejsCustomerMessage.setGame_cote_name(nodejsCrawlerDetailGame.getCote_name());
		nodejsCustomerMessage.setGame_cote_state(nodejsCrawlerDetailGame.getCote_state());
		nodejsCustomerMessage.setGame_master_id(nodejsCrawlerDetailGame.getMaster_id());
		nodejsCustomerMessage.setGame_master_text(nodejsCrawlerDetailGame.getMaster_text());
		nodejsCustomerMessage.setGame_master_type(nodejsCrawlerDetailGame.getMaster_type());
		nodejsCustomerMessage.setGame_master_website(nodejsCrawlerDetailGame.getMaster_website());
		
		nodejsCustomerMessage.setGame_detail_page(nodejsCrawlerDetailGame.getDetail_page());
		nodejsCustomerMessage.setGame_detail_page_index(nodejsCrawlerDetailGame.getDetail_page_index());
		nodejsCustomerMessage.setGame_detail_state(nodejsCrawlerDetailGame.getDetail_state());
		nodejsCustomerMessage.setGame_distence(nodejsCrawlerDetailGame.getDistence());
		nodejsCustomerMessage.setGame_ringnum(nodejsCrawlerDetailGame.getRingnum());
		nodejsCustomerMessage.setGame_pigowner(nodejsCrawlerDetailGame.getPigowner());
		nodejsCustomerMessage.setGame_cometime(nodejsCrawlerDetailGame.getCometime());
		nodejsCustomerMessage.setGame_cotenum(nodejsCrawlerDetailGame.getCotenum());
		nodejsCustomerMessage.setGame_speed(nodejsCrawlerDetailGame.getSpeed());
		
		nodejsCustomerMessage.setGame_rank(nodejsCrawlerDetailGame.getRank());
		    
		nodejsCustomerMessage.setGame_receiver_openid(nodejsCrawlerDetailGame.getReceiver_openid());
		nodejsCustomerMessage.setGame_receiver_nickname(nodejsCrawlerDetailGame.getReceiver_nickname());
		nodejsCustomerMessage.setGame_receiver_headimgurl(nodejsCrawlerDetailGame.getReceiver_headimgurl());
		
		nodejsCustomerMessage.setGame_create_time(nodejsCrawlerDetailGame.getCreate_time());
		nodejsCustomerMessage.setGame_modify_time(nodejsCrawlerDetailGame.getModify_time());
		return nodejsCustomerMessage;
	}
	@Override
	public NodejsCustomerGameMessage convertToNodejsCustomerGameMessage(NodejsCrawlerMasterGame nodejsCrawlerDetailGame) {
		NodejsCustomerGameMessage nodejsCustomerMessage=new NodejsCustomerGameMessage();
		
		nodejsCustomerMessage.setGame_cote_id(nodejsCrawlerDetailGame.getCote_id());
		nodejsCustomerMessage.setGame_cote_name(nodejsCrawlerDetailGame.getCote_name());
		nodejsCustomerMessage.setGame_cote_state(nodejsCrawlerDetailGame.getCote_state());
		nodejsCustomerMessage.setGame_master_id(nodejsCrawlerDetailGame.getMaster_id());
		nodejsCustomerMessage.setGame_master_text(nodejsCrawlerDetailGame.getMaster_text());
		nodejsCustomerMessage.setGame_master_type(nodejsCrawlerDetailGame.getMaster_type());
		nodejsCustomerMessage.setGame_master_website(nodejsCrawlerDetailGame.getMaster_website());
		
		
		
		nodejsCustomerMessage.setGame_create_time(nodejsCrawlerDetailGame.getCreate_time());
		nodejsCustomerMessage.setGame_modify_time(nodejsCrawlerDetailGame.getModify_time());
		return nodejsCustomerMessage;
	}
}
