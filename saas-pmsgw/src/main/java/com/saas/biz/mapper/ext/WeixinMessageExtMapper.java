package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.WeixinMessageMapper;
import com.saas.biz.pojo.WeixinMessage;

public interface WeixinMessageExtMapper   extends WeixinMessageMapper {
	
	List<WeixinMessage> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
}