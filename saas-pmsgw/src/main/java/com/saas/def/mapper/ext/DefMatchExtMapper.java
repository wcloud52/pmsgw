package com.saas.def.mapper.ext;

import java.util.List;
import java.util.Map;

import com.saas.def.mapper.base.DefMatchMapper; 
import com.saas.def.pojo.DefMatch;

public interface DefMatchExtMapper extends DefMatchMapper {
    /**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	List<DefMatch> selectListByDynamic(Map <Object, Object> paraMap);
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
	int insertBatch(List <DefMatch> list);
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	int updateBatch(List <DefMatch> list);
	/**
	 * 动态删除
	 * @param paraMap
	 * @return
	 */
	int deleteByDynamic(Map <Object, Object> paraMap);
}