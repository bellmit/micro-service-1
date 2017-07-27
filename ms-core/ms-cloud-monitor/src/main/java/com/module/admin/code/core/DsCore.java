package com.module.admin.code.core;

import java.util.List;
import java.util.Map;

import com.module.comm.druid.DsUtil;

public abstract class DsCore {
	
	private DsUtil dsUtil;
	private String dbName;

	/**
	 * 获取数据源
	 * @param driverClass
	 * @param jdbcUrl
	 * @param username
	 * @param password
	 * @param dbName	数据库名称
	 * @return
	 */
	public DsUtil init(String driverClass, String jdbcUrl,
			String username, String password, String dbName) {
		this.dbName = dbName;
		Integer initialSize = 10;
		Integer maxIdle = 20;
		Integer minIdle = 5;
		dsUtil = new DsUtil();
		dsUtil.init(driverClass, jdbcUrl, username, password, initialSize, maxIdle, minIdle, true);
		return dsUtil;
	}
	
	/**
	 * 查询list集合
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryForList(String sql, Object ...params) {
		List<Map<String, Object>> data = dsUtil.queryForList(sql, params);
		return data;
	}
	public <T> List<T> query(String sql, Class<?> clazz, Object ...params) {
		return dsUtil.query(sql, clazz, params);
	}
	
	public <T>T get(String sql, Class<?> clazz, Object ...params) {
		return dsUtil.get(sql, clazz, params);
	}
	
	public void close() {
		dsUtil.close();
	}
	public String getDbName() {
		return this.dbName;
	}
}
