package com.frame.user.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.system.comm.model.KvEntity;

/**
 * 类型[10登录、20操作]
 * @author 岳静
 * @date 2016年3月8日 下午8:18:33 
 * @version V1.0
 */
public enum UserLogType {
	LOGIN	(10, "登录"),
	BIZ		(20, "操作");
	
	public static final String KEY = "user_log_type";

	private int code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private UserLogType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		Set<UserLogType> set = EnumSet.allOf(UserLogType.class);
		for(UserLogType e : set){
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
