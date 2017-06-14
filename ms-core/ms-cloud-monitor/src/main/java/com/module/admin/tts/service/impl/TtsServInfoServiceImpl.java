package com.module.admin.tts.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.module.admin.tts.service.TtsServInfoService;
import com.module.admin.tts.utils.TaskUtil;
import com.system.handle.model.ResponseFrame;

@Component
public class TtsServInfoServiceImpl implements TtsServInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TtsServInfoServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findByStatus(Integer status) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("status", status);
		try {
			ResponseFrame frame = TaskUtil.post("/api/servInfo/findByStatus", paramsMap);
			return (List<Map<String, Object>>) frame.getBody();
		} catch (IOException e) {
			LOGGER.error("请求异常: " + e.getMessage(), e);
		}
		return new ArrayList<Map<String, Object>>();
	}

}
