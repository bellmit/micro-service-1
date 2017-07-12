package com.module.admin.ms.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import com.module.admin.ms.dao.MsConfigDao;
import com.module.admin.ms.pojo.MsConfig;
import com.module.admin.ms.service.MsConfigService;
import com.module.admin.ms.service.MsConfigValueService;
import com.module.comm.constants.ConfigCons;
import com.system.auth.AuthUtil;
import com.system.comm.enums.Boolean;
import com.system.comm.model.Page;
import com.system.comm.utils.FrameHttpUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * ms_config的Service
 * @author yuejing
 * @date 2017-04-21 16:02:31
 * @version V1.0.0
 */
@Component
public class MsConfigServiceImpl implements MsConfigService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MsConfigServiceImpl.class);
	@Autowired
	private MsConfigDao msConfigDao;
	@Autowired
	private MsConfigValueService msConfigValueService;
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Override
	public ResponseFrame saveOrUpdate(MsConfig msConfig) {
		ResponseFrame frame = new ResponseFrame();
		if(msConfig.getConfigId() == null) {
			msConfigDao.save(msConfig);
		} else {
			msConfigDao.update(msConfig);
			refreshCfg(msConfig.getConfigId());
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public MsConfig get(Integer configId) {
		return msConfigDao.get(configId);
	}

	@Override
	public ResponseFrame pageQuery(MsConfig msConfig) {
		msConfig.setDefPageSize();
		ResponseFrame frame = new ResponseFrame();
		int total = msConfigDao.findMsConfigCount(msConfig);
		List<MsConfig> data = null;
		if(total > 0) {
			data = msConfigDao.findMsConfig(msConfig);
			for (MsConfig mc : data) {
				mc.setIsUseName(Boolean.getText(mc.getIsUse()));
			}
		}
		Page<MsConfig> page = new Page<MsConfig>(msConfig.getPage(), msConfig.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
	
	@Override
	public ResponseFrame delete(Integer configId) {
		ResponseFrame frame = new ResponseFrame();
		msConfigDao.delete(configId);
		
		//删除属性值
		msConfigValueService.delete(configId);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public List<MsConfig> findUse() {
		return msConfigDao.findUse();
	}

	@Override
	public List<MsConfig> findAll() {
		return msConfigDao.findAll();
	}

	@Override
	public void refreshCfg(Integer configId) {
		MsConfig config = get(configId);
		if(Boolean.FALSE.getCode() == config.getIsUse().intValue()) {
			return;
		}
		int index = config.getName().indexOf(".");
		String serviceId = config.getName().substring(0, index);
		ResponseFrame frame = refresh(serviceId);
		if(ResponseCode.SUCC.getCode() != frame.getCode().intValue()) {
			LOGGER.error("刷新服务配置失败!");
		}
	}
	
	public ResponseFrame refresh(String serviceId) {
		ResponseFrame frame = new ResponseFrame();
		try {
			List<ServiceInstance> list = discoveryClient.getInstances(serviceId);
			for (ServiceInstance instance : list) {
				String baseUrl = instance.getUri().toString();
				Map<String, Object> params = new HashMap<String, Object>();
				String time = String.valueOf(System.currentTimeMillis());
				params.put("clientId", ConfigCons.clientId);
				params.put("time", time);
				params.put("sign", AuthUtil.auth(ConfigCons.clientId, time, ConfigCons.sercret));
				String result = FrameHttpUtil.post(baseUrl + "/refresh", params);
				if(!"[]".equals(result)) {
					LOGGER.error(baseUrl + "刷新失败");
				}
			}
			
			frame.setSucc();
			return frame;
		} catch (IOException e) {
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}
