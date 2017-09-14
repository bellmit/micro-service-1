package com.system.dao.utils;

import com.system.ds.DbContextHolder;

public class DbUtil {

	public static final String DB_MYSQL = "mysql";
	public static final String DB_ORACLE = "oracle";

	public static boolean isMysql() {
		return isType(DB_MYSQL);
	}

	public static boolean isOracle() {
		return isType(DB_ORACLE);
	}

	/**
	 * 根据传入的类型判断数据库类型
	 * @param dbType
	 * @return
	 */
	private static boolean isType(String dbType) {
		return dbType.equals(DbContextHolder.getDbType());
	}
}