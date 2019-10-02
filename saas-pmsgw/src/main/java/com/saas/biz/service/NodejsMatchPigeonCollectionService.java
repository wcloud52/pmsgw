package com.saas.biz.service;

import com.saas.biz.pojo.NodejsMatchPigeonCollection;

import java.util.List;
import java.util.Map;

public interface NodejsMatchPigeonCollectionService extends BaseService<NodejsMatchPigeonCollection,String> {


    List<NodejsMatchPigeonCollection> selectListGroupByPigownerNum(Map<String, Object> map);
    List<NodejsMatchPigeonCollection> selectListBySign(Map<String, Object> map);

    int deleteByMatchId(String match_id);

    List<NodejsMatchPigeonCollection> selectGrpupByPigeonCode(Map<String, Object> map);
}
