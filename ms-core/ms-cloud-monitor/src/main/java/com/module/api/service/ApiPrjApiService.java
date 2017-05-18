package com.module.api.service;

import java.util.List;
import java.util.Map;

import com.system.handle.model.ResponseFrame;

public interface ApiPrjApiService {

	public ResponseFrame saveBatch(String code, List<Map<String, String>> details);

}
