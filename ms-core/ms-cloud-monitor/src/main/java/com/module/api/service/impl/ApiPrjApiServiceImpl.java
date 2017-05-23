package com.module.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.prj.pojo.PrjApi;
import com.module.admin.prj.pojo.PrjInfo;
import com.module.admin.prj.service.PrjApiService;
import com.module.admin.prj.service.PrjInfoService;
import com.module.api.service.ApiPrjApiService;
import com.system.comm.utils.FrameMapUtil;
import com.system.handle.model.ResponseFrame;

@Component
public class ApiPrjApiServiceImpl implements ApiPrjApiService {
	
	@Autowired
	private PrjInfoService prjInfoService;
	@Autowired
	private PrjApiService prjApiService;

	@Override
	public ResponseFrame saveBatch(String code, List<Map<String, String>> details) {
		ResponseFrame frame = new ResponseFrame();
		if(details.size() == 0) {
			frame.setCode(-2);
			frame.setMessage("没有API服务信息");
			return frame;
		}
		PrjInfo prjInfo = prjInfoService.getCode(code);
		if(prjInfo == null) {
			frame.setCode(-3);
			frame.setMessage("不存在该项目");
			return frame;
		}
		List<PrjApi> prjApis = new ArrayList<PrjApi>();
		for (Map<String, String> map : details) {
			PrjApi api = new PrjApi(prjInfo.getPrjId(), FrameMapUtil.getString(map, "path"),
					FrameMapUtil.getString(map, "name"),
					FrameMapUtil.getString(map, "method"),
					FrameMapUtil.getString(map, "params"),
					FrameMapUtil.getString(map, "response"));
			prjApis.add(api);
		}
		prjApiService.saveBatch(prjApis);
		frame.setSucc();
		return frame;
	}

}