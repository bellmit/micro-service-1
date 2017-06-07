package com.module.admin.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.module.admin.sys.dao.SysConfigDao;
import com.module.admin.sys.enums.SysConfigCode;
import com.module.admin.sys.pojo.SysConfig;
import com.module.admin.sys.service.SysConfigService;
import com.system.comm.model.Page;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * sys_config的Service
 * @author yuejing
 * @date 2016-10-19 13:50:15
 * @version V1.0.0
 */
@Component
public class SysConfigServiceImpl implements SysConfigService {

	@Autowired
	private SysConfigDao sysConfigDao;

	@Override
	public ResponseFrame saveOrUpdate(SysConfig sysConfig) {
		ResponseFrame frame = new ResponseFrame();
		SysConfig org = get(sysConfig.getCode());
		if(org == null) {
			sysConfigDao.save(sysConfig);
		} else {
			/*if(SysConfigCode.CONFIG_CLIENT_ID.getCode().equals(sysConfig.getCode())
					|| SysConfigCode.CONFIG_CLIENT_TOKEN.getCode().equals(sysConfig.getCode())) {
				String clientId = getValue(SysConfigCode.CONFIG_CLIENT_ID);
				String token = getValue(SysConfigCode.CONFIG_CLIENT_TOKEN);
				AuthUtil.updateAuthClient(new AuthClient(clientId, SysConfigCode.CONFIG_CLIENT_ID.getName(),
						"http://xxxx:", token, ""));
			}*/
			sysConfigDao.update(sysConfig);
		}
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public SysConfig get(String code) {
		return sysConfigDao.get(code);
	}

	@Override
	public String getValue(SysConfigCode sysConfigCode) {
		SysConfig config = sysConfigDao.get(sysConfigCode.getCode());
		return config != null ? config.getValue() : null;
	}

	@Override
	public ResponseFrame pageQuery(SysConfig sysConfig) {
		ResponseFrame frame = new ResponseFrame();
		int total = sysConfigDao.findSysConfigCount(sysConfig);
		List<SysConfig> data = null;
		if(total > 0) {
			data = sysConfigDao.findSysConfig(sysConfig);
		}
		Page<SysConfig> page = new Page<SysConfig>(sysConfig.getPage(), sysConfig.getSize(), total, data);
		frame.setBody(page);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}

	@Override
	public ResponseFrame delete(String code) {
		ResponseFrame frame = new ResponseFrame();
		sysConfigDao.delete(code);
		frame.setCode(ResponseCode.SUCC.getCode());
		return frame;
	}
}
