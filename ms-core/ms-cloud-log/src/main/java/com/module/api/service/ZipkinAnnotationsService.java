package com.module.api.service;

import java.util.Date;
import java.util.List;

import com.module.api.pojo.ZipkinAnnotations;

public interface ZipkinAnnotationsService {

	/**
	 * 根据key和时间区间获取日志
	 * @param aKey
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public abstract List<ZipkinAnnotations> findByAKeyATimestamp(String aKey, Date beginTime, Date endTime);

}
