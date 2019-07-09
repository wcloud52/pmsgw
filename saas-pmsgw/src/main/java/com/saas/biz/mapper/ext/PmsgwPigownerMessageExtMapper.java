package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.biz.mapper.base.PmsgwPigownerMessageMapper;
import com.saas.biz.pojo.PmsgwPigownerMessage;

public interface PmsgwPigownerMessageExtMapper extends PmsgwPigownerMessageMapper{
	List<PmsgwPigownerMessage> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
}