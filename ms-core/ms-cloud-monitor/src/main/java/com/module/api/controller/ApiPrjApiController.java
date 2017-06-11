package com.module.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.module.admin.BaseController;
import com.module.api.service.ApiPrjApiService;
import com.system.comm.utils.FrameJsonUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 项目注册api的接口
 * @author 岳静
 * @date 2016年3月4日 下午6:22:39 
 * @version V1.0
 */
@RestController
public class ApiPrjApiController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiPrjApiController.class);
	
	@Autowired
	private ApiPrjApiService apiPrjApiService;

	@RequestMapping(name = "批量保存项目API的功能", value = "/api/prjApi/saveBatch")
	@ResponseBody
	public ResponseFrame saveBatch(HttpServletRequest request, HttpServletResponse response,
			String code, String detailString) {
		ResponseFrame frame = null;
		try {
			List<Map<String, String>> details = FrameJsonUtil.toList(detailString, Map.class);
			frame = apiPrjApiService.saveBatch(code, details);
		} catch (Exception e) {
			LOGGER.error("修改客户端发布项目成功异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		return frame;
	}

}