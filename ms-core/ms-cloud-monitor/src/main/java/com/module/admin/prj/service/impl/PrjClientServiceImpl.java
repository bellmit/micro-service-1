package com.module.admin.prj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.cli.pojo.CliInfo;
import com.module.admin.cli.service.CliInfoService;
import com.module.admin.prj.dao.PrjClientDao;
import com.module.admin.prj.enums.PrjClientStatus;
import com.module.admin.prj.pojo.PrjClient;
import com.module.admin.prj.pojo.PrjVersion;
import com.module.admin.prj.service.PrjClientService;
import com.module.admin.prj.service.PrjVersionService;
import com.system.comm.enums.Boolean;
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
	@Autowired
	private PrjVersionService prjVersionService;

	@Override
	public ResponseFrame saveOrUpdate(PrjClient prjClient) {
		ResponseFrame frame = new ResponseFrame();
		PrjVersion pv = prjVersionService.get(prjClient.getPrjId(), prjClient.getVersion());
		if(pv == null) {
			frame.setCode(-2);
			frame.setMessage("不存在该版本");
			return frame;
		}
		if(pv != null && pv.getIsRelease().intValue() == Boolean.FALSE.getCode()) {
			frame.setCode(-3);
			frame.setMessage("该版本没有设置为发布");
			return frame;
		}
		PrjClient org = get(prjClient.getPrjId(), prjClient.getVersion(), prjClient.getClientId());
		if(org == null) {
			/*frame.setCode(-4);
			frame.setMessage("已经添加过了");
			return frame;*/
			prjClient.setStatus(PrjClientStatus.WAIT.getCode());
			prjClientDao.save(prjClient);
		} else {
			prjClientDao.update(prjClient);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public PrjClient get(Integer prjId, String version, String clientId) {
		return prjClientDao.get(prjId, version, clientId);
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
	public ResponseFrame delete(Integer prjId, String version, String clientId) {
		ResponseFrame frame = new ResponseFrame();
		prjClientDao.delete(prjId, version, clientId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	/*@Override
	public List<PrjInfo> findByClientId(String clientId) {
		return prjClientDao.findByClientId(clientId, PrjInfoStatus.NORMAL.getCode());
	}*/

	@Override
	public void updateStatus(String clientId, Integer prjId, String version, Integer status, String statusMsg) {
		prjClientDao.updateStatus(clientId, prjId, version, status, statusMsg);
	}

	@Override
	public List<CliInfo> findByPrjId(Integer prjId, String version, String clientId) {
		return prjClientDao.findByPrjId(prjId, version, clientId);
	}

	@Override
	public ResponseFrame updateShellScript(String clientId, Integer prjId, String version,
			String shellScript) {
		ResponseFrame frame = new ResponseFrame();
		prjClientDao.updateShellScript(clientId, prjId, version, shellScript);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public PrjClient getLastByPrjIdClientId(Integer prjId, String clientId) {
		return prjClientDao.getLastByPrjIdClientId(prjId, clientId);
	}

	@Override
	public List<PrjClient> findByPrjIdVersion(Integer prjId, String version) {
		return prjClientDao.findByPrjIdVersion(prjId, version);
	}
}
