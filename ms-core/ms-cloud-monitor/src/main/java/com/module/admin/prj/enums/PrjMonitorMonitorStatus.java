package com.module.admin.prj.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.comm.model.KvEntity;

/**
 * 检测状态[10正常、20异常]
 * @author yuejing
 * @date 2016年5月16日 下午5:52:03
 * @version V1.0.0
 */
public enum PrjMonitorMonitorStatus {
	/** 正常 */
	NORMAL	(10, "正常"),
	/** 异常 */
	ERROR	(20, "异常"),
	;
	
	public static final String KEY = "prj_monitor_monitor_status";

	private int code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private PrjMonitorMonitorStatus(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		EnumSet<PrjMonitorMonitorStatus> set = EnumSet.allOf(PrjMonitorMonitorStatus.class);
		for(PrjMonitorMonitorStatus e : set){
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
