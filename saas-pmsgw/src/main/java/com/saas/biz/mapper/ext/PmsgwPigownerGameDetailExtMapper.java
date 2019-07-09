package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.mapper.base.PmsgwPigownerGameDetailMapper;
import com.saas.biz.pojo.PmsgwPigownerGameDetail;

public interface PmsgwPigownerGameDetailExtMapper extends PmsgwPigownerGameDetailMapper{
	List<PmsgwPigownerGameDetail> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	 List<PmsgwPigownerGameDetail> selectByReceiverId(@Param("receiverId") String receiverId);
}