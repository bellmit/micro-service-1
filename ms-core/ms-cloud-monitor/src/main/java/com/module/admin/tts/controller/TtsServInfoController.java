package com.module.admin.tts.controller;

import java.io.IOException;
import java.util.List;
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
import com.module.admin.tts.enums.ServInfoStatus;
import com.module.admin.tts.service.TtsServInfoService;
import com.module.admin.tts.service.TtsTaskJobService;
import com.module.admin.tts.utils.TaskUtil;
import com.system.comm.utils.FrameJsonUtil;
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
public class TtsServInfoController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(TtsServInfoController.class);
	@Autowired
	private TtsServInfoService ttsServInfoService;
	@Autowired
	private TtsTaskJobService ttsTaskJobService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ttsServInfo/f-view/manager")
	public String manger(HttpServletRequest request, ModelMap modelMap) {
		return "admin/tts/serv/servInfo-manager";
	}

	@RequestMapping(value = "/ttsServInfo/f-view/chart")
	public String chart(HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, Object>> servInfos = ttsServInfoService.findByStatus(ServInfoStatus.NORMAL.getCode());
		List<Map<String, Object>> servCounts = ttsTaskJobService.findServidCount();
		for (Map<String, Object> si : servInfos) {
			//设置任务总数
			for (Map<String, Object> map : servCounts) {
				String servid = FrameMapUtil.getString(si, "servid");
				if(servid.equals(FrameMapUtil.getString(map, "servid"))) {
					si.put("jobnum", FrameMapUtil.getInteger(map, "total"));
					break;
				}
			}
		}
		modelMap.put("servInfosJson", FrameJsonUtil.toString(servInfos));
		return "admin/tts/serv/servInfo-chart";
	}
	
	@RequestMapping(value = "/ttsServInfo/f-json/{method}")
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
			frame = TaskUtil.post("/api/servInfo/" + method, paramsMap);
		} catch (IOException e) {
			LOGGER.error("请求异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
}