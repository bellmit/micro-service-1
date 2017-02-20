package com.frame.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.frame.sys.pojo.SysRoleRes;

/**
 * sys_role的Dao
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
public interface SysRoleResDao {

	public abstract void save(SysRoleRes sysRoleRes);

	public abstract void createTable();

	public abstract int isExistTable();

	public abstract SysRoleRes get(@Param("roleId")String roleId, @Param("resId")String resId);

}