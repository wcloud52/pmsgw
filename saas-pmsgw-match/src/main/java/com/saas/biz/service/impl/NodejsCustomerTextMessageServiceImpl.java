package com.saas.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saas.common.DynamicQuery;
import com.saas.common.DynamicQuerySpecification;
import com.saas.common.OpEnum;
import com.saas.common.PrependEnum;
import com.saas.common.QueryNode;

import com.saas.biz.mapper.impl.NodejsCustomerTextMessageImplMapper;
import com.saas.biz.pojo.NodejsCustomerTextMessage;
import com.saas.biz.service.NodejsCustomerTextMessageService;

@Service
public class NodejsCustomerTextMessageServiceImpl implements NodejsCustomerTextMessageService {
	@Autowired
	private NodejsCustomerTextMessageImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(NodejsCustomerTextMessage record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(NodejsCustomerTextMessage record) {
		return implMapper.updateByPrimaryKeySelective(record);
	}
    /**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(String message_id) {
		return implMapper.deleteByPrimaryKey(message_id);
	}
    /**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public NodejsCustomerTextMessage selectOneById(String message_id) {
		return implMapper.selectByPrimaryKey(message_id);
	}
	/**
	 * 根据条件记录
	 * @param
	 * @return
	 */
	@Override
	 public List<NodejsCustomerTextMessage> selectListBy(
	   String message_id,		
	   String message_senderId,		
	   String message_senderName,		
	   String message_receiverId,		
	   String message_receiverName,		
	   Date message_sendTime,		
	   String message_type,		
	   String message_title,		
	   String message_text,		
	   Integer message_status,		
	   Date message_create_time,		
	   Date message_modify_time		
     ){
      List<QueryNode> nodes = new ArrayList<QueryNode>();
      /*		
         if(StringUtils.isNotBlank(message_id))
        {
          nodes.add(new QueryNode("message_id", message_id));
        }	
         if(StringUtils.isNotBlank(message_senderId))
        {
          nodes.add(new QueryNode("message_senderId", message_senderId));
        }	
         if(StringUtils.isNotBlank(message_senderName))
        {
          nodes.add(new QueryNode("message_senderName", message_senderName));
        }	
         if(StringUtils.isNotBlank(message_receiverId))
        {
          nodes.add(new QueryNode("message_receiverId", message_receiverId));
        }	
         if(StringUtils.isNotBlank(message_receiverName))
        {
          nodes.add(new QueryNode("message_receiverName", message_receiverName));
        }	
        if(message_sendTime!=null)
        {
          nodes.add(new QueryNode("message_sendTime", message_sendTime));
        }	
         if(StringUtils.isNotBlank(message_type))
        {
          nodes.add(new QueryNode("message_type", message_type));
        }	
         if(StringUtils.isNotBlank(message_title))
        {
          nodes.add(new QueryNode("message_title", message_title));
        }	
         if(StringUtils.isNotBlank(message_text))
        {
          nodes.add(new QueryNode("message_text", message_text));
        }	
        if(message_status!=null)
        {
          nodes.add(new QueryNode("message_status", message_status));
        }	
        if(message_create_time!=null)
        {
          nodes.add(new QueryNode("message_create_time", message_create_time));
        }	
        if(message_modify_time!=null)
        {
          nodes.add(new QueryNode("message_modify_time", message_modify_time));
        }	
	  */
       if(StringUtils.isNotBlank(message_id))
       {
         nodes.add(new QueryNode("message_id", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_id));
       }	
       if(StringUtils.isNotBlank(message_senderId))
       {
         nodes.add(new QueryNode("message_senderId", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_senderId));
       }	
       if(StringUtils.isNotBlank(message_senderName))
       {
         nodes.add(new QueryNode("message_senderName", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_senderName));
       }	
       if(StringUtils.isNotBlank(message_receiverId))
       {
         nodes.add(new QueryNode("message_receiverId", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_receiverId));
       }	
       if(StringUtils.isNotBlank(message_receiverName))
       {
         nodes.add(new QueryNode("message_receiverName", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_receiverName));
       }	
       if(message_sendTime!=null)
       {
         nodes.add(new QueryNode("message_sendTime", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_sendTime));
       }	
       if(StringUtils.isNotBlank(message_type))
       {
         nodes.add(new QueryNode("message_type", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_type));
       }	
       if(StringUtils.isNotBlank(message_title))
       {
         nodes.add(new QueryNode("message_title", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_title));
       }	
       if(StringUtils.isNotBlank(message_text))
       {
         nodes.add(new QueryNode("message_text", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_text));
       }	
       if(message_status!=null)
       {
         nodes.add(new QueryNode("message_status", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_status));
       }	
       if(message_create_time!=null)
       {
         nodes.add(new QueryNode("message_create_time", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_create_time));
       }	
       if(message_modify_time!=null)
       {
         nodes.add(new QueryNode("message_modify_time", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), message_modify_time));
       }	
		List<NodejsCustomerTextMessage> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsCustomerTextMessage>() {
			@Override
			public List<NodejsCustomerTextMessage> selectByDynamic(Map<Object, Object> map) {
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
	public List<NodejsCustomerTextMessage> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<NodejsCustomerTextMessage> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<NodejsCustomerTextMessage>() {
			@Override
			public List<NodejsCustomerTextMessage> selectByDynamic(Map<Object, Object> map) {
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
	public List<NodejsCustomerTextMessage> selectListByDynamic(Map<Object, Object> paraMap) {
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
		String idString = StringUtils.collectionToDelimitedString(ids, ",");
		nodes.add(new QueryNode("message_id", OpEnum.IN.getName(), idString));
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
	public int insertBatch(List<NodejsCustomerTextMessage> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<NodejsCustomerTextMessage> list)
	{
		return implMapper.updateBatch(list);
	}
}
