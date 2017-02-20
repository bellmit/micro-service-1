package com.frame.sys.service;

import org.springframework.stereotype.Component;

import com.frame.sys.pojo.SysRes;
import com.system.handle.model.ResponseFrame;

@Component
public interface SysResService {

	public SysRes get(String resId);
	public void initTable();
	public ResponseFrame save(SysRes sysRes);
}