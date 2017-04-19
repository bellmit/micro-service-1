package com.system.comm.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Map工具类
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @date    2014年12月25日 上午10:42:27 
 * @version 1.0.0
 */
@SuppressWarnings("rawtypes")
public class FrameMapUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FrameMapUtil.class);

	public static String getString(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null) {
			return null;
		}
		return value.toString();
	}
	
	public static Integer getInteger(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null || "".equals(value.toString())) {
			return null;
		}
		return Integer.valueOf(value.toString());
	}
	
	public static Long getLong(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null || "".equals(value.toString())) {
			return null;
		}
		return Long.valueOf(value.toString());
	}
	
	public static Float getFloat(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null || "".equals(value.toString())) {
			return null;
		}
		return Float.valueOf(value.toString());
	}
	
	public static Double getDouble(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null || "".equals(value.toString())) {
			return null;
		}
		return Double.valueOf(value.toString());
	}
	
	public static Date getDate(Map map, String key, String fmt) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null || "".equals(value.toString())) {
			return null;
		}
		return FrameTimeUtil.parseDate(value.toString(), fmt);
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getListString(Map map, String key) {
		if(map == null) {
			return new ArrayList<String>();
		}
		Object value = map.get(key);
		if(value == null) {
			return new ArrayList<String>();
		}
		return (List<String>) value;
	}

	@SuppressWarnings("unchecked")
	public static List<Map> getListMap(Map map, String key) {
		if(map == null) {
			return new ArrayList<Map>();
		}
		Object value = map.get(key);
		if(value == null) {
			return new ArrayList<Map>();
		}
		return (List<Map>) value;
	}
	
	public static Map getMap(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null) {
			return null;
		}
		return (Map) value;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Map<String,Object> param , Class clazz){
		Object value = null;
		Class[] paramTypes = new Class[1];
		Object obj = null;
		try {
			//创建实例
			obj = clazz.newInstance();
			Field[] f = clazz.getDeclaredFields();
			List<Field[]> flist = new ArrayList<Field[]>();
			flist.add(f);
			
			Class superClazz = clazz.getSuperclass();
			while(superClazz != null){
				f = superClazz.getFields();
				flist.add(f);
				superClazz = superClazz.getSuperclass();
			}
			
			for (Field[] fields : flist) {
				for (Field field : fields) {
					String fieldName = field.getName();
					value = param.get(fieldName);
					if(value != null){
						paramTypes[0] = field.getType();
						Method method = null;
						//调用相应对象的set方法
						StringBuffer methodName = new StringBuffer("set");
						methodName.append(fieldName.substring(0, 1).toUpperCase());
						methodName.append(fieldName.substring(1, fieldName.length()));
						
						//测试输出
						//System.out.println(methodName);
						
						method = clazz.getMethod(methodName.toString(), paramTypes);
						
						//ConvertUtil.getValue(value.toString(), fieldName, paramTypes[0])
						String valueStr = value.toString();
						
						if (!valueStr.equals("")) {
							Class paramClazz = paramTypes[0];
							// 如果获取参数值不为"",则通过convertGt方法进行类型转换后返回结果
							if (Date.class.getName().equalsIgnoreCase(paramClazz.getName())) {
								// 增加对从String类型到Date
								if(valueStr.length() > 10) {
									method.invoke(obj, FrameTimeUtil.parseDate(valueStr));
								} else {
									method.invoke(obj, FrameTimeUtil.parseDate(valueStr, FrameTimeUtil.FMT_YYYY_MM_DD));
								}
							} else if(String.class.getName().equalsIgnoreCase(paramClazz.getName())) {
								method.invoke(obj, value);
							} else if(Integer.class.getName().equalsIgnoreCase(paramClazz.getName())) {
								method.invoke(obj, Integer.valueOf(valueStr));
							} else if(Double.class.getName().equalsIgnoreCase(paramClazz.getName())) {
								method.invoke(obj, Double.valueOf(valueStr));
							} else if(Float.class.getName().equalsIgnoreCase(paramClazz.getName())) {
								method.invoke(obj, Float.valueOf(valueStr));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Map转Bean异常: " + e.getMessage(), e);
		}
		return (T)obj;
	}
}