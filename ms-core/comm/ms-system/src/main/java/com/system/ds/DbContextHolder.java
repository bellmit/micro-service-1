package com.system.ds;

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
	
    @SuppressWarnings("rawtypes")
    private static final ThreadLocal contextHolder = new ThreadLocal();
 
    @SuppressWarnings("unchecked")
    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }
 
    public static String getDbType() {
        return (String) contextHolder.get();
    }
 
    public static void clearDbType() {
        contextHolder.remove();
    }
}