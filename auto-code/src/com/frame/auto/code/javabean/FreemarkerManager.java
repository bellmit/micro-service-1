package com.frame.auto.code.javabean;

import java.io.File;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import com.frame.auto.code.FrameConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 代码自动生成工具生成-对应数据库表的操作的适配器类
 * @author yuejing
 * @version 1.0
 */
public class FreemarkerManager {
	private static FreemarkerManager _instance = new FreemarkerManager();
	private Configuration cfg;
	private FreemarkerManager() {
		cfg=this.initConfig(FrameConfig.FREEMARKER_DIR_PATH);
	}

	public static FreemarkerManager getInstance() {
		return _instance;
	}

	/**
	 * 用统一的方法初始化配置信息
	 * 
	 * @param configPath
	 * @return
	 */
	private Configuration initConfig(String configPath) {
		Configuration ccfg = new Configuration();
		try {
			ccfg.setDirectoryForTemplateLoading(new File(configPath));
			ccfg.setNumberFormat("#");
			ccfg.setEncoding(Locale.getDefault(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ccfg;
	}


	/**
	 * 根据map里面的数据和模板生成最终内容(根目录为：WEB-INF\templet)
	 * 
	 * @param map
	 * @param templet
	 * @return
	 */
	public String process(Map<?, ?> map, String templet) {
		return this.process(cfg, map, templet);
	}

	/**
	 * 根据map里面的数据和模板生成最终内容
	 * 
	 * @param map
	 * @param templet
	 * @return
	 */
	public String process(Configuration ccfg, Map<?, ?> map, String templet) {
		String r = "";
		StringWriter out = new StringWriter();
		try {
			Template t = ccfg.getTemplate(templet);
			t.process(map, out);
			r = out.getBuffer().toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return r;
	}

	/**
	 * 根据目录和模板名称生成内容
	 * 
	 * @param map
	 * @param dic
	 * @param templet
	 * @return
	 */
	public String process(Map<?, ?> map, String dic, String templet) {
		String r = "";
		Configuration cfg2 =  this.initConfig(dic);
		r=this.process(cfg2, map, templet);
		return r;
	}

}
