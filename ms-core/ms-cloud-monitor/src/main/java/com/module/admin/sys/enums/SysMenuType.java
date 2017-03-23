package com.module.admin.sys.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.comm.model.KvEntity;

/**
 * 菜单类型
 * @author duanbin
 * @date 2016年5月5日 下午12:25:45
 * @version V1.0.0
 */
public enum SysMenuType {
	/** 菜单 */
	MENU		(0, "菜单"),
	/** 功能 */
	FUNCTION	(1, "功能")
	;
	
	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private SysMenuType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		EnumSet<SysMenuType> set = EnumSet.allOf(SysMenuType.class);
		for(SysMenuType e : set){
			map.put(e.getCode(), e.getName());
			list.add(new KvEntity(e.getCode().toString(), e.getName()));
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

	public Integer getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
