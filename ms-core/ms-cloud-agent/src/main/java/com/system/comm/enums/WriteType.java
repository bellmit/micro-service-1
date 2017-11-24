package com.system.comm.enums;

/**
 * 写入body的类型
 * @author yuejing
 * @date 2016年1月21日 下午1:53:30
 * @version V1.0.0
 */
public enum WriteType {
	JSON		("application/json"	, "JSON类型"),
	TEXT		("text/html"		, "文本类型"),
	;
	
	private String code;
	private String name;

	private WriteType(String code, String name) {
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
