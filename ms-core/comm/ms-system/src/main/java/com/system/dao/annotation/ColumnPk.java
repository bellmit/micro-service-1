package com.system.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value=ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnPk {
	
	/**
	 * 是否自增长
	 * @return
	 */
	public boolean isAuto() default false;
	/**
	 * oracle序列
	 * @return
	 */
	public String sequence() default "";
}
