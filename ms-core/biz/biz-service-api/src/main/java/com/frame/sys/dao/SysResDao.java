package com.frame.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.frame.sys.pojo.SysRes;

/**
 * sys_res的Dao
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
public interface SysResDao {

	public abstract void save(SysRes sysRes);

	public abstract void update(SysRes sysRes);

	public abstract SysRes get(@Param("resId")String resId);

	public abstract void createTable();

	public abstract int isExistTable();

}