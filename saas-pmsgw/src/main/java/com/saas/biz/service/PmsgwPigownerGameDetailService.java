package com.saas.biz.service;

import java.util.List;

import com.saas.biz.pojo.PmsgwGameDetail;
import com.saas.biz.pojo.PmsgwPigownerGameDetail;

public interface PmsgwPigownerGameDetailService {
	 int insert(PmsgwPigownerGameDetail record);
	 List<PmsgwPigownerGameDetail> selectByReceiverId(String receiverId);
	PmsgwPigownerGameDetail convert(PmsgwGameDetail record, String receiverId, String receiverName);
}
