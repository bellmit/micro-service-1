package com.system.dao.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.system.comm.utils.FrameStringUtil;
import com.system.dao.annotation.ColumnIgnore;
import com.system.dao.annotation.ColumnPk;
import com.system.dao.utils.DbUtil;

/**
 * 拼接查询的sql的工具类
 * @author yuejing
 * @date 2013-11-20 下午11:51:53
 * @version V1.0.0
 */
public class QuerySql extends Sql {


	public QuerySql(Class<?> clazz) {
		super(clazz, null);
	}

	public QuerySql(Class<?> clazz, String orderby) {
		super(clazz, null, orderby);
	}

	/**
	 * 添加条件
	 * @param cond
	 * @param value
	 */
	public void addCond(String cond, Object value) {
		if(value == null || FrameStringUtil.isEmpty(value.toString())) {
			return;
		}
		if(conds == null) {
			conds = new ArrayList<String>();
			values = new ArrayList<Object>();
		}
		conds.add(cond);
		values.add(value);
	}

	/**
	 * 添加模糊查询条件（在字符串前面和后面补全%）
	 * @param cond
	 * @param value
	 */
	public void addCondLike(String cond, Object value) {
		if(DbUtil.isMysql()) {
			cond += " like CONCAT(CONCAT('%', ?), '%')";
		} else if(DbUtil.isOracle()) {
			cond += " like CONCAT(CONCAT('%', ?), '%')";
		}
		addCond(cond, value);
	}

	/**
	 * 添加模糊查询条件（在字符串后面补全%）
	 * @param cond
	 * @param value
	 */
	public void addCondLikeToRight(String cond, Object value) {
		if(DbUtil.isMysql()) {
			cond += " like CONCAT(?, '%')";
		} else if(DbUtil.isOracle()) {
			cond += " like CONCAT(?, '%')";
		}
		addCond(cond, value);
	}

	/**
	 * 添加条件
	 * @param cond
	 * @param value
	 */
	public void addCond(String cond, Object value1, Object value2) {
		if(value1 == null || FrameStringUtil.isEmpty(value1.toString()) || value2 == null || FrameStringUtil.isEmpty(value2.toString())) {
			return;
		}
		if(conds == null) {
			conds = new ArrayList<String>();
			values = new ArrayList<Object>();
		}
		conds.add(cond);
		values.add(value1);
		values.add(value2);
	}

	/**
	 * 设置排序内容
	 * @param orderby
	 */
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	/**
	 * 获取拼接的完整的SQL
	 * @return
	 */
	public String getSql() {
		if(FrameStringUtil.isEmpty(sql)) {
			sql = getQueryAll();
		}
		StringBuffer sqlStr = new StringBuffer(sql);
		if(conds != null) {
			if(sql.toLowerCase().contains(" where ")) {
				for (int i = 0; i < conds.size(); i ++) {
					sqlStr.append(" AND ").append(conds.get(i));
					if(conds.get(i).indexOf("?") == -1) {
						sqlStr.append("=?");
					}
				}
			} else {
				sqlStr.append(" WHERE");
				for (int i = 0; i < conds.size(); i ++) {
					sqlStr.append(i == 0 ? " " : " AND ").append(conds.get(i));
					if(conds.get(i).indexOf("?") == -1) {
						sqlStr.append("=?");
					}
				}
			}
		}
		if(FrameStringUtil.isNotEmpty(orderby)) {
			sqlStr.append(" ORDER BY ").append(orderby);
		}
		return sqlStr.toString();
	}

	public String getSqlByPk(Object value) {
		if(FrameStringUtil.isEmpty(sql)) {
			sql = getQueryAll();
		}
		StringBuffer sqlStr = new StringBuffer(sql);

		//得到类中的所有属性集合
		Field[] fields = getClazz().getDeclaredFields();
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
				continue;
			}
			/*if(field.getName().equals(pkKey)) {
					//为主键的列
					pkColumn = FrameStringUtil.setUpcaseConvertUnderline(field.getName());
					continue;
}*/
			sqlStr.append(FrameStringUtil.setUpcaseConvertUnderline(field.getName())).append("=?,");
		}
		if(FrameStringUtil.isEmpty(pkColumn)) {
			throw new RuntimeException("实体 [ " + getClazz().getName() + " ] 没有设置主键注解 [ @ColumnPk ]");
		}

		addValue(value);
		sqlStr.append(" WHERE ").append(pkColumn).append("=?");
		return sqlStr.toString();
	}
}