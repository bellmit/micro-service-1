package com.frame.auto.code.javabean;

import com.frame.auto.code.constants.Config;

/**
 * <p>Title: 代码自动生成工具</p>
 * <p>Description: 主要应用于oracle,sqlserver数据库数据基本操作</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author hellion
 * @version 1.0
 */

public class TableManagerFactory {
  public TableManagerFactory() {
  }

  /**
   * 根据配置取不同数据库的操作实列
   * @return
   */
  public static TableManager getTableManager() {
    TableManager r = null;
    if (Config.getInstance().getDataBaseType() == 0)
      r = OracleTableManager.getInstance();
    else if (Config.getInstance().getDataBaseType() == 1)
      r = SQLTableManager.getInstance();
    else if (Config.getInstance().getDataBaseType() == 2)
      r = MYSQLTableManager.getInstance();
    else
      r = OracleTableManager.getInstance();
    return r;
  }

}