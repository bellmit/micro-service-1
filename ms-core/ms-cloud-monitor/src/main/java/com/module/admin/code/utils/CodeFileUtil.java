package com.module.admin.code.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.system.comm.utils.FrameFileUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class CodeFileUtil {

	private static Configuration configuration = null;

	/** 
	 * 根据模板生成相应的文件 
	 * @param map 保存数据的map 
	 * @param template 模板文件的地址 
	 * @param path 生成的文档输出地址 
	 * @return 
	 */
	public static synchronized File process(Map<?, ?> map, String template, String path) {
		if (null == map ) {
			throw new RuntimeException("数据不能为空");
		}
		if (null == template) {
			throw new RuntimeException("模板文件不能为空");
		}
		if (null == path) {
			throw new RuntimeException("输出路径不能为空");
		}
		int templatePathIndex = template.lastIndexOf("/");
		if(templatePathIndex == -1) {
			templatePathIndex = template.lastIndexOf(File.separator);
		}
		String templatePath = template.substring(0, templatePathIndex);
		FrameFileUtil.createDir(templatePath);
		String templateName = template.substring(templatePathIndex + 1, template.length());
		if (null == configuration) {
			//这里Configurantion对象不能有两个，否则多线程访问会报错
			configuration = new Configuration(Configuration.VERSION_2_3_23);
			configuration.setDefaultEncoding("utf-8");
			configuration.setClassicCompatible(true);
		}
		try {
			configuration.setDirectoryForTemplateLoading(new File(templatePath));
		} catch (Exception e) {
			configuration.setClassForTemplateLoading(CodeFileUtil.class, templatePath);
		}
		
		int pathIndex = path.lastIndexOf("/");
		if(pathIndex == -1) {
			pathIndex = path.lastIndexOf(File.separator);
		}
		String pathDir = path.substring(0, pathIndex);
		FrameFileUtil.createDir(pathDir);
		File file = new File(path);
		try {
			Template t = null;
			t = configuration.getTemplate(templateName);
			Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			// 这里w是一个输出地址，可以输出到任何位置，如控制台，网页等
			t.process(map, w);
			w.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return file;
	}
	
	/**
	 * 获取文件夹
	 * @param string	com.test.dev
	 * @return			com\test\dev
	 */
	public static String getFolder(String string) {
		Pattern p = Pattern.compile("[.]");
		Matcher m = p.matcher(string);
        String str = m.replaceAll(File.separator + File.separator);
        return str;
	}
}
