package com.module.admin.code.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.comm.model.KvEntity;

/**
 * 类型[10java、20jsp、30其它文件]
 * @author yuejing
 * @date 2016年5月16日 下午5:52:03
 * @version V1.0.0
 */
public enum CodeTemplateType {
	JAVA	(10, "java"),
	JSP		(20, "jsp"),
	XML		(30, "xml"),
	OTHER	(100, "other"),
	;
	
	public static final String KEY = "code_template_type";

	private int code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private CodeTemplateType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		EnumSet<CodeTemplateType> set = EnumSet.allOf(CodeTemplateType.class);
		for(CodeTemplateType e : set){
			map.put(e.getCode(), e.getName());
			list.add(new KvEntity(String.valueOf(e.getCode()), e.getName()));
		}
	}

	/**
	 * 根据Code获取对应的汉字
	 * @param code
	 * @return
	 */
	public static String getText(Integer code) {
		return map.get(code);
	}
	
	/**
	 * 获取集合
	 * @return
	 */
	public static List<KvEntity> getList() {
		return list;
	}

	public int getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
