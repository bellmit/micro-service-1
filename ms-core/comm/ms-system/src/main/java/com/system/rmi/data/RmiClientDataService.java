package com.system.rmi.data;

import java.util.List;

import com.system.rmi.model.RmiModel;

/**
 * 保存信息的接口
 * @author yuejing
 *
 */
public interface RmiClientDataService {

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
