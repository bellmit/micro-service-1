package com.module.admin.tts.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface TtsServInfoService {

	/**
	 * 根据状态获取服务信息
	 * @param status
	 * @return
	 */
	public List<Map<String, Object>> findByStatus(Integer status);

}
