package com.module.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.ms.pojo.MsSecretApi;
import com.module.admin.ms.service.MsSecretApiService;
import com.module.api.service.ApiMsSecretApiService;
import com.system.handle.model.ResponseFrame;

@Component
public class ApiMsSecretApiServiceImpl implements ApiMsSecretApiService {

	@Autowired
	private MsSecretApiService msSecretApiService;
	
	@Override
	public ResponseFrame findByCliId(String cliId) {
		ResponseFrame frame = new ResponseFrame();
		List<MsSecretApi> data = msSecretApiService.findByCliId(cliId);
		frame.setBody(data);
		frame.setSucc();
		return frame;
	}

}