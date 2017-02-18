package com.frame.user.service;

import org.springframework.stereotype.Component;

import com.frame.user.pojo.UserInfo;
import com.system.handle.model.ResponseFrame;

@Component
public interface UserInfoService {

	public UserInfo get(String userId);
	public void initTable();
	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @param isEncryption	密码是否加密[0否、1是]
	 * @return
	 */
	public ResponseFrame login(String userName, String password, Integer isEncryption);
	public ResponseFrame updatePassword(String userId, String oldPwd, String newPwd, Integer isEncryption);
	public ResponseFrame pageQuery(UserInfo userInfo);
	public ResponseFrame updateIsDelete(String userId, Integer isDelete);
	public ResponseFrame saveOrUpdate(UserInfo userInfo, Integer isEncryption);
	public ResponseFrame resetPwd(String userId, String password, Integer isEncryption);
}