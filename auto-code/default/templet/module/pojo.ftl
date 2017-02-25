package ${packagePath}.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

/**
 * ${tablename}实体
 * @author ${user}
 * @date ${dateTime}
 * @version ${version}
 */
@Alias("${table.beanName}")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class ${table.className} extends BaseEntity implements Serializable {
<#list table.columns as column>
	//${column.comments}
	private ${column.typeName} ${column.fieldName};
</#list>
<#list table.columns as column>
	
	public ${column.typeName} get${column.fieldName?cap_first}() {
		return ${column.fieldName};
	}
	public void set${column.fieldName?cap_first}(${column.typeName} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
	}
</#list>
}