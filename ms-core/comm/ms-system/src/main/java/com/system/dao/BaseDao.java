package com.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.system.comm.model.Page;
import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.dao.sql.DeleteSql;
import com.system.dao.sql.InsertSql;
import com.system.dao.sql.QuerySql;
import com.system.dao.sql.Sql;
import com.system.dao.sql.UpdateSql;
import com.system.dao.utils.DbUtil;

/**
 * springJDBC操作<br>
 * <!-- 在spring配置jdbc -->
	&lt;bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
		&lt;constructor-arg ref="dataSource" />
	&lt;/bean>
 * @author yuejing
 * @date 2017年1月26日 下午9:18:24
 * @version V1.0.0
 */
@Component
public class BaseDao {
	//private static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);
	private JdbcTemplate jdbcTemplate;

	//采用该方法是为了兼容在其它项目中没有使用SpringJdbcTemplate的情况
	private JdbcTemplate getJdbcTemplate() {
		if(jdbcTemplate == null) {
			jdbcTemplate = FrameSpringBeanUtil.getBean(JdbcTemplate.class);
		}
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 保存[不返回自增长的主键]
	 * @param sql
	 * @param params	动态参数[对应where条件中的?]
	 */
	/*public int saveSql(final String sql, final Object ...params){
		return getJdbcTemplate().update(sql, params);
	}*/

	/**
	 * 保存
	 * @param object
	 */
	/*public int save(Object object) {
		Sql sql = new InsertSql(object);
		return saveSql(sql.getSql(), sql.getParams());
	}*/

	/**
	 * 保存返回生成的主键
	 * @param object
	 * @return
	 */
	public Integer save(Object object) {
		InsertSql sql = new InsertSql(object);
		if(DbUtil.isMysql()) {
			return saveSql(sql.getSql(), sql.getParams());
		} else if(DbUtil.isOracle()) {
			saveSql(sql.getSql(), sql.getParams());
			return sql.getSequenceValue();
		}
		throw new RuntimeException("目前不支持该数据库类型");
	}

	/**
	 * 保存[返回自增长的主键] [下个版本将会保密]
	 * @param sql
	 * @param params
	 * @return
	 */
	public Integer saveSql(final String sql, final Object ...params){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				for (int i = 0; i < params.length; i ++) {
					ps.setObject( (i + 1), params[i]);
				}
				return ps;
			}
		}, keyHolder);
		Number key = keyHolder.getKey();
		return key != null ? key.intValue() : null;
	}

	/**
	 * 保存或修改
	 * @param object
	 * @param value
	 */
	/*public void saveOrUpdate(Object object, Object value) {
		if(value == null) {
			Sql sql = new InsertSql(object);
			saveSql(sql.getSql(), sql.getParams());
		} else {
			Sql sql = new UpdateSql(object, value);
			updateSql(sql.getSql(), sql.getParams());
		}
	}*/

	/**
	 * 删除
	 * @param sql
	 * @param params	动态参数[对应where条件中的?]
	 */
	public int deleteSql(final String sql, final Object ...params){
		return getJdbcTemplate().update(sql, params);
	}

	/**
	 * 根据主键删除记录
	 * @param cls
	 * @param value	主键的值
	 */
	public int delete(Class<?> clazz, Object value) {
		Sql sql = new DeleteSql(clazz, value);
		return deleteSql(sql.getSql(), sql.getParams());
	}

	/**
	 * 修改
	 * @param sql
	 * @param params
	 */
	public int updateSql(final String sql, final Object ...params) {
		return getJdbcTemplate().update(sql, params);
	}

	/**
	 * 根据主键修改对象<br>
	 * 注意：要修改表字段为空，需要设置字段的值为空字符串("")
	 * 为主键的列也不会修改
	 * @param object
	 * @return
	 */
	public int update(Object object) {
		Sql sql = new UpdateSql(object);
		return updateSql(sql.getSql(), sql.getParams());
	}
	
	/**
	 * 查询对象
	 * @param querySql
	 * @return
	 */
	public <T>T get(QuerySql querySql) {
		return get(querySql.getSql(), querySql.getClazz(), querySql.getParams());
	}
	

	public <T>T get(Class<?> clazz, Object value) {
		QuerySql querySql = new QuerySql(clazz);
		return get(querySql.getSqlByPk(value), querySql.getClazz(), querySql.getParams());
	}

	/**
	 * 查询对象
	 * @param <T>
	 * @param sql
	 * @param object
	 * @param params	动态参数[对应where条件中的?]
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T>T get(final String sql, final Class clazz, final Object ...params) {
		try {
			return (T) getJdbcTemplate().queryForObject(sql, params, new BeanPropertyRowMapper(clazz));
		} catch (CannotGetJdbcConnectionException e) {
			throw new RuntimeException(e.getLocalizedMessage(), e);
		} catch (DataAccessException e) {
			/*if(LOGGER.isInfoEnabled()) {
				LOGGER.info("get方法没有获取到记录返回null");
			}*/
			return null;
		}
	}

	/**
	 * 获取Long类型的值<br>
	 * 用于查询oracle的序列等
	 * @param sql
	 * @param params
	 * @return
	 */
	public Long queryForLong(String sql, Object ...params) {
		Long num = getJdbcTemplate().queryForObject(sql, Long.class, params);
		return num;
		//return getJdbcTemplate().queryForLong(sql, params);
	}

	/**
	 * 查询List集合
	 * @param querySql
	 * @return
	 */
	public <T> List<T> query(QuerySql querySql) {
		return query(querySql.getSql(), querySql.getClazz(), querySql.getParams());
	}
	
	/**
	 * 查询List集合
	 * @param sql
	 * @param object
	 * @param params	动态参数[对应where条件中的?]
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> query(final String sql, final Class clazz, final Object ...params){
		return getJdbcTemplate().query(sql, params, new BeanPropertyRowMapper(clazz));
	}

	/**
	 * 获取返回List&lt;Map&gt;的集合
	 * @param querySql
	 * @return
	 */
	public List<Map<String, Object>> queryForList(QuerySql querySql){
		return queryForList(querySql.getSql(), querySql.getParams());
	}
	/**
	 * 获取返回List&lt;Map&gt;的集合
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryForList(final String sql, final Object ...params){
		return getJdbcTemplate().queryForList(sql, params);
	}

	/**
	 * 查询函数统计出来的值[如带有count(*)/sum(num)]
	 * @param sql
	 * @param params	动态参数[对应where条件中的?]
	 * @return
	 */
	public Integer queryForInt(final String sql, final Object ...params) {
		Integer num = getJdbcTemplate().queryForObject(sql, Integer.class, params);
		return num;
		//return getJdbcTemplate().queryForInt(sql, params);
	}

	/**
	 * Mysql分页查询
	 * @param <T>
	 * @param sql
	 * @param page
	 * @param size
	 * @param cls
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public <T> Page<T> pageQuery(final String sql, final Integer page, final Integer size, final Class clazz, final Object ...params){
		if(DbUtil.isMysql()) {
			return pageQueryMysql(sql, page, size, clazz, params);
		} else if(DbUtil.isOracle()) {
			return pageQueryOracle(sql, page, size, clazz, params);
		}
		return null;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> Page<T> pageQueryOracle(final String sql, final Integer page, final Integer size, final Class clazz, final Object ...params){
		StringBuffer pageSql = new StringBuffer("select * from ( select a.*, rownum rn from (");
		pageSql.append(sql);
		int orclBegNum = 0;
		int orclEndNum = 0;
		if(page != null && size != null) {
			orclBegNum = ( (page - 1) * size) + 1;
		}
		if(page != null && size != null) {
			orclEndNum = page * size;
		}
		pageSql.append(") a where rownum <="+orclEndNum+") where rn>=").append(orclBegNum);
		List<?> list = getJdbcTemplate().query(pageSql.toString(), params, new BeanPropertyRowMapper(clazz));
		return (Page<T>) setMyPageOracle(sql.toString(), list, page, size, params);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Page<?> setMyPageOracle(String sql, List<?> data, Integer page, Integer size, Object ...params) {
		String countSql = null;
		String sonSql = sql;
		if(sql.contains("ORDER BY"))
			sonSql = sql.substring(0, sql.indexOf("ORDER BY"));
		else if(sql.contains("order by"))
			sonSql = sql.substring(0, sql.indexOf("order by"));
		//判断是否有group by
		if(sql.indexOf("group by") != -1 || sql.indexOf("GROUP BY") != -1) {
			countSql = "select count(*) from (" + sonSql + ") t";
		} else {
			if(sonSql.indexOf("from") != -1){
				sonSql = sonSql.substring(sonSql.indexOf("from"));
			}else if(sonSql.indexOf("FROM") != -1){
				sonSql = sonSql.substring(sonSql.indexOf("FROM"));
			}
			countSql = "select count(*) " + sonSql;
		}
		//查询总的记录条数
		Integer total = queryForInt(countSql, params);
		return new Page(page, size, total, data);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> Page<T> pageQueryMysql(final String sql, final Integer page, final Integer size, final Class clazz, final Object ...params){
		StringBuffer pageSql = new StringBuffer(sql);
		String sqlStr = sql.toString();
		pageSql.append(" LIMIT ").append((page - 1) * size).append(",").append(size);
		List<?> list = getJdbcTemplate().query(pageSql.toString(), params, new BeanPropertyRowMapper(clazz));
		return (Page<T>) setMyPageMysql(sqlStr.toString(), list, page, size, params);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Page<?> setMyPageMysql(String sql, List<?> data, Integer page, Integer size, Object ...params) {
		String countSql = null;
		String sonSql = sql;
		if(sql.contains("ORDER BY"))
			sonSql = sql.substring(0, sql.indexOf("ORDER BY"));
		else if(sql.contains("order by"))
			sonSql = sql.substring(0, sql.indexOf("order by"));
		//判断是否有group by
		if(sql.indexOf("group by") != -1 || sql.indexOf("GROUP BY") != -1) {
			countSql = "select count(*) from (" + sonSql + ") t";
		} else {
			if(sonSql.indexOf("from") != -1){
				sonSql = sonSql.substring(sonSql.indexOf("from"));
			}else if(sonSql.indexOf("FROM") != -1){
				sonSql = sonSql.substring(sonSql.indexOf("FROM"));
			}
			countSql = "select count(*) " + sonSql;
		}
		//查询总的记录条数
		Integer total = queryForInt(countSql, params);
		return new Page(page, size, total, data);
	}
}