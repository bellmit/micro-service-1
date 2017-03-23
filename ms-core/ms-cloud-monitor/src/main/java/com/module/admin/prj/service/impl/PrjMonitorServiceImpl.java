package com.module.admin.prj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.prj.dao.PrjMonitorDao;
import com.module.admin.prj.enums.PrjMonitorMonitorStatus;
import com.module.admin.prj.enums.PrjMonitorType;
import com.module.admin.prj.pojo.PrjMonitor;
import com.module.admin.prj.service.PrjInfoService;
import com.module.admin.prj.service.PrjMonitorService;
import com.system.comm.enums.Boolean;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * prj_monitor的Service
 * @author yuejing
 * @date 2016-11-30 13:30:00
 * @version V1.0.0
 */
@Component
public class PrjMonitorServiceImpl implements PrjMonitorService {

	@Autowired
	private PrjMonitorDao prjMonitorDao;
	@Autowired
	private PrjInfoService prjInfoService;
	
	@Override
	public ResponseFrame saveOrUpdate(PrjMonitor prjMonitor) {
		ResponseFrame frame = new ResponseFrame();
		PrjMonitor org = prjMonitorDao.getByPrjIdRemark(prjMonitor.getPrjId(), prjMonitor.getRemark());
		if(org == null) {
			prjMonitorDao.save(prjMonitor);
		} else {
			prjMonitor.setPrjmId(org.getPrjmId());
			prjMonitorDao.update(prjMonitor);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public PrjMonitor get(Integer prjmId) {
		return prjMonitorDao.get(prjmId);
	}

	@Override
	public ResponseFrame pageQuery(PrjMonitor prjMonitor) {
		ResponseFrame frame = new ResponseFrame();
		int total = prjMonitorDao.findPrjMonitorCount(prjMonitor);
		List<PrjMonitor> data = null;
		if(total > 0) {
			data = prjMonitorDao.findPrjMonitor(prjMonitor);
			for (PrjMonitor pm : data) {
				pm.setPrjName(prjInfoService.getName(pm.getPrjId()));
				pm.setTypeName(PrjMonitorType.getText(pm.getType()));
				pm.setMonitorIsName(Boolean.getText(pm.getMonitorIs()));
				pm.setMonitorStatusName(PrjMonitorMonitorStatus.getText(pm.getMonitorStatus()));
			}
		}
		Page<PrjMonitor> page = new Page<PrjMonitor>(prjMonitor.getPage(), prjMonitor.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(Integer prjmId) {
		ResponseFrame frame = new ResponseFrame();
		prjMonitorDao.delete(prjmId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public List<PrjMonitor> findMonitor() {
		List<PrjMonitor> data = prjMonitorDao.findMonitor();
		for (PrjMonitor pm : data) {
			pm.setPrjName(prjInfoService.getName(pm.getPrjId()));
			pm.setTypeName(PrjMonitorType.getText(pm.getType()));
			pm.setMonitorIsName(Boolean.getText(pm.getMonitorIs()));
			pm.setMonitorStatusName(PrjMonitorMonitorStatus.getText(pm.getMonitorStatus()));
		}
		return data;
	}

	@Override
	public void updateMonitorSucc(Integer prjmId) {
		prjMonitorDao.updateMonitorStatus(prjmId, PrjMonitorMonitorStatus.NORMAL.getCode(), 0);
	}

	@Override
	public void updateMonitorFail(Integer prjmId, Integer monitorFailNum) {
		prjMonitorDao.updateMonitorStatus(prjmId, PrjMonitorMonitorStatus.ERROR.getCode(), monitorFailNum);
	}

	@Override
	public void updateMonitorFailSendInfo(Integer prjmId) {
		prjMonitorDao.updateMonitorFailSendTime(prjmId);
	}
}
