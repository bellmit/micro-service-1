package com.module.admin.prj.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.cli.pojo.CliInfo;
import com.module.admin.prj.pojo.PrjClient;
import com.system.handle.model.ResponseFrame;

/**
 * prj_client的Service
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
@Component
public interface PrjClientService {
	
	/**
	 * 保存或修改
	 * @param prjClient
	 * @return
	 */
	public ResponseFrame saveOrUpdate(PrjClient prjClient);
	
	/**
	 * 根据prjId获取对象
	 * @param prjId
	 * @return
	 */
	public PrjClient get(Integer prjId, String version, String clientId);

	/**
	 * 分页获取对象
	 * @param prjClient
	 * @return
	 */
	public ResponseFrame pageQuery(PrjClient prjClient);
	
	/**
	 * 根据prjId删除对象
	 * @param prjId
	 * @return
	 */
	public ResponseFrame delete(Integer prjId, String version, String clientId);

	/**
	 * 根据客户端编号获取要发布的项目
	 * @param clientId
	 * @return
	 */
	//public List<PrjInfo> findByClientId(String clientId);

	/**
	 * 修改客户端发布项目成功
	 * @param clientId
	 * @param prjId
	 * @param version 
	 * @param status
	 * @param statusMsg 
	 */
	public void updateStatus(String clientId, Integer prjId, String version, Integer status, String statusMsg);

	/**
	 * 根据项目编号获取发布的客户端
	 * @param prjId
	 * @param version 
	 * @param clientId	客户端编号，不传代表获取所有关联的
	 * @return
	 */
	public List<CliInfo> findByPrjId(Integer prjId, String version, String clientId);

	/**
	 * 修改shell
	 * @param clientId
	 * @param prjId
	 * @param shellScript
	 * @return
	 */
	public ResponseFrame updateShellScript(String clientId, Integer prjId, String version,
			String shellScript);

	/**
	 * 根据项目编号和客户端编号获取最后一次的对象
	 * @param prjId
	 * @param clientId
	 * @return
	 */
	public PrjClient getLastByPrjIdClientId(Integer prjId, String clientId);

	public List<PrjClient> findByPrjIdVersion(Integer prjId, String version);
}
