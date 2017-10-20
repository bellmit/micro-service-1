package com.frame.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.user.dao.UserLogDao;
import com.frame.user.pojo.UserLog;
import com.frame.user.service.UserLogService;
import com.system.comm.utils.FrameNoUtil;
import com.system.handle.model.ResponseFrame;

@Component
public class UserLogServiceImpl implements UserLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserLogServiceImpl.class);
	@Autowired
	private UserLogDao userLogDao;

	@Override
	public void initTable() {
		if(!isExistTable()) {
			userLogDao.createTable();
			LOGGER.info("||===== 初始化 [ user_log ] 表结构成功!");
		}
	}

	private boolean isExistTable() {
		try {
			userLogDao.isExistTable();
			return true;
		} catch (Exception e) {
			LOGGER.error("表[user_log]不存在: " + e.getMessage());
		}
		return false;
	}

	@Override
	public ResponseFrame save(UserLog userLog) {
		ResponseFrame frame = new ResponseFrame();
		userLog.setLogId(FrameNoUtil.uuid());
		userLogDao.save(userLog);
		frame.setSucc();
		return frame;
	}
}