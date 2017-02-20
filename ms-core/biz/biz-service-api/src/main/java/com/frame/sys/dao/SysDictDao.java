package com.frame.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.frame.sys.pojo.SysDict;

/**
 * sys_dict的Dao
 * @author yuejing
 * @date 2016-03-25 10:52:47
 * @version V1.0.0
 */
public interface SysDictDao {

	public abstract void save(SysDict sysDict);

	public abstract void update(SysDict sysDict);

	public abstract SysDict get(@Param("typeCode")String typeCode, @Param("dictId")String dictId);

	public abstract void createTable();

	public abstract int isExistTable();

	public abstract int findSysDictCount(SysDict sysDict);
	public abstract List<SysDict> findSysDict(SysDict sysDict);

	public abstract void delete(@Param("dictId")String dictId);

	public abstract void deleteByTypeCode(@Param("typeCode")String typeCode);

	public abstract List<SysDict> findByTypeCodeValue(@Param("typeCode")String typeCode, @Param("value")String value);

}