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
	 * 根据cliId获取对象
	 * @param cliId
	 * @return
	 */
	public MsSecret get(String cliId);

	/**
	 * 分页获取对象
	 * @param msSecret
	 * @return
	 */
	public ResponseFrame pageQuery(MsSecret msSecret);
	
	/**
	 * 根据cliId删除对象
	 * @param cliId
	 * @return
	 */
	public ResponseFrame delete(String cliId);

	/**
	 * 获取已经使用的密钥
	 * @return
	 */
	public List<MsSecret> findUse();
}
