package com.module.admin.prj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.prj.dao.PrjDsDao;
import com.module.admin.prj.pojo.PrjDs;
import com.module.admin.prj.service.PrjDsService;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseFrame;
import com.system.handle.model.ResponseCode;

/**
 * prj_ds的Service
 * @author yuejing
 * @date 2017-06-21 14:43:52
 * @version V1.0.0
 */
@Component
public class PrjDsServiceImpl implements PrjDsService {

	@Autowired
	private PrjDsDao prjDsDao;
	
	@Override
	public ResponseFrame saveOrUpdate(PrjDs prjDs) {
		ResponseFrame frame = new ResponseFrame();
		PrjDs org = get(prjDs.getCode());
		if(org == null) {
			prjDsDao.save(prjDs);
		} else {
			prjDsDao.update(prjDs);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public PrjDs get(String code) {
		return prjDsDao.get(code);
	}

	@Override
	public ResponseFrame pageQuery(PrjDs prjDs) {
		prjDs.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = prjDsDao.findPrjDsCount(prjDs);
		List<PrjDs> data = null;
		if(total > 0) {
			data = prjDsDao.findPrjDs(prjDs);
		}
		Page<PrjDs> page = new Page<PrjDs>(prjDs.getPage(), prjDs.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(String code) {
		ResponseFrame frame = new ResponseFrame();
		prjDsDao.delete(code);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}
