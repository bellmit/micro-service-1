package com.module.admin.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.module.admin.sys.enums.SysMenuType;
import com.module.admin.sys.pojo.SysMenu;
import com.module.admin.sys.service.SysMenuService;
import com.system.comm.utils.FrameStringUtil;

/**
 * 系统菜单
 * @author duanbin
 * @date 2016年5月5日 下午12:22:58
 * @version V1.0.0
 */
@Component
public class SysMenuServiceImpl implements SysMenuService {
	/**
	 * 菜单
	 */
	private static Map<Integer, SysMenu> menuMap = new HashMap<Integer, SysMenu>();
	private static List<SysMenu> treeList = new ArrayList<SysMenu>();

	static {
		SysMenu cldMenu = null;
		SysMenu menu = addParent(1000, SysMenuType.MENU, "系统管理", "fa fa-user", null, 1);
		menu.addCldMenu(addCld(1001, 1000, SysMenuType.MENU, "部门管理", "fa fa-circle-o", "/sysRole/f-view/manager.shtml", 1));
		cldMenu = addCld(1002, 1000, SysMenuType.MENU, "员工管理", "fa fa-circle-o", "/sysUser/f-view/manager.shtml", 1);
		menu.addCldMenu(cldMenu);
		cldMenu.addCldMenu(addCld(1101, 1002, SysMenuType.FUNCTION, "查询", "fa fa-circle-o", "", 1));
		cldMenu.addCldMenu(addCld(1102, 1002, SysMenuType.FUNCTION, "修改", "fa fa-circle-o", "", 1));
		/*		
		menu = addParent(20150, SysMenuType.MENU, "牛人牛基", "fa fa-server", null, 1);
		menu.addCldMenu(addCld(20151, 20150, SysMenuType.MENU, "app首页-广告", "fa fa-circle-o", "/nrnjSysAppConfig/f-view/advManager.htm", 1));
		menu.addCldMenu(addCld(20152, 20150, SysMenuType.MENU, "app首页-导航按钮", "fa fa-circle-o", "/nrnjSysAppConfig/f-view/navBtnManager.htm", 1));
		menu.addCldMenu(addCld(20153, 20150, SysMenuType.MENU, "app首页-排行相关", "fa fa-circle-o", "/nrnjSysAppConfig/f-view/rankManager.htm", 1));
		menu.addCldMenu(addCld(20154, 20150, SysMenuType.MENU, "app首页-引导", "fa fa-circle-o", "/nrnjSysAppConfig/f-view/guideManager.htm", 1));

		menu.addCldMenu(addCld(20155, 20150, SysMenuType.MENU, "基金-基金管理", "fa fa-bar-chart-o", "/nrnjFundInfo/f-view/manager.htm", 1));
		menu.addCldMenu(addCld(20156, 20150, SysMenuType.MENU, "牛人-牛人榜", "fa fa-street-view", "/nrnjFundManager/f-view/manager.htm", 1));
		menu.addCldMenu(addCld(20157, 20150, SysMenuType.MENU, "主题-主题组合管理", "fa fa-area-chart", "/nrnjFundSubject/f-view/manager.htm", 1));
		menu.addCldMenu(addCld(20158, 20150, SysMenuType.MENU, "基民-用户管理", "fa fa-server", "/nrnjUserManager/f-view/userManager.htm", 1));
		menu.addCldMenu(addCld(20159, 20150, SysMenuType.MENU, "系统-反馈管理", "fa fa-book", "/nrnjFeedbackManager/f-view/feedbackManager.htm", 1));
		menu.addCldMenu(addCld(20160, 20150, SysMenuType.MENU, "系统-消息管理", "fa fa-comments-o", "/nrnjUserMsgManager/f-view/userMsgManager.htm", 1));
		menu.addCldMenu(addCld(20161, 20150, SysMenuType.MENU, "app-版本管理", "fa fa-folder-open", "/nrnjSysAppVersion/f-view/manager.htm", 1));
		
		menu = addParent(20200, SysMenuType.MENU, "云研报", "fa fa-server", null, 1);
		menu.addCldMenu(addCld(20201, 20200, SysMenuType.MENU, "博文-博文管理", "fa fa-circle-o", "/yybBlogManager/f-view/blogManager.htm", 1));
		menu.addCldMenu(addCld(20202, 20200, SysMenuType.MENU, "博文-重点团队管理", "fa fa-circle-o", "/yybBlogMainUser/f-view/blogMainUserManager.htm", 1));
		menu.addCldMenu(addCld(20203, 20200, SysMenuType.MENU, "博文-重点团队博文", "fa fa-circle-o", "/yybBlogMainUser/f-view/blogMainUserBlog.htm", 1));
		menu.addCldMenu(addCld(20204, 20200, SysMenuType.MENU, "云研报-用户管理", "fa fa-circle-o", "/yybUserManager/f-view/userManager.htm", 1));
		 */
	}
	public static Map<Integer, SysMenu> getMapMenus() {
		return menuMap;
	}

