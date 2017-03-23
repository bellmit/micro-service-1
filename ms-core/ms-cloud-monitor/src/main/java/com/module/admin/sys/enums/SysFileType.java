package com.module.admin.sys.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.comm.model.KvEntity;

/**
 * 类型[10项目]
 * @author yuejing
 * @date 2016年5月16日 下午3:52:03
 * @version V1.0.0
 */
public enum SysFileType {
	/** 10 项目 */
	PRJ			(10, "项目",				"omd/prj"),
	;
	
	public static final String KEY = "sys_file_type";

	private int code;
	private String name;
	//文件夹名称
	private String alias;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, SysFileType> map = new HashMap<Integer, SysFileType>();

	private SysFileType(Integer code, String name, String alias) {
		this.code = code;
		this.name = name;
		this.alias = alias;
	}
	
	static {
		EnumSet<SysFileType> set = EnumSet.allOf(SysFileType.class);
		for(SysFileType e : set){
			map.put(e.getCode(), e);
			list.add(new KvEntity(String.valueOf(e.getCode()), e.getName()));
		}
	}

	/**
	 * 根据Code获取对应的汉字
	 * @param code
	 * @return
	 */
	public static String getText(int code) {
		return map.get(code).getName();
	}
	/**
	 * 获取对象
	 * @param code
	 * @return
	 */
	public static SysFileType getSysFileType(int code) {
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
	public String getAlias() {
		return alias;
	}
}