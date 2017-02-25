package ${packagePath}.handle;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.system.handle.model.ResponseFrame;

/**
 * ${tablename}的Handle
 * @author ${user}
 * @date ${dateTime}
 * @version ${version}
 */
@Component
public interface ${table.className}Handle {
	
	/**
	 * 保存或修改
	 * @param ${table.beanName}
	 * @return
	 */
	public ResponseFrame saveOrUpdate(Map<String, Object> map);
	
	/**
	 * 根据${table.firstKColumn.fieldName}获取对象
	 * @param ${table.firstKColumn.fieldName}
	 * @return
	 */
	public ResponseFrame get(Map<String, Object> map);

	/**
	 * 分页获取对象
	 * @param ${table.beanName}
	 * @return
	 */
	public ResponseFrame pageQuery(Map<String, Object> map);
	
	/**
	 * 根据${table.firstKColumn.fieldName}删除对象
	 * @param ${table.firstKColumn.fieldName}
	 * @return
	 */
	public ResponseFrame delete(Map<String, Object> map);
}