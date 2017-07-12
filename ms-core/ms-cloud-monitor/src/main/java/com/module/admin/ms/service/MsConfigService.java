package com.module.admin.ms.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.ms.pojo.MsConfig;
import com.system.handle.model.ResponseFrame;

/**
 * ms_config的Service
 * @author yuejing
 * @date 2017-04-21 16:02:31
 * @version V1.0.0
 */
@Component
public interface MsConfigService {
	
	/**
	 * 保存或修改
	 * @param msConfig
	 * @return
	 */
	public ResponseFrame saveOrUpdate(MsConfig msConfig);
	
	/**
	 * 根据configId获取对象
	 * @param configId
	 * @return
	 */
	public MsConfig get(Integer configId);

	/**
	 * 分页获取对象
	 * @param msConfig
	 * @return
	 */
	public ResponseFrame pageQuery(MsConfig msConfig);
	
	/**
	 * 根据configId删除对象
	 * @param configId
	 * @return
	 */
	public ResponseFrame delete(Integer configId);

	/**
	 * 查找使用的属性文件
	 * @return
	 */
	public List<MsConfig> findUse();

	public List<MsConfig> findAll();

	/**
	 * 根据configId刷新系统的配置文件
	 * @param configId
	 */
	public void refreshCfg(Integer configId);
}
