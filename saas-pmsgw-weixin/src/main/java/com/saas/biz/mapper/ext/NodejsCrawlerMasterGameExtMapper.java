package com.saas.biz.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.mapper.base.NodejsCrawlerMasterGameMapper;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;

public interface NodejsCrawlerMasterGameExtMapper extends NodejsCrawlerMasterGameMapper{
	List<NodejsCrawlerMasterGame> selectByMasterState(@Param("master_state") String master_state);
}