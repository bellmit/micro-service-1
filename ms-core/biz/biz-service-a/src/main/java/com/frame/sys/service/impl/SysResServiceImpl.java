package com.frame.sys.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.sys.dao.SysResDao;
import com.frame.sys.pojo.SysRes;
import com.frame.sys.service.SysResService;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@Component
public class SysResServiceImpl implements SysResService {

	private static final Logger LOGGER = Logger.getLogger(SysResServiceImpl.class);
	@Autowired
	private SysResDao sysResDao;

	@Override
	public SysRes get(String resId) {
		return sysResDao.get(resId);
	}

	@Override
	public void initTable() {
		if(!isExistTable()) {
			sysResDao.createTable();
			LOGGER.info("||===== 初始化 [ sys_res ] 表结构成功!");
		}
	}

	private boolean isExistTable() {
		try {
			sysResDao.isExistTable();
			return true;
		} catch (Exception e) {
			LOGGER.error("表[sys_res]不存在: " + e.getMessage());
		}
		return false;
	}
	
	@Override
	public ResponseFrame save(SysRes sysRes) {
		ResponseFrame frame = new ResponseFrame();
		SysRes sr = get(sysRes.getResId());
		if(sr == null) {
			if(FrameStringUtil.isEmpty(sysRes.getParentResId())) {
				sysRes.setParentResId("0");
			}
			if(FrameStringUtil.isEmpty(sysRes.getShowName())) {
				sysRes.setShowName(sysRes.getName());
			}
			sysResDao.save(sysRes);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}