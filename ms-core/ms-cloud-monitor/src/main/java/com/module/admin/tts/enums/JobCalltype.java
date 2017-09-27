package com.module.admin.tts.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.system.comm.model.KvEntity;

/**
 * 调用方式[10http或https/20微服务]
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年3月30日 下午4:59:53 
 * @version 1.0.0
 */
public enum JobCalltype {
	HTTP		(10, "http或https"),
	MS			(20, "微服务");

	public static final String KEY = "job_calltype";

	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private JobCalltype(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	static {
		Set<JobCalltype> set = EnumSet.allOf(JobCalltype.class);
		for(JobCalltype e : set){
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
