package ${implMapperPackageName?replace('mapper.impl','service' )};

import com.zichan100.common.service.BaseService;
import ${pojoPackageName}.${pojoName};

public interface ${implMapperName?replace('ImplMapper', 'Service')} extends BaseService<${pojoName},${table.primaryKeyColumns[0].fullyQualifiedJavaType.shortName}> {

     public List<${pojoName}> selectListBy(
      <#list table.allColumns as column>  
	   ${column.fullyQualifiedJavaType.shortName} ${column.javaProperty}<#if column_has_next>,</#if>		
	</#list>
     );
}
