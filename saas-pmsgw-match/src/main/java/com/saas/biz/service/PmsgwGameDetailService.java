package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.PmsgwGameDetail;

public interface PmsgwGameDetailService {
	PmsgwGameDetail selectOneById(Long id);
	int insert(PmsgwGameDetail record);
	int update(PmsgwGameDetail record);
	int delete(Long id);
	List<PmsgwGameDetail> selectListByDynamic(Map<Object, Object> paraMap);
	long selectListByDynamicCount(Map<Object, Object> paraMap);
	int deleteByMasterId(Long masterId);
	List<PmsgwGameDetail> selectListByMasterId( Long masterId);
	PmsgwGameDetail selectTopOne(String pigowner);
	int insertByList(List<PmsgwGameDetail> list);
	List<PmsgwGameDetail> selectListByList(Long masterId, List<PmsgwGameDetail> list);
	List<PmsgwGameDetail> selectTopMore(String pigowner);
}
