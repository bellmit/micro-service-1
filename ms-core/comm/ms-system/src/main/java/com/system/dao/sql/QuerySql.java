package com.system.dao.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

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
		conds = new ArrayList<String>();
		values = new ArrayList<Object>();
	}

	public QuerySql(Class<?> clazz, String orderby) {
		super(clazz, null, orderby);
		conds = new ArrayList<String>();
		values = new ArrayList<Object>();
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
		conds.add(cond);
		values.add(value);
	}

	/**
	 * 添加模糊查询条件[生成sql条件为：cond like concat(concat(value, ?), '%')]
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
	 * 添加模糊查询条件[生成sql条件为：cond like concat(value, '%')]
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
	 * 根据日期区间查询[生成sql条件为：cond>=beginTime and cond<=endTime]
	 * @param cond
	 * @param beginTime
	 * @param endTime
	 */
	public void addCondDate(String cond, Date beginTime, Date endTime) {
		if(beginTime == null || endTime == null) {
			return;
		}
		if(beginTime != null) {
			conds.add(cond + ">=?");
			values.add(beginTime);
		}
		if(endTime != null) {
			conds.add(cond + "<=?");
			values.add(endTime);
		}
	}
	
	/**
	 * 添加in的查询[生成sql为：cond in(value1,value2,...)]
	 * @param cond
	 * @param value
	 */
	public void addCondIn(String cond, Object ...value) {
		if(value == null || value.length == 0) {
			return;
		}
		StringBuffer condBuffer = new StringBuffer(cond);
		condBuffer.append(" in (");
		for (Object obj : value) {
			condBuffer.append("?,");
			values.add(obj);
		}
		condBuffer.setCharAt(condBuffer.length() - 1, ')');
		conds.add(condBuffer.toString());
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
		if(conds != null && conds.size() > 0) {
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
				break;
			}
			/*if(field.getName().equals(pkKey)) {
					//为主键的列
					pkColumn = FrameStringUtil.setUpcaseConvertUnderline(field.getName());
					continue;
}*/
			//sqlStr.append(FrameStringUtil.setUpcaseConvertUnderline(field.getName())).append("=?,");
		}
		if(FrameStringUtil.isEmpty(pkColumn)) {
			throw new RuntimeException("实体 [ " + getClazz().getName() + " ] 没有设置主键注解 [ @ColumnPk ]");
		}

		addValue(value);
		sqlStr.append(" WHERE ").append(pkColumn).append("=?");
		return sqlStr.toString();
	}
}