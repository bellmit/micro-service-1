package com.module.admin.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.sys.dao.SysUserDao;
import com.module.admin.sys.enums.SysUserStatus;
import com.module.admin.sys.pojo.SysUser;
import com.module.admin.sys.service.SysRoleService;
import com.module.admin.sys.service.SysUserService;
import com.module.admin.sys.utils.SysUserUtil;
import com.system.comm.model.Page;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * sys_user的Service
 * @author duanbin
 * @date 2016-03-22 11:17:54
 * @version V1.0.0
 */
@Component
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysRoleService sysRoleService;
	
	@Override
	public ResponseFrame save(SysUser userInfo) {
		ResponseFrame frame = new ResponseFrame();
		SysUser org = sysUserDao.getByUsername(userInfo.getUsername());
		if(org != null) {
			frame.setCode(-2);
			frame.setMessage("该用户已存在");
			return frame;
		}
		if(userInfo.getStatus() == null) {
			userInfo.setStatus(SysUserStatus.NORMAL.getCode());
		}
		userInfo.setPassword(SysUserUtil.encryptionPassword(userInfo.getPassword()));
		sysUserDao.save(userInfo);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	@Override
	public ResponseFrame update(SysUser userInfo) {
		ResponseFrame frame = new ResponseFrame();
		if(FrameStringUtil.isNotEmpty(userInfo.getPassword())) {
			userInfo.setPassword(SysUserUtil.encryptionPassword(userInfo.getPassword()));
		}
		sysUserDao.update(userInfo);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public SysUser get(Integer userId) {
		return sysUserDao.get(userId);
	}

	@Override
	public ResponseFrame pageQuery(SysUser userInfo) {
		ResponseFrame frame = new ResponseFrame();
		int total = sysUserDao.findUserInfoCount(userInfo);
		List<SysUser> data = null;
		if(total > 0) {
			data = sysUserDao.findUserInfo(userInfo);
			for (SysUser ui : data) {
				/*ui.setDep(sysRoleService.get(ui.getDepId()));
				ui.setRole(sysRoleService.get(ui.getRoleId()));*/
				ui.setStatusName(SysUserStatus.getText(ui.getStatus()));
			}
		}
		Page<SysUser> page = new Page<SysUser>(userInfo.getPage(), userInfo.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame login(String username, String password) {
		ResponseFrame frame = new ResponseFrame();
		SysUser userInfo = sysUserDao.getByUsername(username);
		if(userInfo == null) {
			frame.setCode(-2);
			frame.setMessage("用户名或密码错误");
			return frame;
		}
		if(!userInfo.getPassword().equals(SysUserUtil.encryptionPassword(password))) {
			frame.setCode(-2);
			frame.setMessage("用户名或密码错误");
			return frame;
		}
		/*//设置用户角色
		userInfo.setRole(sysRoleService.get(userInfo.getRoleId()));*/
		frame.setBody(userInfo);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	@Override
	public ResponseFrame resetPwd(Integer userId, String password) {
		ResponseFrame frame = new ResponseFrame();
		password = SysUserUtil.encryptionPassword(password);
		sysUserDao.updatePassword(userId, password);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public List<SysUser> findByUsernameAndPwd(String username,String orgpwd){
		orgpwd = SysUserUtil.encryptionPassword(orgpwd);
		List<SysUser> userlist = sysUserDao.findByUsernamePassword(username,orgpwd);
		return userlist;
	}
	
	@Override
	public ResponseFrame updatePwd(SysUser sysUser, String orgpwd, String newpwd) {
		ResponseFrame frame = new ResponseFrame();
		String md5Orgpwd = SysUserUtil.encryptionPassword(orgpwd);
		String md5Newpwd = SysUserUtil.encryptionPassword(newpwd);
		if(!sysUser.getPassword().equals(md5Orgpwd)) {
			//原始密码输入错误
			frame.setCode(-2);
			frame.setMessage("原始密码输入错误!");
		} else {
			//修改密码
			sysUserDao.updatePassword(sysUser.getUserId(), md5Newpwd);

			sysUser.setPassword(md5Newpwd);
			frame.setBody(sysUser);
			frame.setCode(ResponseCode.SUCC.getCode());
		}
		return frame;
	}
	
	@Override
	public ResponseFrame updateStatus(String userId, String status) {
		ResponseFrame frame = new ResponseFrame();
		sysUserDao.updateStatus(userId, status);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	@Override
	public ResponseFrame delete(Integer userId) {
		ResponseFrame frame = new ResponseFrame();
		sysUserDao.delete(userId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}
