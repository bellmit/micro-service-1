package com.module.admin.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.sys.dao.SysFileDao;
import com.module.admin.sys.enums.SysFileStatus;
import com.module.admin.sys.pojo.SysFile;
import com.module.admin.sys.service.SysFileService;
import com.system.comm.utils.FrameNoUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 系统文件的Service
 * @author duanbin
 * @date 2016-04-06 15:45:49
 * @version V1.0.0
 */
@Component
public class SysFileServiceImpl implements SysFileService {

	@Autowired
	private SysFileDao sysFileDao;

	public ResponseFrame save(SysFile sysFile) {
		ResponseFrame frame = new ResponseFrame();
		if(sysFile.getStatus() == null) {
			sysFile.setStatus(SysFileStatus.WAIT.getCode());
		}
		if(FrameStringUtil.isEmpty(sysFile.getFileId())) {
			sysFile.setFileId(FrameNoUtil.uuid());
		}
		if(sysFile.getStatus() == null) {
			sysFile.setStatus(SysFileStatus.WAIT.getCode());
		}
		sysFileDao.save(sysFile);
		
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	public SysFile get(String fileId) {
		return sysFileDao.get(fileId);
	}

	/*@Override
	public MyPage<SysFile> pageQuery(SysFile sysFile) {
		int total = sysFileDao.findSysFileCount(sysFile);
		List<SysFile> data = null;
		if(total > 0) {
			data = sysFileDao.findSysFile(sysFile);
		}
		return new MyPage<SysFile>(sysFile.getPage(), sysFile.getSize(), total, data);
	}*/

	public void updateStatus(String fileId, Integer status) {
		sysFileDao.updateStatus(fileId, status);
	}

	public List<SysFile> findByFileIds(String fileIds) {
		if(FrameStringUtil.isEmpty(fileIds)) {
			return null;
		}
		String[] fileIdArr = fileIds.split(",");
		List<SysFile> list = new ArrayList<SysFile>();
		for (String fileId : fileIdArr) {
			if(FrameStringUtil.isNotEmpty(fileId)) {
				SysFile file = get(fileId);
				if(file != null) {
					list.add(file);
				}
			}
		}
		return list;
	}

	public void updateStatusByFileIds(List<String> fileIds, Integer status) {
		if(fileIds.size() > 0) {
			sysFileDao.updateStatusByFileIds(fileIds, status);
		}
	}

}