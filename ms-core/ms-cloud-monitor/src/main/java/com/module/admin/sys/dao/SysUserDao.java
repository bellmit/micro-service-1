package com.module.admin.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.sys.pojo.SysUser;

/**
 * user_info的Dao
 * @author duanbin
 * @date 2016-03-22 11:17:54
 * @version V1.0.0
 */
public interface SysUserDao {

	public abstract void save(SysUser userInfo);

	public abstract void update(SysUser userInfo);

	public abstract SysUser get(@Param("userId")Integer userId);

	public abstract List<SysUser> findUserInfo(SysUser userInfo);
	
	public abstract int findUserInfoCount(SysUser userInfo);

	public abstract SysUser getByUsername(@Param("username")String username);
	
	public abstract List<SysUser> findByUsernamePassword(@Param("username")String username,@Param("password")String password);

	public abstract void updatePassword(@Param("userId")Integer userId, @Param("password")String password);

	public abstract SysUser getByCustomNo(@Param("customNo")String customNo);

	public abstract void updateStatus(@Param("userId")String userId, @Param("status")String status);

	public abstract void delete(@Param("userId")Integer userId);
}
