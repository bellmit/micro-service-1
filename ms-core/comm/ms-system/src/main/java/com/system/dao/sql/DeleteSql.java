package com.system.dao.sql;

import java.lang.reflect.Field;

import com.system.comm.utils.FrameStringUtil;
import com.system.dao.annotation.ColumnIgnore;
import com.system.dao.annotation.ColumnPk;

/**
 * 拼接删除的sql的工具类
 * @author yuejing
 * @date 2013-11-20 下午11:51:53
 * @version V1.0.0
 */
public class DeleteSql extends Sql {

	private Class<?> clazz;

	public DeleteSql(Class<?> clazz, Object value) {
		super(clazz, "delete from ", value);
		this.clazz = clazz;
	}

	/**
	 * 获取拼接的完整的SQL
	 * @return
	 */
	public String getSql() {
		StringBuffer sqlStr = new StringBuffer(sql);
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
			ColumnPk columnPk = field.getAnnotation(ColumnPk.class);
			if(columnPk != null) {
				//为主键的列
				pkColumn = FrameStringUtil.setUpcaseConvertUnderline(field.getName());
			}
			/*if(field.getName().equals(pkKey)) {
				//为主键的列
				pkColumn = FrameStringUtil.setUpcaseConvertUnderline(field.getName());
				break;
			}*/
		}
		if(FrameStringUtil.isEmpty(pkColumn)) {
			throw new RuntimeException("实体 [ " + clazz.getName() + " ] 没有设置主键注解 [ @ColumnPk ]");
		}
		sqlStr.append(getTableName(clazz)).append(" where ").append(pkColumn).append("=?");
		addValue(pkValue);
		return sqlStr.toString();
	}
}