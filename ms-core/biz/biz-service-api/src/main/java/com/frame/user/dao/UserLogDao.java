package com.frame.user.dao;

import com.frame.user.pojo.UserLog;

/**
 * 用户的日志的Dao
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
public interface UserLogDao {

	public abstract void save(UserLog userLog);
	
	public abstract void createTable();

	public abstract int isExistTable();
}