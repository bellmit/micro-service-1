package com.module.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.module.api.pojo.ZipkinAnnotations;
import com.module.api.service.ZipkinAnnotationsService;
import com.monitor.api.ApiInfo;
import com.monitor.api.ApiParam;
import com.monitor.api.ApiRes;
import com.system.comm.utils.FrameTimeUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 客户端调用的接口
 * @author 岳静
 * @date 2016年3月4日 下午6:22:39 
 * @version V1.0
 */
@RestController
public class ApiLogController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiLogController.class);
	@Autowired
	private ZipkinAnnotationsService zipkinAnnotationsService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(FrameTimeUtil.FMT_DEFAULT);
			dateFormat.setLenient(false);
			//true:允许输入空值，false:不能为空值
			binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		}catch(Exception e) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(FrameTimeUtil.FMT_YYYY_MM_DD);
			dateFormat.setLenient(false);
			//true:允许输入空值，false:不能为空值
			binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		}
	}
	
	@RequestMapping(name = "获取日志", value = "/api/log/find")
	@ApiInfo(params = {
			@ApiParam(name="key的值[INFO/ERROR]", code="key", clazz=String.class, value="INFO"),
			@ApiParam(name="开始日期", code="beginTime", clazz=String.class, value="2017-10-24 12:12:12"),
			@ApiParam(name="开始日期", code="endTime", clazz=String.class, value="2017-10-25 12:12:12")
	},
	response = {
			@ApiRes(name="响应码", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=List.class, value=""),
			@ApiRes(name="编号", code="traceId", pCode="body", clazz=String.class, value=""),
			@ApiRes(name="key的内容", code="aKey", pCode="body", clazz=String.class, value=""),
			@ApiRes(name="key的值", code="aValue", pCode="body", clazz=String.class, value=""),
			@ApiRes(name="添加时间", code="aTime", pCode="body", clazz=String.class, value="")
	})
	public ResponseFrame find(HttpServletRequest request, HttpServletResponse response,
			String key, Date beginTime, Date endTime) {
		ResponseFrame frame = new ResponseFrame();
		try {
			//frame = zipkinAnnotationsService.updateHeartbeat(clientId);
			//Date beginTime = FrameTimeUtil.parseDate("2017-10-22 10:10:10", "yyyy-MM-dd HH:mm:ss");
			//Date endTime = FrameTimeUtil.parseDate("2017-10-23 10:10:10", "yyyy-MM-dd HH:mm:ss");
			List<ZipkinAnnotations> list = zipkinAnnotationsService.findByAKeyATimestamp(key, beginTime, endTime);
			frame.setBody(list);
			frame.setSucc();
		} catch (Exception e) {
			LOGGER.error("获取日志异常: " + e.getMessage(), e);
			frame = new ResponseFrame(ResponseCode.FAIL);
		}
		return frame;
	}

}