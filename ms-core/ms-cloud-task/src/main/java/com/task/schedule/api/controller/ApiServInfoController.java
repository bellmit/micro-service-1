package com.task.schedule.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;
import com.task.schedule.comm.model.MyPage;
import com.task.schedule.manager.pojo.ServInfo;
import com.task.schedule.manager.service.ServInfoService;

@RestController
public class ApiServInfoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiServInfoController.class);
	@Autowired
	private ServInfoService servInfoService;

	@RequestMapping(name = "servInfo-分页获取信息", value = "/api/servInfo/pageQuery")
	@ResponseBody
	public ResponseFrame pageQuery(ServInfo servInfo) {
		ResponseFrame frame = new ResponseFrame();
		try {
			MyPage<ServInfo> page = servInfoService.pageQuery(servInfo);
			frame.setBody(page);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}
	
	@RequestMapping(name = "servInfo-根据状态获取服务集合", value = "/api/servInfo/findByStatus")
	@ResponseBody
	public ResponseFrame findByStatus(Integer status) {
		ResponseFrame frame = new ResponseFrame();
		try {
			List<ServInfo> data = servInfoService.findByStatus(status);
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