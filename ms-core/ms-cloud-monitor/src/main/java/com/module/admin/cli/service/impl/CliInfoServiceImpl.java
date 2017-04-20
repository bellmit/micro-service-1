package com.module.admin.cli.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.cli.dao.CliInfoDao;
import com.module.admin.cli.enums.CliInfoActivityStatus;
import com.module.admin.cli.enums.CliInfoStatus;
import com.module.admin.cli.pojo.CliInfo;
import com.module.admin.cli.service.CliInfoService;
import com.system.comm.model.KvEntity;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * cli_info的Service
 * @author yuejing
 * @date 2016-10-20 17:55:37
 * @version V1.0.0
 */
@Component
public class CliInfoServiceImpl implements CliInfoService {

	@Autowired
	private CliInfoDao cliInfoDao;
	
	@Override
	public ResponseFrame saveOrUpdate(CliInfo cliInfo) {
		ResponseFrame frame = new ResponseFrame();
		CliInfo org = get(cliInfo.getClientId());
		if(org == null) {
			cliInfoDao.save(cliInfo);
		} else {
			cliInfoDao.update(cliInfo);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public CliInfo get(String clientId) {
		return cliInfoDao.get(clientId);
	}

	@Override
	public ResponseFrame pageQuery(CliInfo cliInfo) {
		ResponseFrame frame = new ResponseFrame();
		int total = cliInfoDao.findCliInfoCount(cliInfo);
		List<CliInfo> data = null;
		if(total > 0) {
			data = cliInfoDao.findCliInfo(cliInfo);
			for (CliInfo ci : data) {
				ci.setStatusName(CliInfoStatus.getText(ci.getStatus()));
				ci.setActivityStatusName(CliInfoActivityStatus.getText(ci.getActivityStatus()));
			}
		}
		Page<CliInfo> page = new Page<CliInfo>(cliInfo.getPage(), cliInfo.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(String clientId) {
		ResponseFrame frame = new ResponseFrame();
		cliInfoDao.delete(clientId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public ResponseFrame find(CliInfo cliInfo) {
		ResponseFrame frame = new ResponseFrame();
		List<CliInfo> data = cliInfoDao.find(cliInfo);
		frame.setBody(data);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public void updateActivityTime(String clientId) {
		cliInfoDao.updateActivityTime(clientId);
	}

	@Override
	public void updateActivityStatusError() {
		cliInfoDao.updateActivityStatusNormal(CliInfoActivityStatus.NORMAL.getCode());
		cliInfoDao.updateActivityStatusError(CliInfoActivityStatus.ERROR.getCode());
	}

	@Override
	public List<CliInfo> findAll() {
		return cliInfoDao.findAll();
	}

	@Override
	public List<KvEntity> findKvAll() {
		List<KvEntity> data = new ArrayList<KvEntity>();
		List<CliInfo> list = findAll();
		for (CliInfo cliInfo : list) {
			data.add(new KvEntity(cliInfo.getClientId(), cliInfo.getName()));
		}
		return data;
	}
}
