package com.frame.sys.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.sys.dao.SysDictDao;
import com.frame.sys.enums.SysDictTypeCode;
import com.frame.sys.pojo.SysDict;
import com.frame.sys.service.SysDictService;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseFrame;

@Component
public class SysDictServiceImpl implements SysDictService {

	private static final Logger LOGGER = Logger.getLogger(SysDictServiceImpl.class);
	@Autowired
	private SysDictDao sysDictDao;

	@Override
	public SysDict get(String typeCode, String dictId) {
		return sysDictDao.get(typeCode, dictId);
	}

	@Override
	public void initTable() {
		if(!isExistTable()) {
			sysDictDao.createTable();
			LOGGER.info("||===== 初始化 [ sys_dict ] 表结构成功!");
			initData();
		}
	}

	private void initData() {
		SysDict sysDict = new SysDict();
		sysDict.setTypeCode(SysDictTypeCode.ADMIN_INFO.getCode());
		sysDict.setDictId("roleId");
		sysDict.setName("超级管理员角色ID");
		sysDict.setValue("r1000");
		sysDict.setRemark("超级管理员的角色ID");
		sysDict.setAddUserId("u1000");
		saveOrUpdate(sysDict);
		sysDict = new SysDict();
		sysDict.setTypeCode(SysDictTypeCode.ADMIN_INFO.getCode());
		sysDict.setDictId("userId");
		sysDict.setName("超级管理员用户ID");
		sysDict.setValue("u1000");
		sysDict.setRemark("超级管理员的用户ID");
		sysDict.setAddUserId("u1000");
		saveOrUpdate(sysDict);
	}

	private boolean isExistTable() {
		try {
			sysDictDao.isExistTable();
			return true;
		} catch (Exception e) {
			LOGGER.error("表[sys_dict]不存在: " + e.getMessage());
		}
		return false;
	}

	@Override
	public ResponseFrame saveOrUpdate(SysDict sysDict) {
		ResponseFrame frame = new ResponseFrame();
		SysDict org = get(sysDict.getTypeCode(), sysDict.getDictId());
		if(org == null) {
			sysDictDao.save(sysDict);
		} else {
			sysDictDao.update(sysDict);
		}
		frame.setSucc();
		return frame;
	}

	@Override
	public ResponseFrame pageQuery(SysDict sysDict) {
		ResponseFrame frame = new ResponseFrame();
		sysDict.setDefPageSize();
		int total = sysDictDao.findSysDictCount(sysDict);
		List<SysDict> data = null;
		if(total > 0) {
			data = sysDictDao.findSysDict(sysDict);
		}
		Page<SysDict> page = new Page<SysDict>(sysDict.getPage(), sysDict.getSize(), total, data);
		frame.setBody(page);
		frame.setSucc();
		return frame;
	}

	@Override
	public ResponseFrame delete(String dictId) {
		ResponseFrame frame = new ResponseFrame();
		sysDictDao.delete(dictId);
		frame.setSucc();
		return frame;
	}

	@Override
	public ResponseFrame deleteByTypeCode(String typeCode) {
		ResponseFrame frame = new ResponseFrame();
		sysDictDao.deleteByTypeCode(typeCode);
		frame.setSucc();
		return frame;
	}

	@Override
	public String getValue(String typeCode, String dictId) {
		SysDict dict = get(typeCode, dictId);
		return dict == null ? null : dict.getValue();
	}

	@Override
	public String getValue(SysDictTypeCode sysDictTypeCode, String dictId) {
		return getValue(sysDictTypeCode.getCode(), dictId);
	}
	
	private List<SysDict> findByTypeCodeValue(String typeCode, String value) {
		List<SysDict> data = sysDictDao.findByTypeCodeValue(typeCode, value);
		return data;
	}

	@Override
	public List<SysDict> findProvince() {
		return findByTypeCodeValue(SysDictTypeCode.ADDRESS.getCode(), "1");
	}

	@Override
	public List<SysDict> findCity(String province) {
		return findByTypeCodeValue(SysDictTypeCode.ADDRESS.getCode(), province);
	}

	@Override
	public List<SysDict> findArea(String city) {
		return findByTypeCodeValue(SysDictTypeCode.ADDRESS.getCode(), city);
	}
}