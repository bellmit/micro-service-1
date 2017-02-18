package com.frame.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.frame.sys.pojo.SysRole;

/**
 * sys_role的Dao
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
public interface SysRoleDao {

	public abstract void save(SysRole sysRole);

	public abstract void update(SysRole sysRole);

	public abstract SysRole get(@Param("roleId")String roleId);

	public abstract void createTable();

	public abstract int isExistTable();

	public abstract int findSysRoleCount(SysRole sysRole);

	public abstract List<SysRole> findSysRole(SysRole sysRole);

}