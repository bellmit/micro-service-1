package com.module.admin.prj.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.prj.dao.PrjDsDao;
import com.module.admin.prj.pojo.PrjDs;
import com.module.admin.prj.service.PrjDsService;
import com.system.comm.model.KvEntity;
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
		PrjDs org = get(prjDs.getPrjId(), prjDs.getCode());
		if(org == null) {
			prjDsDao.save(prjDs);
		} else {
			prjDsDao.update(prjDs);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public PrjDs get(Integer prjId, String code) {
		return prjDsDao.get(prjId, code);
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
	public ResponseFrame delete(Integer prjId, String code) {
		ResponseFrame frame = new ResponseFrame();
		prjDsDao.delete(prjId, code);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public List<KvEntity> findKvByPrjId(Integer prjId) {
		List<KvEntity> data = new ArrayList<KvEntity>();
		List<PrjDs> ds = prjDsDao.findByPrjId(prjId);
		for (PrjDs prjDs : ds) {
			data.add(new KvEntity(prjDs.getCode(), prjDs.getCode() + "(" + prjDs.getType() + ")"));
		}
		return data;
	}
}
