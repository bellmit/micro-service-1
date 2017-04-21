package com.frame.auto.code.create;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.frame.auto.code.FrameConfig;
import com.frame.auto.code.javabean.FreemarkerManager;
import com.frame.auto.code.javabean.Log;
import com.frame.auto.code.model.Table;

/**
 * Title: 代码自动生成工具-生成对应的JAVA
 * Description:
 * 主要应用于oracle,sqlserver数据库数据基本操作
 * @author yuejing
 * @version 1.0
 */
public class CreateJava {

	private static CreateJava _instance = new CreateJava();
	private String dPath; // 目录路径
	//private String resourcePath;// 资源文件路径
	private String packagePath; // 包路径
	private String moduleName;// 模块名称
	private Date currentDate;// 当前日期

	private CreateJava() {
		currentDate = new Date();
	}

	public static CreateJava getInstance() {
		return _instance;
	}

	public void createJava(String path, String packagePath, Table table) {
		System.out.println("table:" + table);
		if (table != null) {
			System.out.println("name:" + table.getName());
		}
		this.dPath = path + "src/java/";
		this.dPath += packagePath.replaceAll("\\.", "/") + "/";
		//this.resourcePath = path + "src/resources/";
		this.packagePath = packagePath;
		moduleName = this.packagePath.substring(this.packagePath
				.lastIndexOf('.') + 1);

		this.createDictory(); // 创建目录
		
		this.createPojo(table);
		this.createDao(table);
		this.createDaoXml(table);
		this.createService(table);
		this.createServiceImpl(table);
		//this.createHandle(table);
		//this.createHandleImpl(table);
		this.createController(table);
	}
	

	/**
	 * 创建存放目录
	 * 
	 * @param path
	 * @param packagePath
	 */
	protected void createDictory() {
		Log.createDictory(dPath);
		Log.createDictory(dPath + "pojo/");
		Log.createDictory(dPath + "dao/");
		Log.createDictory(dPath + "dao/mysql/");
		Log.createDictory(dPath + "dao/oracle/");
		Log.createDictory(dPath + "service/");
		Log.createDictory(dPath + "service/impl/");
		//Log.createDictory(dPath + "handle/");
		//Log.createDictory(dPath + "handle/impl/");
		Log.createDictory(dPath + "controller/");
		//Log.createDictory(this.resourcePath);
	}
	
	/**
	 * 获取描叙信息
	 * @param table
	 * @return
	 */
	private Map<String, Object> getDescMap(Table table) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", FrameConfig.DESC_USER);
		map.put("dateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("version", "V1.0.0");
		map.put("tablename", table.getName());
		map.put("className", table.getClassName());
		map.put("packagePath", packagePath);
		map.put("table", table);
		map.put("moduleName", moduleName);
		map.put("currentDate", currentDate);
		return map;
	}
	
	public void createPojo(Table table) {
		String s = FreemarkerManager.getInstance().process(getDescMap(table), "/pojo.ftl");
		Log.writeFile(s, this.dPath + "pojo/" + table.getClassName()
				+ ".java", false);
	}
	public void createDao(Table table) {
		String s = FreemarkerManager.getInstance().process(getDescMap(table), "/dao.ftl");
		Log.writeFile(s, this.dPath + "dao/" + table.getClassName()
				+ "Dao.java", false);
	}
	public void createDaoXml(Table table) {
		Map<String, Object> map = getDescMap(table);
		String s = FreemarkerManager.getInstance().process(map, "/mysql.dao.xml.ftl");
		Log.writeFile(s, this.dPath + "dao/mysql/" + table.getClassName()
				+ "Dao.xml", false);
		
		s = FreemarkerManager.getInstance().process(map, "/oracle.dao.xml.ftl");
		Log.writeFile(s, this.dPath + "dao/oracle/" + table.getClassName()
				+ "Dao.xml", false);
	}
	public void createService(Table table) {
		String s = FreemarkerManager.getInstance().process(getDescMap(table), "/service.ftl");
		Log.writeFile(s, this.dPath + "service/" + table.getClassName()
				+ "Service.java", false);
	}
	public void createServiceImpl(Table table) {
		String s = FreemarkerManager.getInstance().process(getDescMap(table), "/serviceImpl.ftl");
		Log.writeFile(s, this.dPath + "service/impl/" + table.getClassName()
				+ "ServiceImpl.java", false);
	}
	
	public void createController(Table table) {
		String s = FreemarkerManager.getInstance().process(getDescMap(table), "/controller.ftl");
		Log.writeFile(s, this.dPath + "controller/" + table.getClassName()
				+ "Controller.java", false);
	}
	public void createHandle(Table table) {
		String s = FreemarkerManager.getInstance().process(getDescMap(table), "/handle.ftl");
		Log.writeFile(s, this.dPath + "handle/" + table.getClassName()
				+ "Handle.java", false);
	}
	public void createHandleImpl(Table table) {
		String s = FreemarkerManager.getInstance().process(getDescMap(table), "/handleImpl.ftl");
		Log.writeFile(s, this.dPath + "handle/impl/" + table.getClassName()
				+ "HandleImpl.java", false);
	}
}