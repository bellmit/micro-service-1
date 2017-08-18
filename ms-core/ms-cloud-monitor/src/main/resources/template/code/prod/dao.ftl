package ${packagePath}.dao;

import org.springframework.stereotype.Component;

import ${packagePath}.pojo.${table.className};
import com.system.comm.model.Page;
import com.system.dao.BaseDao;
import com.system.dao.sql.QuerySql;

/**
 * ${tablename} Dao
 * @author ${user}
 * @date ${dateTime}
 * @version ${version}
 */
@Component
public class ${table.className}Dao extends BaseDao {

	public void save(${table.className} ${table.beanName}) {
		super.save(${table.beanName});
	}
	
	public void update(${table.className} ${table.beanName}) {
		super.update(${table.beanName});
	}

	public ${table.className} get(${table.firstKColumn.typeName} ${table.firstKColumn.fieldName}) {
		return super.get(${table.className}.class, ${table.firstKColumn.fieldName});
	}

	public Page<${table.className}> pageQuery(${table.className} ${table.beanName}) {
		QuerySql sql = new QuerySql(${table.className}.class);
		//sql.addCondLike("name", ${table.beanName}.getName());
		Page<${table.className}> page = super.pageQuery(sql.getSql(), ${table.beanName}.getPage(), ${table.beanName}.getSize(), ${table.className}.class, sql.getParams());
		return page;
	}
	
	public void delete(${table.firstKColumn.typeName} ${table.firstKColumn.fieldName}) {
		super.delete(${table.firstKColumn.typeName}.class, ${table.firstKColumn.fieldName});
	}
}