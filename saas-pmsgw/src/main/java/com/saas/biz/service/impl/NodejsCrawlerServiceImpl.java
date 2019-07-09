package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saas.biz.mapper.base.NodejsCrawlerMasterGameMapper;
import com.saas.biz.mapper.ext.NodejsCrawlerMapper;
import com.saas.biz.pojo.NodejsCrawlerDetailGame;
import com.saas.biz.pojo.NodejsCrawlerMasterGame;
import com.saas.biz.pojo.PmsgwGameDetail;
import com.saas.biz.pojo.PmsgwGameMaster;
import com.saas.biz.service.NodejsCrawlerService;
import com.saas.common.BaseResponse;
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
public class NodejsCrawlerServiceImpl implements NodejsCrawlerService {
	@Autowired
	private NodejsCrawlerMapper nodejsCrawlerMapper;
	
	@Autowired
	private NodejsCrawlerMasterGameMapper nodejsCrawlerMasterGameMapper;

	@Override
	public int insertNodejsCrawlerMasterGame(NodejsCrawlerMasterGame record)
	{
		return nodejsCrawlerMasterGameMapper.insert(record);
	}
	@Override
	public List<NodejsCrawlerMasterGame> selectNodejsCrawlerMasterGameListByDynamic(Map<Object, Object> paraMap) {
		
		return nodejsCrawlerMapper.selectNodejsCrawlerMasterGameListByDynamic(paraMap);
	}

	@Override
	public long selectNodejsCrawlerMasterGameListByDynamicCount( Map<Object, Object> paraMap) {
		
		return nodejsCrawlerMapper.selectNodejsCrawlerMasterGameListByDynamicCount(paraMap);
	}
	
	@Override
	public NodejsCrawlerMasterGame selectNodejsCrawlerMasterGameById(String database,String master_id) {
	
		return nodejsCrawlerMapper.selectNodejsCrawlerMasterGameById( database, master_id);
	}

	@Override
	public List<NodejsCrawlerDetailGame> selectNodejsCrawlerDetailGameListByDynamic(Map<Object, Object> paraMap) {
		return nodejsCrawlerMapper.selectNodejsCrawlerDetailGameListByDynamic(paraMap);
	}

	@Override
	public long selectNodejsCrawlerDetailGameListByDynamicCount(Map<Object, Object> paraMap) {
		return nodejsCrawlerMapper.selectNodejsCrawlerDetailGameListByDynamicCount(paraMap);
	}
	
	@Override
	public List<NodejsCrawlerMasterGame> selectTop50NodejsCrawlerMasterGameList(String database) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("database",OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(),database,SignEnum.NONE.getName()));
		nodes.add(new QueryNode("LEFT(master_id, 8)",OpEnum.EQUALS.getName(),PrependEnum.AND.getName(),"DATE_FORMAT(NOW(), '%Y%m%d')",SignEnum.NONE.getName()));
		
		List<QueryNodes> listNodes = QueryNodes.createQueryNodesList(nodes, "and");
		
		QueryObject query = new QueryObject();
		query.setPageIndex(1);
		query.setPageSize(500);
		query.setSort("cote_state DESC,create_time desc");
		query.setFuzzyQuery(listNodes);
		
		BaseResponse<JsonResult<List<NodejsCrawlerMasterGame>, Object>> restult= PagingAndSortingRepository.find(query, new PageSpecification<NodejsCrawlerMasterGame>() {

			@Override
			public List<NodejsCrawlerMasterGame> query(Map<Object, Object> map) {
				
				return nodejsCrawlerMapper.selectNodejsCrawlerMasterGameListByDynamic(map);
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
		return restult.getData().getList();
	}
	@Override
	public List<NodejsCrawlerDetailGame> selectNodejsCrawlerDetailGameListByMasterId(String database,String masterId) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("master_id",OpEnum.EQUALS.getName(),PrependEnum.AND.getName(),masterId,SignEnum.YES.getName()));
		nodes.add(new QueryNode("database",OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(),database,SignEnum.NONE.getName()));
		List<QueryNodes> queryNodes = QueryNodes.createQueryNodesList(nodes,"and");
		
		QueryObject query = new QueryObject();
		query.setPageIndex(1);
		query.setPageSize(10000);
		query.setSort(" rank asc,create_time asc ");
		query.setFuzzyQuery(queryNodes);
		
		BaseResponse<JsonResult<List<NodejsCrawlerDetailGame>, Object>> restult= PagingAndSortingRepository.find(query, new PageSpecification<NodejsCrawlerDetailGame>() {

			@Override
			public List<NodejsCrawlerDetailGame> query(Map<Object, Object> map) {
				return nodejsCrawlerMapper.selectNodejsCrawlerDetailGameListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return 10000;
			}
		});
		return restult.getData().getList();
	}

	@Override
	public List<NodejsCrawlerDetailGame> selectNodejsCrawlerDetailGameListByMasterId(String database,String pigowner,String masterId,int pageIndex,int pageSize) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		nodes.add(new QueryNode("master_id",OpEnum.EQUALS.getName(),PrependEnum.AND.getName(),masterId,SignEnum.YES.getName()));
		nodes.add(new QueryNode("database",OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(),database,SignEnum.NONE.getName()));
		if(pigowner!=null&&pigowner.trim().length()>0)
		{
			nodes.add(new QueryNode("pigowner",OpEnum.LIKE.getName(),PrependEnum.AND.getName(),pigowner,SignEnum.YES.getName()));
		}
		
		List<QueryNodes> queryNodes = QueryNodes.createQueryNodesList(nodes,"and");
		
		QueryObject query = new QueryObject();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		query.setSort(" rank asc,create_time asc ");
		query.setFuzzyQuery(queryNodes);
		
		BaseResponse<JsonResult<List<NodejsCrawlerDetailGame>, Object>> restult= PagingAndSortingRepository.find(query, new PageSpecification<NodejsCrawlerDetailGame>() {

			@Override
			public List<NodejsCrawlerDetailGame> query(Map<Object, Object> map) {
				return nodejsCrawlerMapper.selectNodejsCrawlerDetailGameListByDynamic(map);
			}

			@Override
			public Object queryExt(Map<Object, Object> map) {
				return null;
			}

			@Override
			public long queryCount(Map<Object, Object> map) {
				return nodejsCrawlerMapper.selectNodejsCrawlerDetailGameListByDynamicCount(map);
			}
		});
		return restult.getData().getList();
	}

}
