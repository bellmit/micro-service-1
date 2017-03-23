package com.module.admin.cli.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.comm.model.KvEntity;

/**
 * 活动状态[10正常、20心跳异常]
 * @author yuejing
 * @date 2016年5月16日 下午5:52:03
 * @version V1.0.0
 */
public enum CliInfoActivityStatus {
	/** 正常 */
	NORMAL	(10, "正常"),
	/** 心跳异常 */
	ERROR	(20, "心跳异常"),
	;
	
	public static final String KEY = "cli_info_activity_status";

	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private CliInfoActivityStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		EnumSet<CliInfoActivityStatus> set = EnumSet.allOf(CliInfoActivityStatus.class);
		for(CliInfoActivityStatus e : set){
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
