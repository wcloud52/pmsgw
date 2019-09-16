package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.saas.biz.mapper.impl.NodejsCrawlerCoteImplMapper;
import com.saas.biz.pojo.NodejsCrawlerCote;
import com.saas.biz.service.NodejsCrawlerCoteService;
import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.OpEnum;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;

@Service
public class NodejsCrawlerCoteServiceImpl implements NodejsCrawlerCoteService {
	@Autowired
	private NodejsCrawlerCoteImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(NodejsCrawlerCote record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(NodejsCrawlerCote record) {
		return implMapper.updateByPrimaryKeySelective(record);
	}
    /**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(String cote_id) {
		return implMapper.deleteByPrimaryKey(cote_id);
	}
    /**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public NodejsCrawlerCote selectOneById(String cote_id) {
		return implMapper.selectByPrimaryKey(cote_id);
	}
	/**
	 * 根据条件记录
	 * @param
	 * @return
	 */
	@Override
	 public List<NodejsCrawlerCote> selectListBy(
	   String cote_id,		
	   String cote_name,		
	   String cote_website,		
	   String cote_state,		
	   Date create_time,		
	   Date modify_time		
     ){
      List<QueryNode> nodes = new ArrayList<QueryNode>();
      /*		
         if(StringUtils.isNotBlank(cote_id))
        {
          nodes.add(new QueryNode("cote_id", cote_id));
        }	
         if(StringUtils.isNotBlank(cote_name))
        {
          nodes.add(new QueryNode("cote_name", cote_name));
        }	
         if(StringUtils.isNotBlank(cote_website))
        {
          nodes.add(new QueryNode("cote_website", cote_website));
        }	
         if(StringUtils.isNotBlank(cote_state))
        {
          nodes.add(new QueryNode("cote_state", cote_state));
        }	
        if(create_time!=null)
        {
          nodes.add(new QueryNode("create_time", create_time));
        }	
        if(modify_time!=null)
        {
          nodes.add(new QueryNode("modify_time", modify_time));
        }	
	  */
       if(StringUtils.isNotBlank(cote_id))
       {
         nodes.add(new QueryNode("cote_id", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), cote_id));
       }	
       if(StringUtils.isNotBlank(cote_name))
       {
         nodes.add(new QueryNode("cote_name", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), cote_name));
       }	
       if(StringUtils.isNotBlank(cote_website))
       {
         nodes.add(new QueryNode("cote_website", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), cote_website));
       }	
       if(StringUtils.isNotBlank(cote_state))
       {
         nodes.add(new QueryNode("cote_state", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), cote_state));
       }	
       if(create_time!=null)
       {
         nodes.add(new QueryNode("create_time", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), create_time));
       }	
       if(modify_time!=null)
       {
         nodes.add(new QueryNode("modify_time", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), modify_time));
       }	
		List<NodejsCrawlerCote> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsCrawlerCote>() {
			@Override
			public List<NodejsCrawlerCote> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		return list;
     }
	/**
	 * 获取全部记录
	 * @return
	 */
	@Override
	public List<NodejsCrawlerCote> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<NodejsCrawlerCote> list = DynamicQuery.selectByDynamic("cote_name asc",nodes, new DynamicQuerySpecification<NodejsCrawlerCote>() {
			@Override
			public List<NodejsCrawlerCote> selectByDynamic(Map<Object, Object> map) {
				return implMapper.selectListByDynamic(map);
			}
		});
		return list;
	}
	/**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	@Override
	public List<NodejsCrawlerCote> selectListByDynamic(Map<Object, Object> paraMap) {
		return implMapper.selectListByDynamic(paraMap);
	}
	/**
	 * 动态查询总数
	 * @param paraMap
	 * @return
	 */
	@Override
	public long selectCountByDynamic(Map<Object, Object> paraMap) {
		return implMapper.selectCountByDynamic(paraMap);
	}
	/**
	 * 根据主键列表删除
	 * @param ids
	 * @return
	 */
	@Override
	public int deleteByIds(List<String> ids) {
		/*List<QueryNode> nodes = new ArrayList<QueryNode>();
		String idString = org.springframework.util.StringUtils.collectionToDelimitedString(ids, ",");
		nodes.add(new QueryNode("cote_id", OpEnum.IN.getName(), idString));
		int result = DynamicDelete.deleteByDynamic(nodes, new DynamicDeleteSpecification() {
			@Override
			public int deleteByDynamic(Map<Object, Object> map) {
				return implMapper.deleteByDynamic(map);
			}
		});
		return result;*/
		return 0;
	}
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	@Override
	public int insertBatch(List<NodejsCrawlerCote> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<NodejsCrawlerCote> list)
	{
		return implMapper.updateBatch(list);
	}
}
