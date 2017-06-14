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
import com.task.schedule.manager.pojo.TaskJobLog;
import com.task.schedule.manager.service.TaskJobLogService;

@RestController
public class ApiTaskJobLogController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiTaskJobLogController.class);
	@Autowired
	private TaskJobLogService taskJobLogService;

	@RequestMapping(name = "taskJobLog-分页获取信息", value = "/api/taskJobLog/pageQuery")
	@ResponseBody
	public ResponseFrame pageQuery(TaskJobLog taskJobLog) {
		ResponseFrame frame = new ResponseFrame();
		try {
			MyPage<TaskJobLog> page = taskJobLogService.pageQuery(taskJobLog);
			frame.setBody(page);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}
	
	@RequestMapping(name = "taskJobLog-get", value = "/api/taskJobLog/get")
	@ResponseBody
	public ResponseFrame get(Integer id) {
		ResponseFrame frame = new ResponseFrame();
		try {
			TaskJobLog data = taskJobLogService.get(id);
			frame.setBody(data);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}
}