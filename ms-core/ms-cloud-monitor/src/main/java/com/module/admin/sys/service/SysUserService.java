package com.module.admin.sys.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.sys.pojo.SysUser;
import com.system.handle.model.ResponseFrame;

/**
 * user_info的Service
 * @author duanbin
 * @date 2016-03-22 11:17:54
 * @version V1.0.0
 */
@Component
public interface SysUserService {
	
	/**
	 * 保存
	 * @param userInfo
	 * @return
	 */
	public ResponseFrame save(SysUser userInfo);

	/**
	 * 修改
	 * @param userInfo
	 * @return
	 */
	public ResponseFrame update(SysUser userInfo);
	
	/**
	 * 根据userId获取对象
	 * @param userId
	 * @return
	 */
	public SysUser get(Integer userId);

	/**
	 * 分页获取对象
	 * @param userInfo
	 * @return
	 */
	public ResponseFrame pageQuery(SysUser userInfo);

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	public ResponseFrame login(String username, String password);

	/**
	 * 重置密码
	 * @param userId
	 * @param password
	 * @return
	 */
	public ResponseFrame resetPwd(Integer userId, String password);
	
	/**
	 * 根据用户名和密码查询
	 * @param username
	 * @param orgpwd
	 * @return
	 */
	public List<SysUser> findByUsernameAndPwd(String username,String orgpwd);

	/**
	 * 登录用户修改密码
	 * @param sysUser
	 * @param orgpwd
	 * @param newpwd
	 * @return
	 */
	public ResponseFrame updatePwd(SysUser sysUser, String orgpwd, String newpwd);

	/**
	 * 修改用户状态
	 * @param userId
	 * @param status
	 * @return
	 */
	public ResponseFrame updateStatus(String userId, String status);

	public ResponseFrame delete(Integer userId);
	
}
