package com.frame.sys.service;

import org.springframework.stereotype.Component;

import com.frame.sys.pojo.SysRoleRes;
import com.system.handle.model.ResponseFrame;

@Component
public interface SysRoleResService {

	public void initTable();

	public ResponseFrame save(SysRoleRes sysRoleRes);
}