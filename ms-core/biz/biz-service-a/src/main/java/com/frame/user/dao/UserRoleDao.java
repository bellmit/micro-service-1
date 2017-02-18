package com.frame.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.frame.sys.pojo.SysRes;
import com.frame.user.pojo.UserRole;


/**
 * sys_role的Dao
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
public interface UserRoleDao {

	public abstract void save(UserRole userRole);

	public abstract void createTable();

	public abstract int isExistTable();

	public abstract UserRole get(@Param("userId")String userId, @Param("roleId")String roleId);

	public abstract List<SysRes> findResListByUserId(@Param("userId")String userId);

}