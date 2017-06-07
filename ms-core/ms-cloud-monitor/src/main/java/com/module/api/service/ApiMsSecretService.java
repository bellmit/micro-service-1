package com.module.api.service;

import org.springframework.stereotype.Component;

import com.system.handle.model.ResponseFrame;

@Component
public interface ApiMsSecretService {

	public abstract ResponseFrame findUse();

}
