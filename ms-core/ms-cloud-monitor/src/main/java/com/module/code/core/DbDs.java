package com.module.code.core;

import java.util.List;

import com.module.code.pojo.Column;
import com.module.code.pojo.Table;
import com.module.comm.druid.DsUtil;

public interface DbDs {
	
	public abstract DsUtil init(String driverClass, String jdbcUrl,
			String username, String password, String dbName);

	/**
	 * 获取所有表名
	 * @return
	 */
	public abstract List<String> getAllTableName();
	/**
	 * 获取表注释
	 * @param tableName
	 * @return
	 */
	public abstract String getTableComment(String tableName);

	/**
	 * 获取表的所有列
	 * @param tableName
	 * @return
	 */
	public abstract List<Column> queryTableColumns(String tableName);
	/**
	 * 获取表的信息
	 * @param tableName
	 * @return
	 */
	public abstract Table getTable(String tableName);
	
	public abstract void close();
}
