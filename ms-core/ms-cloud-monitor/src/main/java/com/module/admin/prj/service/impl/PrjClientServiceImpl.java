package com.module.admin.prj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.cli.pojo.CliInfo;
import com.module.admin.cli.service.CliInfoService;
import com.module.admin.prj.dao.PrjClientDao;
import com.module.admin.prj.enums.PrjClientStatus;
import com.module.admin.prj.pojo.PrjClient;
import com.module.admin.prj.service.PrjClientService;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * 项目客户端的Service
 * @author yuejing
 * @date 2016-10-20 17:54:59
 * @version V1.0.0
 */
@Component
public class PrjClientServiceImpl implements PrjClientService {

	@Autowired
	private PrjClientDao prjClientDao;
	@Autowired
	private CliInfoService cliInfoService;
	
	@Override
	public ResponseFrame saveOrUpdate(PrjClient prjClient) {
		ResponseFrame frame = new ResponseFrame();
		PrjClient org = get(prjClient.getPrjId(), prjClient.getClientId());
		if(org != null) {
			frame.setCode(-2);
			frame.setMessage("已经添加过了");
			return frame;
		}
		prjClient.setStatus(PrjClientStatus.WAIT.getCode());
		prjClientDao.save(prjClient);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public PrjClient get(Integer prjId, String clientId) {
		return prjClientDao.get(prjId, clientId);
	}

	@Override
	public ResponseFrame pageQuery(PrjClient prjClient) {
		ResponseFrame frame = new ResponseFrame();
		int total = prjClientDao.findPrjClientCount(prjClient);
		List<PrjClient> data = null;
		if(total > 0) {
			data = prjClientDao.findPrjClient(prjClient);
			for (PrjClient pc : data) {
				pc.setStatusName(PrjClientStatus.getText(pc.getStatus()));
				CliInfo cliInfo = cliInfoService.get(pc.getClientId());
				if(cliInfo != null) {
					pc.setIp(cliInfo.getIp());
					pc.setPort(cliInfo.getPort());
				}
			}
		}
		Page<PrjClient> page = new Page<PrjClient>(prjClient.getPage(), prjClient.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(Integer prjId, String clientId) {
		ResponseFrame frame = new ResponseFrame();
		prjClientDao.delete(prjId, clientId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	/*@Override
	public List<PrjInfo> findByClientId(String clientId) {
		return prjClientDao.findByClientId(clientId, PrjInfoStatus.NORMAL.getCode());
	}*/

	@Override
	public void updateStatus(String clientId, Integer prjId, Integer status, String statusMsg) {
		prjClientDao.updateStatus(clientId, prjId, status, statusMsg);
	}

	@Override
	public List<CliInfo> findByPrjId(Integer prjId) {
		return prjClientDao.findByPrjId(prjId);
	}

	@Override
	public ResponseFrame updateShellScript(String clientId, Integer prjId,
			String shellScript) {
		ResponseFrame frame = new ResponseFrame();
		prjClientDao.updateShellScript(clientId, prjId, shellScript);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}
