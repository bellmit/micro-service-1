package com.frame.sys.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.system.comm.model.KvEntity;

/**
 * 字典类型code
 * @author 岳静
 * @date 2016年3月8日 下午8:18:33 
 * @version V1.0
 */
public enum SysDictTypeCode {
	ADMIN_INFO				("ADMIN_INFO", "超级管理员的常用配置"),
	ADDRESS					("ADDRESS", "地址信息"),
	;
	
	private String code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<String, String> map = new HashMap<String, String>();

	private SysDictTypeCode(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		Set<SysDictTypeCode> set = EnumSet.allOf(SysDictTypeCode.class);
		for(SysDictTypeCode e : set){
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

	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}