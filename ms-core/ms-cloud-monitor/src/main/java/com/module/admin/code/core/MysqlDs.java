package com.module.admin.code.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.module.admin.code.constants.DataType;
import com.module.admin.code.core.model.Column;
import com.module.admin.code.core.model.Table;
import com.system.comm.utils.FrameMapUtil;
import com.system.comm.utils.FrameStringUtil;

public class MysqlDs extends DsCore implements DbDs {

	/**
	 * 获取所有表名
	 * @return
	 */
	@Override
	public List<String> findAllTableName() {
		String sql = "select * from information_schema.tables where table_schema=? order by TABLE_NAME";
		List<Map<String, Object>> data = super.queryForList(sql, getDbName());
		List<String> list = new ArrayList<String>();
		for (Map<String, Object> map : data) {
			list.add(FrameMapUtil.getString(map, "TABLE_NAME"));
		}
		return list;
	}

	/**
	 * 获取表注释
	 * @param tableName
	 * @return
	 */
	@Override
	public String getTableComment(String tableName) {
		String sql = "select * from information_schema.tables where table_schema=? and TABLE_NAME=?";
		List<Map<String, Object>> data = super.queryForList(sql, getDbName(), tableName);
		if(data.size() > 0) {
			return FrameMapUtil.getString(data.get(0), "TABLE_COMMENT");
		}
		return null;
	}

	@Override
	public List<Column> queryTableColumns(String tableName) {
		String sql = "select * from information_schema.columns where table_schema=? and table_name=? order by ordinal_position";
		List<Map<String, Object>> data = super.queryForList(sql, getDbName(), tableName);
		List<Column> list = new ArrayList<Column>();
		for (Map<String, Object> map : data) {
			Column column=new Column();
			//名称
			column.setColumnName(FrameMapUtil.getString(map, "COLUMN_NAME").toLowerCase().trim());
			column.setFieldName(FrameStringUtil.setUnderlineConvertUpcase(column.getColumnName()));
			column.setComments(FrameMapUtil.getString(map, "COLUMN_COMMENT"));
			//类型
			String tmp = FrameMapUtil.getString(map, "DATA_TYPE").toLowerCase().trim();
			column.setColumnType(DataType.getInstance().getTypeMap(tmp));
			if (column.getColumnType() == DataType.DATA_FLOAT
					|| column.getColumnType() == DataType.DATA_INT
					|| column.getColumnType() == DataType.DATA_LONG) {//长度
				column.setLength(FrameMapUtil.getInteger(map, "NUMERIC_PRECISION"));
			} else if (column.getColumnType() == DataType.DATA_String) {
				column.setLength(FrameMapUtil.getInteger(map, "CHARACTER_MAXIMUM_LENGTH"));
			}
			tmp = FrameMapUtil.getString(map, "IS_NULLABLE");//是否允许为空
			column.setNullAble("NO".equals(tmp)?0:1);
			if ("PRI".equalsIgnoreCase(FrameMapUtil.getString(map, "COLUMN_KEY"))) {//是否为主键
				column.setIsKey(1);
			}
			if ("auto_increment".equals(FrameMapUtil.getString(map, "EXTRA"))) {//是否自动增长
				column.setAutoGeneral(1);
			}
			list.add(column);
		}
		return list;
	}

	@Override
	public Table getTable(String tableName) {
		Table table = new Table();
		table.setName(tableName);
		table.setClassName(FrameStringUtil.setUnderlineConvertUpcase(tableName.toLowerCase()));
		table.setTableComment(this.getTableComment(tableName));
		table.setColumns(queryTableColumns(tableName));
		return table;
	}

}