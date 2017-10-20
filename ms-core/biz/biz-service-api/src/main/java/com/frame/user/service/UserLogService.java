package com.frame.user.service;

import org.springframework.stereotype.Component;

import com.frame.user.pojo.UserLog;
import com.system.handle.model.ResponseFrame;

@Component
public interface UserLogService {

	public void initTable();
	public ResponseFrame save(UserLog userLog);
}