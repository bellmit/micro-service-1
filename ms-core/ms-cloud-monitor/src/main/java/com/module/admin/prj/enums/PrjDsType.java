package com.module.admin.prj.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.comm.model.KvEntity;

/**
 * 类型[mysql/oracle]
 * @author yuejing
 * @date 2016年5月16日 下午5:52:03
 * @version V1.0.0
 */
public enum PrjDsType {
	/** mysql */
	MYSQL	("mysql", "Mysql"),
	/** oracle */
	ORACLE	("oracle", "Oracle"),
	;
	
	public static final String KEY = "prj_ds_type";

	private String code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<String, String> map = new HashMap<String, String>();

	private PrjDsType(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		EnumSet<PrjDsType> set = EnumSet.allOf(PrjDsType.class);
		for(PrjDsType e : set){
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

	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
