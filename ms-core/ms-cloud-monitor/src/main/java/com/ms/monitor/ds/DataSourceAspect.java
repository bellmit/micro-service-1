package com.ms.monitor.ds;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.system.ds.DbContextHolder;

@Aspect
@Order(0)
@Component
public class DataSourceAspect {
    
    @Before("execution(* com.module.admin.*.dao.*.*(..)) || execution(* com.module.admin.*.service.*.*(..))")
    public void frame() {
    	DbContextHolder.setDbType("dataSource1");
    }
/*
    @Before("execution(* com.frame.user.dao.*.*(..)) || execution(* com.frame.user.service.*.*(..))")
    public void user() {
    	DbContextHolder.setDbType("dataSource2");
    }*/
}