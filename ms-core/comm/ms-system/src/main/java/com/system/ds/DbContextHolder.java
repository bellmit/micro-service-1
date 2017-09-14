package com.system.ds;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.comm.utils.FrameStringUtil;

/**
 * Db处理类<br>
 * 代码手动控制使用：<br>
 * DbContextHolder.setDbType("dataSource1");
 * AOP控制使用：<br>
 @Aspect
 public class DynamicDataSourceAspect {
     @Pointcut("execution (public service.impl..*.*(..))")
     public void serviceExecution(){}
     
     @Before("serviceExecution()")
     public void setDynamicDataSource(JoinPoint jp) {
         for(Object o : jp.getArgs()) {
             //处理具体的逻辑 ，根据具体的境况
               DbContextHolder.setDbType()选取DataSource
         }
     }
 }
 * @author yuejing
 */
public class DbContextHolder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DbContextHolder.class);
    @SuppressWarnings("rawtypes")
    private static final ThreadLocal dsNameLocal = new ThreadLocal();
    private static Map<String, String> dbTypeMap = new HashMap<String, String>();
    @SuppressWarnings("rawtypes")
	private static final ThreadLocal dbTypeLocal = new ThreadLocal();
 
    @SuppressWarnings("unchecked")
    public static void setDsName(String dsName) {
    	dsNameLocal.set(dsName);

        String dbt = dbTypeMap.get(dsName);
        if(FrameStringUtil.isEmpty(dbt)) {
        	dbt = getDbType(dsName);
        	dbTypeMap.put(dsName, dbt);
        }
        dbTypeLocal.set(dbt);
    }
    
    /**
     * 获取当前的数据库的类型
     * @return
     */
    public static String getDbType() {
    	return (String) dbTypeLocal.get();
    }
    
    private static String getDbType(String dsName) {
    	String dbType = null;
    	DynamicDataSource dataSource = FrameSpringBeanUtil.getBean(DynamicDataSource.class);
    	Map<Object, Object> dsMap = dataSource.getTargetDataSources();
    	DataSource cldDs = (DataSource) dsMap.get(dsName);
    	try {
			DatabaseMetaData md = cldDs.getConnection().getMetaData();
			dbType = md.getDatabaseProductName().toLowerCase();
		} catch (SQLException e) {
			LOGGER.error("获取数据库类型异常：" + e.getLocalizedMessage(), e);
		}
    	LOGGER.info("获取数据源[" + dsName + "] 的数据库类型为[" + dbType + "]");
    	return dbType;
		//DbUtil.dbVersion = md.getDatabaseProductVersion();
    }
 
    public static String getDsName() {
        return (String) dsNameLocal.get();
    }
 
    public static void clearDsName() {
    	dsNameLocal.remove();
    }
}