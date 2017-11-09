package com.module.api.service;

import org.springframework.stereotype.Component;

import com.system.handle.model.ResponseFrame;

@Component
public interface ApiMsSecretApiService {

	public abstract ResponseFrame findByCliId(String cliId);

}