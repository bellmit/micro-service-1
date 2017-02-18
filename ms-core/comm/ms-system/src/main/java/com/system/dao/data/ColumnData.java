package com.system.dao.data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.system.comm.utils.FrameStringUtil;
import com.system.dao.annotation.ColumnIgnore;

public class ColumnData {

	/**
	 * 保存所有列的数据记录
	 */
	private static Map<String, Map<String, String>> columnsMap = new HashMap<String, Map<String,String>>();
	
	/**
	 * 添加类对应的列信息
	 * @param clazz
	 * @param columns
	 */
	public static void addColumns(Class<?> clazz) {
		String clazzName = clazz.getName();
		Map<String, String> org = columnsMap.get(clazzName);
		if(org == null) {
			Map<String, String> columns = new HashMap<String, String>();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				//设置些属性是可以访问的
				field.setAccessible(true);
				ColumnIgnore column = field.getAnnotation(ColumnIgnore.class);
				if(column != null) {
					continue;
				}
				columns.put(field.getName(), FrameStringUtil.setUpcaseConvertUnderline(field.getName()));
			}
			columnsMap.put(clazzName, columns);
		}
	}
	
	/**
	 * 获取类对应的列
	 * @param clazz
	 * @return
	 */
	public static Map<String, String> getColumns(Class<?> clazz) {
		String clazzName = clazz.getName();
		return columnsMap.get(clazzName);
	}
}
