package com.module.admin.sys.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.comm.model.KvEntity;

/**
 * 状态 [10正常、20冻结]
 * @author duanbin
 * @date 2016年5月16日 下午5:52:03
 * @version V1.0.0
 */
public enum SysUserStatus {
	/** 10 正常 */
	NORMAL	(10, "正常"),
	/** 20 冻结 */
	FREEZE	(20, "冻结");
	
	public static final String KEY = "sys_user_status";

	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private SysUserStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		EnumSet<SysUserStatus> set = EnumSet.allOf(SysUserStatus.class);
		for(SysUserStatus e : set){
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
