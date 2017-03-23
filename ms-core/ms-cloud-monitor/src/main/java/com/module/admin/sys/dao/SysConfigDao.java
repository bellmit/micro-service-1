package com.module.admin.sys.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.module.admin.sys.pojo.SysConfig;

/**
 * sys_config的Dao
 * @author yuejing
 * @date 2016-10-19 13:50:15
 * @version V1.0.0
 */
public interface SysConfigDao {

	public abstract void save(SysConfig sysConfig);

	public abstract void update(SysConfig sysConfig);

	public abstract void delete(@Param("code")String code);

	public abstract SysConfig get(@Param("code")String code);

	public abstract List<SysConfig> findSysConfig(SysConfig sysConfig);
	
	public abstract int findSysConfigCount(SysConfig sysConfig);
}
