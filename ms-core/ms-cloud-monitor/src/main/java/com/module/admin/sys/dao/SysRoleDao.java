package com.module.admin.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.sys.pojo.SysRole;

/**
 * @author duanbin
 * @date 2016-04-21
 * @version V1.0.0
 */
public interface SysRoleDao {

	public abstract void save(SysRole sysRole);

	public abstract void update(SysRole sysRole);
	
	public abstract void delete(@Param("roleId")Integer roleId);

	public abstract SysRole get(@Param("roleId")Integer roleId);

	public abstract List<SysRole> findSysRole(SysRole sysRole);
	
	public abstract List<SysRole> findAll();
	
	public abstract int findSysRoleCount(SysRole sysRole);
	
	public abstract SysRole getByName(@Param("name")String name);


}
