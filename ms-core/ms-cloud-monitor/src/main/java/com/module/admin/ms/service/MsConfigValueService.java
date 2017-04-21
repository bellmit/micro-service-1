package com.module.admin.ms.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.ms.pojo.MsConfigValue;
import com.system.handle.model.ResponseFrame;

/**
 * ms_config_value的Service
 * @author yuejing
 * @date 2017-04-21 16:02:31
 * @version V1.0.0
 */
@Component
public interface MsConfigValueService {
	
	/**
	 * 保存或修改
	 * @param msConfigValue
	 * @return
	 */
	public ResponseFrame saveOrUpdate(MsConfigValue msConfigValue);
	
	/**
	 * 根据configId获取对象
	 * @param configId
	 * @return
	 */
	public MsConfigValue get(Integer configId);

	/**
	 * 分页获取对象
	 * @param msConfigValue
	 * @return
	 */
	public ResponseFrame pageQuery(MsConfigValue msConfigValue);
	
	/**
	 * 根据configId删除对象
	 * @param configId
	 * @return
	 */
	public ResponseFrame delete(Integer configId);
}
