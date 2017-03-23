package com.module.admin.sys.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.sys.pojo.SysRole;
import com.system.comm.model.KvEntity;
import com.system.handle.model.ResponseFrame;

/**
 * 角色的Service
 * @author yuejing
 * @date 2015-07-08 09:47:07
 * @version V1.0.0
 */
@Component
public interface SysRoleService {
	
	/**
	 * 保存
	 * @param SysRole
	 * @return
	 */
	public ResponseFrame save(SysRole sysRole);

	/**
	 * 修改
	 * @param SysRole
	 * @return
	 */
	public ResponseFrame update(SysRole sysRole);
	
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	public ResponseFrame delete(Integer roleId);

	/**
	 * 根据id获取对象
	 * @param roleId
	 * @return
	 */
	public SysRole get(Integer roleId);
	
	/**
	 * 根据id获取角色名称
	 * @param roleId
	 * @return
	 */
	public String getName(Integer roleId);

	/**
	 * 分页获取对象
	 * @param sysRole
	 * @return
	 */
	public ResponseFrame pageQuery(SysRole sysRole);

	/**
	 * 获取角色集合
	 * @return
	 */
	public List<KvEntity> findKvAll();
	
	/**
	 * 获取角色集合
	 * @return
	 */
	public List<SysRole> findAll();

}