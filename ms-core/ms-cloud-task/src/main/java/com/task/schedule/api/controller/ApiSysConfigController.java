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
import com.task.schedule.manager.pojo.SysConfig;
import com.task.schedule.manager.service.SysConfigService;

@RestController
public class ApiSysConfigController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiSysConfigController.class);
	@Autowired
	private SysConfigService sysConfigService;

	@RequestMapping(name = "sysConfig-分页获取信息", value = "/api/sysConfig/pageQuery")
	@ResponseBody
	public ResponseFrame pageQuery(SysConfig sysConfig) {
		ResponseFrame frame = new ResponseFrame();
		try {
			MyPage<SysConfig> page = sysConfigService.pageQuery(sysConfig);
			frame.setBody(page);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}
	
	@RequestMapping(name = "sysConfig-保存", value = "/api/sysConfig/save")
	@ResponseBody
	public ResponseFrame save(SysConfig sysConfig) {
		ResponseFrame frame = new ResponseFrame();
		try {
			if(sysConfig.getId() == null) {
				sysConfigService.save(sysConfig);
			} else {
				sysConfigService.update(sysConfig);
			}
			frame.setBody(sysConfig);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("操作异常: " + e.getMessage(), e);
			frame.setCode(ResponseCode.FAIL.getCode());
			frame.setMessage(ResponseCode.FAIL.getMessage());
		}
		return frame;
	}
	
	@RequestMapping(name = "sysConfig-get", value = "/api/sysConfig/get")
	@ResponseBody
	public ResponseFrame get(Integer id) {
		ResponseFrame frame = new ResponseFrame();
		try {
			SysConfig data = sysConfigService.get(id);
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