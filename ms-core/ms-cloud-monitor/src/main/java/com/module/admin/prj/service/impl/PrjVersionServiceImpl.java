package com.module.admin.prj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.prj.dao.PrjVersionDao;
import com.module.admin.prj.pojo.PrjClient;
import com.module.admin.prj.pojo.PrjVersion;
import com.module.admin.prj.service.PrjClientService;
import com.module.admin.prj.service.PrjInfoService;
import com.module.admin.prj.service.PrjVersionService;
import com.system.comm.enums.Boolean;
import com.system.comm.model.KvEntity;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * prj_version的Service
 * @author yuejing
 * @date 2016-10-19 15:55:36
 * @version V1.0.0
 */
@Component
public class PrjVersionServiceImpl implements PrjVersionService {

	@Autowired
	private PrjVersionDao prjVersionDao;
	@Autowired
	private PrjInfoService prjInfoService;
	@Autowired
	private PrjClientService prjClientService;

	@Override
	public ResponseFrame saveOrUpdate(PrjVersion prjVersion) {
		ResponseFrame frame = new ResponseFrame();
		PrjVersion org = get(prjVersion.getPrjId(), prjVersion.getVersion());
		/*if(Boolean.TRUE.getCode() == prjVersion.getIsRelease().intValue()) {
			prjVersionDao.updateNotRelease();
		}*/
		if(org == null) {
			prjVersionDao.save(prjVersion);
			//根据回滚版本，设置发布到的客户端
			List<PrjClient> clients = prjClientService.findByPrjIdVersion(prjVersion.getPrjId(), prjVersion.getRbVersion());
			for (PrjClient client : clients) {
				PrjClient prjClient = new PrjClient();
				prjClient.setPrjId(prjVersion.getPrjId());
				prjClient.setClientId(client.getClientId());
				prjClient.setReleaseTime(client.getReleaseTime());
				prjClient.setShellScript(client.getShellScript());
				prjClient.setVersion(prjVersion.getVersion());
				prjClient.setLogPath(client.getLogPath());
				prjClientService.saveOrUpdate(prjClient);
			}
		} else {
			prjVersionDao.update(prjVersion);
		}
		/*if(Boolean.FALSE.getCode() == prjVersion.getIsRelease().intValue()) {
			//判断是否都没有发布版本
			int num = prjVersionDao.getCountByIsRelease(prjVersion.getPrjId(), Boolean.TRUE.getCode());
			if(num == 0) {
				//修改项目的发布的版本号
				prjInfoService.updateReleaseVersion(prjVersion.getPrjId(), null);
			}
		}*/
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public PrjVersion get(Integer prjId, String version) {
		return prjVersionDao.get(prjId, version);
	}

	@Override
	public ResponseFrame pageQuery(PrjVersion prjVersion) {
		ResponseFrame frame = new ResponseFrame();
		int total = prjVersionDao.findPrjVersionCount(prjVersion);
		List<PrjVersion> data = null;
		if(total > 0) {
			data = prjVersionDao.findPrjVersion(prjVersion);
			for (PrjVersion pv : data) {
				pv.setIsReleaseName(Boolean.getText(pv.getIsRelease()));
			}
		}
		Page<PrjVersion> page = new Page<PrjVersion>(prjVersion.getPage(), prjVersion.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public ResponseFrame delete(Integer prjId, String version) {
		ResponseFrame frame = new ResponseFrame();
		prjVersionDao.delete(prjId, version);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public List<KvEntity> findKvAll() {
		return prjVersionDao.findKvAll();
	}

	@Override
	public List<PrjVersion> findByPrjId(Integer prjId) {
		return prjVersionDao.findByPrjId(prjId);
	}
}
