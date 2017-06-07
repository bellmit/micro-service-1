package com.module.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.ms.pojo.MsSecret;
import com.module.admin.ms.service.MsSecretService;
import com.module.api.service.ApiMsSecretService;
import com.system.handle.model.ResponseFrame;

@Component
public class ApiMsSecretServiceImpl implements ApiMsSecretService {

	@Autowired
	private MsSecretService msSecretService;
	
	@Override
	public ResponseFrame findUse() {
		ResponseFrame frame = new ResponseFrame();
		List<MsSecret> data = msSecretService.findUse();
		frame.setBody(data);
		frame.setSucc();
		return frame;
	}

}