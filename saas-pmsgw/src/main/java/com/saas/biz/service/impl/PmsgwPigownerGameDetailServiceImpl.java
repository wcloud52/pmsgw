package com.saas.biz.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.saas.biz.mapper.ext.PmsgwPigownerGameDetailExtMapper;
import com.saas.biz.pojo.PmsgwGameDetail;
import com.saas.biz.pojo.PmsgwPigownerGameDetail;
import com.saas.biz.service.PmsgwPigownerGameDetailService;

@Service
public class PmsgwPigownerGameDetailServiceImpl implements PmsgwPigownerGameDetailService {
	@Autowired
	private PmsgwPigownerGameDetailExtMapper pmsgwPigownerGameDetailExtMapper;

	@Override
	public int insert(PmsgwPigownerGameDetail record) {
		return pmsgwPigownerGameDetailExtMapper.insert(record);
	}

	@Override
	public List<PmsgwPigownerGameDetail> selectByReceiverId(String receiverId) {
		return pmsgwPigownerGameDetailExtMapper.selectByReceiverId(receiverId);
	}

	@Override
	public PmsgwPigownerGameDetail convert(PmsgwGameDetail record, String receiverId, String receiverName) {	  
		PmsgwPigownerGameDetail ret=new PmsgwPigownerGameDetail();
		ret.setId(record.getId());
		ret.setMasterId(record.getMasterId());
		ret.setMasterText(record.getMasterText());
		ret.setMm(record.getMm());
		ret.setRaceid(record.getRaceid());
		ret.setDistence(record.getDistence());
		ret.setFlag(record.getFlag());
		ret.setXmbh(record.getXmbh());
		ret.setQh(record.getQh());
		ret.setRingnum(record.getRingnum());
		ret.setPigowner(record.getPigowner());
		
		ret.setCotenum(record.getCotenum());
		ret.setCometime(record.getCometime());
		ret.setSpeed(record.getSpeed());
		ret.setRank(record.getRank());
		ret.setCreate_time(new Date());
		ret.setModify_time(new Date());
		ret.setReceiverId(receiverId);
		ret.setReceiverName(receiverName);
		
		return ret;
	}
}
