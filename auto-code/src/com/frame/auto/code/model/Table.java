package com.frame.auto.code.model;

import java.util.Arrays;

import com.frame.auto.code.javabean.StringUtil;

/**
 * <p>
 * Title: 代码自动生成工具-数据库表信息
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

public class Table {
	private String name;//表名
	private String tableComment;//表的注释
	private int columnNum;
	private Column[] columns;
	private Column firstKColumn;//主键

	int[] keyPos;
	private String className;// 用在java当中的类名称
	private String beanName;// bean的名称，等于className的首字母小写

	public Table() {
	}

	public int getColumnNum() {
		return columnNum;
	}

	/**
	 * 取普通列的数组
	 * 
	 * @return
	 */
	public Column[] getNColumn() {
		if (keyPos == null || keyPos.length < 1) {
			return this.columns;
		} else {
			Column[] r = new Column[this.columns.length - this.keyPos.length];
			for (int i = 0, j = 0; i < this.columns.length; i++) {
				if (Arrays.binarySearch(this.keyPos, i) < 0)
					r[j++] = this.columns[i];
			}
			return r;
		}
	}

	/**
	 * 取主键所在列
	 * 
	 * @return
	 */
	public Column[] getKColumn() {
		if (keyPos == null || keyPos.length < 1) {
			return new Column[0];
		} else {
			Column[] r = new Column[this.keyPos.length];
			for (int i = 0, j = 0; i < this.columns.length; i++) {
				if (Arrays.binarySearch(this.keyPos, i) >= 0)
					r[j++] = this.columns[i];
			}
			return r;
		}
	}

	public Column getFirstKColumn() {
		Column[] r = this.getKColumn();
		if (r != null && r.length > 0) {
			firstKColumn= r[0];
		} else {
			firstKColumn= new Column();
		}
		return firstKColumn;
	}

	public int[] getKeyPos() {
		return keyPos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setKeyPos(int[] keyPos) {
		this.keyPos = keyPos;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	public Column[] getColumns() {
		return columns;
	}

	public void setColumns(Column[] columns) {
		this.columns = columns;
		this.columnNum = this.columns.length;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getBeanName() {
		if (this.beanName == null) {
			if (this.className != null) {
				this.beanName = StringUtil.setFirstCharLowcase(this.className);
			}
		}
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	/**
	 * @param firstKColumn
	 *            the firstKColumn to set
	 */
	public void setFirstKColumn(Column firstKColumn) {
		this.firstKColumn = firstKColumn;
	}
	
	public boolean hasStatus(){
		for(int i=0;columns!=null&&i<columns.length;i++){
			if("status".equals(columns[i].getFieldName())){
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the tableComment
	 */
	public String getTableComment() {
		return tableComment;
	}

	/**
	 * @param tableComment the tableComment to set
	 */
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	
}