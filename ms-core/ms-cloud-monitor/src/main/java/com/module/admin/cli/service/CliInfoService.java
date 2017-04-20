package com.module.admin.cli.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.cli.pojo.CliInfo;
import com.system.comm.model.KvEntity;
import com.system.handle.model.ResponseFrame;

/**
 * cli_info的Service
 * @author yuejing
 * @date 2016-10-20 17:55:37
 * @version V1.0.0
 */
@Component
public interface CliInfoService {
	
	/**
	 * 保存或修改
	 * @param cliInfo
	 * @return
	 */
	public ResponseFrame saveOrUpdate(CliInfo cliInfo);
	
	/**
	 * 根据clientId获取对象
	 * @param clientId
	 * @return
	 */
	public CliInfo get(String clientId);

	/**
	 * 分页获取对象
	 * @param cliInfo
	 * @return
	 */
	public ResponseFrame pageQuery(CliInfo cliInfo);
	
	/**
	 * 根据clientId删除对象
	 * @param clientId
	 * @return
	 */
	public ResponseFrame delete(String clientId);

	/**
	 * 搜索
	 * @param cliInfo
	 * @return
	 */
	public ResponseFrame find(CliInfo cliInfo);

	/**
	 * 修改活动时间信息
	 * @param clientId
	 */
	public void updateActivityTime(String clientId);

	/**
	 * 修改心跳失败
	 */
	public void updateActivityStatusError();

	/**
	 * 获取所有客户端
	 * @return
	 */
	public List<CliInfo> findAll();

	public List<KvEntity> findKvAll();
}
