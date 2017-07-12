package com.module.admin.ms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.ms.dao.MsConfigValueDao;
import com.module.admin.ms.pojo.MsConfigValue;
import com.module.admin.ms.service.MsConfigService;
import com.module.admin.ms.service.MsConfigValueService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

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
	@Autowired
	private MsConfigService msConfigService;
	
	@Override
	public ResponseFrame saveList(Integer configId, List<MsConfigValue> values) {
		ResponseFrame frame = new ResponseFrame();
		delete(configId);
		for (MsConfigValue mcv : values) {
			msConfigValueDao.save(mcv);
		}
		msConfigService.refreshCfg(configId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public MsConfigValue get(Integer configId, String code) {
		return msConfigValueDao.get(configId, code);
	}

	
	@Override
	public ResponseFrame delete(Integer configId) {
		ResponseFrame frame = new ResponseFrame();
		msConfigValueDao.delete(configId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public List<MsConfigValue> findByConfigId(Integer configId) {
		return msConfigValueDao.findByConfigId(configId);
	}

}
