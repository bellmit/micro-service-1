package com.module.comm.csrf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Csrf过滤注解
 * @author yuejing
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsrfToken {
	/**
	 * 创建token
	 * @return
	 */
	boolean create() default false;
	/**
	 * 移除token
	 * @return
	 */
	boolean remove() default false;
}
