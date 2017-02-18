package com.system.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略字段
 * @author yuejing
 * @date 2017年1月26日 下午11:11:41
 * @version V1.0.0
 */
@Target(value=ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnIgnore {
	
}
