package com.saas.def.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.saas.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saas.def.mapper.impl.DefMatchItemImplMapper;
import com.saas.def.pojo.DefMatchItem;
import com.saas.def.service.DefMatchItemService;

@Service
public class DefMatchItemServiceImpl implements DefMatchItemService {
	@Autowired
	private DefMatchItemImplMapper implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(DefMatchItem record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(DefMatchItem record) {
		return implMapper.updateByPrimaryKeySelective(record);
	}
    /**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(String item_id) {
		return implMapper.deleteByPrimaryKey(item_id);
	}
    /**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public DefMatchItem selectOneById(String item_id) {
		return implMapper.selectByPrimaryKey(item_id);
	}

	/**
	 * 获取全部记录
	 * @return
	 */
	@Override
	public List<DefMatchItem> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<DefMatchItem> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<DefMatchItem>() {
			@Override
			public List<DefMatchItem> selectByDynamic(Map<Object, Object> map) {
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
	public List<DefMatchItem> selectListByDynamic(Map<Object, Object> paraMap) {
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
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		String idString = StringUtils.collectionToDelimitedString(ids, ",");
		//nodes.add(new QueryNode("item_id", OpEnum.IN.getName(), idString));
		int result = DynamicDelete.deleteByDynamic(nodes, new DynamicDeleteSpecification() {
			@Override
			public int deleteByDynamic(Map<Object, Object> map) {
				return implMapper.deleteByDynamic(map);
			}
		});
		return result;
	}
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	@Override
	public int insertBatch(List<DefMatchItem> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<DefMatchItem> list)
	{
		return implMapper.updateBatch(list);
	}
}
