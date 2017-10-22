package com.module.api.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.api.dao.ZipkinAnnotationsDao;
import com.module.api.pojo.ZipkinAnnotations;
import com.module.api.service.ZipkinAnnotationsService;
import com.system.comm.utils.FrameTimeUtil;

@Component
public class ZipkinAnnotationsServiceImpl implements ZipkinAnnotationsService {

	@Autowired
	private ZipkinAnnotationsDao zipkinAnnotationsDao;

	@Override
	public List<ZipkinAnnotations> findByAKeyATimestamp(String aKey, Date beginTime, Date endTime) {
		return null;
	}
}
