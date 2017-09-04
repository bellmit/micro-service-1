package com.system.dao.sql;

import java.lang.reflect.Field;

import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.dao.BaseDao;
import com.system.dao.annotation.ColumnDefault;
import com.system.dao.annotation.ColumnIgnore;
import com.system.dao.annotation.ColumnPk;

/**
 * 拼接保存的sql的工具类
 * @author yuejing
 * @date 2013-11-20 下午11:51:53
 * @version V1.0.0
 */
public class InsertSql extends Sql {

	private Class<?> clazz;
	private Object object;
	private Integer sequenceValue;

	/**
	 * 创建对象
	 * @param object
	 * @param pkKey		为null代表改字典不是自增长
	 */
	public InsertSql(Object object) {
		super(object.getClass(), "insert into ");
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
		sqlStr.append(getTableName(clazz)).append("(");
		int paramNum = 0;
		//得到类中的所有属性集合
		Field[] fields = clazz.getDeclaredFields();
		String sequenceName = null;
		//String sequenceColumn = null;
		for (Field field : fields) {
			//设置些属性是可以访问的
			field.setAccessible(true);
			ColumnIgnore columnIgnore = field.getAnnotation(ColumnIgnore.class);
			if(columnIgnore != null) {
				continue;
			}
			ColumnPk columnPk = field.getAnnotation(ColumnPk.class);
			if(columnPk != null && columnPk.isAuto()) {
				//为自增长
				continue;
			}
			sqlStr.append(FrameStringUtil.setUpcaseConvertUnderline(field.getName())).append(",");
			try {
				if(columnPk != null && FrameStringUtil.isNotEmpty(columnPk.sequence())) {
					//存在序列（放列名，不放值）
					sequenceName = columnPk.sequence();
					//sequenceColumn = column.name();
					BaseDao baseDao = FrameSpringBeanUtil.getBean(BaseDao.class);
					sequenceValue = baseDao.queryForLong("select " + sequenceName + ".nextVal from dual").intValue();
					field.set(object, sequenceValue);
					addValue(sequenceValue);
				} else {
					Object value = field.get(object);
					if(value == null) {
						ColumnDefault columnDefault = field.getAnnotation(ColumnDefault.class);
						if(columnDefault != null && FrameStringUtil.isNotEmpty(columnDefault.value())) {
							value = columnDefault.value();
						}
					}
					addValue(value);
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException("获取属性异常!", e);
			}
			paramNum ++;
		}
		/*if(StringUtil.isNotEmpty(sequenceColumn) && DbUtil.isOracle()) {
			//有序列
			sqlStr.append(sequenceColumn).append(",");
		}*/
		sqlStr.setCharAt(sqlStr.length() - 1, ')');
		sqlStr.append(" values(");
		for(int i = 0; i < paramNum; i ++) {
			sqlStr.append("?,");
		}
		/*if(StringUtil.isNotEmpty(sequenceName) && DbUtil.isOracle()) {
			//存在序列，并且数据库为Oracle
			sqlStr.append(sequenceName).append(".nextVal,");
		}*/
		sqlStr.setCharAt(sqlStr.length() - 1, ')');
		return sqlStr.toString();
	}

	public Integer getSequenceValue() {
		return this.sequenceValue;
	}
}