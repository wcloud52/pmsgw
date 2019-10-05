package com.saas.biz.service;

import com.alibaba.fastjson.JSONObject;
import com.saas.biz.pojo.NodejsMatchRegist;

import java.util.List;
import java.util.Map;

public interface NodejsMatchRegistService extends BaseService<NodejsMatchRegist,String> {

    /**
     * 比赛结束后根据比赛结果更新报名表
     * @param paraMap
     * @return
     */
    int updateRegistRank(Map<Object, Object> paraMap);

    /**
     * 更新单个鸽子的奖金（）
     * @param list
     */
    int updateSumReward(List<NodejsMatchRegist> list);
    /**
     * 从比赛结果表中查询结果
     * @param paraMap
     * @return
     */
    List<NodejsMatchRegist> selectCrawlerDetailGame(Map<Object, Object> paraMap);
    Map echoResult(JSONObject rule, float money, List<NodejsMatchRegist> rankLst, boolean update);
    Map echoResultRealTime(JSONObject rule, float money, List<NodejsMatchRegist> rankLst, boolean update);
    Map<String,Object> echoOneResult(JSONObject rule, String key, List<NodejsMatchRegist> rankLst, String pigeon_code);

    int sendMessage(List<NodejsMatchRegist> registlist) ;
}
