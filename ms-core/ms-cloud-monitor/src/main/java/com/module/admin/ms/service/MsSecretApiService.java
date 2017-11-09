package com.module.admin.ms.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.ms.pojo.MsSecretApi;
import com.system.handle.model.ResponseFrame;

/**
 * ms_secret_api的Service
 * @author yuejing
 * @date 2017-06-02 15:44:56
 * @version V1.0.0
 */
@Component
public interface MsSecretApiService {
	
	/**
	 * 保存
	 * @param msSecretApi
	 * @return
	 */
	public ResponseFrame save(MsSecretApi msSecretApi);
	
	/**
	 * 根据cliId获取对象
	 * @param cliId
	 * @return
	 */
	public MsSecretApi get(String cliId, Integer prjId, String url);

	/**
	 * 分页获取对象
	 * @param msSecret
	 * @return
	 */
	public ResponseFrame pageQuery(MsSecretApi msSecretApi);
	
	/**
	 * 根据cliId删除对象
	 * @param cliId
	 * @param prjId 
	 * @param url 
	 * @return
	 */
	public ResponseFrame delete(String cliId, Integer prjId, String url);

	public List<MsSecretApi> findByCliId(String cliId);
}