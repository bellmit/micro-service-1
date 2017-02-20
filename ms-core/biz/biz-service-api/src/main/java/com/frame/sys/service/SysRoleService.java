package com.frame.sys.service;

import org.springframework.stereotype.Component;

import com.frame.sys.pojo.SysRole;
import com.system.handle.model.ResponseFrame;

@Component
public interface SysRoleService {

	public SysRole get(String roleId);
	public void initTable();
	public ResponseFrame pageQuery(SysRole sysRole);
}