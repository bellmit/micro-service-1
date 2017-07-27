package com.module.admin.code.core.model;

import java.util.ArrayList;
import java.util.List;

import com.system.comm.utils.FrameStringUtil;

/**
 * <p>
 * 代码自动生成工具-数据库表信息
 * </p>
 * <p>
 * Description: 主要应用于oracle,sqlserver数据库数据基本操作
 * </p>
 * @author yuejing
 * @version 1.0
 */

public class Table {
	//表名
	private String name;
	//表的注释
	private String tableComment;
	private int columnNum;
	private List<Column> columns;
	//主键
	private Column firstKColumn;
	// 用在java当中的类名称
	private String className;
	// bean的名称，等于className的首字母小写
	private String beanName;
	//主键列
	private List<Column> kcolumns;
	//普通列
	private List<Column> ncolumns;

	public Table() {
	}

	public int getColumnNum() {
		return columnNum;
	}

	/**
	 * 取普通列的数组
	 * @return
	 */
	public List<Column> getNcolumns() {
		return ncolumns;
	}
	public void setNcolumns(List<Column> ncolumns) {
		this.ncolumns = ncolumns;
	}

	/**
	 * 取主键所在列
	 * 
	 * @return
	 */
	public List<Column> getKcolumns() {
		return this.kcolumns;
	}

	public void setKcolumns(List<Column> kcolumns) {
		this.kcolumns = kcolumns;
	}

	public Column getFirstKColumn() {
		return firstKColumn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
		this.columnNum = this.columns.size();
		
		List<Column> kcolumns = new ArrayList<Column>();
		List<Column> ncolumns = new ArrayList<Column>();
		for (Column column : columns) {
			if(column.getIsKey() == 1) {
				kcolumns.add(column);
			} else {
				ncolumns.add(column);
			}
		}
		//设置主键列集合
		setKcolumns(kcolumns);
		//设置普通列集合
		setNcolumns(ncolumns);
		
		//设置第一个主键
		List<Column> r = this.getKcolumns();
		if (r != null && r.size() > 0) {
			setFirstKColumn(r.get(0));
		} else {
			setFirstKColumn(new Column());
		}
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		if (className != null) {
			this.className = FrameStringUtil.setFirstCharUpcase(className);
			setBeanName(FrameStringUtil.setFirstCharLowcase(this.className));
		}
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		if(this.beanName == null) {
			this.beanName = beanName;
		}
	}

	/**
	 * @param firstKColumn
	 *            the firstKColumn to set
	 */
	public void setFirstKColumn(Column firstKColumn) {
		this.firstKColumn = firstKColumn;
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