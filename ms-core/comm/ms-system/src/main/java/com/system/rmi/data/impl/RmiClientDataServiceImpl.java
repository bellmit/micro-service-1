package com.system.rmi.data.impl;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.system.rmi.RmiConsumer;
import com.system.rmi.data.RmiClientDataService;
import com.system.rmi.model.RmiModel;
import com.system.rmi.service.RmiDataCenterService;
import com.system.rmi.service.impl.RmiDataCenterServiceImpl;

@Component
public class RmiClientDataServiceImpl implements RmiClientDataService {

	private static final Logger LOGGER = Logger.getLogger(RmiClientDataServiceImpl.class);

	private RmiDataCenterService getRdcService() {
		RmiConsumer consumer = new RmiConsumer();
		RmiDataCenterService rdcService = consumer.lookup(RmiDataCenterServiceImpl.class.getName());
		if(rdcService == null) {
			for (RmiModel rm : RmiConsumer.rmiServers) {
				try {
					rdcService = (RmiDataCenterService) Naming.lookup(rm.getUrl());
				} catch (Exception e) {
					LOGGER.error("getRdcService异常" + e.getMessage(), e);
				}
				if(rdcService != null) {
					break;
				}
			}
		}

		/*RmiConsumer consumer = new RmiConsumer();
		RmiDataCenterService rdcService = consumer.lookup(RmiDataCenterServiceImpl.class.getName());*/
		return rdcService;
	}

	@Override
	public void add(RmiModel rmiModel) {
		try {
			getRdcService().add(rmiModel);
		} catch (RemoteException e) {
			LOGGER.error("add异常" + e.getMessage(), e);
		}
	}

	@Override
	public void del(String url) {
		try {
			getRdcService().del(url);
		} catch (RemoteException e) {
			LOGGER.error("del异常" + e.getMessage(), e);
		}
	}

	@Override
	public List<RmiModel> list() {
		try {
			return getRdcService().list();
		} catch (RemoteException e) {
			LOGGER.error("list异常" + e.getMessage(), e);
		}
		return new ArrayList<RmiModel>();
	}

}