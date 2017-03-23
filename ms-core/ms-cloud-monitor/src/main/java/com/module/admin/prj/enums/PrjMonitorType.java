package com.module.admin.prj.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.comm.model.KvEntity;

/**
 * 监控类型[10服务、20数据库、30缓存、40其它]
 * @author yuejing
 * @date 2016年5月16日 下午5:52:03
 * @version V1.0.0
 */
public enum PrjMonitorType {
	/** 服务 */
	WEB		(10, "服务"),
	/** 数据库 */
	DB		(20, "数据库"),
	/** 缓存 */
	CACHE	(30, "缓存"),
	/** 其它 */
	OTHER	(40, "其它"),
	;
	
	public static final String KEY = "prj_monitor_type";

	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private PrjMonitorType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		EnumSet<PrjMonitorType> set = EnumSet.allOf(PrjMonitorType.class);
		for(PrjMonitorType e : set){
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
