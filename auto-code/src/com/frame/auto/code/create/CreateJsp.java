package com.frame.auto.code.create;

import java.io.File;
import java.util.HashMap;

import com.frame.auto.code.javabean.FreemarkerManager;
import com.frame.auto.code.javabean.Log;
import com.frame.auto.code.model.Table;

/**
 * <p>Java 代码自动生成工具</p>
 * <p>Description: 主要应用于oracle,sqlserver数据库数据基本操作</p>
 * @author yuejing
 * @version 1.0
 */
public class CreateJsp {

	private static CreateJsp _instance = new CreateJsp();

	private String dPath; // 目录路径

	private String className; // 类名称
	private String moduleName;// 模块名称
	private String packagePath; // 包路径

	private CreateJsp() {
	}

	public static CreateJsp getInstance() {
		return _instance;

	}

	/**
	 * 创建存放目录
	 * 
	 * @param path
	 * @param packagePath
	 */
	protected void createDictory() {
		Log.createDictory(dPath);
	}

	public int createJsp(String path, String packagePath, Table table) {
		int r = 0;
		
		this.className = table.getClassName();
		this.packagePath = packagePath;
		moduleName = this.packagePath.substring(this.packagePath
				.lastIndexOf('.') + 1);
		this.dPath = path + "/webapp/WEB-INF/view/"+moduleName+"/";
//		moduleName = StringUtil.setFirstCharUpcase(moduleName);
		
		this.createDictory(); // 创建目录
		this.createList(table);
//		this.createAdd(table);
		this.createEdit(table);
		//this.createSearch(table);
		//this.createOrder(table);
		return r;
	}

	protected void createList(Table table) {
		String s = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tablename", table.getName());
		map.put("table", table);
		map.put("moduleName", moduleName);
		s = FreemarkerManager.getInstance().process(map, "/jsp-list.ftl");
		Log.writeFile(s, this.dPath + File.separator 
				+ table.getBeanName() + "-manager.jsp", false);
	}

	protected void createSearch(Table table) {
		String s = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tablename", table.getName());
		map.put("table", table);
		s = FreemarkerManager.getInstance().process(map, "search.ftl");
		Log.writeFile(s, this.dPath + File.separator + "search"
				+ table.getBeanName() + ".xhtml", false);
	}
	
	protected void createEdit(Table table) {
		String s = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tablename", table.getName());
		map.put("table", table);
		map.put("moduleName", moduleName);
		s = FreemarkerManager.getInstance().process(map, "/jsp-edit.ftl");
		Log.writeFile(s, this.dPath + File.separator 
				+ table.getBeanName() + "-edit.jsp", false);
	}

}