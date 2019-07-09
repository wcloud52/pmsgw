package com.saas.biz.mapper.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.mapper.ext.NodejsCustomerMessagePigownerDetailExtMapper; 

public interface NodejsCustomerMessagePigownerDetailImplMapper extends NodejsCustomerMessagePigownerDetailExtMapper {
	public List<Map<String, Object>> selectMapByCoteIdOpenid(@Param("game_cote_id") String game_cote_id,@Param("game_receiver_openid") String game_receiver_openid);
	
	public List<Map<String, String>> selectGameMapByCoteIdOpenid(@Param("game_cote_id") String game_cote_id,@Param("game_receiver_openid") String game_receiver_openid);
	
	public List<Map<String, String>> selectGameRankMapByCoteIdOpenid(@Param("game_cote_id") String game_cote_id,@Param("game_receiver_openid") String game_receiver_openid,@Param("game_master_id") String game_master_id);
}