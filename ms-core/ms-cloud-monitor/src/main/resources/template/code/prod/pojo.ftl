package ${packagePath}.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;
import com.system.dao.annotation.ColumnIgnore;
import com.system.dao.annotation.ColumnPk;

/**
 * ${tablename}
 * @author ${user}
 * @date ${dateTime}
 * @version ${version}
 */
@Alias("${table.beanName}")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class ${table.className} extends BaseEntity implements Serializable {
<#list table.columns as column>
	<#if (column.fieldName == table.firstKColumn.fieldName)>@ColumnPk(isAuto=true)</#if>//${column.comments}
	private ${column.typeName} ${column.fieldName};
</#list>

	//================= Extended attribute
	@ColumnIgnore
	private String ext;
	
<#list table.columns as column>
	public ${column.typeName} get${column.fieldName?cap_first}() {
		return ${column.fieldName};
	}
	public void set${column.fieldName?cap_first}(${column.typeName} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
	}
</#list>
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
}