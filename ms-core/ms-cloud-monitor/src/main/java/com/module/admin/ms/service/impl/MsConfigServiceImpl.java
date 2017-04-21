package com.module.admin.ms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.ms.dao.MsConfigDao;
import com.module.admin.ms.pojo.MsConfig;
import com.module.admin.ms.service.MsConfigService;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseFrame;
import com.system.handle.model.ResponseCode;

/**
 * ms_config的Service
 * @author yuejing
 * @date 2017-04-21 16:02:31
 * @version V1.0.0
 */
@Component
public class MsConfigServiceImpl implements MsConfigService {

	@Autowired
	private MsConfigDao msConfigDao;
	
	@Override
	public ResponseFrame saveOrUpdate(MsConfig msConfig) {
		ResponseFrame frame = new ResponseFrame();
		if(msConfig.getConfigId() == null) {
			msConfigDao.save(msConfig);
		} else {
			msConfigDao.update(msConfig);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public MsConfig get(Integer configId) {
		return msConfigDao.get(configId);
	}

	@Override
	public ResponseFrame pageQuery(MsConfig msConfig) {
		msConfig.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = msConfigDao.findMsConfigCount(msConfig);
		List<MsConfig> data = null;
		if(total > 0) {
			data = msConfigDao.findMsConfig(msConfig);
		}
		Page<MsConfig> page = new Page<MsConfig>(msConfig.getPage(), msConfig.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(Integer configId) {
		ResponseFrame frame = new ResponseFrame();
		msConfigDao.delete(configId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}
