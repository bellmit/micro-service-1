package com.frame.test.service;

import org.springframework.stereotype.Component;

import com.frame.test.pojo.Test;
import com.system.handle.model.ResponseFrame;

@Component
public interface TestService {

	public ResponseFrame save(Test test);
	
	public Test get(String id);
}
