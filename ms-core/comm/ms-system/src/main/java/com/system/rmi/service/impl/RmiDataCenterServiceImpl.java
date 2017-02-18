package com.system.rmi.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.rmi.model.RmiModel;
import com.system.rmi.service.DataCenterService;
import com.system.rmi.service.RmiDataCenterService;

/**
 * 服务端使用
 * @author yuejing
 *
 */
@SuppressWarnings("serial")
public class RmiDataCenterServiceImpl extends UnicastRemoteObject implements RmiDataCenterService {

	private DataCenterService getDcService() {
		return FrameSpringBeanUtil.getBean(DataCenterService.class);
	}

	public RmiDataCenterServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public void add(RmiModel rmiModel) throws RemoteException {
		getDcService().add(rmiModel);
	}

	@Override
	public void del(String url) throws RemoteException {
		getDcService().del(url);
	}

	@Override
	public List<RmiModel> list() throws RemoteException {
		return getDcService().list();
	}

}
