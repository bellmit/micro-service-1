package com.frame.sys.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.sys.dao.SysRoleResDao;
import com.frame.sys.pojo.SysRoleRes;
import com.frame.sys.service.SysRoleResService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@Component
public class SysRoleResServiceImpl implements SysRoleResService {

	private static final Logger LOGGER = Logger.getLogger(SysRoleResServiceImpl.class);
	@Autowired
	private SysRoleResDao sysRoleResDao;

	@Override
	public void initTable() {
		if(!isExistTable()) {
			sysRoleResDao.createTable();
			LOGGER.info("||===== 初始化 [ sys_role_res ] 表结构成功!");
		}
	}

	private boolean isExistTable() {
		try {
			sysRoleResDao.isExistTable();
			return true;
		} catch (Exception e) {
			LOGGER.error("表[sys_role_res]不存在: " + e.getMessage());
		}
		return false;
	}
	
	@Override
	public ResponseFrame save(SysRoleRes sysRoleRes) {
		ResponseFrame frame = new ResponseFrame();
		SysRoleRes srr = sysRoleResDao.get(sysRoleRes.getRoleId(), sysRoleRes.getResId());
		if(srr == null) {
			sysRoleResDao.save(sysRoleRes);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}