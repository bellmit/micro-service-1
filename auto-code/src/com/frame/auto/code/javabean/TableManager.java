package com.frame.auto.code.javabean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.frame.auto.code.constants.Config;
import com.frame.auto.code.model.Column;
import com.frame.auto.code.model.Table;

/**
 * <p>
 * Title:Java 代码自动生成工具
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

public class TableManager {
	
	public Table[] getTableInfos(String[] names){
		return null;
	}

	public Table getTableInfo(String name) {
		return null;
	}

	/**
	 * 取主键
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	protected String[] getKey(ResultSet rs) throws SQLException {
		String[] r = null;
		String indexName = "";
		int i = 0;
		while (rs.next()) {
			i++;
		}
		r = new String[i];
		while (rs.previous()) {
			r[--i] = rs.getString("column_name").toLowerCase();
		}
		return r;
	}

	protected Map<String, String> getKeys(ResultSet rs) throws SQLException {
		Map<String, String> r = new HashMap<String, String>();
		int seq=0;
		String tmp ="";
		while (rs.next()) {
			seq++;
			tmp=rs.getString("column_name");
			Log.log("columnName in keys:"+tmp);
			r.put(tmp, String.valueOf(seq));
		}
		return r;
	}
	
	
	protected Map<String, String> getComments(ResultSet rs) throws SQLException {
		Map<String, String> r = new HashMap<String, String>();
		String col ="";
		String comments="";
		while (rs.next()) {
			col=rs.getString("column_name");
			comments=rs.getString("comments");
			r.put(col, comments);
		}
		return r;
	}

	/**
	 * 取主键所在的位置
	 * 
	 * @param cs
	 * @param uniques
	 * @return
	 */
	protected int[] getKeyPos(String[] cs, String[] uniques) {
		if (cs == null || uniques == null)
			return new int[0];
		int[] pos = new int[uniques.length];
		for (int i = 0; i < uniques.length; i++) {
			for (int j = 0; j < cs.length; j++) {
				if (cs[j].equals(uniques[i])) {
					pos[i] = j;
					break;
				}
			}
		}
		return pos;
	}

	/**
	 * 取主键的位置
	 * 
	 * @param cs
	 * @return
	 */
	protected int[] getKeyPos(Column[] cs) {
		if (cs == null)
			return new int[0];
		ArrayList<Integer> r = new ArrayList<Integer>(2);
		for (int i = 0; i < cs.length; i++) {
			if (cs[i].getIsKey() == 1)
				r.add(i);
		}
		int[] pos = new int[r.size()];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = r.get(i);
		}
		return pos;
	}

	/**
	 * 取数据库所有表的列表
	 * 
	 * @return Stirng[] 表的名称
	 */
	public String[] getAllTableName() {
		return null;
	}

	/**
	 * 生产查询条件的字符串
	 * 
	 * @param tablename
	 * @return
	 */
	public String getSearchString(String tablename) {
		return null;
	}

	/**
	 * 取jdbc驱动的名称
	 * 
	 * @return
	 */
	public String getJDBCDrive() {
		return "";
	}

	public String buildClassName(String tableName ){
		if(StringUtil.isEmpty(tableName)){
			return "";
		}
		tableName=tableName.toLowerCase();
		String className="";
		
		//转换为帕斯卡（pascal）命名，首字母大写，每一个逻辑断点都有一个大写字母来标记
		//去掉一个前缀
		String prefix = Config.getInstance().getTablePrefix();
		if(!StringUtil.isEmpty(prefix)){
			if(tableName.indexOf(prefix)==0){
				tableName = tableName.substring(prefix.length());
			}
		}
		
		String[] words = tableName.split("[_\\.-]");
		for(int i=0;i<words.length;i++){
			if(i==0){
				className=StringUtil.setFirstCharUpcase(words[i]);
			}
			else{
				className+=StringUtil.setFirstCharUpcase(words[i]);
			}
		}
		return className;
	}
	/**
	 * 根据列名创建java方法的field名称
	 * @param columnName
	 * @return
	 */
	public String buildFieldName(String columnName){
		if(StringUtil.isEmpty(columnName)){
			return "";
		}
		columnName=columnName.toLowerCase();
		String fieldName="";
		
		//转换为帕斯卡（pascal）命名，首字母大写，每一个逻辑断点都有一个大写字母来标记
		
		String[] words = columnName.split("[_\\.-]");
		for(int i=0;i<words.length;i++){
			if(i==0){
				fieldName=words[i];
			}
			else{
				fieldName+=StringUtil.setFirstCharUpcase(words[i]);
			}
		}
		return fieldName;
	}
	
}