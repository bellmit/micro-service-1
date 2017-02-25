package com.frame.auto.code.javabean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sun.jdbc.rowset.CachedRowSet;

import com.frame.auto.code.constants.Config;
import com.frame.auto.code.constants.DataType;
import com.frame.auto.code.model.Column;
import com.frame.auto.code.model.Table;

/**
 * <p>
 * Title:Java 代码自动生成工具
 * </p>
 * <p>
 * Description: 主要应用于SQLServer数据库数据基本操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author hellion
 * @version 1.0
 */

public class MYSQLTableManager extends TableManager {
	private static MYSQLTableManager _instance = new MYSQLTableManager();

	private MYSQLTableManager() {
	}

	public static TableManager getInstance() {
		return _instance;
	}

	public Table getTableInfo(String name) {
		Table table = new Table();
		table.setName(name);
		table.setClassName(this.buildClassName(name));
		table.setTableComment(this.getTableComment(name));
		
		Config config = Config.getInstance();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection(
					config.getDbConnectString(), config.getDbUsername(),
					config.getDbPasswd());
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = st
					.executeQuery("select * from information_schema.columns where table_schema='"
							+ Config.getInstance().getDbSID()
							+ "' and table_name='"
							+ name
							+ "' order by ordinal_position");
			String tmp = "";
			List<Column> c = new ArrayList<Column>();

			while (rs.next()) { // 取数据列列表
				Column column=new Column();
				column.setColumnName(rs.getString("COLUMN_NAME").toLowerCase().trim());//名称
				column.setFieldName(this.buildFieldName(column.getColumnName()));
				column.setComments(rs.getString("COLUMN_COMMENT"));
				
				tmp = rs.getString("DATA_TYPE").toLowerCase().trim();//类型
				column.setColumnType(DataType.getInstance().getTypeMap(tmp));
				if (column.getColumnType() == DataType.DATA_FLOAT
						|| column.getColumnType() == DataType.DATA_INT
						|| column.getColumnType() == DataType.DATA_LONG) {//长度
					column.setLength(rs.getInt("NUMERIC_PRECISION"));
				} else if (column.getColumnType() == DataType.DATA_String) {
					column.setLength(rs.getInt("CHARACTER_MAXIMUM_LENGTH"));
				}
				tmp=rs.getString("IS_NULLABLE");//是否允许为空
				column.setNullAble("NO".equals(tmp)?0:1);
				if ("PRI".equalsIgnoreCase(rs.getString("COLUMN_KEY"))) {//是否为主键
					column.setIsKey(1);
				}
				if ("auto_increment".equals(rs.getString("EXTRA"))) {//是否自动增长
					column.setAutoGeneral(1);
				}
				c.add(column);
			}
			table.setColumns((Column[])c.toArray(new Column[c.size()]));
			table.setKeyPos(this.getKeyPos(table.getColumns()));
		} catch (Exception e) {
			Log.log(e);
			e.printStackTrace();
		} finally {
			DBManager.getInstance().cleanup(conn, st, rs);
		}
		return table;
	}

	/**
	 * 取表的注释
	 * @param tableName
	 * @return
	 */
	public String getTableComment(String tableName) {
		String sql = "select * from information_schema.tables where table_schema=? " +
				"and TABLE_NAME=?  ";
		CachedRowSet crs = DBManager.getInstance().executeQuery(sql,
				new String[] { Config.getInstance().getDbSID(),tableName });
		try {
			if (crs.next()) {
				return  crs.getString("TABLE_COMMENT");
			}
		} catch (Exception e) {
			Log.log(e);
		}
		return "";
	}

	/**
	 * 取数据库所有表的列表
	 * 
	 * @return Stirng[] 表的名称
	 */
	public String[] getAllTableName() {
		String[] tables = null;
		String sql = "select * from information_schema.tables where table_schema=? order by TABLE_NAME";
		CachedRowSet crs = DBManager.getInstance().executeQuery(sql,
				new String[] { Config.getInstance().getDbSID() });
		try {
			tables = new String[crs.size()];
			int i = 0;
			while (crs.next()) {
				tables[i++] = crs.getString("TABLE_NAME").toLowerCase();
			}
		} catch (Exception e) {
			Log.log(e);
		}
		return tables;
	}

	/**
	 * 取jdbc驱动的名称
	 * 
	 * @return
	 */
	public String getJDBCDrive() {
		return "org.gjt.mm.mysql.Driver";
	}

}