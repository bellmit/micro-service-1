package com.module.comm.druid;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.system.dao.BaseDao;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

public class DsUtil extends BaseDao {
	
	private JdbcTemplate jdbcTemplate;
	private DruidDataSource dataSource;
	private boolean isInit = false;

	/**
	 * 初始化信息
	 * @param driverClass
	 * @param jdbcUrl
	 * @param username
	 * @param password
	 * @param initialSize
	 * @param maxIdle
	 * @param minIdle
	 */
	public void init(String driverClass, String jdbcUrl,
			String username, String password, Integer initialSize,
			Integer maxIdle, Integer minIdle) {
		init(driverClass, jdbcUrl, username, password, initialSize, maxIdle, minIdle, false);
	}
	/**
	 * 初始化信息
	 * @param driverClass
	 * @param jdbcUrl
	 * @param username
	 * @param password
	 * @param initialSize
	 * @param maxIdle
	 * @param minIdle
	 * @param isSetDs	是否设置数据源
	 */
	public void init(String driverClass, String jdbcUrl,
			String username, String password, Integer initialSize,
			Integer maxIdle, Integer minIdle, boolean isSetDs) {
		if(initialSize == null) {
			initialSize = 10;
		}
		if(maxIdle == null) {
			maxIdle = 30;
		}
		if(minIdle == null) {
			minIdle = 10;
		}
		dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setInitialSize(initialSize);
		dataSource.setMaxActive(maxIdle);
		//dataSource.setMaxIdle(maxIdle);
		dataSource.setMinIdle(minIdle);
		if(isSetDs) {
			setDs();
		}
	}
	
	/**
	 * 设置数据源
	 * @return
	 */
	public ResponseFrame setDs() {
		ResponseFrame frame = new ResponseFrame();
		if(isInit) {
			frame.setSucc();
			return frame;
		}
		try {
			dataSource.init();
		} catch (SQLException e) {
			frame.setCode(-3);
			frame.setMessage(e.getMessage());
			return frame;
		}
		jdbcTemplate = new JdbcTemplate(dataSource);
		setJdbcTemplate(jdbcTemplate);
		frame.setSucc();
		return frame;
	}
	
	/**
	 * 关闭
	 */
	public void close() {
		if(dataSource != null) {
			dataSource.close();
			dataSource = null;
		}
		if(jdbcTemplate != null) {
			jdbcTemplate = null;
		}
	}
	
	/**
	 * 执行sql
	 * @param sql
	 * @return
	 */
	public ResponseFrame exec(String sql) {
		ResponseFrame frame = new ResponseFrame();
		setDs();
		if(sql.endsWith(";")) {
			sql = sql.substring(0, sql.length() - 1);
		}
		try {
			jdbcTemplate.execute(sql);
			frame.setSucc();
		} catch (DataAccessException e) {
			frame.setCode(-2);
			frame.setMessage(e.getMessage());
		}
		return frame;
	}
	
	/**
	 * 执行测试语句【自带关闭链接】
	 * @param sql
	 * @return
	 */
	public ResponseFrame test(String sql) {
		ResponseFrame frame = null;
		frame = setDs();
		if(ResponseCode.SUCC.getCode() != frame.getCode().intValue()) {
			return frame;
		}
		if(sql.endsWith(";")) {
			sql = sql.substring(0, sql.length() - 1);
		}
		try {
			List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
			frame.setBody(data);
			frame.setSucc();
		} catch (DataAccessException e) {
			frame.setCode(-2);
			frame.setMessage(e.getMessage());
		}
		close();
		return frame;
	}
	
	/*public static void main(String[] args) {
		String driverClass = "com.mysql.jdbc.Driver";
		String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/monitor?useUnicode=true&characterEncoding=UTF-8";
		String username = "root";
		String password = "root";
		Integer initialSize = 10;
		Integer maxIdle = 30;
		Integer minIdle = 10;
		DsUtil ds = new DsUtil();
		ds.init(driverClass, jdbcUrl, username, password, initialSize, maxIdle, minIdle);
		String sql = "select now() from dual;";
		ResponseFrame frame = ds.test(sql);
		System.out.println(FrameJsonUtil.toString(frame));
	}*/
}
