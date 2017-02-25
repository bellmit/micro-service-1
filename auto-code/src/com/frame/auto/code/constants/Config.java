package com.frame.auto.code.constants;

import javax.swing.JTextArea;

import java.io.*;

import com.frame.auto.code.javabean.Log;

import java.util.Properties;
import java.util.TreeSet;

/**
 * <p>
 * Title: 代码自动生成工具-配置信息类
 * </p>
 * <p>
 * Description: 主要应用于oracle,sqlserver数据库数据基本操作
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

public class Config {
	private static Config _instance;
	private String dbUsername;
	private String dbPasswd;
	// private String dbConnectString;
	private String dbIP;
	private String dbPort;
	private String dbSID;
	private String packagePath; // 包路径
	private String path; // 工作路径
	private int logLevel; // 日志记录等级
	private String sequence; // 数据库序列,用户数据写入自动参数Number行主码
	private int perNum = 20; // 默认每页数据行数
	private String[] tableList = new String[0];
	private JTextArea logArea = null;
	private int dataBaseType = 0; // 默认Oracle 0:oracle 1:sqlserver 2:mysql
	private String tablePrefix = "";// 表名的前缀，用于在生成className时候
	private String[] oldTableList = new String[0];
	private String[] allTableList = new String[0];
	private String excColumn = "";
	private String projectName = "";
	
	/**
	 * 配置文件名称
	 */
	public static String configName = "config.p";

	private Config() {
		this.loadFromFile("./" + configName);
	}

	public static Config getInstance() {
		if(_instance == null) {
			_instance = new Config();
		}
		return _instance;
	}

	public String getDbConnectString() {
		// return dbConnectString;
		String s = "";
		if (this.dataBaseType == 1) {
			s = "jdbc:jtds:sqlserver://" + this.dbIP + ":" + this.dbPort + "/"
					+ this.dbSID;
		} else if (this.dataBaseType == 2) {
			s = "jdbc:mysql://" + this.dbIP + ":" + this.dbPort + "/"
					+ this.dbSID;
		} else {
			s = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST="
					+ this.dbIP
					+ ")(PORT="
					+ this.dbPort
					+ ")))(CONNECT_DATA=(SID =" + this.dbSID + ")))";
		}

		return s;
	}

	public String getDbPasswd() {
		return dbPasswd;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public void setDbPasswd(String dbPasswd) {
		this.dbPasswd = dbPasswd;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public int getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public void setPath(String path) {
		if (path == null
				|| path.lastIndexOf(File.separator) != path.length() - 1)
			path += File.separator;
		this.path = path;
		Log.createDictory(Config.getInstance().getPath() + File.separator
				+ "log");
	}

	public String getPath() {
		return path;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public int getPerNum() {
		return perNum;
	}

	public void setPerNum(int perNum) {
		this.perNum = perNum;
	}

	public String[] getTableList() {
		return tableList;
	}

	public void setTableList(String[] tableList) {
		this.tableList = tableList;
		// 计算所有表
		TreeSet s = new TreeSet();
		for (int i = 0; i < this.oldTableList.length; i++) {
			s.add(this.oldTableList[i]);
		}
		for (int i = 0; i < this.tableList.length; i++) {
			s.add(this.tableList[i]);
		}
		this.allTableList = new String[s.size()];
		for (int i = 0; i < this.allTableList.length; i++) {
			this.allTableList[i] = (String) s.first();
			s.remove(this.allTableList[i]);
		}
	}

	public JTextArea getLogArea() {
		return logArea;
	}

	public void setLogArea(JTextArea logArea) {
		this.logArea = logArea;
	}

	public int getDataBaseType() {
		return dataBaseType;
	}

	public void setDataBaseType(int dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

	public void loadFromFile(String file) {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream(new File(file)));
			this.loadConfigFromProperties(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeToFile(String file) {
		try {
			Properties p = new Properties();
			this.setConfigToProperties(p);
			p.save(new FileOutputStream(new File(file)), "autoCode config");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadConfigFromProperties(Properties p) {
		this.dbUsername = p.getProperty("dbUsername");
		this.dbPasswd = p.getProperty("dbPasswd");
		this.dbIP = p.getProperty("dbIP");
		this.dbPort = p.getProperty("dbPort");
		this.dbSID = p.getProperty("dbSID");
		this.packagePath = p.getProperty("packagePath");
		this.path = p.getProperty("path");
		this.logLevel = Integer.parseInt(p.getProperty("logLevel", "0"));
		this.sequence = p.getProperty("sequence");
		this.perNum = Integer.parseInt(p.getProperty("perNum", "0"));
		this.dataBaseType = Integer
				.parseInt(p.getProperty("dataBaseType", "0"));
		if (p.getProperty("oldtablelist", "").length() > 0)
			this.oldTableList = p.getProperty("oldtablelist", "").split(",");// 已经生成的表
		this.allTableList = this.oldTableList;
		this.tablePrefix = p.getProperty("tablePrefix", "");
		this.setExcColumn(p.getProperty("excColumn", ""));
		this.projectName = p.getProperty("projectName", "");

	}

	public void setConfigToProperties(Properties p) {
		p.setProperty("dbUsername", this.dbUsername);
		p.setProperty("dbPasswd", this.dbPasswd);
		// p.setProperty("dbConnectString",this.dbConnectString);
		p.setProperty("dbIP", this.dbIP);
		p.setProperty("dbPort", this.dbPort);
		p.setProperty("dbSID", this.dbSID);
		p.setProperty("packagePath", this.packagePath);
		p.setProperty("path", this.path);
		p.setProperty("logLevel", String.valueOf(this.logLevel));
		p.setProperty("sequence", this.sequence);
		p.setProperty("perNum", String.valueOf(this.perNum));
		p.setProperty("dataBaseType", String.valueOf(this.dataBaseType));
		StringBuffer otable = new StringBuffer();
		for (int i = 0; i < this.allTableList.length; i++) {
			if (i != 0)
				otable.append(",");
			otable.append(this.allTableList[i]);
		}
		p.setProperty("oldtablelist", otable.toString());
		p.setProperty("tablePrefix", tablePrefix);
		p.setProperty("excColumn", this.excColumn);
		p.setProperty("projectName", this.projectName);
	}

	public String getDbIP() {
		return dbIP;
	}

	public String getDbPort() {
		return dbPort;
	}

	public String getDbSID() {
		return dbSID;
	}

	public void setDbSID(String dbSID) {
		this.dbSID = dbSID;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public void setDbIP(String dbIP) {
		this.dbIP = dbIP;
	}

	public String[] getAllTable() {
		return this.allTableList;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	/**
	 * @return the excColumn
	 */
	public String getExcColumn() {
		return excColumn;
	}

	/**
	 * @param excColumn
	 *            the excColumn to set
	 */
	public void setExcColumn(String excColumn) {
		this.excColumn = excColumn;
		if (this.excColumn == null) {
			this.excColumn = "";
		}
		if (this.excColumn.charAt(0) != ',') {
			this.excColumn = "," + this.excColumn;
		}
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}