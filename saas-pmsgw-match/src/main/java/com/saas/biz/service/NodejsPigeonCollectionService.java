package com.saas.biz.service;

import java.util.List;
import java.util.Map;

import com.saas.biz.pojo.NodejsPigeonCollection;

public interface NodejsPigeonCollectionService extends BaseService<NodejsPigeonCollection,String> {

	List<Map<String, String>> selectMapByCoteId(String cote_id,String typeId);
    int updateStatusByCoteId(String cote_id,String typeId);
  
}
