package com.system.template.core;

import java.io.File;
import java.util.Map;

public abstract class TemplateCore {

	public TemplateCore() {
		super();
	}

	/** 
	 * 根据模板生成相应的文件
	 * @param map 			保存数据的map
	 * @param templatePath 	模板文件的地址
	 * @param createPath	 生成的文档输出地址
	 * @return
	 */
	public abstract File process(Map<?, ?> map, String templatePath, String createPath);
}
