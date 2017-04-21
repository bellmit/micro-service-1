package com.module.admin.ms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.ms.dao.MsConfigValueDao;
import com.module.admin.ms.pojo.MsConfigValue;
import com.module.admin.ms.service.MsConfigValueService;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseFrame;
import com.system.handle.model.ResponseCode;

/**
 * ms_config_value的Service
 * @author yuejing
 * @date 2017-04-21 16:02:31
 * @version V1.0.0
 */
@Component
public class MsConfigValueServiceImpl implements MsConfigValueService {

	@Autowired
	private MsConfigValueDao msConfigValueDao;
	
	@Override
	public ResponseFrame saveOrUpdate(MsConfigValue msConfigValue) {
		ResponseFrame frame = new ResponseFrame();
		if(msConfigValue.getConfigId() == null) {
			msConfigValueDao.save(msConfigValue);
		} else {
			msConfigValueDao.update(msConfigValue);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public MsConfigValue get(Integer configId) {
		return msConfigValueDao.get(configId);
	}

	@Override
	public ResponseFrame pageQuery(MsConfigValue msConfigValue) {
		msConfigValue.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = msConfigValueDao.findMsConfigValueCount(msConfigValue);
		List<MsConfigValue> data = null;
		if(total > 0) {
			data = msConfigValueDao.findMsConfigValue(msConfigValue);
		}
		Page<MsConfigValue> page = new Page<MsConfigValue>(msConfigValue.getPage(), msConfigValue.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(Integer configId) {
		ResponseFrame frame = new ResponseFrame();
		msConfigValueDao.delete(configId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}
