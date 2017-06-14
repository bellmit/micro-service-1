package com.module.admin.tts.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.module.admin.tts.service.TtsTaskJobService;
import com.module.admin.tts.utils.TaskUtil;
import com.system.handle.model.ResponseFrame;

@SuppressWarnings("unchecked")
@Component
public class TtsTaskJobServiceImpl implements TtsTaskJobService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TtsTaskJobServiceImpl.class);
	
	@Override
	public List<Map<String, Object>> findProjectidCount() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			ResponseFrame frame = TaskUtil.post("/api/taskJob/findProjectidCount", paramsMap);
			return (List<Map<String, Object>>) frame.getBody();
		} catch (IOException e) {
			LOGGER.error("请求异常: " + e.getMessage(), e);
		}
		return new ArrayList<Map<String, Object>>();
	}

	@Override
	public Map<String, Object> get(Integer id) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", id);
		try {
			ResponseFrame frame = TaskUtil.post("/api/taskJob/get", paramsMap);
			return (Map<String, Object>) frame.getBody();
		} catch (IOException e) {
			LOGGER.error("请求异常: " + e.getMessage(), e);
		}
		return new HashMap<String, Object>();
	}

	@Override
	public List<Map<String, Object>> findServidCount() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			ResponseFrame frame = TaskUtil.post("/api/taskJob/findServidCount", paramsMap);
			return (List<Map<String, Object>>) frame.getBody();
		} catch (IOException e) {
			LOGGER.error("请求异常: " + e.getMessage(), e);
		}
		return new ArrayList<Map<String, Object>>();
	}

}
