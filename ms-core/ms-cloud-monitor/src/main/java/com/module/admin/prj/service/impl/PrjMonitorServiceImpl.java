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
import com.module.admin.sys.enums.SysConfigCode;
import com.module.admin.sys.service.SysConfigService;
import com.system.comm.enums.Boolean;
import com.system.comm.model.Page;
import com.system.comm.utils.FrameStringUtil;
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
	@Autowired
	private SysConfigService sysConfigService;
	
	@Override
	public ResponseFrame saveOrUpdate(PrjMonitor prjMonitor) {
		ResponseFrame frame = new ResponseFrame();
		PrjMonitor org = prjMonitorDao.getByPrjIdRemark(prjMonitor.getPrjId(), prjMonitor.getRemark());
		if(org == null) {
			if(prjMonitor.getMonitorIs() == null) {
				prjMonitor.setMonitorIs(Boolean.TRUE.getCode());
			}
			if(prjMonitor.getMonitorFailNumRemind() == null) {
				prjMonitor.setMonitorFailNumRemind(3);
			}
			if(prjMonitor.getMonitorFailSendInterval() == null) {
				prjMonitor.setMonitorFailSendInterval(30);
			}
			if(FrameStringUtil.isEmpty(prjMonitor.getMonitorFailEmail())) {
				prjMonitor.setMonitorFailEmail(sysConfigService.getValue(SysConfigCode.PRJ_MONITOR_FAIL_EMAIL));
			}
			prjMonitorDao.save(prjMonitor);
		} else {
			prjMonitor.setPrjmId(org.getPrjmId());
			if(org.getMonitorFailNumRemind() == null) {
				prjMonitor.setMonitorFailNumRemind(3);
			}
			if(org.getMonitorFailSendInterval() == null) {
				prjMonitor.setMonitorFailSendInterval(30);
			}
			/*if(FrameStringUtil.isEmpty(org.getMonitorFailEmail())) {
				prjMonitor.setMonitorFailEmail(sysConfigService.getValue(SysConfigCode.PRJ_MONITOR_FAIL_EMAIL));
			}*/
			if(org.getMonitorIs().intValue() == Boolean.FALSE.getCode()) {
				prjMonitor.setMonitorTime(null);
			}
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

	@Override
	public PrjMonitor getService(String prjId) {
		return prjMonitorDao.getByPrjIdType(prjId, PrjMonitorType.WEB.getCode());
	}
}
