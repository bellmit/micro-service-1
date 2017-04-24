package com.module.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.module.admin.BaseController;
import com.module.api.service.ApiMsConfigService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 客户端调用的接口
 * @author 岳静
 * @date 2016年3月4日 下午6:22:39 
 * @version V1.0
 */
@Controller
public class ApiMsConfigController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiMsConfigController.class);
	
	@Autowired
	private ApiMsConfigService apiMsConfigService;

	/**
	 * 获取所有的配置文件
	 */
	@RequestMapping(value = "/api/msConfig/findAll")
	@ResponseBody
	public void findAll(HttpServletRequest request, HttpServletResponse response) {
		ResponseFrame frame = null;
		try {
			frame = apiMsConfigService.findAll();
		} catch (Exception e) {
			LOGGER.error("修改客户端发布项目成功异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		writerJson(response, frame);
	}
}