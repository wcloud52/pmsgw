package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.biz.mapper.ext.PmsgwGameMasterExtMapper;
import com.saas.biz.pojo.PmsgwGameMaster;
import com.saas.biz.service.PmsgwGameMasterService;
import com.saas.common.BaseResponse;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.JsonResult;
import com.saas.common.OpEnum;
import com.saas.common.PageSpecification;
import com.saas.common.PagingAndSortingRepository;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;
import com.saas.common.QueryNodes;
import com.saas.common.QueryObject;
import com.saas.common.SignEnum;

@Service
public class PmsgwGameMasterServiceImpl implements PmsgwGameMasterService {
	@Autowired
	private PmsgwGameMasterExtMapper pmsgwGameMasterExtMapper;

	@Override
	public PmsgwGameMaster selectOneById(Long id) {
	
		return pmsgwGameMasterExtMapper.selectByPrimaryKey(id);
	}

	@Transactional
	@Override
	public int insert(PmsgwGameMaster record) {
	
		List<PmsgwGameMaster> list= selectListByMainhref(record.getMain_href());
		if(list==null||list.size()==0)
		 pmsgwGameMasterExtMapper.insert(record);
		return 1;
	}

	@Override
	public int update(PmsgwGameMaster record) {
		
		return pmsgwGameMasterExtMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<PmsgwGameMaster> selectListByDynamic(Map<Object, Object> paraMap) {
	
		return pmsgwGameMasterExtMapper.selectListByDynamic(paraMap);
	}

	@Override
	public long selectListByDynamicCount(Map<Object, Object> paraMap) {
		
		return pmsgwGameMasterExtMapper.selectListByDynamicCount(paraMap);
	}
	
	@Override
	public List<PmsgwGameMaster> selectListByWebsite(String website) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("website", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), website, SignEnum.YES.getName()));
		List<PmsgwGameMaster> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<PmsgwGameMaster>() {
			@Override
			public List<PmsgwGameMaster> selectByDynamic(Map<Object, Object> map) {
				return pmsgwGameMasterExtMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	@Override
	public List<PmsgwGameMaster> selectListByWebsiteCreatetime(String website) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("website", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), website, SignEnum.YES.getName()));
		DateTime dateTime1 = new DateTime(new Date());
		String timestamp1 = dateTime1.toString("yyyy-MM-dd");
		
		DateTime dateTime2 =new DateTime(DateUtils.addDays(new Date(), 1));
		String timestamp2 = dateTime2.toString("yyyy-MM-dd");
		String []time= {timestamp1,timestamp2};
		nodes.add(new QueryNode("create_time", OpEnum.BETWEEM_AND.getName(), PrependEnum.AND.getName(), timestamp1+","+timestamp2, SignEnum.YES.getName()));
		List<PmsgwGameMaster> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<PmsgwGameMaster>() {
			@Override
			public List<PmsgwGameMaster> selectByDynamic(Map<Object, Object> map) {
				return pmsgwGameMasterExtMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	@Override
	public List<PmsgwGameMaster> selectTop10ListByWebsite(String website) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("website", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), website, SignEnum.YES.getName()));
		
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");
		
		QueryObject query = new QueryObject();
		query.setPageIndex(1);
		query.setPageSize(100);
		query.setSort("main_date asc");
		query.setFuzzyQuery(listNodes);
		
		List<PmsgwGameMaster> restult = PagingAndSortingRepository.findList(query, new PageSpecification<PmsgwGameMaster>() {

			@Override
			public List<PmsgwGameMaster> query(Map<Object, Object> map) {
				return pmsgwGameMasterExtMapper.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return 1;
			}
		});
		
		return restult;
	}
	@Override
	public List<PmsgwGameMaster> selectTop50List() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");
		
		QueryObject query = new QueryObject();
		query.setPageIndex(1);
		query.setPageSize(50);
		query.setSort("create_time desc");
		query.setFuzzyQuery(listNodes);
		
		List<PmsgwGameMaster> restult = PagingAndSortingRepository.findList(query, new PageSpecification<PmsgwGameMaster>() {

			@Override
			public List<PmsgwGameMaster> query(Map<Object, Object> map) {
				return pmsgwGameMasterExtMapper.selectListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return 1;
			}
		});
		
		return restult;
	}
	@Override
	public List<PmsgwGameMaster> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<PmsgwGameMaster> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<PmsgwGameMaster>() {
			@Override
			public List<PmsgwGameMaster> selectByDynamic(Map<Object, Object> map) {
				return pmsgwGameMasterExtMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	@Override
	public int delete( Long id) {
		return pmsgwGameMasterExtMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<PmsgwGameMaster> selectListByMainhref(String main_href) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("main_href", OpEnum.EQUALS.getName(), PrependEnum.AND.getName(), main_href, SignEnum.YES.getName()));
		List<PmsgwGameMaster> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<PmsgwGameMaster>() {
			@Override
			public List<PmsgwGameMaster> selectByDynamic(Map<Object, Object> map) {
				return pmsgwGameMasterExtMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	
	@Override
	public int deleteByMainhref( String main_href) {
		return pmsgwGameMasterExtMapper.deleteByMainhref(main_href);
	}

	@Override
	public PmsgwGameMaster selectTopOne(String main_text) {
		return pmsgwGameMasterExtMapper.selectTopOne(main_text);
	}

	@Override
	public int insertCrawlerMasterFormPmsgw() {
		return pmsgwGameMasterExtMapper.insertCrawlerMasterFormPmsgw();
	}

	@Override
	public int insertCrawlerDetailFormPmsgw() {
		return pmsgwGameMasterExtMapper.insertCrawlerDetailFormPmsgw();
	}

}
