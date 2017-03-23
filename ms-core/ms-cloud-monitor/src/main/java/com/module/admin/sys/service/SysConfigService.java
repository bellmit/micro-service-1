package com.module.admin.sys.service;

import org.springframework.stereotype.Component;

import com.module.admin.sys.enums.SysConfigCode;
import com.module.admin.sys.pojo.SysConfig;
import com.system.handle.model.ResponseFrame;

/**
 * sys_config的Service
 * @author yuejing
 * @date 2016-10-19 13:50:15
 * @version V1.0.0
 */
@Component
public interface SysConfigService {
	
	/**
	 * 保存或修改
	 * @param sysConfig
	 * @return
	 */
	public ResponseFrame saveOrUpdate(SysConfig sysConfig);
	
	/**
	 * 根据code获取对象
	 * @param code
	 * @return
	 */
	public SysConfig get(String code);

	/**
	 * 根据code获取对象
	 * @param code
	 * @return
	 */
	public String getValue(SysConfigCode sysConfigCode);
	
	/**
	 * 分页获取对象
	 * @param sysConfig
	 * @return
	 */
	public ResponseFrame pageQuery(SysConfig sysConfig);
	
	/**
	 * 根据code删除对象
	 * @param code
	 * @return
	 */
	public ResponseFrame delete(String code);
}
