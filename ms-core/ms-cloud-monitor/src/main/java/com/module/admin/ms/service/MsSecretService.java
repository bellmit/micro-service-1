package com.module.admin.ms.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.ms.pojo.MsSecret;
import com.system.handle.model.ResponseFrame;

/**
 * ms_secret的Service
 * @author yuejing
 * @date 2017-06-02 15:44:56
 * @version V1.0.0
 */
@Component
public interface MsSecretService {
	
	/**
	 * 保存或修改
	 * @param msSecret
	 * @return
	 */
	public ResponseFrame saveOrUpdate(MsSecret msSecret);
	
	/**
	 * 根据cilId获取对象
	 * @param cilId
	 * @return
	 */
	public MsSecret get(String cilId);

	/**
	 * 分页获取对象
	 * @param msSecret
	 * @return
	 */
	public ResponseFrame pageQuery(MsSecret msSecret);
	
	/**
	 * 根据cilId删除对象
	 * @param cilId
	 * @return
	 */
	public ResponseFrame delete(String cilId);
}
