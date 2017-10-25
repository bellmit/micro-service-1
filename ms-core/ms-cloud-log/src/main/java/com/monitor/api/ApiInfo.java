package com.monitor.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiInfo {

	//@AliasFor("value")
	ApiParam[] params();

	ApiRes[] response() default {};
	/*@AliasFor("params")
	ApiParam[] value();*/
}
