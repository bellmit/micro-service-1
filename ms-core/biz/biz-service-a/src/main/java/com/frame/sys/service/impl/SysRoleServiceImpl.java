package com.frame.sys.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.sys.dao.SysRoleDao;
import com.frame.sys.pojo.SysRole;
import com.frame.sys.service.SysRoleService;
import com.frame.user.enums.UserInfoStatus;
import com.frame.user.pojo.UserInfo;
import com.system.comm.model.Page;
import com.system.comm.utils.FrameNoUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

@Component
public class SysRoleServiceImpl implements SysRoleService {

	private static final Logger LOGGER = Logger.getLogger(SysRoleServiceImpl.class);
	@Autowired
	private SysRoleDao sysRoleDao;

	@Override
	public SysRole get(String roleId) {
		return sysRoleDao.get(roleId);
	}

	@Override
	public void initTable() {
		if(!isExistTable()) {
			sysRoleDao.createTable();
			LOGGER.info("||===== 初始化 [ sys_role ] 表结构成功!");
			initAdmin();
		}
	}

	private boolean isExistTable() {
		try {
			sysRoleDao.isExistTable();
			return true;
		} catch (Exception e) {
			LOGGER.error("表[sys_role]不存在: " + e.getMessage());
		}
		return false;
	}

	private void initAdmin() {
		//初始化管理员帐号
		SysRole admin = new SysRole();
		admin.setRoleId("admin");
		admin.setName("系统管理员");
		admin.setRemark("最高权限人员");
		admin.setAddUserId("admin");
		save(admin);
	}
	
	private void save(SysRole sysRole) {
		if(FrameStringUtil.isEmpty(sysRole.getRoleId())) {
			sysRole.setRoleId(FrameNoUtil.uuid());
		}
		//密码加密
		sysRoleDao.save(sysRole);
	}

	@Override
	public ResponseFrame pageQuery(SysRole sysRole) {
		sysRole.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = sysRoleDao.findSysRoleCount(sysRole);
		List<SysRole> data = null;
		if(total > 0) {
			data = sysRoleDao.findSysRole(sysRole);
			/*for (SysRole ui : data) {
			}*/
		}
		Page<SysRole> page = new Page<SysRole>(sysRole.getPage(), sysRole.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}