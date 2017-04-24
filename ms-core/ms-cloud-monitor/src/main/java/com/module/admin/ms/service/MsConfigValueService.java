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
	public ResponseFrame saveList(Integer configId, List<MsConfigValue> values);
	
	/**
	 * 根据configId/code获取对象
	 * @param configId
	 * @param code
	 * @return
	 */
	public MsConfigValue get(Integer configId, String code);

	public List<MsConfigValue> findByConfigId(Integer configId);
	
	/**
	 * 根据configId删除对象
	 * @param configId
	 * @return
	 */
	public ResponseFrame delete(Integer configId);
}
