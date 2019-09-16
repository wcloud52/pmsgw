package com.saas.biz.mapper.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saas.biz.mapper.ext.NodejsPigeonCollectionExtMapper; 

public interface NodejsPigeonCollectionImplMapper extends NodejsPigeonCollectionExtMapper {
	int flashNodejsPigeonCollection();
	List<Map<String, String>> selectMapByCoteId(@Param("cote_id") String cote_id,@Param("typeId") String typeId);
	int updateStatusByCoteId(@Param("cote_id") String cote_id,@Param("typeId") String typeId);
}