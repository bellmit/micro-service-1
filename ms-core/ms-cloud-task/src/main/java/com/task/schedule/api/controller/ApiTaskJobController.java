package com.task.schedule.api.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;
import com.task.schedule.comm.model.MyPage;
import com.task.schedule.manager.pojo.TaskJob;
import com.task.schedule.manager.service.TaskJobService;

@RestController
public class ApiTaskJobController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiTaskJobController.class);
	@Autowired
	private TaskJobService taskJobService;

	@RequestMapping(name = "taskJob-分页获取信息", value = "/api/taskJob/pageQuery")
	@ResponseBody
	public ResponseFrame pageQuery(TaskJob taskJob) {
		ResponseFrame frame = new ResponseFrame();
		try {
			MyPage<TaskJob> page = taskJobService.pageQuery(taskJob);
			frame.setBody(page);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}
	
	@RequestMapping(name = "taskJob-保存", value = "/api/taskJob/save")
	@ResponseBody
	public ResponseFrame save(TaskJob taskJob) {
		ResponseFrame frame = new ResponseFrame();
		try {
			if(taskJob.getAdduser() == null) {
				frame.setCode(-2);
				frame.setMessage("请传入操作人");
				return frame;
			}
			if(taskJob.getId() == null) {
				taskJobService.save(taskJob);
			} else {
				taskJobService.update(taskJob);
			}
			frame.setBody(taskJob);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}

	@RequestMapping(name = "taskJob-删除", value = "/api/taskJob/delete")
	@ResponseBody
	public ResponseFrame delete(Integer id) {
		ResponseFrame frame = new ResponseFrame();
		try {
			taskJobService.delete(id);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}
	
	@RequestMapping(name = "taskJob-修改任务状态", value = "/api/taskJob/updateStatus")
	@ResponseBody
	public ResponseFrame updateStatus(Integer id, Integer status) {
		ResponseFrame frame = new ResponseFrame();
		try {
			taskJobService.updateStatus(id, status);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}
	
	@RequestMapping(name = "taskJob-执行job", value = "/api/taskJob/execJob")
	@ResponseBody
	public ResponseFrame execJob(Integer id) {
		ResponseFrame frame = new ResponseFrame();
		try {
			taskJobService.execJob(id);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}

	@RequestMapping(name = "taskJob-获取项目状态的任务数目", value = "/api/taskJob/findProjectidCount")
	@ResponseBody
	public ResponseFrame findProjectidCount() {
		ResponseFrame frame = new ResponseFrame();
		try {
			List<Map<String, Object>> data = taskJobService.findProjectidCount();
			frame.setBody(data);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}
	
	@RequestMapping(name = "taskJob-get", value = "/api/taskJob/get")
	@ResponseBody
	public ResponseFrame get(Integer id) {
		ResponseFrame frame = new ResponseFrame();
		try {
			TaskJob data = taskJobService.get(id);
			frame.setBody(data);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}

	@RequestMapping(name = "taskJob-获取正常执行的任务数目", value = "/api/taskJob/findServidCount")
	@ResponseBody
	public ResponseFrame findServidCount() {
		ResponseFrame frame = new ResponseFrame();
		try {
			List<Map<String, Object>> data = taskJobService.findServidCount();
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