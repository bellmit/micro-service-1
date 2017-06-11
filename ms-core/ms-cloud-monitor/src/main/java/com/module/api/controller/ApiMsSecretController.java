package com.module.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.module.api.service.ApiMsSecretService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 密钥的接口
 * @author 岳静
 * @date 2016年3月4日 下午6:22:39 
 * @version V1.0
 */
@RestController
public class ApiMsSecretController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiMsSecretController.class);
	
	@Autowired
	private ApiMsSecretService apiMsSecretService;

	/**
	 * 获取使用的密钥
	 */
	@RequestMapping(value = "/api/msSecret/findUse")
	@ResponseBody
	public ResponseFrame findUse(HttpServletRequest request, HttpServletResponse response) {
		ResponseFrame frame = null;
		try {
			frame = apiMsSecretService.findUse();
		} catch (Exception e) {
			LOGGER.error("获取使用的密钥异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		return frame;
	}
}