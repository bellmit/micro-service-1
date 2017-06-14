package com.module.admin.tts.service;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface TtsSysConfigService {

	public Map<String, Object> get(Integer id);

}
