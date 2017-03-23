package com.module.admin.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.sys.dao.SysRoleDao;
import com.module.admin.sys.pojo.SysRole;
import com.module.admin.sys.service.SysRoleService;
import com.system.comm.model.KvEntity;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 角色的Service
 * @author duanbin
 * @date 2016-05-08 09:47:07
 * @version V1.0.0
 */
@Component
public class SysRoleServiceImpl implements SysRoleService {
	
	@Autowired
	private SysRoleDao sysRoleDao;
	
	/*
	private static List<SysRole> list = new ArrayList<SysRole>();
	
		//老版本
	static {
		SysRole role = new SysRole();
		role.setRoleId(1);
		role.setName("管理员");
		role.setGrantids("20050,20051,20052,20150,20151,20152,20153,20154,20155,20156,20157,20158,20159,20200,20201,20202,20203");
		list.add(role);
	}*/
	
	@Override
	public ResponseFrame save(SysRole sysRole) {
		ResponseFrame frame = new ResponseFrame();
		SysRole org = sysRoleDao.getByName(sysRole.getName());
		if(org != null) {
			frame.setCode(-2);
			frame.setMessage("该角色已存在");
			return frame;
		}
		
		sysRoleDao.save(sysRole);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	@Override
	public ResponseFrame update(SysRole sysRole) {
		ResponseFrame frame = new ResponseFrame();
		sysRoleDao.update(sysRole);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(Integer roleId) {
		ResponseFrame frame = new ResponseFrame();
		sysRoleDao.delete(roleId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame pageQuery(SysRole sysRole) {
		ResponseFrame frame = new ResponseFrame();
		int total = sysRoleDao.findSysRoleCount(sysRole);
		List<SysRole> data = null;
		if(total > 0) {
			data = sysRoleDao.findSysRole(sysRole);
		}
		Page<SysRole> page = new Page<SysRole>(sysRole.getPage(), sysRole.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public SysRole get(Integer roleId) {
		return sysRoleDao.get(roleId);
	}
	
	/*	//老版本
	@Override
	public SysRole get(Integer roleId) {
		List<SysRole> roles = findAll();
		for (SysRole sysRole : roles) {
			if(sysRole.getRoleId().intValue() == roleId.intValue()) {
				return sysRole;
			}
		}
		return null;
	}*/
	
	@Override
	public String getName(Integer roleId) {
		SysRole role = get(roleId);
		if(role != null) {
			return role.getName();
		}
		return null;
	}

	@Override
	public List<KvEntity> findKvAll() {
		List<KvEntity> kvList = new ArrayList<KvEntity>();
		for (SysRole role : findAll()) {
			kvList.add(new KvEntity(role.getRoleId().toString(), role.getName()));
		}
		return kvList;
	}
	/*   //老版本
	@Override
	public List<SysRole> findAll() {
		return list;
	}*/
	
	@Override
	public List<SysRole> findAll() {
		return sysRoleDao.findAll();
	}
	
}