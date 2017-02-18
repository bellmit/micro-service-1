package com.system.rmi.service;

import java.util.List;

import com.system.rmi.model.RmiModel;

/**
 * 检测rmi机器是否活动的信息
 * @author yuejing
 *
 */
public interface DataCenterService {

	/**
	 * 添加节点
	 * @param rmiModel
	 */
	public abstract void add(RmiModel rmiModel);
	
	/**
	 * 删除节点
	 * @param url
	 */
	public abstract void del(String url);
	
	/**
	 * 根据获取集合
	 * @return
	 */
	public abstract List<RmiModel> list();
}
