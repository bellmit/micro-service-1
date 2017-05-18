package com.module.admin.prj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.prj.dao.PrjInfoDao;
import com.module.admin.prj.enums.PrjInfoStatus;
import com.module.admin.prj.pojo.PrjInfo;
import com.module.admin.prj.service.PrjInfoService;
import com.system.comm.enums.Boolean;
import com.system.comm.model.KvEntity;
import com.system.comm.model.Page;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * prj_info的Service
 * @author yuejing
 * @date 2016-10-19 15:56:37
 * @version V1.0.0
 */
@Component
public class PrjInfoServiceImpl implements PrjInfoService {

	@Autowired
	private PrjInfoDao prjInfoDao;
	
	@Override
	public ResponseFrame saveOrUpdate(PrjInfo prjInfo) {
		ResponseFrame frame = new ResponseFrame();
		PrjInfo org = prjInfoDao.getCode(prjInfo.getCode());
		if(org == null) {
			if(FrameStringUtil.isEmpty(prjInfo.getName())) {
				prjInfo.setName(prjInfo.getCode());
			}
			if(FrameStringUtil.isEmpty(prjInfo.getRemark())) {
				prjInfo.setRemark("暂无");
			}
			prjInfo.setMonitorStatus(Boolean.TRUE.getCode());
			prjInfoDao.save(prjInfo);
		} else {
			prjInfo.setPrjId(org.getPrjId());
			prjInfoDao.update(prjInfo);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public PrjInfo get(Integer prjId) {
		return prjInfoDao.get(prjId);
	}

	@Override
	public PrjInfo getCode(String code) {
		return prjInfoDao.getCode(code);
	}

	@Override
	public ResponseFrame pageQuery(PrjInfo prjInfo) {
		ResponseFrame frame = new ResponseFrame();
		int total = prjInfoDao.findPrjInfoCount(prjInfo);
		List<PrjInfo> data = null;
		if(total > 0) {
			data = prjInfoDao.findPrjInfo(prjInfo);
			for (PrjInfo pi : data) {
				pi.setStatusName(PrjInfoStatus.getText(pi.getStatus()));
			}
		}
		Page<PrjInfo> page = new Page<PrjInfo>(prjInfo.getPage(), prjInfo.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(Integer prjId) {
		ResponseFrame frame = new ResponseFrame();
		prjInfoDao.delete(prjId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public List<KvEntity> findKvAll() {
		return prjInfoDao.findKvAll();
	}

	@Override
	public String getName(Integer prjId) {
		PrjInfo prjInfo = get(prjId);
		return prjInfo == null ? null : prjInfo.getName();
	}
}
