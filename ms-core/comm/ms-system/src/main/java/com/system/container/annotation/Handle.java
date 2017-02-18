package com.system.container.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 参照spring源码<br>
 * https://github.com/spring-projects/spring-framework/releases/tag/
 * @author yuejing
 * @date 2017年1月24日 上午8:57:50
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Handle {

	/**
	 * @return 返回对应的请求大类
	 */
	String value() default "";

}