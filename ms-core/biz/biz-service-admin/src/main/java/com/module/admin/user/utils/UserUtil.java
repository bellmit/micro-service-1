package com.module.admin.user.utils;

import com.system.comm.utils.FrameMd5Util;

/**
 * 用户的常用工具类
 * @author duanbin
 * @date 2016年5月6日 下午2:41:47 
 * @version V1.0
 */
public class UserUtil {
	
	/**
	 * 对用户的密码进行加密<br/>
	 * @param id
	 * @param password
	 * @return
	 */
	public static String encryptionPassword(String password) {
		password = FrameMd5Util.getInstance().encodePassword(password, "frsa2m0e");
		password = FrameMd5Util.getInstance().encodePassword(password);
		return password;
	}
	
	public static void main(String[] args) {
		System.out.println(encryptionPassword("admin"));
	}
}
