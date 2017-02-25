package com.frame.auto.code.javabean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sun.jdbc.rowset.CachedRowSet;

import com.frame.auto.code.constants.Config;
import com.frame.auto.code.constants.DataType;
import com.frame.auto.code.model.Table;

/**
 * <p>Title:Java 代码自动生成工具</p>
 * <p>Description: 主要应用于SQLServer数据库数据基本操作</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author hellion
 * @version 1.0
 */

public class DB2TableManager
    extends TableManager {
  private static DB2TableManager _instance = new DB2TableManager();
  private DB2TableManager() {
  }

  public static TableManager getInstance() {
    return _instance;
  }

  public Table getTableInfo(String name) {
    Table table = new Table();
    table.setName(name);
    Config config = Config.getInstance();
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    try {
      conn = DBManager.getInstance().getConnection(config.getDbConnectString(),
          config.getDbUsername(), config.getDbPasswd());
      st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);

      rs = st.executeQuery(
          "SELECT * FROM SYSCAT.COLUMNS where tabname ='" + name +
          "' order by colno");
      int num = 0;
      while (rs.next()) {
        num++;
      }
      rs.beforeFirst();
      table.setColumnNum(num);
      String tmp = "";
      String[] c = new String[num];
      int[] t = new int[num];
      int i = 0;
      while (rs.next()) { //取数据列列表
        c[i] = rs.getString("COLNAME").toLowerCase().trim();
        tmp = rs.getString("TYPENAME").toLowerCase().trim();
        t[i++] = DataType.getInstance().getTypeMap(tmp);
      }
//      table.setColumnName(c);
//      table.setColumnType(t);
      table.setKeyPos(this.getKeyPos(c, this.getKey(rs)));
    }
    catch (Exception e) {
      Log.log(e);
    }
    finally {
      DBManager.getInstance().cleanup(conn, st, rs);
    }

    return table;
  }

  /**
   * @deprecated 需要修改
   */
  protected String[] getKey(ResultSet rs) throws SQLException {
    String[] r = null;
    int i = 0;
    rs.beforeFirst();
    while (rs.next()) {
      if ("PRI".equals(rs.getString("COLUMN_KEY")))
        i++;
    }
    r = new String[i];
    while (rs.previous()) {
      if ("PRI".equals(rs.getString("COLUMN_KEY")))
        r[--i] = rs.getString("COLUMN_NAME").toLowerCase();
    }
    return r;
  }

  /**
   * 取数据库所有表的列表
   * @return Stirng[] 表的名称
   * @deprecated 需要修改
   */
  public String[] getAllTableName() {
    String[] tables = null;
    String sql =
        "SELECT * FROM SYSCAT.TABLES where owner <>'SYSIBM' order by tabname";
    CachedRowSet crs = DBManager.getInstance().executeQuery(sql,
        new String[] {Config.getInstance().getDbSID()});
    try {
      tables = new String[crs.size()];
      int i = 0;
      while (crs.next()) {
        tables[i++] = crs.getString("tabname").toLowerCase();
      }
    }
    catch (Exception e) {
      Log.log(e);
    }
    return tables;
  }

  /**
   * 生产查询条件的字符串
   * @param tablename
   * @return
   */
  public String getSearchString(String tablename) {
    StringBuffer s = new StringBuffer(200);
    s.append("    String sql = \"Select *   from ").append(tablename).
        append("   \";\n");
    s.append("    for(int i =0;i<names.length;i++){\n");
    s.append("      if(i==0)sql+=\" where \";");
    s.append("      sql = sql +names[i]+\" \"+op[i]+\" ? \";\n");
    s.append(
        "      if(i!=names.length-1)sql+=andOr==0?\" and \":\" or \";\n    }\n");
    s.append(
        "    sql+=\"Order By \"+sortBy+\" \"+(sortType==0?\"asc\":\" Desc \")+\n");
    s.append("    \" limit \"+beginNum+\",\"+perNum;\n");
    return s.toString();
  }
  /**
   * 取jdbc驱动的名称
   * @return
   */
  public String getJDBCDrive(){
    return "com.ibm.db2.jdbc.net.DB2Driver";
  }

}