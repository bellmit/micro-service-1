package ${packagePath}.service;

import java.util.List;

import org.springframework.stereotype.Component;

import ${packagePath}.pojo.${table.className};
import com.system.handle.model.ResponseFrame;

/**
 * ${tablename}的Service
 * @author ${user}
 * @date ${dateTime}
 * @version ${version}
 */
@Component
public interface ${table.className}Service {
	
	/**
	 * 保存或修改
	 * @param ${table.beanName}
	 * @return
	 */
	public ResponseFrame saveOrUpdate(${table.className} ${table.beanName});
	
	/**
	 * 根据${table.firstKColumn.fieldName}获取对象
	 * @param ${table.firstKColumn.fieldName}
	 * @return
	 */
	public ${table.className} get(${table.firstKColumn.typeName} ${table.firstKColumn.fieldName});

	/**
	 * 分页获取对象
	 * @param ${table.beanName}
	 * @return
	 */
	public ResponseFrame pageQuery(${table.className} ${table.beanName});
	
	/**
	 * 根据${table.firstKColumn.fieldName}删除对象
	 * @param ${table.firstKColumn.fieldName}
	 * @return
	 */
	public ResponseFrame delete(${table.firstKColumn.typeName} ${table.firstKColumn.fieldName});
}