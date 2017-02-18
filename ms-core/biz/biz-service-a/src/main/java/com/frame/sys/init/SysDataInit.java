package com.frame.sys.init;

import org.apache.log4j.Logger;

import com.frame.sys.service.SysDictService;
import com.frame.sys.service.SysDictTypeService;
import com.frame.sys.service.SysResService;
import com.frame.sys.service.SysRoleResService;
import com.frame.sys.service.SysRoleService;
import com.system.comm.utils.FrameSpringBeanUtil;

/**
 * 初始化数据
 * @author yuejing
 *
 */
public class SysDataInit {

	private static final Logger LOGGER = Logger.getLogger(SysDataInit.class);
	
	public static void init() {
		LOGGER.info("||==========================================");
		LOGGER.info("||===== 初始化 sys 模块相关的表结构...");
		SysDictTypeService dictTypeService = FrameSpringBeanUtil.getBean(SysDictTypeService.class);
		dictTypeService.initTable();
		
		SysDictService dictService = FrameSpringBeanUtil.getBean(SysDictService.class);
		dictService.initTable();
		
		SysResService resService = FrameSpringBeanUtil.getBean(SysResService.class);
		resService.initTable();

		SysRoleService roleService = FrameSpringBeanUtil.getBean(SysRoleService.class);
		roleService.initTable();

		SysRoleResService roleResService = FrameSpringBeanUtil.getBean(SysRoleResService.class);
		roleResService.initTable();

		LOGGER.info("||===== 初始化 sys 模块相关的表结构 全部完成!");
		LOGGER.info("||==========================================");
	}
}
