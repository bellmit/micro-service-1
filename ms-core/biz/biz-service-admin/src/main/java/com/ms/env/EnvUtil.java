package com.ms.env;

import org.springframework.core.env.Environment;

import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.comm.utils.FrameStringUtil;

public class EnvUtil {

	/**
	 * 获取http.port的值
	 * @return
	 */
	public static String getHttpPort() {
		return get("http.port");
	}
	/**
	 * 获取project.model的值<br>
	 * 项目模式[dev开发、test测试、release正式]
	 * @return
	 */
	public static String getProjectModel() {
		return get("project.model");
	}

	/**
	 * 获取属性的值
	 * @param env
	 * @return
	 */
	public static String get(String code) {
		Environment environment = FrameSpringBeanUtil.getBean(Environment.class);
		return environment.getProperty(code);
	}
	
	/**
	 * 获取项目模式[dev开发、test测试、release正式]
	 * @return
	 */
	public static String projectMode() {
		String model = getProjectModel();
		return FrameStringUtil.isEmpty(model) ? "dev" : model;
	}
	public static boolean projectModeIsDev() {
		return "dev".equals(getProjectModel()) ? true : false;
	}
	public static boolean projectModeIsTest() {
		return "test".equals(getProjectModel()) ? true : false;
	}
	public static boolean projectModeIsRelease() {
		return "release".equals(getProjectModel()) ? true : false;
	}
}
