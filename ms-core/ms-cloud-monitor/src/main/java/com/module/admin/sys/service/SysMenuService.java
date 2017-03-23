package com.module.admin.sys.service;

import java.util.List;

import com.module.admin.sys.pojo.SysMenu;

public interface SysMenuService {

	/**
	 * 根据菜单编号字符串集合获取集合对象[,分隔]
	 * @param menuIds
	 * @return
	 */
	public List<SysMenu> findByMenuIds(String menuIds);

	/**
	 * 根据菜单编号字符串集合获取树形的集合对象[,分隔]
	 * @param menuIds
	 * @return
	 */
	public List<SysMenu> findTreeByMenuIds(String menuIds);
	
	/**
	 * 获取所有菜单  tree
	 * @param menuIds
	 * @return
	 */
	public List<SysMenu> findAllTreeMenu();

}
