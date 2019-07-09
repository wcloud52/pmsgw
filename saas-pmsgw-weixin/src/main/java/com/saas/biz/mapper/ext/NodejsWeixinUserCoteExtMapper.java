package com.saas.biz.mapper.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.mapper.base.NodejsWeixinUserCoteMapper;
import com.saas.biz.pojo.NodejsWeixinUserCote;

public interface NodejsWeixinUserCoteExtMapper extends NodejsWeixinUserCoteMapper{
	List<NodejsWeixinUserCote> selectByCoteId(@Param("cote_id") String cote_id);
}