package com.module.admin.tts.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.module.admin.tts.service.TtsSysConfigService;
import com.module.admin.tts.utils.TaskUtil;
import com.system.handle.model.ResponseFrame;

@Component
public class TtsSysConfigServiceImpl implements TtsSysConfigService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TtsSysConfigServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> get(Integer id) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("id", id);
		try {
			ResponseFrame frame = TaskUtil.post("/api/sysConfig/get", paramsMap);
			return (Map<String, Object>) frame.getBody();
		} catch (IOException e) {
			LOGGER.error("请求异常: " + e.getMessage(), e);
		}
		return new HashMap<String, Object>();
	}

}
