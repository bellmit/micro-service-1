package com.module.admin.ms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.ms.dao.MsSecretApiDao;
import com.module.admin.ms.pojo.MsSecretApi;
import com.module.admin.ms.service.MsSecretApiService;
import com.module.admin.prj.service.PrjInfoService;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * ms_secret的Service
 * @author yuejing
 * @date 2017-06-02 15:44:56
 * @version V1.0.0
 */
@Component
public class MsSecretApiServiceImpl implements MsSecretApiService {

	@Autowired
	private MsSecretApiDao msSecretApiDao;
	@Autowired
	private PrjInfoService prjInfoService;
	
	@Override
	public ResponseFrame save(MsSecretApi msSecretApi) {
		ResponseFrame frame = new ResponseFrame();
		MsSecretApi org = msSecretApiDao.get(msSecretApi.getCliId(), msSecretApi.getPrjId(), msSecretApi.getUrl());
		if(org == null) {
			msSecretApiDao.save(msSecretApi);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public MsSecretApi get(String cliId, Integer prjId, String url) {
		return msSecretApiDao.get(cliId, prjId, url);
	}

	@Override
	public ResponseFrame pageQuery(MsSecretApi msSecretApi) {
		msSecretApi.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = msSecretApiDao.findMsSecretApiCount(msSecretApi);
		List<MsSecretApi> data = null;
		if(total > 0) {
			data = msSecretApiDao.findMsSecretApi(msSecretApi);
			for (MsSecretApi msa : data) {
				msa.setPrjName(prjInfoService.getName(msa.getPrjId()));
			}
		}
		Page<MsSecretApi> page = new Page<MsSecretApi>(msSecretApi.getPage(), msSecretApi.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(String cliId, Integer prjId, String url) {
		ResponseFrame frame = new ResponseFrame();
		msSecretApiDao.delete(cliId, prjId, url);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public List<MsSecretApi> findByCliId(String cliId) {
		return msSecretApiDao.findByCliId(cliId);
	}

}
