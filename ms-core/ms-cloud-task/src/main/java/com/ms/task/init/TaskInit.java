package com.ms.task.init;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.comm.utils.FrameTimeUtil;
import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.constants.DictCons;
import com.task.schedule.core.exec.TaskManager;
import com.task.schedule.manager.service.ServInfoService;

/**
 * 初始化系统数据的Servlet
 * @author yuejing
 * @date 2016-5-16 下午9:54:12
 * @version V1.0.0
 */
@WebServlet(urlPatterns="/TaskInit", description="初始数据", loadOnStartup=1)
public class TaskInit extends HttpServlet {
	private static final long serialVersionUID = 5764818908745849607L;
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskInit.class);

	/**
	 * 初始化方法
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		LOGGER.info("初始化数据中...");
		long startTime = System.currentTimeMillis();
		Constant.webroot = config.getServletContext().getContextPath();
		config.getServletContext().setAttribute("webroot", Constant.webroot);
		//版本号为年月日[如: 20130126]
		String version = String.format("?version=%s", FrameTimeUtil.parseString(FrameTimeUtil.getTime(), FrameTimeUtil.FMT_YYYYMMDDHH));
		config.getServletContext().setAttribute("version", version);
		
		//初始化字典信息
		DictCons.init(config.getServletContext());
		
		//生成服务信息
		ServInfoService servInfoService = (ServInfoService)FrameSpringBeanUtil.getBean(ServInfoService.class);
		servInfoService.registerServer();
		
		//添加定时任务
		TaskManager taskManager = (TaskManager)FrameSpringBeanUtil.getBean(TaskManager.class);
		taskManager.init();
		
		LOGGER.info("初始化资源花费" + (System.currentTimeMillis() - startTime) + "毫秒!");
	}

}
