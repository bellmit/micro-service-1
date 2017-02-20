package com.frame.sys.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frame.sys.dao.SysDictTypeDao;
import com.frame.sys.pojo.SysDictType;
import com.frame.sys.service.SysDictTypeService;

@Component
public class SysDictTypeServiceImpl implements SysDictTypeService {

	private static final Logger LOGGER = Logger.getLogger(SysDictTypeServiceImpl.class);
	@Autowired
	private SysDictTypeDao sysDictTypeDao;

	@Override
	public SysDictType get(String code) {
		return sysDictTypeDao.get(code);
	}

	@Override
	public void initTable() {
		if(!isExistTable()) {
			sysDictTypeDao.createTable();
			LOGGER.info("||===== 初始化 [ sys_dict_type ] 表结构成功!");
		}
	}

	private boolean isExistTable() {
		try {
			sysDictTypeDao.isExistTable();
			return true;
		} catch (Exception e) {
			LOGGER.error("表[sys_dict_type]不存在: " + e.getMessage());
		}
		return false;
	}
}