package com.system.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.system.rmi.model.RmiModel;

/**
 * 检测rmi机器是否活动的信息
 * @author yuejing
 *
 */
public interface RmiDataCenterService extends Remote {

	/**
	 * 添加节点
	 * @param rmiModel
	 */
	public abstract void add(RmiModel rmiModel) throws RemoteException;
	
	/**
	 * 删除节点
	 * @param url
	 */
	public abstract void del(String url) throws RemoteException;
	
	/**
	 * 根据获取集合
	 * @return
	 */
	public abstract List<RmiModel> list() throws RemoteException;
}
