package com.module.admin.tts.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface TtsTaskJobService {

	/**
	 * 获取项目总数
	 * @return
	 */
	public List<Map<String, Object>> findProjectidCount();

	public Map<String, Object> get(Integer id);

	public List<Map<String, Object>> findServidCount();

}
