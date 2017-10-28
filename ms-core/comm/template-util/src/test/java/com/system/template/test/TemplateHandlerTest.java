package com.system.template.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.template.TemplateHandler;

public class TemplateHandlerTest {

	public static void main(String[] args) {
		TemplateHandler handler = TemplateHandler.createFreemarker();
		handler.addValue("name", "张三");

		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int j = 0; j < 10; j++) {
				map.put("col" + j, "行" + i + "列" + j);
			}
			list.add(map);
		}
		handler.addValue("list", list);

		String baseDir = "D:\\git\\micro-service\\ms-core\\comm\\template-util\\src\\test\\java\\";
		String templatePath = baseDir + "word.ftl";
		String createPath = baseDir + "test.doc";
		//生成word文档
		handler.generateWord(templatePath, createPath);

		templatePath = baseDir + "excel.ftl";
		createPath = baseDir + "test.xls";
		//生成excel表格
		handler.generateExcel(templatePath, createPath);
	}
}