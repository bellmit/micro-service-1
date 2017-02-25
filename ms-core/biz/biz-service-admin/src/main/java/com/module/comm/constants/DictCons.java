package com.module.comm.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.module.admin.user.enums.UserInfoStatus;
import com.system.comm.model.KvEntity;

/**
 * 字典常量
 * @author  duanbin
 * @date    2016年5月27日 下午1:55:23 
 * @version 1.0.0
 */
public class DictCons {
	
	private static final Logger LOGGER = Logger.getLogger(DictCons.class);

	private static Map<String, Object> dictMap = new HashMap<String, Object>();
	
	/**
	 * 初始化权限需要的信息
	 * @param servletContext
	 */
	public static void init(ServletContext servletContext) {
		DictCons.addValue(com.system.comm.enums.Boolean.KEY	, com.system.comm.enums.Boolean.getList(), servletContext);
		
		DictCons.addValue(UserInfoStatus.KEY	, UserInfoStatus.getList(), servletContext);


		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("========================= 初始化字典信息成功 ===========================");
		}
	}
	
	/**
	 * 添加字典信息
	 * @param key
	 * @param list
	 * @param servletContext
	 */
	public static void addValue(String key, List<KvEntity> list, ServletContext servletContext) {
		Map<String, String> valueMap = new HashMap<String, String>();
		for (KvEntity kvEntity : list) {
			valueMap.put(kvEntity.getCode(), kvEntity.getValue());
		}
		dictMap.put(key, valueMap);
		dictMap.put(key + "_list", list);
		servletContext.setAttribute(key, list);
	}
	
	/**
	 * 根据值获取显示值
	 * @param key
	 * @param value
	 * @return
	 */
	public static String getValue(String key, Object value) {
		if(value == null) {
			return null;
		}
		return getMap(key).get(value.toString());
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getMap(String key) {
		return (Map<String, String>) dictMap.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public static List<KvEntity> getList(String key) {
		return (List<KvEntity>) dictMap.get(key + "_list");
	}
}