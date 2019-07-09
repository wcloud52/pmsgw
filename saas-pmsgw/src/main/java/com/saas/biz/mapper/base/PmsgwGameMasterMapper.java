package com.saas.biz.mapper.base;

import com.saas.biz.pojo.PmsgwGameMaster;

public interface PmsgwGameMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsgwGameMaster record);

    int insertSelective(PmsgwGameMaster record);

    PmsgwGameMaster selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsgwGameMaster record);

    int updateByPrimaryKey(PmsgwGameMaster record);
}