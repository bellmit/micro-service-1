package com.module.admin.sys.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.system.comm.model.BaseEntity;

/**
 * sys_menu实体
 * @author duanbin
 * @date 2016-5-26 15:31:03
 * @version V1.0.0
 */
@Alias("sysMenu")
@SuppressWarnings("serial")
public class SysMenu extends BaseEntity implements Serializable {
	//编号
	private Integer menuId;
	//类型[0菜单、1功能]
	private Integer type;
	//菜单名称
	private String name;
	//图标[可以是链接、字符串等]
	private String icon;
	//菜单地址
	private String url;
	//父编号【0为顶级】
	private Integer pid;
	//是否显示【0否、1是】
	private Integer isshow;
	
	//====================== 扩展属性
	//子级菜单
	private List<SysMenu> cldMenus;
	//是否选中
	private Integer ischeck;
	
	public SysMenu() {
		super();
	}
	public SysMenu(SysMenu sysMenu) {
		this.menuId = sysMenu.getMenuId();
		this.type = sysMenu.getType();
		this.name = sysMenu.getName();
		this.url = sysMenu.getUrl();
		this.icon = sysMenu.getIcon();
		this.pid = sysMenu.getPid();
		this.isshow = sysMenu.getIsshow();
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getIsshow() {
		return isshow;
	}
	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}
	public List<SysMenu> getCldMenus() {
		return cldMenus;
	}
	public void setCldMenus(List<SysMenu> cldMenus) {
		this.cldMenus = cldMenus;
	}
	public Integer getIscheck() {
		return ischeck;
	}
	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}
	/**
	 * 添加二级菜单
	 */
	public void addCldMenu(SysMenu sysMenu) {
		if(cldMenus == null) {
			cldMenus = new ArrayList<SysMenu>();
		}
		cldMenus.add(sysMenu);
	}
	@Override
	public String toString() {
		return "SysMenu [menuId=" + menuId + ", type=" + type + ", name=" + name
				+ ", url=" + url + ", pid=" + pid
				+ ", isshow=" + isshow + "]";
	}
}