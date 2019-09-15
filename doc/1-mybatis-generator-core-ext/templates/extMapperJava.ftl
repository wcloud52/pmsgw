package ${extMapperPackageName};

import java.util.List;
import java.util.Map;

import ${baseMapperPackageName}.${baseMapperName}; 
import ${pojoPackageName}.${pojoName};

public interface ${extMapperName} extends ${baseMapperName} {
    /**
	 * 动态查询列表
	 * @param paraMap
	 * @return
	 */
	List<${pojoName}> selectListByDynamic(Map<Object, Object> paraMap);
	/**
	 * 动态查询总数
	 * @param paraMap
	 * @return
	 */
	long selectCountByDynamic(Map<Object, Object> paraMap);
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int insertBatch(List<${pojoName}> list);
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	int updateBatch(List<${pojoName}> list);
	/**
	 * 动态删除
	 * @param paraMap
	 * @return
	 */
	int deleteByDynamic(Map<Object, Object> paraMap);
}