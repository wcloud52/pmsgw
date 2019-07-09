package com.saas.biz.mapper.base;

import com.saas.biz.pojo.WeixinMessage;

public interface WeixinMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeixinMessage record);

    int insertSelective(WeixinMessage record);

    WeixinMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeixinMessage record);

    int updateByPrimaryKey(WeixinMessage record);
}