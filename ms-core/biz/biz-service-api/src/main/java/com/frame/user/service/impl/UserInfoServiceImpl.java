package com.frame.user.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.user.dao.UserInfoDao;
import com.frame.user.enums.UserInfoStatus;
import com.frame.user.pojo.UserInfo;
import com.frame.user.service.UserInfoService;
import com.frame.user.utils.UserUtil;
import com.system.comm.enums.Boolean;
import com.system.comm.model.Page;
import com.system.comm.utils.FrameNoUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@Component
public class UserInfoServiceImpl implements UserInfoService {

	private static final Logger LOGGER = Logger.getLogger(UserInfoServiceImpl.class);
	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public UserInfo get(String userId) {
		UserInfo userInfo = userInfoDao.get(userId);
		if(userInfo != null) {
			userInfo.setPassword(null);
		}
		return userInfo;
	}

	@Override
	public void initTable() {
		if(!isExistTable()) {
			userInfoDao.createTable();
			LOGGER.info("||===== 初始化 [ user_info ] 表结构成功!");
			
			initAdmin();
			LOGGER.info("||===== 初始化 [ admin ] 账户信息成功!");
		}
	}

	private boolean isExistTable() {
		try {
			userInfoDao.isExistTable();
			return true;
		} catch (Exception e) {
			LOGGER.error("表[user_info]不存在: " + e.getMessage());
		}
		return false;
	}

	private void initAdmin() {
		//初始化管理员帐号
		UserInfo admin = new UserInfo();
		admin.setUserId("admin");
		admin.setUserName("admin");
		admin.setPassword("123456");
		admin.setName("超级管理员");
		admin.setAddUserId("admin");
		save(admin, Boolean.FALSE.getCode());
	}

	private void save(UserInfo userInfo, Integer isEncryption) {
		if(FrameStringUtil.isEmpty(userInfo.getUserId())) {
			userInfo.setUserId(FrameNoUtil.uuid());
		}
		if(isEncryption == null || isEncryption.intValue() == 0) {
			//密码加密
			userInfo.setPassword(UserUtil.encryptionPassword(userInfo.getPassword()));
		}
		if(userInfo.getIsDelete() == null) {
			userInfo.setIsDelete(Boolean.FALSE.getCode());
		}
		if(FrameStringUtil.isEmpty(userInfo.getName())) {
			userInfo.setName(userInfo.getUserName());
		}
		if(userInfo.getStatus() == null) {
			userInfo.setStatus(UserInfoStatus.NORMAL.getCode());
		}
		userInfoDao.save(userInfo);
	}

	@Override
	public ResponseFrame login(String userName, String password, Integer isEncryption) {
		ResponseFrame frame = new ResponseFrame();
		UserInfo ui = userInfoDao.getByUserName(userName);
		if(ui == null || Boolean.TRUE.getCode() == ui.getIsDelete().intValue()) {
			frame.setCode(-2);
			frame.setMessage("不存在该用户");
			return frame;
		}
		if(UserInfoStatus.NORMAL.getCode() != ui.getStatus().intValue()) {
			frame.setCode(-3);
			frame.setMessage("帐号被冻结");
			return frame;
		}
		boolean pwd = false;
		if((isEncryption == null || isEncryption.intValue() == 0) && !ui.getPassword().equals(UserUtil.encryptionPassword(password))) {
			pwd = true;
		} else if(isEncryption.intValue() == 1 && !ui.getPassword().equals(password)) {
			pwd = true;
		}
		if(pwd) {
			frame.setCode(-4);
			frame.setMessage("密码错误");
			return frame;
		}
		
		frame.setBody(ui);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public ResponseFrame updatePassword(String userId, String oldPwd, String newPwd, Integer isEncryption) {
		ResponseFrame frame = new ResponseFrame();
		if(isEncryption == null || isEncryption.intValue() == 0) {
			oldPwd = UserUtil.encryptionPassword(oldPwd);
			newPwd = UserUtil.encryptionPassword(newPwd);
		}
		UserInfo org = get(userId);
		if(org == null) {
			frame.setCode(-2);
			frame.setMessage("不存在用户");
			return frame;
		}
		if(!org.getPassword().equals(oldPwd)) {
			frame.setCode(-3);
			frame.setMessage("原始密码输入错误");
			return frame;
		}
		userInfoDao.updatePassword(userId, newPwd);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public ResponseFrame resetPwd(String userId, String password, Integer isEncryption) {
		ResponseFrame frame = new ResponseFrame();
		if(isEncryption == null || isEncryption.intValue() == 0) {
			password = UserUtil.encryptionPassword(password);
		}
		UserInfo org = get(userId);
		if(org == null) {
			frame.setCode(-2);
			frame.setMessage("不存在用户");
			return frame;
		}
		userInfoDao.updatePassword(userId, password);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public ResponseFrame pageQuery(UserInfo userInfo) {
		userInfo.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = userInfoDao.findUserInfoCount(userInfo);
		List<UserInfo> data = null;
		if(total > 0) {
			data = userInfoDao.findUserInfo(userInfo);
			for (UserInfo ui : data) {
				ui.setPassword(null);
				ui.setStatusName(UserInfoStatus.getText(ui.getStatus()));
			}
		}
		
		Page<UserInfo> page = new Page<UserInfo>(userInfo.getPage(), userInfo.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public ResponseFrame updateIsDelete(String userId, Integer isDelete) {
		ResponseFrame frame = new ResponseFrame();
		userInfoDao.updateIsDelete(userId, isDelete);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public ResponseFrame saveOrUpdate(UserInfo userInfo, Integer isEncryption) {
		ResponseFrame frame = new ResponseFrame();
		if(FrameStringUtil.isEmpty(userInfo.getUserId())) {
			UserInfo org = userInfoDao.getByUserName(userInfo.getUserName());
			if(org != null) {
				frame.setCode(-2);
				frame.setMessage("新增的用户名已被使用");
				return frame;
			}
			save(userInfo, isEncryption);
		} else {
			userInfoDao.update(userInfo);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

}