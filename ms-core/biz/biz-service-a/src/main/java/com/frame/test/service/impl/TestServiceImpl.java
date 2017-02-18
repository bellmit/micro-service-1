package com.frame.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.test.dao.TestDao;
import com.frame.test.pojo.Test;
import com.frame.test.service.TestService;
import com.system.handle.model.ResponseFrame;

@Component
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;
	
	@Override
	public ResponseFrame save(Test test) {
		ResponseFrame frame = new ResponseFrame();
		testDao.save(test);
		frame.setSucc();
		return frame;
	}

	@Override
	public Test get(String id) {
		return testDao.get(id);
	}

}
