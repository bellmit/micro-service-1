package com.module.comm.enums;


/**
 * 配置文件的key
 * @author yuejing
 * @date 2016年5月16日 下午5:52:03
 * @version V1.0.0
 */
public enum Env {
	CODE_TEMPLATE_PATH	("code.template.path", "模板存放路径"),
	CODE_SOURCE_PATH	("code.source.path", "源码存放路径"),
	;
	
	private String code;
	private String name;

	private Env(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}