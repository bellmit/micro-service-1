package com.frame.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.frame.sys.pojo.SysDictType;

/**
 * fund_type的Dao
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
public interface SysDictTypeDao {

	public abstract void save(SysDictType sysDictType);

	public abstract void update(SysDictType sysDictType);

	public abstract SysDictType get(@Param("code")String code);

	public abstract void createTable();

	public abstract int isExistTable();

}