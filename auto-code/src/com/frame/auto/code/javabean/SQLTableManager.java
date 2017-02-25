package com.frame.auto.code.javabean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public class SQLTableManager extends TableManager {
	private static SQLTableManager _instance = new SQLTableManager();

	private SQLTableManager() {
	}

	public static TableManager getInstance() {
		return _instance;
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
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT syscolumns.name column_name "
					+ "From sysobjects inner join syscolumns on sysobjects.id = syscolumns.id "
					+ "left outer join "
					+ "(select o.name sTableName,c.Name sColName From sysobjects o inner join sysindexes i on o.id = i.id and (i.status & 0X800) = 0X800 "
					+ "inner join syscolumns c1 on c1.colid <= i.keycnt and c1.id = o.id  "
					+ "inner join syscolumns c on o.id = c.id and c.name = index_col (o.name, i.indid, c1.colid)) pkElements "
					+ "on pkElements.sTableName = sysobjects.name and pkElements.sColName = syscolumns.name "
					+ "inner join sysobjects syscons on sysobjects.id=syscons.parent_obj and syscons.xtype='PK' "
					+ "where sysobjects.name in ('"+name+"') and sTableName is not null ";
			// 取表主键列表
			//Log.log("取表主键:"+sql);
			rs = st.executeQuery(sql);
			Map<String, String> keyMap = this.getKeys(rs);
			DBManager.getInstance().cleanup(null, null, rs);

			// 取注释
			sql = "select o.name tableName,c.name  column_name,p.value  comments "
					+ "FROM sys.extended_properties p join sys.objects o on o.object_id=p.major_id "
					+ "join sys.columns c on   p.major_id=c.object_id      "
					+ "and   p.minor_id=c.column_id "
					+ "and o.name ='"
					+ name
					+ "'";
			//Log.log("取注释:"+sql);
			
			rs = st.executeQuery(sql);
			Map<String, String> commentsMap = this.getComments(rs);
			DBManager.getInstance().cleanup(null, null, rs);
			sql = "select c.name column_name,ty.name data_type, c.length,c.xscale,isnullable nullable "
					+ "from dbo.syscolumns c,dbo.sysobjects t,dbo.systypes ty where c.id = t.id "
					+ " and c.xtype=ty.xtype and ty.xtype = ty.xusertype and t.name='"
					+ name + "' order by colid";
			rs = st.executeQuery(sql);
			
			//Log.log("取类型:"+sql);
			int num = 0;
			String tmp = "";
			List<Column> c = new ArrayList<Column>();
			int i = 0;
			while (rs.next()) { // 取数据列列表
				tmp = rs.getString("column_name").trim();
				if (Config.getInstance().getExcColumn().indexOf(
						',' + (tmp.toLowerCase()) + ',') >= 0) {
					// 排除不需要生成的自动
					continue;
				}
				Column column = new Column();
				column.setColumnName(tmp);
				column
						.setFieldName(this.buildFieldName(column
								.getColumnName()));
				tmp = rs.getString("data_type").toLowerCase().trim();
				if ("numeric".equals(tmp)) {
					if (rs.getObject("xscale")==null|| rs.getInt("xscale") > 0) {
						tmp = tmp + "f"; // 浮点数据
					} else if (rs.getObject("length") == null
							|| rs.getInt("length") > 8) {
						tmp = tmp + "b"; // long数据
					}
				}
				column.setColumnType(DataType.getInstance().getTypeMap(tmp));

				tmp = rs.getString("nullable");// 是否允许为空
				column.setNullAble("0".equals(tmp) ? 0 : 1);
				if (keyMap.get(column.getColumnName()) != null) {// 是否为主码
					column.setIsKey(1);
				}
				String cmm = commentsMap.get(column.getColumnName());
				if(cmm!=null){
					cmm = new String(cmm.getBytes("iso8859-1"),"GBK");
				}
				column.setComments(cmm);
				// 添加注释
				Log.log("columnName:" + column.getColumnName()+","+column.getComments());
				
				c.add(column);
			}
			DBManager.getInstance().cleanup(null, null, rs);
			// 取表主键列表
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
		String sql = "select name table_name from dbo.sysobjects where xtype='u' order by name ";
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
		s.append("    String sql = \"select c.* from ( \"+\n");
		s.append("        \"select top \"+perNum+\" t.* from (\" +\n");
		s.append("        \"select top \"+endNum+\" * from ").append(tablename)
				.append("   \";\n");
		s.append("    for (int i = 0; i < names.length; i++) {\n");
		s.append("      if (i == 0) {\n");
		s.append("        sql += \" where \";\n");
		s.append("      }\n");
		s.append("      sql = sql + names[i] + \" \" + op[i] + \" ? \";\n");
		s.append("      if (i != names.length - 1) {\n");
		s.append("        sql += andOr == 0 ? \" and \" : \" or \";\n");
		s.append("      }\n");
		s.append("    }\n");
		s
				.append("    sql += \"Order By \" + sortBy + \" \" + (sortType == 0 ? \"asc\" : \" Desc \") +\n");
		s
				.append("        \") t order by t.\"+sortBy+\" \"+(sortType == 1 ? \"asc\" : \" Desc \")+\n");
		s.append("        \")c order by c.\"  + sortBy + \" \" +\n");
		s.append("        (sortType == 0 ? \"asc\" : \" Desc \");");
		return s.toString();
	}

	/**
	 * 取jdbc驱动的名称
	 * 
	 * @return
	 */
	public String getJDBCDrive() {
		return "net.sourceforge.jtds.jdbc.Driver";
	}

}