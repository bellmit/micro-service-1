package com.module.admin.prj.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.comm.model.KvEntity;

/**
 * 容器类型[10tomcat、50自定义服务、100其它]
 * @author yuejing
 * @date 2016年5月16日 下午5:52:03
 * @version V1.0.0
 */
public enum PrjInfoContainer {
	/** tomcat */
	LINUX_TOMCAT(10, "linux", "linux: tomcat"),
	/** 自定义服务 */
	LINUX_CUST	(50, "linux", "linux: 自定义服务"),
	/** 其它 */
	LINUX_OTHER	(100, "linux", "linux: 其它"),
	/** tomcat */
	WIN_TOMCAT	(1010, "win", "win: tomcat"),
	/** 自定义服务 */
	WIN_CUST	(1050, "win", "win: 自定义服务"),
	/** 其它 */
	WIN_OTHER	(1100, "win", "win: 其它"),
	;
	
	public static final String KEY = "prj_info_container";

	private Integer code;
	private String name;
	private String sysType;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private PrjInfoContainer(Integer code, String sysType, String name) {
		this.code = code;
		this.sysType = sysType;
		this.name = name;
	}
	
	static {
		EnumSet<PrjInfoContainer> set = EnumSet.allOf(PrjInfoContainer.class);
		for(PrjInfoContainer e : set){
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

	public String getSysType() {
		return sysType;
	}
}