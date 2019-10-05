package com.saas.biz.service.impl;

import com.saas.biz.mapper.base.PaymentCodeMapper;
import com.saas.biz.pojo.PaymentCode;
import com.saas.biz.service.PaymentCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PaymentCodeServiceImpl implements PaymentCodeService {
	@Autowired
	private PaymentCodeMapper implMapper;

	@Override
	public PaymentCode selectOneById(String id) {

		return implMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<PaymentCode> selectAll() {
		return null;
	}

	@Override
	public List<PaymentCode> selectListByDynamic(Map<Object, Object> paraMap) {
		return null;
	}

	@Override
	public long selectCountByDynamic(Map<Object, Object> paraMap) {
		return 0;
	}

	@Override
	public int insertBatch(List<PaymentCode> list) {
		return 0;
	}

	@Override
	public int updateBatch(List<PaymentCode> list) {
		return 0;
	}

	@Transactional
	@Override
	public int insert(PaymentCode record) {
		return implMapper.insert(record);
	}

	@Override
	public int update(PaymentCode record) {
		return  implMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteById(String id) {
		return implMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByIds(List<String> ids) {
		return 0;
	}

}
