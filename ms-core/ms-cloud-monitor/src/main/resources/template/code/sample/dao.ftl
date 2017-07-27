package ${packagePath}.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ${packagePath}.pojo.${table.className};

/**
 * ${tablename}的Dao
 * @author ${user}
 * @date ${dateTime}
 * @version ${version}
 */
public interface ${table.className}Dao {

	public abstract void save(${table.className} ${table.beanName});

	public abstract void update(${table.className} ${table.beanName});

	public abstract void delete(@Param("${table.firstKColumn.fieldName}")${table.firstKColumn.typeName} ${table.firstKColumn.fieldName});

	public abstract ${table.className} get(@Param("${table.firstKColumn.fieldName}")${table.firstKColumn.typeName} ${table.firstKColumn.fieldName});

	public abstract List<${table.className}> find${table.className}(${table.className} ${table.beanName});
	
	public abstract int find${table.className}Count(${table.className} ${table.beanName});
}