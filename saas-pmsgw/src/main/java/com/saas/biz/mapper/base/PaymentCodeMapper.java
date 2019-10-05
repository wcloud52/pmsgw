package com.saas.biz.mapper.base;

import com.saas.biz.pojo.PaymentCode;

public interface PaymentCodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(PaymentCode record);

    int insertSelective(PaymentCode record);

    PaymentCode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PaymentCode record);

    int updateByPrimaryKey(PaymentCode record);
}