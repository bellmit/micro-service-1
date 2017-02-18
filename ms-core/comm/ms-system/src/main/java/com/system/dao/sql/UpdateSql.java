package com.system.dao.sql;

import java.lang.reflect.Field;

import com.system.comm.utils.FrameStringUtil;
import com.system.dao.annotation.ColumnIgnore;
import com.system.dao.annotation.ColumnPk;

/**
 * 拼接修改的sql的工具类
 * @author yuejing
 * @date 2013-11-20 下午11:51:53
 * @version V1.0.0
 */
public class UpdateSql extends Sql {

	private Class<?> clazz;
	private Object object;

	public UpdateSql(Object object) {
		super(object.getClass(), "update ");
		this.object = object;
		this.clazz = object.getClass();
	}

	/**
	 * 获取拼接的完整的SQL
	 * @return
	 */
	public String getSql() {
		StringBuffer sqlStr = new StringBuffer(sql);
		//获取表名
		sqlStr.append(getTableName(clazz)).append(" set ");
		//得到类中的所有属性集合
		Field[] fields = clazz.getDeclaredFields();
		String pkColumn = null;
		for (Field field : fields) {
			//设置些属性是可以访问的
			field.setAccessible(true);
			ColumnIgnore column = field.getAnnotation(ColumnIgnore.class);
			if(column != null) {
				continue;
			}

			try {
				ColumnPk columnPk = field.getAnnotation(ColumnPk.class);
				Object value = field.get(object);
				if(columnPk != null) {
					//为主键的列
					pkColumn = FrameStringUtil.setUpcaseConvertUnderline(field.getName());
					pkValue = value;
					continue;
				}
				/*if(field.getName().equals(pkKey)) {
				//为主键的列
				pkColumn = FrameStringUtil.setUpcaseConvertUnderline(field.getName());
				continue;
			}*/
				if(value == null) {
					//值为null则不修改
					continue;
				}
				addValue(value);
				sqlStr.append(FrameStringUtil.setUpcaseConvertUnderline(field.getName())).append("=?,");
			} catch (IllegalAccessException e) {
				throw new RuntimeException("获取属性异常!", e);
			}
		}
		if(FrameStringUtil.isEmpty(pkColumn)) {
			throw new RuntimeException("实体 [ " + clazz.getName() + " ] 没有设置主键注解 [ @ColumnPk ]");
		}
		sqlStr.setCharAt(sqlStr.length() - 1, ' ');
		sqlStr.append("where ").append(pkColumn).append("=?");
		addValue(pkValue);
		return sqlStr.toString();
	}
}