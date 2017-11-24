package com.system.auth;

import com.client.ConfigCons;
import com.system.comm.utils.FrameMd5Util;

/**
 * 前面相关的工具类
 * @author  duanbin
 * @date    2016年4月2日 下午4:20:28 
 * @version 1.0.0
 */
public class AuthUtil {
	
	/**
	 * 获取SSO的签名
	 * @param clientId
	 * @param time
	 * @param sercret
	 * @return
	 */
	public static String auth(String clientId, String time, String sercret) {
		return FrameMd5Util.getInstance().encodePassword(clientId + time + sercret);
	}

	/**
	 * 获取SSO的签名并进行验证
	 * @param clientId
	 * @param time
	 * @param sercret
	 * @param sign
	 * @return
	 */
	public static boolean authVerify(String clientId, String time, String sign) {
		/*String token = AuthCons.getId(clientId);
		if(token == null) {
			return false;
		}*/
		String newSign = auth(clientId, time, ConfigCons.clientToken);
		return newSign.equalsIgnoreCase(sign);
	}
}