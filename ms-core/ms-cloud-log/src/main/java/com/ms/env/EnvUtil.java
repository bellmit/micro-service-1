package com.ms.env;

import org.springframework.core.env.Environment;

import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.comm.utils.FrameStringUtil;

public class EnvUtil {

	/**
	 * 获取属性的值
	 * @param env
	 * @return
	 */
	public static String get(Env env) {
		return get(env.getCode());
	}
	public static String get(String code) {
		Environment environment = FrameSpringBeanUtil.getBean(Environment.class);
		return environment.getProperty(code);
	}
	
	/**
	 * 获取项目模式[dev开发、test测试、release正式]
	 * @return
	 */
	public static String projectMode() {
		String model = get(Env.PROJECT_MODEL);
		return FrameStringUtil.isEmpty(model) ? "dev" : model;
	}
	public static boolean projectModeIsDev() {
		return "dev".equals(get(Env.PROJECT_MODEL)) ? true : false;
	}
	public static boolean projectModeIsTest() {
		return "test".equals(get(Env.PROJECT_MODEL)) ? true : false;
	}
	public static boolean projectModeIsRelease() {
		return "release".equals(get(Env.PROJECT_MODEL)) ? true : false;
	}
}
