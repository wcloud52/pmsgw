package com.saas.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saas.biz.mapper.ext.NodejsCustomerMessageExtMapper;
import com.saas.biz.mapper.impl.NodejsCustomerMessagePigownerDetailImplMapper;
import com.saas.biz.mapper.impl.NodejsCustomerMessagePigownerImplMapper;
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCustomerMessage;
import com.saas.biz.pojo.NodejsCustomerMessagePigowner;
import com.saas.biz.pojo.NodejsCustomerMessagePigownerDetail;
import com.saas.biz.service.NodejsCustomerMessageService;

@Service
public class NodejsCustomerMessageServiceImpl implements NodejsCustomerMessageService {
	
	@Autowired
	private NodejsCustomerMessagePigownerDetailImplMapper nodejsCustomerMessagePigownerDetailImplMapper;
	
	@Autowired
	private NodejsCustomerMessagePigownerImplMapper nodejsCustomerMessagePigownerImplMapper;

	
	@Autowired
	private NodejsCustomerMessageExtMapper nodejsCustomerMessageExtMapper;

	@Override
	public List<NodejsCustomerMessage> selectListByDynamic(Map<Object, Object> paraMap) {
		return nodejsCustomerMessageExtMapper.selectListByDynamic(paraMap);
	}

	@Override
	public long selectCountByDynamic(Map<Object, Object> paraMap) {
		return nodejsCustomerMessageExtMapper.selectCountByDynamic(paraMap);
	}

	@Override
	public long selectCountByGamedetailId(String gamedetailId) {
		return nodejsCustomerMessageExtMapper.selectCountByGamedetailId(gamedetailId);
	}
	@Override
	public List<NodejsCustomerMessage> selectByMessageReceiverId(String message_receiverId)
	{
		return nodejsCustomerMessageExtMapper.selectByMessageReceiverId(message_receiverId);
	}
	@Override
	public int insert(NodejsCustomerMessage nodejsCustomerMessage) {
		long count=selectCountByGamedetailId(nodejsCustomerMessage.getGame_detail_id());
		int result=0;
		if(count==0)
		{
			result=nodejsCustomerMessageExtMapper.insertSelective(nodejsCustomerMessage);
		}
		else
		{
			result=-1;
		}
		return result;
	}
	@Override
	public int changeStatus(String message_id,Integer message_status) {
		NodejsCustomerMessage nodejsCustomerMessage=new NodejsCustomerMessage();
		nodejsCustomerMessage.setMessage_id(message_id);
		nodejsCustomerMessage.setMessage_status(message_status);
		return nodejsCustomerMessageExtMapper.updateByPrimaryKeySelective(nodejsCustomerMessage);
	}
	@Override
	public NodejsCustomerMessage convert(NodejsCrawlerDetailGame nodejsCrawlerDetailGame) {
		NodejsCustomerMessage nodejsCustomerMessage=new NodejsCustomerMessage();
		nodejsCustomerMessage.setGame_detail_id(nodejsCrawlerDetailGame.getDetail_id());
		nodejsCustomerMessage.setGame_cote_id(nodejsCrawlerDetailGame.getCote_id());
		nodejsCustomerMessage.setGame_cote_name(nodejsCrawlerDetailGame.getCote_name());
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
		nodejsCustomerMessage.setGame_create_time(nodejsCrawlerDetailGame.getCreate_time());
		nodejsCustomerMessage.setGame_modify_time(nodejsCrawlerDetailGame.getModify_time());
		return nodejsCustomerMessage;
	}
	
	
	/**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	@Override
	public List<NodejsCustomerMessagePigownerDetail> selectNodejsCustomerMessagePigownerDetailListByDynamic(Map<Object, Object> paraMap) {
		return nodejsCustomerMessagePigownerDetailImplMapper.selectListByDynamic(paraMap);
	}
	/**
	 * 动态查询总数
	 * @param paraMap
	 * @return
	 */
	@Override
	public long selectNodejsCustomerMessagePigownerDetailCountByDynamic(Map<Object, Object> paraMap) {
		return nodejsCustomerMessagePigownerDetailImplMapper.selectCountByDynamic(paraMap);
	}
	/**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public NodejsCustomerMessagePigowner selectNodejsCustomerMessagePigownerOneById(String openid_gameid) {
		return nodejsCustomerMessagePigownerImplMapper.selectByPrimaryKey(openid_gameid);
	}
	/**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	@Override
	public List<NodejsCustomerMessagePigowner> selectNodejsCustomerMessagePigownerListByDynamic(Map<Object, Object> paraMap) {
		return nodejsCustomerMessagePigownerImplMapper.selectListByDynamic(paraMap);
	}
	/**
	 * 动态查询总数
	 * @param paraMap
	 * @return
	 */
	@Override
	public long selectNodejsCustomerMessagePigownerCountByDynamic(Map<Object, Object> paraMap) {
		return nodejsCustomerMessagePigownerImplMapper.selectCountByDynamic(paraMap);
	}

	@Override
	public List<Map<String, Object>> selectMapByCoteIdOpenid(String game_cote_id, String game_receiver_openid) {
		
		return nodejsCustomerMessagePigownerDetailImplMapper.selectMapByCoteIdOpenid(game_cote_id, game_receiver_openid);
	}
}
