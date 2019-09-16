package com.saas.biz.mapper.base;

import com.saas.biz.pojo.PmsgwPigownerMessage;

public interface PmsgwPigownerMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PmsgwPigownerMessage record);

    int insertSelective(PmsgwPigownerMessage record);

    PmsgwPigownerMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PmsgwPigownerMessage record);

    int updateByPrimaryKey(PmsgwPigownerMessage record);
}