package com.module.admin.prj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.prj.dao.PrjApiDao;
import com.module.admin.prj.pojo.PrjApi;
import com.module.admin.prj.service.PrjApiService;
import com.system.comm.enums.Boolean;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseFrame;
import com.system.handle.model.ResponseCode;

/**
 * prj_api的Service
 * @author yuejing
 * @date 2017-05-18 16:00:41
 * @version V1.0.0
 */
@Component
public class PrjApiServiceImpl implements PrjApiService {

	@Autowired
	private PrjApiDao prjApiDao;
	
	@Override
	public ResponseFrame saveOrUpdate(PrjApi prjApi) {
		ResponseFrame frame = new ResponseFrame();
		PrjApi org = get(prjApi.getPrjId(), prjApi.getPath());
		if(org == null) {
			prjApi.setIsUse(Boolean.TRUE.getCode());
			prjApiDao.save(prjApi);
		} else {
			prjApi.setIsUse(Boolean.TRUE.getCode());
			prjApiDao.update(prjApi);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public PrjApi get(Integer prjId, String path) {
		return prjApiDao.get(prjId, path);
	}

	@Override
	public ResponseFrame pageQuery(PrjApi prjApi) {
		prjApi.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = prjApiDao.findPrjApiCount(prjApi);
		List<PrjApi> data = null;
		if(total > 0) {
			data = prjApiDao.findPrjApi(prjApi);
			for (PrjApi pa : data) {
				pa.setIsUseName(Boolean.getText(pa.getIsUse()));
			}
		}
		Page<PrjApi> page = new Page<PrjApi>(prjApi.getPage(), prjApi.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(Integer prjId) {
		ResponseFrame frame = new ResponseFrame();
		prjApiDao.delete(prjId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public void saveBatch(List<PrjApi> prjApis) {
		//将对应的修改为未使用
		prjApiDao.updateIsUse(prjApis.get(0).getPrjId(), prjApis.get(0).getPath(), Boolean.FALSE.getCode());
		for (PrjApi prjApi : prjApis) {
			saveOrUpdate(prjApi);
		}
	}
}