	/**
	 * 根据所属类型获取树形集合
	 * @param network
	 * @return
	 */
	public static List<SysMenu> getTreeMenus() {
		return treeList;
	}
	
	/**
	 * 获取所有菜单  tree
	 * @return
	 */
	public List<SysMenu> findAllTreeMenu(){
		return getTreeMenus();
	}

	/**
	 * 根据所属类型获取集合
	 * @param network
	 * @return
	 */
	public static List<SysMenu> getMenus(Integer network) {
		List<SysMenu> list = new ArrayList<SysMenu>();
		for (SysMenu m : treeList) {
			list.add(new SysMenu(m));
			for (SysMenu cld : m.getCldMenus()) {
				list.add(new SysMenu(cld));
			}
		}
		return list;
	}

	/**
	 * 添加父级菜单
	 * @param menuId
	 * @param sysMenuType
	 * @param name
	 * @param icon
	 * @param url
	 * @param isshow
	 * @return
	 */
	public static SysMenu addParent(Integer menuId, SysMenuType sysMenuType, String name, String icon, String url, Integer isshow) {
		SysMenu menu = new SysMenu();
		menu.setMenuId(menuId);
		menu.setPid(0);
		menu.setType(sysMenuType.getCode());
		menu.setName(name);
		menu.setIcon(icon);
		menu.setUrl(url);
		menu.setIsshow(isshow);
		menuMap.put(menuId, menu);
		treeList.add(menu);
		return menu;
	}
	/**
	 * 添加子级菜单
	 * @param menuId
	 * @param pid
	 * @param sysMenuType
	 * @param name
	 * @param icon
	 * @param url
	 * @param isshow
	 * @return
	 */
	private static SysMenu addCld(Integer menuId, Integer pid, SysMenuType sysMenuType, String name, String icon, String url, Integer isshow) {
		SysMenu menu = new SysMenu();
		menu.setMenuId(menuId);
		menu.setPid(pid);
		menu.setType(sysMenuType.getCode());
		menu.setName(name);
		menu.setIcon(icon);
		menu.setUrl(url);
		menu.setIsshow(isshow);
		menuMap.put(menuId, menu);
		return menu;
	}

	/**
	 * 根据菜单ID字符串集合获取菜单集合[,分隔]
	 * @param ids
	 * @return
	 */
	@Override
	public List<SysMenu> findByMenuIds(String menuIds) {
		List<SysMenu> list = new ArrayList<SysMenu>();
		List<String> idArr = FrameStringUtil.toArray(menuIds, ",");
		for (String id : idArr) {
			SysMenu menu = menuMap.get(Integer.valueOf(id));
			if(menu != null) {
				list.add(new SysMenu(menu));
			}
		}
		return list;
	}

	@Override
	public List<SysMenu> findTreeByMenuIds(String menuIds) {
		List<SysMenu> treeList = new ArrayList<SysMenu>();
		if(FrameStringUtil.isEmpty(menuIds)) {
			return treeList;
		}
		List<SysMenu> list = getTreeMenus();
		for (SysMenu sysMenu : list) {
			if(menuIds.contains(sysMenu.getMenuId().toString())) {
				SysMenu menu = new SysMenu(sysMenu);
				if(sysMenu.getCldMenus() != null) {
					for (SysMenu cld : sysMenu.getCldMenus()) {
						if(menuIds.contains(cld.getMenuId().toString())) {
							SysMenu cldMenu = new SysMenu(cld);
							menu.addCldMenu(cldMenu);
							if(sysMenu.getCldMenus() != null) {
								for (SysMenu cld2 : cld.getCldMenus()) {
									if(menuIds.contains(cld2.getMenuId().toString())) {
										cldMenu.addCldMenu(new SysMenu(cld2));
									}
								}
							}
						}
					}
				}
				treeList.add(menu);
			}
		}
		return treeList;
	}
}