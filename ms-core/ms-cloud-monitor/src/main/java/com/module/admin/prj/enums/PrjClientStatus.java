package com.module.admin.prj.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.comm.model.KvEntity;

/**
 * 状态[10待发布、20发布中、30发布失败、40发布成功]
 * @author yuejing
 * @date 2016年5月16日 下午5:52:03
 * @version V1.0.0
 */
public enum PrjClientStatus {
	/** 待发布 */
	WAIT	(10, "待发布"),
	/** 发布中 */
	ING		(20, "发布中"),
	/** 发布失败 */
	FAIL	(30, "发布失败"),
	/** 发布成功 */
	SUCC	(40, "发布成功"),
	;
	
	public static final String KEY = "prj_client_status";

	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private PrjClientStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		EnumSet<PrjClientStatus> set = EnumSet.allOf(PrjClientStatus.class);
		for(PrjClientStatus e : set){
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
