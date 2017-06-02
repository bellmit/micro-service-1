package com.module.admin.ms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.ms.dao.MsSecretDao;
import com.module.admin.ms.pojo.MsSecret;
import com.module.admin.ms.service.MsSecretService;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseFrame;
import com.system.handle.model.ResponseCode;

/**
 * ms_secret的Service
 * @author yuejing
 * @date 2017-06-02 15:44:56
 * @version V1.0.0
 */
@Component
public class MsSecretServiceImpl implements MsSecretService {

	@Autowired
	private MsSecretDao msSecretDao;
	
	@Override
	public ResponseFrame saveOrUpdate(MsSecret msSecret) {
		ResponseFrame frame = new ResponseFrame();
		if(msSecret.getCilId() == null) {
			msSecretDao.save(msSecret);
		} else {
			msSecretDao.update(msSecret);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public MsSecret get(String cilId) {
		return msSecretDao.get(cilId);
	}

	@Override
	public ResponseFrame pageQuery(MsSecret msSecret) {
		msSecret.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = msSecretDao.findMsSecretCount(msSecret);
		List<MsSecret> data = null;
		if(total > 0) {
			data = msSecretDao.findMsSecret(msSecret);
		}
		Page<MsSecret> page = new Page<MsSecret>(msSecret.getPage(), msSecret.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(String cilId) {
		ResponseFrame frame = new ResponseFrame();
		msSecretDao.delete(cilId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}
