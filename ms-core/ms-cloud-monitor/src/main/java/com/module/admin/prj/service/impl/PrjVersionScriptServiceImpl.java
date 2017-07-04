package com.module.admin.prj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.prj.dao.PrjVersionScriptDao;
import com.module.admin.prj.pojo.PrjVersionScript;
import com.module.admin.prj.service.PrjVersionScriptService;
import com.system.comm.enums.Boolean;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseFrame;
import com.system.handle.model.ResponseCode;

/**
 * prj_version_script的Service
 * @author yuejing
 * @date 2017-07-04 09:46:06
 * @version V1.0.0
 */
@Component
public class PrjVersionScriptServiceImpl implements PrjVersionScriptService {

	@Autowired
	private PrjVersionScriptDao prjVersionScriptDao;
	
	@Override
	public ResponseFrame saveOrUpdate(PrjVersionScript prjVersionScript) {
		ResponseFrame frame = new ResponseFrame();
		if(prjVersionScript.getPvsId() == null) {
			if(prjVersionScript.getIsUp() == null) {
				prjVersionScript.setIsUp(Boolean.FALSE.getCode());
			}
			prjVersionScriptDao.save(prjVersionScript);
		} else {
			prjVersionScriptDao.update(prjVersionScript);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public PrjVersionScript get(Integer pvsId) {
		return prjVersionScriptDao.get(pvsId);
	}

	@Override
	public ResponseFrame pageQuery(PrjVersionScript prjVersionScript) {
		prjVersionScript.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = prjVersionScriptDao.findPrjVersionScriptCount(prjVersionScript);
		List<PrjVersionScript> data = null;
		if(total > 0) {
			data = prjVersionScriptDao.findPrjVersionScript(prjVersionScript);
			for (PrjVersionScript pvs : data) {
				pvs.setIsUpName(Boolean.getText(pvs.getIsUp()));
			}
		}
		Page<PrjVersionScript> page = new Page<PrjVersionScript>(prjVersionScript.getPage(), prjVersionScript.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(Integer pvsId) {
		ResponseFrame frame = new ResponseFrame();
		prjVersionScriptDao.delete(pvsId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public void updateIsUp(Integer pvsId, Integer isUp) {
		prjVersionScriptDao.updateIsUp(pvsId, isUp);
	}
}
