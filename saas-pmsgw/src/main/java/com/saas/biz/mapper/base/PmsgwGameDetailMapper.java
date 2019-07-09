package com.saas.biz.mapper.base;

import com.saas.biz.pojo.PmsgwGameDetail;

public interface PmsgwGameDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsgwGameDetail record);

    int insertSelective(PmsgwGameDetail record);

    PmsgwGameDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsgwGameDetail record);

    int updateByPrimaryKey(PmsgwGameDetail record);
}