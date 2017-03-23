package com.module.admin.sys.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.comm.model.KvEntity;

/**
 * 文件状态 [0待确定、1使用中、2未使用、3已作废]
 * @author duanbin
 * @date 2016年5月16日 下午5:52:03
 * @version V1.0.0
 */
public enum SysFileStatus {
	/** 0 待确定 */
	WAIT	(0, "待确定"),
	/** 1 使用中 */
	USE		(1, "使用中"),
	/** 2 未使用 */
	UNUSED	(2, "未使用"),
	/** 3 已作废 */
	CANCEL	(3, "已作废");
	
	public static final String KEY = "sys_file_status";

	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private SysFileStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		EnumSet<SysFileStatus> set = EnumSet.allOf(SysFileStatus.class);
		for(SysFileStatus e : set){
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
