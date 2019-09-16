package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saas.biz.mapper.ext.PmsgwGameWhiteListExtMapper;
import com.saas.biz.mapper.ext.WeixinMessageExtMapper;
import com.saas.biz.pojo.PmsgwGameMaster;
import com.saas.biz.pojo.PmsgwGameWhiteList;
import com.saas.biz.pojo.WeixinMessage;
import com.saas.biz.service.WeixinMessageService;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.OpEnum;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.SignEnum;

@Service
public class WeixinMessageServiceImpl implements WeixinMessageService {
	
	@Autowired
	private WeixinMessageExtMapper weixinMessageExtMapper;
	
	@Autowired
	private PmsgwGameWhiteListExtMapper pmsgwGameWhiteListExtMapper;


	@Override
	public List<WeixinMessage> selectListByDynamic(Map<Object, Object> paraMap) {
	
		return weixinMessageExtMapper.selectListByDynamic(paraMap);
	}

	@Override
	public long selectCountByDynamic(Map<Object, Object> paraMap) {
		
		return weixinMessageExtMapper.selectCountByDynamic(paraMap);
	}
	
	@Override
	public String selectPmsgwGameWhiteList() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		
		List<PmsgwGameWhiteList> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<PmsgwGameWhiteList>() {
			@Override
			public List<PmsgwGameWhiteList> selectByDynamic(Map<Object, Object> map) {
				return pmsgwGameWhiteListExtMapper.selectListByDynamic(map);
			}
		});
		List<String > names=new ArrayList<String>();
		for(PmsgwGameWhiteList obj:list)
		{
			names.add(obj.getName());
		}
		String citiesCommaSeparated = String.join(",", names);
		return citiesCommaSeparated;
	}
}
