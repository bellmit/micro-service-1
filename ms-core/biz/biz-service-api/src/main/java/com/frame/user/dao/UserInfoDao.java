package com.frame.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.frame.user.pojo.UserInfo;

/**
 * userInfo的Dao
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
public interface UserInfoDao {

	public abstract void save(UserInfo userInfo);
	
	public abstract void update(UserInfo userInfo);

	public abstract UserInfo get(@Param("userId")String userId);

	public abstract void createTable();

	public abstract int isExistTable();

	public abstract UserInfo getByUserName(@Param("userName")String userName);

	public abstract void updatePassword(@Param("userId")String userId, @Param("password")String password);

	public abstract List<UserInfo> findUserInfo(UserInfo userInfo);

	public abstract int findUserInfoCount(UserInfo userInfo);

	public abstract void updateIsDelete(@Param("userId")String userId, @Param("isDelete")Integer isDelete);

}
