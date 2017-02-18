package com.system.dao.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.ibatis.type.Alias;

import com.system.comm.utils.FrameStringUtil;
import com.system.dao.data.ColumnData;

/**
 * 拼接sql的工具类<br>
 * InsertSql / DeleteSql / UpdateSql / QuerySql 的使用：
 * public static void main(String[] args) {
		Test t = new Test();
		t.setId("23");
		t.setName("test1");
		Sql sql = new InsertSql(t);
		System.out.println(sql.getSql());
		
		t.setAddUser("");
		sql = new UpdateSql(t);
		System.out.println(sql.getSql());

		sql = new DeleteSql(Test.class, "1");
		System.out.println(sql.getSql());
		
		sql = new QuerySql(Test.class, "id");
		System.out.println(sql.getSql());
	}
 * @author yuejing
 * @date 2013-11-20 下午11:51:53
 * @version V1.0.0
 */
public abstract class Sql {

	protected String sql;
	//主键值
	protected Object pkValue;
	protected String orderby;
	protected List<String> conds;
	protected List<Object> values;
	private Class<?> clazz;

	public Sql(Class<?> clazz) {
		this.clazz = clazz;
		init();
	}
	public Sql(Class<?> clazz, String sql) {
		this.clazz = clazz;
		this.sql = sql;
		init();
	}
	
	public Sql(Class<?> clazz, String sql, Object pkValue) {
		this.clazz = clazz;
		this.sql = sql;
		this.pkValue = pkValue;
		init();
	}
	
	public Sql(Class<?> clazz, String sql, String orderby) {
		this.clazz = clazz;
		this.sql = sql;
		this.orderby = orderby;
		init();
	}

	/**
	 * 添加参数值
	 * @param cond
	 * @param value
	 */
	public void addValue(Object value) {
		if(values == null) {
			values = new ArrayList<Object>();
		}
		values.add(value);
	}

	/**
	 * 获取拼接的完整的SQL
	 * @return
	 */
	public abstract String getSql();

	/**
	 * 获取参数的值集合
	 * @return
	 */
	public Object[] getParams() {
		return values == null ? null : values.toArray();
	}
	
	/**
	 * 获取表名
	 * @param clazz
	 * @return
	 */
	public String getTableName(Class<?> cls) {
		Alias table = cls.getAnnotation(Alias.class);
		return FrameStringUtil.setUpcaseConvertUnderline(table.value());
	}
	
	/**
	 * 获取实体名
	 * @param clazz
	 * @return
	 */
	public String getBeanName(Class<?> cls) {
		Alias table = cls.getAnnotation(Alias.class);
		return table.value();
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	
	/**
	 * 初始信息
	 */
	private void init() {
		//获取所有属性
		ColumnData.addColumns(clazz);
	}

	/**
	 * 根据类属性获取数据库列名
	 * @param field
	 * @return
	 */
	public String getColumn(String field) {
		return ColumnData.getColumns(clazz).get(field);
	}

	/**
	 * 获取该实体的所有列名
	 * @return
	 */
	public String getColumns() {
		return getColumns(null);
	}
	/**
	 * 获取列名
	 * @param prefix 前缀，比如a.name  a为传入的前缀
	 * @return
	 */
	public String getColumns(String prefix) {
		if(FrameStringUtil.isNotEmpty(prefix)) {
			prefix += ".";
		} else {
			prefix = "";
		}
		StringBuffer sqlStr = new StringBuffer();
		Iterator<Entry<String, String>> entryKeyIterator = ColumnData.getColumns(clazz).entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<String, String> e = entryKeyIterator.next();
			sqlStr.append(prefix).append(e.getValue()).append(" as ").append(e.getKey()).append(",");
		}
		sqlStr.setCharAt(sqlStr.length() - 1, ' ');
		return sqlStr.toString();
	}
	
	/**
	 * 获取查询表所有记录的SQL
	 * @return
	 */
	public String getQueryAll() {
		StringBuffer sqlStr = new StringBuffer("select ");
		sqlStr.append(getColumns()).append(" from ").append(getTableName(clazz));
		return sqlStr.toString();
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
}