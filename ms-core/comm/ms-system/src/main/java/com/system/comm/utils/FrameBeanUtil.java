package com.system.comm.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.system.comm.model.ContrastBean;
import com.system.comm.model.Orderby;

/**
 * 实体的工具类<br>
 * public static void main(String[] args) {
		String dir = "D:/project/csm/csm-core";
		List<String> list = getFiles(dir, "pom.xml");
		for (String path : list) {
			System.out.println(path);
		}
		String path = "D:\\project\\csm\\csm-core\\functions\\window\\csm-window-api\\pom.xml";
		String content = readFileString(path);
		System.out.println(content);
	}
 * @author yuejing
 * @date 2017年1月23日 下午3:07:40
 * @version V1.0.0
 */
public class FrameBeanUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FrameBeanUtil.class);
	
	/**
	 * 得到值相同的属性[类必须相同]<br>
	 * 注意：bean里面的类属性是不能比较的
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static List<ContrastBean> contrastEqual(Object obj1, Object obj2) {
		List<ContrastBean> list = new ArrayList<ContrastBean>();
		try {
			Class<?> clazz = obj1.getClass();
			Field[] fields = obj1.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
				Method getMethod = pd.getReadMethod();
				getMethod.setAccessible(true);
				Object o1 = getMethod.invoke(obj1);
				Object o2 = getMethod.invoke(obj2);
				if (o1.toString().equals(o2.toString())) {
					list.add(new ContrastBean(field.getName(), o1, o2));
				}
			}
		} catch (Exception e) {
			LOGGER.error("对比异常: " + e.getMessage(), e);
		}
		return list;
	}
	/**
	 * 得到值不同的属性[类必须相同]<br>
	 * 注意：bean里面的类属性是不能比较的
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static List<ContrastBean> contrastDiff(Object obj1, Object obj2) {
		List<ContrastBean> list = new ArrayList<ContrastBean>();
		try {
			Class<?> clazz = obj1.getClass();
			Field[] fields = obj1.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
				Method getMethod = pd.getReadMethod();
				getMethod.setAccessible(true);
				Object o1 = getMethod.invoke(obj1);
				Object o2 = getMethod.invoke(obj2);
				if ( (o1 == null && o2 != null)
						|| (o2 == null && o1 != null)
						|| (o1 != null && o2 != null && !o1.toString().equals(o2.toString()))) {
					list.add(new ContrastBean(field.getName(), o1, o2));
				}
			}
		} catch (Exception e) {
			LOGGER.error("对比异常: " + e.getMessage(), e);
		}
		return list;
	}
	
	public static void main(String[] args) {
		Orderby obj1 = new Orderby("sdf", "asc", 0);
		Orderby obj2 = new Orderby("sdf", "asc", 1);
		List<ContrastBean> list = contrastDiff(obj1, obj2);
		for (ContrastBean cb : list) {
			System.out.println(cb.getProperty() + "||" + cb.getValue1() + "||" + cb.getValue2());
		}
	}
}