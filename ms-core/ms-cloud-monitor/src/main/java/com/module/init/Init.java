package com.module.init;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.module.comm.constants.ConfigCons;
import com.module.comm.constants.DictCons;
import com.module.task.TaskManager;
import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.comm.utils.FrameTimeUtil;

/**
 * 初始化系统数据的Servlet
 * @author yuejing
 * @date 2016-5-16 下午9:54:12
 * @version V1.0.0
 */
@WebServlet(urlPatterns="/init", description="初始数据", loadOnStartup=1)
public class Init extends HttpServlet {
	private static final long serialVersionUID = 5764818908745849607L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Init.class);

	/**
	 * 初始化方法
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		LOGGER.info("初始化数据中...");
		long startTime = System.currentTimeMillis();
		ConfigCons.webroot = config.getServletContext().getContextPath();
		//ConfigCons.webroot = "/admin";
		config.getServletContext().setAttribute("webroot", ConfigCons.webroot);
		//版本号为年月日[如: 20130126]
		String version = String.format("?version=%s", FrameTimeUtil.parseString(FrameTimeUtil.getTime(), FrameTimeUtil.FMT_YYYYMMDDHH));
		config.getServletContext().setAttribute("version", version);
		config.getServletContext().setAttribute("projectName", ConfigCons.projectName);

		/*SysConfigService configService = FrameSpringBeanUtil.getBean(SysConfigService.class);
		config.getServletContext().setAttribute("prjFilePath", configService.getValue(SysConfigCode.PRJ_FILE_PATH));
*/
		ConfigCons.realpath = config.getServletContext().getRealPath("");
		
		//初始化数据
		DictCons.init(config.getServletContext());
		
		//初始化任务
		TaskManager taskManager = FrameSpringBeanUtil.getBean(TaskManager.class);
		taskManager.init();
		
		LOGGER.info("初始化资源花费" + (System.currentTimeMillis() - startTime) + "毫秒!");
	}


}
