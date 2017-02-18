package com.frame.user.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.sys.pojo.SysRes;
import com.frame.user.dao.UserRoleDao;
import com.frame.user.pojo.UserRole;
import com.frame.user.service.UserRoleService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@Component
public class UserRoleServiceImpl implements UserRoleService {

	private static final Logger LOGGER = Logger.getLogger(UserRoleServiceImpl.class);
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public void initTable() {
		if(!isExistTable()) {
			userRoleDao.createTable();
			LOGGER.info("||===== 初始化 [ user_role ] 表结构成功!");
			
			initAdmin();
		}
	}

	private boolean isExistTable() {
		try {
			userRoleDao.isExistTable();
			return true;
		} catch (Exception e) {
			LOGGER.error("表[user_role]不存在: " + e.getMessage());
		}
		return false;
	}
	private void initAdmin() {
		//初始化管理员帐号
		UserRole admin = new UserRole();
		admin.setRoleId("admin");
		admin.setUserId("admin");
		admin.setAddUserId("admin");
		save(admin);
	}
	
	@Override
	public ResponseFrame save(UserRole userRole) {
		ResponseFrame frame = new ResponseFrame();
		UserRole ur = userRoleDao.get(userRole.getUserId(), userRole.getRoleId());
		if(ur == null) {
			userRoleDao.save(userRole);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public List<SysRes> findResListByUserId(String userId) {
		return userRoleDao.findResListByUserId(userId);
	}
}