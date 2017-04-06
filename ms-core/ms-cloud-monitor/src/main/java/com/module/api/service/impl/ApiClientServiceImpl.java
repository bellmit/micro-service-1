package com.module.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.cli.service.CliInfoService;
import com.module.admin.prj.service.PrjClientService;
import com.module.admin.prj.service.PrjVersionService;
import com.module.api.service.ApiClientService;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * prj_client的Service
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
@Component
public class ApiClientServiceImpl implements ApiClientService {
	
	@Autowired
	private PrjClientService prjClientService;
	@Autowired
	private PrjVersionService prjVersionService;
	@Autowired
	private CliInfoService cliInfoService;

	/*@Override
	public ResponseFrame findRelease(String clientId) {
		ResponseFrame frame = new ResponseFrame();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		List<PrjInfo> list = prjClientService.findByClientId(clientId);
		for (PrjInfo pi : list) {
			PrjVersion version = prjVersionService.get(pi.getPrjId(), pi.getReleaseVersion());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("prjId", pi.getPrjId());
			map.put("code", pi.getCode());
			map.put("name", pi.getName());
			map.put("version", pi.getReleaseVersion());
			//下载路径
			map.put("pathUrl", version.getPathUrl());
			//容器类型
			map.put("container", pi.getContainer());
			//执行的shell命令
			map.put("shellScript", pi.getClientShellScript());
			data.add(map);
		}
		
		frame.setBody(data);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}*/

	@Override
	public ResponseFrame updateRelease(String clientId, Integer prjId, String version, Integer status, String statusMsg) {
		ResponseFrame frame = new ResponseFrame();
		prjClientService.updateStatus(clientId, prjId, version, status, statusMsg);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public ResponseFrame updateHeartbeat(String clientId) {
		ResponseFrame frame = new ResponseFrame();
		cliInfoService.updateActivityTime(clientId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

}