package com.saas.biz.mapper.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.mapper.base.NodejsCrawlerCoteMapper;
import com.saas.biz.pojo.NodejsCrawlerCote;

public interface NodejsCrawlerCoteExtMapper extends NodejsCrawlerCoteMapper{
	List<NodejsCrawlerCote> selectListByDynamic(Map<Object, Object> paraMap);
	long selectCountByDynamic(Map<Object, Object> paraMap);
	List<NodejsCrawlerCote> selectByCoteState(@Param("cote_state") String cote_state);
}