<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packagePath}.dao.${table.className}Dao">

	<sql id="commCols">
		<#list table.columns as column>${column.columnName} ${column.fieldName}<#if column_has_next>,</#if></#list>
	</sql>

	<insert id="save" parameterType="${table.beanName}" flushCache="true">
		 insert into ${table.name}(<#list table.columns as column><#if (column.fieldName != table.firstKColumn.fieldName)>${column.columnName}<#if column_has_next>,</#if></#if></#list>)
		 values(<#list table.columns as column><#if (column.fieldName != table.firstKColumn.fieldName)><#if (column.fieldName == "isdel")>0<#elseif (column.fieldName == "addtime")>now()<#else>${r"#{"}${column.fieldName}${r"}"}</#if><#if column_has_next>,</#if></#if></#list>)
	</insert>
	
	<delete id="delete" flushCache="true">
		delete from ${table.name}
		<where>${table.firstKColumn.columnName}=${r"#{"}${table.firstKColumn.fieldName}${r"}"}</where>
	</delete>
	
	<update id="update" parameterType="${table.beanName}" flushCache="true">
		update ${table.name}
		<set>
			<#list table.columns as column><#if (column.fieldName != table.firstKColumn.fieldName && column.fieldName != "isdel" && column.fieldName != "adduser" && column.fieldName != "addtime" && column.fieldName != "companyid")><#if (column_index > 1)>,</#if>${column.columnName}=${r"#{"}${column.fieldName}${r"}"}</#if></#list>
		</set>
		<where>${table.firstKColumn.columnName}=${r"#{"}${table.firstKColumn.fieldName}${r"}"}</where>
	</update>
	
	<select id="get" resultType="${table.beanName}">
		select <include refid="commCols"/> from ${table.name}
  		<where>${table.firstKColumn.columnName}=${r"#{"}${table.firstKColumn.fieldName}${r"}"}</where>
	</select>
	
	<select id="find${table.className}" parameterType="${table.beanName}" resultType="${table.beanName}">
		select <include refid="commCols"/> from ${table.name}
		<where>
			<if test="name!=null and name!=''">and name like concat(concat('%', ${r"#{name}"}), '%')</if>
			and isdel=0
		</where>
  		limit ${r"#{currentIndex}"}, ${r"#{size}"}
	</select>
	<select id="find${table.className}Count" parameterType="${table.beanName}" resultType="int">
		select count(*) from ${table.name}
		<where>
			<if test="name!=null and name!=''">and name like concat(concat('%', ${r"#{name}"}), '%')</if>
			and isdel=0
		</where>
	</select>
</mapper>