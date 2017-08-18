package ${packagePath}.service;

import org.springframework.stereotype.Component;

import ${packagePath}.pojo.${table.className};
import com.system.handle.model.ResponseFrame;

/**
 * ${tablename} Service
 * @author ${user}
 * @date ${dateTime}
 * @version ${version}
 */
@Component
public interface ${table.className}Service {
	
	/**
	 * saveOrUpdate
	 * @param ${table.beanName}
	 * @return
	 */
	public ResponseFrame saveOrUpdate(${table.className} ${table.beanName});
	
	/**
	 * ${table.firstKColumn.fieldName} get ${table.className}
	 * @param ${table.firstKColumn.fieldName}
	 * @return
	 */
	public ${table.className} get(${table.firstKColumn.typeName} ${table.firstKColumn.fieldName});

	/**
	 * pageQuery ${table.className}
	 * @param ${table.beanName}
	 * @return
	 */
	public ResponseFrame pageQuery(${table.className} ${table.beanName});
	
	/**
	 * ${table.firstKColumn.fieldName} delete ${table.className}
	 * @param ${table.firstKColumn.fieldName}
	 * @return
	 */
	public ResponseFrame delete(${table.firstKColumn.typeName} ${table.firstKColumn.fieldName});
}