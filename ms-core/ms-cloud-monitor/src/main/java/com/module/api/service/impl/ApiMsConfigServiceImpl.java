package com.module.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.ms.pojo.MsConfig;
import com.module.admin.ms.service.MsConfigService;
import com.module.admin.ms.service.MsConfigValueService;
import com.module.api.service.ApiMsConfigService;
import com.system.handle.model.ResponseFrame;

/**
 * prj_client的Service
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
@Component
public class ApiMsConfigServiceImpl implements ApiMsConfigService {

	@Autowired
	private MsConfigService msConfigService;
	@Autowired
	private MsConfigValueService msConfigValueService;
	
	@Override
	public ResponseFrame findAll() {
		ResponseFrame frame = new ResponseFrame();
		List<MsConfig> data = msConfigService.findUse();
		for (MsConfig msConfig : data) {
			msConfig.setValues(msConfigValueService.findByConfigId(msConfig.getConfigId()));
		}
		frame.setBody(data);
		frame.setSucc();
		return frame;
	}

}