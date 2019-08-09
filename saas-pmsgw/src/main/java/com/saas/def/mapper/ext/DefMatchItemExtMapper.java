package com.saas.def.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.def.mapper.base.DefMatchItemMapper; 
import com.saas.def.pojo.DefMatchItem;

public interface DefMatchItemExtMapper extends DefMatchItemMapper {
    /**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	List<DefMatchItem> selectListByDynamic(Map <Object, Object> paraMap);
	/**
	 * 动态查询总数
	 * @param paraMap
	 * @return
	 */
	long selectCountByDynamic(Map <Object, Object> paraMap);
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int insertBatch(List <DefMatchItem> list);
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	int updateBatch(List <DefMatchItem> list);
	/**
	 * 动态删除
	 * @param paraMap
	 * @return
	 */
	int deleteByDynamic(Map <Object, Object> paraMap);
}