<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${extMapperPackageName}.${extMapperName}">
    <!-- 动态查询列表 -->
	<select id="selectListByDynamic"
		resultMap="${baseMapperPackageName}.${baseMapperName}.BaseResultMap"
		parameterType="Object">
		select <include refid="${baseMapperPackageName}.${baseMapperName}.Base_Column_List" />
		from ${tableName}
		<where>
			<if test="customQuerySegment != null and customQuerySegment != '' ">
				and <#noparse>${customQuerySegment}</#noparse>
			</if>
            <#list table.allColumns as column>          
            <if test="queryMap.${column.javaProperty} != null">
              and ${column.actualColumnName} = <#noparse>#{queryMap.</#noparse>${column.javaProperty},jdbcType=${column.jdbcTypeName} <#noparse>}</#noparse>			
			</if>		
			</#list>
		</where>
		<if test="sort != null and sort != '' ">
			order by <#noparse>${sort}</#noparse>
		</if>
		<if test="page != null ">
			limit <#noparse>${page.offset},${page.pageSize}</#noparse>
		</if>
	</select>

    <!-- 动态查询总数 -->
	<select id="selectCountByDynamic" resultType="long"
		parameterType="Object">
		SELECT count(*)
		from ${tableName}
		<where>
			<if test="customQuerySegment != null and customQuerySegment != '' ">
				and <#noparse>${customQuerySegment}</#noparse>
			</if>
            <#list table.allColumns as column>          
            <if test="queryMap.${column.javaProperty} != null">
              and ${column.actualColumnName} = <#noparse>#{queryMap.</#noparse>${column.javaProperty},jdbcType=${column.jdbcTypeName} <#noparse>}</#noparse>			
			</if>		
			</#list>
		</where>
	</select>
	
	 <!-- 动态删除 -->
	<delete id="deleteByDynamic" parameterType="Object">
		delete from ${tableName}
		<where>
			<if test="customQuerySegment != null and customQuerySegment != '' ">
				and <#noparse>${customQuerySegment}</#noparse>
			</if>
            <#list table.allColumns as column>          
            <if test="queryMap.${column.javaProperty} != null">
              and ${column.actualColumnName} = <#noparse>#{queryMap.</#noparse>${column.javaProperty},jdbcType=${column.jdbcTypeName} <#noparse>}</#noparse>			
			</if>		
			</#list>		
		</where>
	</delete>
	
	<!-- 批量插入 -->
	<insert id="insertBatch" parameterType="list">
		insert into ${tableName} 
		(
		<#list  table.allColumns as column>
		${column.actualColumnName}<#if column_has_next>,</#if>
		</#list>
		)
		values
		<foreach item="item" index="index" collection="list" open=""
			separator="," close="">
			(
			  <#list  table.allColumns as column>
			    <#noparse>#{item.</#noparse>${column.javaProperty},jdbcType=${column.jdbcTypeName}<#noparse>}</#noparse><#if column_has_next>,</#if>
		      </#list>
			)
		</foreach>
	</insert>
    
    <!-- 批量更新 -->
    <update id="updateBatch" parameterType="list">
		<foreach collection="list" item="item" index="index" open=""
			close="" separator=";">
			 update ${tableName} 
			<set>
			   <#list table.baseColumns as column>          
                <if test="item.${column.javaProperty} != null">
                  and ${column.actualColumnName} = <#noparse>#{item.</#noparse>${column.javaProperty},jdbcType=${column.jdbcTypeName}<#noparse>}</#noparse>			
			     </if>		
			    </#list>		
			</set>
			where
			<#list  table.primaryKeyColumns as column>
		      ${column.actualColumnName}=<#noparse>#{item.</#noparse>${column.javaProperty},jdbcType=${column.jdbcTypeName}<#noparse>}</#noparse><#if column_has_next> and </#if>
		    </#list>
		</foreach>
	</update>
	
</mapper>