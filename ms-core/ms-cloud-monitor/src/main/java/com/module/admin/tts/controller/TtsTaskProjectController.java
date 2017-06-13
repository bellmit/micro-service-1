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
import com.module.admin.tts.enums.ProjectSign;
import com.module.admin.tts.service.TtsTaskJobService;
import com.module.admin.tts.service.TtsTaskProjectService;
import com.module.admin.tts.utils.TaskUtil;
import com.system.comm.utils.FrameJsonUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * task_project的Controller
 * @author yuejing
 * @date 2015-03-30 14:07:28
 * @version V1.0.0
 */
@Controller
public class TtsTaskProjectController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(TtsTaskProjectController.class);
	@Autowired
	private TtsTaskJobService ttsTaskJobService;
	@Autowired
	private TtsTaskProjectService ttsTaskProjectService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ttsTaskProject/f-view/manager")
	public String manger(HttpServletRequest request) {
		return "admin/tts/task/project-manager";
	}

	@RequestMapping(value = "/ttsTaskProject/f-view/chart")
	public String chart(HttpServletRequest request, ModelMap modelMap) {
		/*List<Map<String, Object>> projects = ttsTaskJobService.findProjectidCount();
		Map<Integer, String> projectnames = new HashMap<Integer, String>();
		for (Map<String, Object> map : projects) {
			Integer projectid = FrameMapUtil.getInteger(map, "projectid");
			String projectname = FrameMapUtil.getString(projectnames, projectid.toString());
			if(FrameStringUtil.isEmpty(projectname)) {
				projectname = ttsTaskProjectService.get(projectid).getName();
				projectnames.put(projectid, projectname);
			}
			map.put("projectname", projectname);
		}
		modelMap.put("projectsJson", FrameJsonUtil.toString(projects));*/
		return "manager/task/project_chart";
	}

	/**
	 * 跳转到编辑页[包含新增和编辑]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ttsTaskProject/f-view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer id) {
		if(id != null) {
			Map<String, Object> taskProject = ttsTaskProjectService.get(id);
			modelMap.put("taskProject", taskProject);
		}
		String projectSigns = FrameJsonUtil.toString(ProjectSign.getList());
		modelMap.put("projectSigns", projectSigns);
		return "admin/tts/task/project-edit";
	}
	
	@RequestMapping(value = "/ttsTaskProject/f-json/{method}")
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
			frame = TaskUtil.post("/api/taskProject/" + method, paramsMap);
		} catch (IOException e) {
			LOGGER.error("请求异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
}