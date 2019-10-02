package com.saas.biz.service;

import com.saas.biz.pojo.WeixinUserPurse;

import java.util.List;
import java.util.Map;

public interface WeixinUserPurseService extends BaseService<WeixinUserPurse,String> {
    List<WeixinUserPurse> selectByJoin(WeixinUserPurse weixinUserPurse);
}
