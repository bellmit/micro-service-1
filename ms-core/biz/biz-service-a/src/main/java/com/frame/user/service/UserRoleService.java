package com.frame.user.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.frame.sys.pojo.SysRes;
import com.frame.user.pojo.UserRole;
import com.system.handle.model.ResponseFrame;

@Component
public interface UserRoleService {

	public void initTable();
	public ResponseFrame save(UserRole userRole);
	/**
	 * 根据用户编号获取对应的资源集合
	 * @param userId
	 * @return
	 */
	public List<SysRes> findResListByUserId(String userId);
}