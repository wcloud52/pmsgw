package com.saas.biz.mapper.base;

import com.saas.biz.pojo.WappPigownerGame;
import com.saas.biz.pojo.WappPigownerGameKey;

public interface WappPigownerGameMapper {
    int deleteByPrimaryKey(WappPigownerGameKey key);

    int insert(WappPigownerGame record);

    int insertSelective(WappPigownerGame record);

    WappPigownerGame selectByPrimaryKey(WappPigownerGameKey key);

    int updateByPrimaryKeySelective(WappPigownerGame record);

    int updateByPrimaryKey(WappPigownerGame record);
}