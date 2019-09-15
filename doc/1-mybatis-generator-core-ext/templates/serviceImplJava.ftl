package ${implMapperPackageName?replace('mapper.impl','service.impl' )};

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zichan100.common.enums.OpEnum;
import com.zichan100.common.repository.DynamicDelete;
import com.zichan100.common.repository.DynamicDeleteSpecification;
import com.zichan100.common.repository.DynamicQuery;
import com.zichan100.common.repository.DynamicQuerySpecification;
import com.zichan100.common.repository.QueryNode;

import ${implMapperPackageName}.${implMapperName};
import ${pojoPackageName}.${pojoName};
import ${implMapperPackageName?replace('mapper.impl','service' )}.${implMapperName?replace('ImplMapper', 'Service')};

@Service
public class ${implMapperName?replace('ImplMapper', 'ServiceImpl')} implements ${implMapperName?replace('ImplMapper', 'Service')} {
	@Autowired
	private ${implMapperName} implMapper;

    /**
	 * 插入
	 * @param record
	 * @return
	 */
	@Override
	public int insert(${pojoName} record) {
		return implMapper.insert(record);
	}
	/**
	 * 根据主键更新
	 * @param record
	 * @return
	 */
	@Override
	public int update(${pojoName} record) {
		return implMapper.updateByPrimaryKeySelective(record);
	}
    /**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(${table.primaryKeyColumns[0].fullyQualifiedJavaType.shortName} ${table.primaryKeyColumns[0].javaProperty}) {
		return implMapper.deleteByPrimaryKey(${table.primaryKeyColumns[0].javaProperty});
	}
    /**
	 * 根据主键获取一条记录
	 * @param id
	 * @return
	 */
	@Override
	public ${pojoName} selectOneById(${table.primaryKeyColumns[0].fullyQualifiedJavaType.shortName} ${table.primaryKeyColumns[0].javaProperty}) {
		return implMapper.selectByPrimaryKey(${table.primaryKeyColumns[0].javaProperty});
	}
	/**
	 * 根据条件记录
	 * @param
	 * @return
	 */
	@Override
	 public List<${pojoName}> selectListBy(
      <#list table.allColumns as column>  
	   ${column.fullyQualifiedJavaType.shortName} ${column.javaProperty}<#if column_has_next>,</#if>		
	</#list>
     ){
      List<QueryNode> nodes = new ArrayList<QueryNode>();
      /*		
      <#list table.allColumns as column>  
        <#if column.fullyQualifiedJavaType.shortName == "String" >
         if(StringUtils.isNotBlank(${column.javaProperty}))
        {
          nodes.add(new QueryNode("${column.actualColumnName}", ${column.javaProperty}));
        }	
        </#if>	
      <#if column.fullyQualifiedJavaType.shortName != "String" >
        if(${column.javaProperty}!=null)
        {
          nodes.add(new QueryNode("${column.actualColumnName}", ${column.javaProperty}));
        }	
        </#if>	
	  </#list>	
	  */
     <#list table.allColumns as column>  
     <#if column.fullyQualifiedJavaType.shortName == "String" >
       if(StringUtils.isNotBlank(${column.javaProperty}))
       {
         nodes.add(new QueryNode("${column.actualColumnName}", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), ${column.javaProperty}));
       }	
     </#if>	
     <#if column.fullyQualifiedJavaType.shortName != "String" >
       if(${column.javaProperty}!=null)
       {
         nodes.add(new QueryNode("${column.actualColumnName}", OpEnum.EQUALS.getName(),PrependEnum.NONE.getName(), ${column.javaProperty}));
       }	
     </#if>	
	</#list>	
		List<${pojoName}> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<${pojoName}>() {
			@Override
			public List<${pojoName}> selectByDynamic(Map<Object, Object> map) {
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
	public List<${pojoName}> selectAll() {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		List<${pojoName}> list = DynamicQuery.selectByDynamic(nodes, new DynamicQuerySpecification<${pojoName}>() {
			@Override
			public List<${pojoName}> selectByDynamic(Map<Object, Object> map) {
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
	public List<${pojoName}> selectListByDynamic(Map<Object, Object> paraMap) {
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
	public int deleteByIds(List<${table.primaryKeyColumns[0].fullyQualifiedJavaType.shortName}> ids) {
		List<QueryNode> nodes = new ArrayList<QueryNode>();
		String idString = StringUtils.collectionToDelimitedString(ids, ",");
		nodes.add(new QueryNode("${table.primaryKeyColumns[0].actualColumnName}", OpEnum.IN.getName(), idString));
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
	public int insertBatch(List<${pojoName}> list)
	{
		return implMapper.insertBatch(list);
	}
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	@Override
	public int updateBatch(List<${pojoName}> list)
	{
		return implMapper.updateBatch(list);
	}
}
