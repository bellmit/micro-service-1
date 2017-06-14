package com.task.schedule.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;
import com.task.schedule.comm.model.MyPage;
import com.task.schedule.manager.pojo.TaskProject;
import com.task.schedule.manager.service.TaskProjectService;

@RestController
public class ApiTaskProjectController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiTaskProjectController.class);
	@Autowired
	private TaskProjectService taskProjectService;

	@RequestMapping(name = "taskProject-分页获取信息", value = "/api/taskProject/pageQuery")
	@ResponseBody
	public ResponseFrame pageQuery(TaskProject taskProject) {
		ResponseFrame frame = new ResponseFrame();
		try {
			MyPage<TaskProject> page = taskProjectService.pageQuery(taskProject);
			frame.setBody(page);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}
	
	@RequestMapping(name = "taskProject-保存", value = "/api/taskProject/save")
	@ResponseBody
	public ResponseFrame save(TaskProject taskProject) {
		ResponseFrame frame = new ResponseFrame();
		try {
			if(taskProject.getAdduser() == null) {
				frame.setCode(-2);
				frame.setMessage("请传入操作用户");
				return frame;
			}
			if(taskProject.getId() == null) {
				taskProjectService.save(taskProject);
			} else {
				taskProjectService.update(taskProject);
			}
			frame.setBody(taskProject);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}
	
	@RequestMapping(name = "taskProject-get", value = "/api/taskProject/get")
	@ResponseBody
	public ResponseFrame get(Integer id) {
		ResponseFrame frame = new ResponseFrame();
		try {
			TaskProject taskProject = taskProjectService.get(id);
			frame.setBody(taskProject);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}

	@RequestMapping(name = "taskProject-删除", value = "/api/taskProject/delete")
	@ResponseBody
	public ResponseFrame delete(Integer id) {
		ResponseFrame frame = new ResponseFrame();
		try {
			taskProjectService.delete(id);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}

}