package com.frame.auto.code.javabean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.jdbc.rowset.CachedRowSet;

import com.frame.auto.code.constants.Config;
import com.frame.auto.code.constants.DataType;
import com.frame.auto.code.model.Column;
import com.frame.auto.code.model.Table;

/**
 * <p> Title:Java 代码自动生成工具 </p> <p> Description: 主要应用于oracle,sqlserver数据库数据基本操作
 * </p> <p> Copyright: Copyright (c) 2006 </p> <p> Company: </p>
 * 
 * @author hellion
 * @version 1.0
 */

public class OracleTableManager extends TableManager {

	private static OracleTableManager _instance = new OracleTableManager();

	private OracleTableManager() {
	}

	public static TableManager getInstance() {
		return _instance;
	}

	public Table[] getTableInfos(String[] names) {
		Table[] tables = new Table[names == null ? 0 : names.length];
		for (int i = 0; i < tables.length; i++) {
			tables[i] = this.getTableInfo(names[i]);
		}
		return tables;
	}

	public Table getTableInfo(String name) {
		Log.log("tablename:" + name, Log.LEVEL_DEBUG);
		Table table = new Table();
		table.setName(name);
		table.setClassName(this.buildClassName(name));

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
			String sql = "select c.index_name,c.column_name,data_type,data_precision,data_scale  "
					+ "from Sys.user_constraints i,Sys.all_ind_columns c,sys.user_tab_columns u  "
					+ "Where i.TABLE_NAME ='"
					+ name
					+ "' And  i.CONSTRAINT_TYPE='P' And i.CONSTRAINT_name=c.index_name  "
					+ "And c.column_name=u.column_name And u.table_name='"
					+ name + "' Order By index_name ";
			// Log.log("sql:" + sql);
			// 取表主键列表
			rs = st.executeQuery(sql);
			Map<String, String> keyMap = this.getKeys(rs);
			DBManager.getInstance().cleanup(null, null, rs);
			sql = "select * from sys.ALL_COL_COMMENTS Where table_name='"
					+ name + "'";
			rs = st.executeQuery(sql);
			Map<String, String> commentsMap = this.getComments(rs);
			DBManager.getInstance().cleanup(null, null, rs);

			sql = "select column_name,data_type,data_precision,data_scale,nullable from sys.user_tab_columns Where table_name='"
					+ name + "'";
			rs = st.executeQuery(sql);
			// Log.log("sql:" + sql);
			// select * from sys.ALL_COL_COMMENTS Where
			// table_name='S_PERMISSION'
			// select * from user_constraints where table_name='S_PERMISSION'
			String tmp = "";
			List<Column> c = new ArrayList<Column>();
			while (rs.next()) { // 取数据列列表
				tmp = rs.getString("column_name").trim();
				if (Config.getInstance().getExcColumn().indexOf(
						',' + (tmp.toLowerCase()) + ',') >= 0) {
					// 排除不需要生成的自动
					continue;
				}
				Log.log("columnName:" + tmp);
				Column column = new Column();
				column.setColumnName(tmp);
				column.setFieldName(this.buildFieldName(column.getColumnName()));

				tmp = rs.getString("data_type").toLowerCase().trim();
				if ("number".equals(tmp)) {
					if (rs.getObject("data_scale")==null|| rs.getInt("data_scale") > 0) {
						tmp = tmp + "f"; // 浮点数据
					} else if (rs.getObject("data_precision") == null
							|| rs.getInt("data_precision") > 8) {
						tmp = tmp + "b"; // long数据
					}
					
//					if (rs.getObject("data_scale")==null|| rs.getInt("data_scale") > 0) {
//						tmp = tmp + "b"; // long数据
//					} 
					
					
				}
				column.setColumnType(DataType.getInstance().getTypeMap(tmp));

				tmp = rs.getString("nullable");// 是否允许为空
				column.setNullAble("N".equals(tmp) ? 0 : 1);
				if (keyMap.get(column.getColumnName()) != null) {// 是否为主码
					column.setIsKey(1);
				}
				column.setComments(commentsMap.get(column.getColumnName()));
				// TODO 添加注释
				c.add(column);
			}
			table.setColumns((Column[]) c.toArray(new Column[c.size()]));
			table.setKeyPos(this.getKeyPos(table.getColumns()));

		} catch (Exception e) {
			Log.log(e);
		} finally {
			DBManager.getInstance().cleanup(conn, st, rs);
		}

		return table;
	}

	/**
	 * 取数据库所有表的列表
	 * 
	 * @return Stirng[] 表的名称
	 */
	public String[] getAllTableName() {
		String[] tables = null;
		String sql = "select * from sys.all_tables  Where owner = '"
				+ Config.getInstance().getDbUsername().toUpperCase()
				+ "' order by table_name";
		CachedRowSet crs = DBManager.getInstance().executeQuery(sql, null);
		try {
			tables = new String[crs.size()];
			int i = 0;
			while (crs.next()) {
				tables[i++] = crs.getString("table_name");
			}
		} catch (Exception e) {
			Log.log(e);
		}
		return tables;
	}

	/**
	 * 生产查询条件的字符串
	 * 
	 * @param tablename
	 * @return
	 */
	public String getSearchString(String tablename) {
		StringBuffer s = new StringBuffer(200);
		s.append("    String sql = \"Select ").append(tablename).append(
				".* From ").append(tablename).append(",( \"+\n");
		s.append("        \"Select * From( \"+\n");
		s.append("        \"Select row_id,Rownum rowseq  From( \"+\n");
		s.append("        \"select Rowid row_id  from ").append(tablename)
				.append("   \";\n");
		s.append("    for(int i =0;i<names.length;i++){\n");
		s.append("      if(i==0)sql+=\" where \";");
		s.append("      sql = sql +names[i]+\" \"+op[i]+\" ? \";\n");
		s
				.append("      if(i!=names.length-1)sql+=andOr==0?\" and \":\" or \";\n    }\n");
		s
				.append("    sql+=\"Order By \"+sortBy+\" \"+(sortType==0?\"asc\":\" Desc \")+\n");
		s.append("        \")Where Rownum<= \"+endNum+\n");
		s.append("        \" )Where rowseq>\"+beginNum+\")t Where t.row_id = ");
		s
				.append(tablename)
				.append(
						".Rowid Order By \"+sortBy+\" \"+(sortType==0?\"asc\":\" Desc \");\n");
		return s.toString();
	}

	/**
	 * 取jdbc驱动的名称
	 * 
	 * @return
	 */
	public String getJDBCDrive() {
		return "oracle.jdbc.driver.OracleDriver";
	}

	
	public static void main(String[] args) {
		String s = "number(6,0)";
		String ss = "number|,";
		Pattern pat = Pattern.compile(ss);
		Matcher mat = pat.matcher(s);
		System.out.println(mat.find());
	}
	
}