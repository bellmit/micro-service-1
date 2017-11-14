package com.monitor.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitor.api.ApiInfo;
import com.monitor.api.ApiParam;
import com.monitor.api.ApiRes;
import com.system.cache.redis.BaseCache;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@RestController
public class CacheController extends BaseCache {

	private final static Logger LOGGER = LoggerFactory.getLogger(CacheController.class);

	@RequestMapping(name = "缓存-获取所有key", value = "/cache/findKey")
	@ApiInfo(params = {
			@ApiParam(name="key[adc*代表查询adc开头的所有key]", code="key", value="abc*"),
		}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=List.class, value="")
	})
	public ResponseFrame findKey(String key) {
		try {
			ResponseFrame frame = new ResponseFrame();
			frame.setBody(super.keys(key));
			frame.setSucc();
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
	
	@RequestMapping(name = "缓存-删除key", value = "/cache/delete")
	@ApiInfo(params = {
			@ApiParam(name="key", code="key", value="abc"),
		}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value="")
	})
	public ResponseFrame del(String key) {
		try {
			ResponseFrame frame = new ResponseFrame();
			super.delete(key);
			frame.setSucc();
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(name = "缓存-批量删除key", value = "/cache/deleteBatch")
	@ApiInfo(params = {
			@ApiParam(name="key[adc*代表删除adc开头的所有key]", code="key", value="abc*"),
		}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value="")
	})
	public ResponseFrame delBatch(String key) {
		try {
			ResponseFrame frame = new ResponseFrame();
			super.deleteBatch(key);
			frame.setSucc();
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}