package com.module.admin.code.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.module.admin.code.constants.DataType;
import com.module.admin.code.core.model.Column;
import com.module.admin.code.core.model.Table;
import com.system.comm.utils.FrameMapUtil;
import com.system.comm.utils.FrameStringUtil;

public class OracleDs extends DsCore implements DbDs {

	/**
	 * 获取所有表名
	 * @return
	 */
	@Override
	public List<String> findAllTableName() {
		String sql = "select * from sys.all_tables Where owner=? order by table_name";
		List<Map<String, Object>> data = super.queryForList(sql, getDbName().toUpperCase());
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
		/*String sql = "select * from information_schema.tables where table_schema=? and TABLE_NAME=?";
		List<Map<String, Object>> data = super.queryForList(sql, getDbName(), tableName);
		if(data.size() > 0) {
			return FrameMapUtil.getString(data.get(0), "TABLE_COMMENT");
		}
		return null;*/
		return tableName;
	}

	@Override
	public List<Column> queryTableColumns(String tableName) {
		//获取主键
		String sql = "select c.INDEX_NAME,c.COLUMN_NAME,DATA_TYPE,DATA_PRECISION,DATA_SCALE from Sys.user_constraints i,Sys.all_ind_columns c,sys.user_tab_columns u "
				+ "where i.TABLE_NAME =? And  i.CONSTRAINT_TYPE='P' And i.CONSTRAINT_name=c.index_name "
				+ "and c.column_name=u.column_name And u.table_name=? Order By index_name";
		List<Map<String, Object>> data = super.queryForList(sql, tableName.toUpperCase(), tableName.toUpperCase());
		Map<String, String> keyMap = new HashMap<String, String>();
		int seq = 0;
		for (Map<String, Object> map : data) {
			seq ++;
			keyMap.put(FrameMapUtil.getString(map, "COLUMN_NAME"), String.valueOf(seq));
		}
		
		//获取注解
		sql = "select * from sys.ALL_COL_COMMENTS Where table_name=?";
		data = super.queryForList(sql, tableName.toUpperCase());
		Map<String, String> commentsMap = new HashMap<String, String>();
		for (Map<String, Object> map : data) {
			commentsMap.put(FrameMapUtil.getString(map, "COLUMN_NAME"), FrameMapUtil.getString(map, "COMMENTS"));
		}
		
		//获取列
		sql = "select COLUMN_NAME,DATA_TYPE,DATA_PRECISION,DATA_SCALE,NULLABLE from sys.user_tab_columns Where table_name=?";
		data = super.queryForList(sql, tableName.toUpperCase());
		List<Column> list = new ArrayList<Column>();
		for (Map<String, Object> map : data) {
			Column column=new Column();
			column.setColumnName(FrameMapUtil.getString(map, "COLUMN_NAME").trim());
			column.setFieldName(FrameStringUtil.setUnderlineConvertUpcase(column.getColumnName()));

			String dataType = FrameMapUtil.getString(map, "DATA_TYPE").toLowerCase().trim();
			if ("number".equals(dataType)) {
				Integer dataScale = FrameMapUtil.getInteger(map, "DATA_SCALE");
				Integer dataPrecision = FrameMapUtil.getInteger(map, "DATA_PRECISION");
				if (dataScale == null || dataScale.intValue() > 0) {
					dataType = dataType + "f"; // 浮点数据
				} else if (dataPrecision == null
						|| dataPrecision.intValue() > 8) {
					dataType = dataType + "b"; // long数据
				}
//				if (rs.getObject("data_scale")==null|| rs.getInt("data_scale") > 0) {
//					tmp = tmp + "b"; // long数据
//				} 
			}
			column.setColumnType(DataType.getInstance().getTypeMap(dataType));

			String nullable = FrameMapUtil.getString(map, "NULLABLE");// 是否允许为空
			column.setNullAble("N".equals(nullable) ? 0 : 1);
			if (keyMap.get(column.getColumnName()) != null) {// 是否为主码
				column.setIsKey(1);
			}
			column.setComments(commentsMap.get(column.getColumnName()));
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