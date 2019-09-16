package com.saas.biz.mapper.base;

import com.saas.biz.pojo.PmsgwPigownerGameDetail;

public interface PmsgwPigownerGameDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsgwPigownerGameDetail record);

    int insertSelective(PmsgwPigownerGameDetail record);

    PmsgwPigownerGameDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsgwPigownerGameDetail record);

    int updateByPrimaryKey(PmsgwPigownerGameDetail record);
}