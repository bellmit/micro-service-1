package com.module.admin.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.sys.pojo.SysFile;

/**
 * sys_file的Dao
 * @author duanbin
 * @date 2016-03-22 21:02:08
 * @version V1.0.0
 */
public interface SysFileDao {

	public abstract void save(SysFile sysFile);

	public abstract void update(SysFile sysFile);

	public abstract SysFile get(@Param("fileId")String fileId);

	public abstract List<SysFile> findSysFile(SysFile sysFile);
	
	public abstract int findSysFileCount(SysFile sysFile);

	public abstract void updateStatus(@Param("fileId")String fileId, @Param("status")Integer status);

	public abstract void updateStatusByFileIds(@Param("fileIds")List<String> fileIds, @Param("status")Integer status);
}
