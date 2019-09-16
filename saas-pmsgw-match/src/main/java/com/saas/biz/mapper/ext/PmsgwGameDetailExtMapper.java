package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.mapper.base.PmsgwGameDetailMapper;
import com.saas.biz.pojo.PmsgwGameDetail;

public interface PmsgwGameDetailExtMapper extends PmsgwGameDetailMapper{
	List<PmsgwGameDetail> selectListByDynamic(Map<Object, Object> paraMap);
	long selectListByDynamicCount(Map<Object, Object> paraMap);
	int deleteByMasterId(Long masterId);
	PmsgwGameDetail selectTopOne(@Param("pigowner") String pigowner );
	List<PmsgwGameDetail> selectTopMore(@Param("pigowner") String pigowner );
	
	int insertByList(List<PmsgwGameDetail> list);
	
	List<PmsgwGameDetail> selectListByList(@Param("masterId") Long masterId,@Param("list") List<PmsgwGameDetail> list);
}