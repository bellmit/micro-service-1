package com.frame.auto.code.javabean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import sun.jdbc.rowset.CachedRowSet;

import com.frame.auto.code.constants.Config;

/**
 * <p>Title:Java 代码自动生成工具</p>
 * <p>Description: 主要应用于oracle,sqlserver数据库数据基本操作</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author hellion
 * @version 1.0
 */

public class DBManager {

  private static DBManager _instance = new DBManager();

  private DBManager() {
  }

  public static DBManager getInstance() {
    return _instance;
  }

  /**
   * 直接取数据库链接。
   * @return
   */
  public Connection getConnection(String url, String name, String passwd) {
    Connection conn = null;
    try {
      Class.forName(TableManagerFactory.getTableManager().getJDBCDrive()).newInstance();
      conn = DriverManager.getConnection(url, name, passwd);
      conn.setAutoCommit(false);
    }
    catch (Exception e) {
      Log.log(e);
    }
    return conn;
  }

  /**
   *清除数据库链接
   * @param conn
   * @param ps
   * @param rs
   */
  public void cleanup(Connection conn, Statement ps, ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
        rs = null;
      }
    }
    catch (Exception e) {
      Log.log(e);
    }
    try {
      if (ps != null) {
        ps.close();
        ps = null;
      }
    }
    catch (Exception e) {
      Log.log(e);
    }
    try {
      if (conn != null && !conn.isClosed()) {
        conn.close();
        conn = null;
      }
    }
    catch (Exception e) {
      Log.log(e);
    }
  }

  /**
   * 执行查询操作
   * @param sql
   * @param values
   * @return
   */
  public CachedRowSet executeQuery(String sql, Object[] values) {
    CachedRowSet crs = null;
    PreparedStatement st = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      crs = new CachedRowSet();
      conn = getConnection(Config.getInstance().getDbConnectString(),
                           Config.getInstance().getDbUsername(),
                           Config.getInstance().getDbPasswd());
      st = conn.prepareStatement(sql);
      for (int i = 0; values != null && i < values.length; i++) {
        st.setObject(i + 1, values[i]);
      }
      rs = st.executeQuery();
      crs.populate(rs);
    }
    catch (Exception se) {
      Log.log("SQLException in DBManager.exceuteQuery, sql is :\r\n" +
              sql, Log.LEVEL_ERROR);
      Log.log(se);
    }
    finally {
      cleanup(conn, st, rs);
    }
    return crs;
  }

}