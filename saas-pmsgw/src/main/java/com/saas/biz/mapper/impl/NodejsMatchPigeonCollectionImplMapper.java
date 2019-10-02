package com.saas.biz.mapper.impl;

import com.saas.biz.mapper.ext.NodejsMatchPigeonCollectionExtMapper;
import com.saas.biz.pojo.NodejsMatchPigeonCollection;

import java.util.List;
import java.util.Map;

public interface NodejsMatchPigeonCollectionImplMapper extends NodejsMatchPigeonCollectionExtMapper {

    /**
     * 查询某比赛上传的集鸽，根据会员编号分组
     * @param map
     * @return
     */
    List<NodejsMatchPigeonCollection> selectListGroupByPigownerNum(Map<String, Object> map);
    List<NodejsMatchPigeonCollection> selectListBySign(Map<String, Object> map);

    int deleteByMatchId(String match_id);

    List<NodejsMatchPigeonCollection> selectGrpupByPigeonCode(Map<String, Object> map);
}