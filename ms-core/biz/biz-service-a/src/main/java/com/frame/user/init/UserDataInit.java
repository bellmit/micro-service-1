package com.frame.user.init;

import org.apache.log4j.Logger;

import com.frame.user.service.UserInfoService;
import com.frame.user.service.UserRoleService;
import com.system.comm.utils.FrameSpringBeanUtil;

/**
 * 初始化数据
 * @author yuejing
 *
 */
public class UserDataInit {

	private static final Logger LOGGER = Logger.getLogger(UserDataInit.class);
	
	public static void init() {
		LOGGER.info("||==========================================");
		LOGGER.info("||===== 初始化 user 模块相关的表结构...");
		UserInfoService userInfoService = FrameSpringBeanUtil.getBean(UserInfoService.class);
		userInfoService.initTable();

		UserRoleService userRoleService = FrameSpringBeanUtil.getBean(UserRoleService.class);
		userRoleService.initTable();

		LOGGER.info("||===== 初始化 user 模块相关的表结构 全部完成!");
		LOGGER.info("||==========================================");
	}
}
