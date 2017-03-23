package com.module.admin.sys.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.sys.pojo.SysFile;
import com.system.handle.model.ResponseFrame;

/**
 * sys_file的Service
 * @author duanbin
 * @date 2016-04-06 15:45:49
 * @version V1.0.0
 */
@Component
public interface SysFileService {
	
	/**
	 * 保存
	 * @param sysFile
	 * @return
	 */
	public ResponseFrame save(SysFile sysFile);

	/**
	 * 根据id获取对象
	 * @param fileId
	 * @return
	 */
	public SysFile get(String fileId);

	/**
	 * 分页获取对象
	 * @param sysFile
	 * @return
	 */
	//public MyPage<SysFile> pageQuery(SysFile sysFile);

	/**
	 * 根据文件编号修改为状态
	 * @param fileId
	 * @param status
	 */
	public void updateStatus(String fileId, Integer status);

	/**
	 * 根据id字符串获取集合[如1,2,3]
	 * @param fileIds
	 * @return
	 */
	public List<SysFile> findByFileIds(String fileIds);

	/**
	 * 根据文件ID集合修改状态
	 * @param fileIds
	 * @param status
	 */
	public void updateStatusByFileIds(List<String> fileIds, Integer status);
	
}
