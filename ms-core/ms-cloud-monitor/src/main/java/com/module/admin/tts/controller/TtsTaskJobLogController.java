package com.module.admin.tts.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.module.admin.BaseController;
import com.module.admin.sys.pojo.SysUser;
import com.module.admin.tts.service.TtsTaskJobLogService;
import com.module.admin.tts.service.TtsTaskJobService;
import com.module.admin.tts.utils.TaskUtil;
import com.system.comm.utils.FrameMapUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * task_project的Controller
 * @author yuejing
 * @date 2015-03-30 14:07:28
 * @version V1.0.0
 */
@Controller
public class TtsTaskJobLogController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(TtsTaskJobLogController.class);
	@Autowired
	private TtsTaskJobService ttsTaskJobService;
	@Autowired
	private TtsTaskJobLogService ttsTaskJobLogService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ttsTaskJobLog/f-view/manager")
	public String manger(HttpServletRequest request, ModelMap modelMap) {
		return "admin/tts/task/jobLog-manager";
	}

	@RequestMapping(value = "/ttsTaskJobLog/f-view/look")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer id, Integer projectid) {
		if(id != null) {
			Map<String, Object> taskJobLog = ttsTaskJobLogService.get(id);
			Integer jobid = FrameMapUtil.getInteger(taskJobLog, "jobid");
			Map<String, Object> taskJob = ttsTaskJobService.get(jobid);
			modelMap.put("taskJobLog", taskJobLog);
			modelMap.put("taskJob", taskJob);
		}
		return "admin/tts/task/jobLog-look";
	}
	
	@RequestMapping(value = "/ttsTaskJobLog/f-json/{method}")
	@ResponseBody
	public void method(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("method")String method) {
		Map<String, Object> paramsMap = getParamsMap(request);
		/*if("saveOrUpdate".equals(method)) {
			String roleIds = request.getParameter("roleIds");
			roleIds = HtmlUtils.htmlUnescape(roleIds);
			paramsMap.put("roleIds", roleIds);
		}*/
		if("save".equals(method)) {
			SysUser loginUser = getSessionUser(request);
			//部门
			//paramsMap.put("depId", loginUser.getDepId());
			//设置添加人为当前登录用户
			paramsMap.put("adduser", loginUser.getUserId());
		}
		ResponseFrame frame = null;
		try {
			frame = TaskUtil.post("/api/taskJobLog/" + method, paramsMap);
		} catch (IOException e) {
			LOGGER.error("请求异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
}