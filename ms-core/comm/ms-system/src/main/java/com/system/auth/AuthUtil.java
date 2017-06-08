package com.system.auth;

import com.system.auth.model.AuthClient;
import com.system.comm.utils.FrameMd5Util;

/**
 * 前面相关的工具类
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.itoujing.com
 * @date    2016年3月2日 下午4:20:28 
 * @version 1.0.0
 */
public class AuthUtil {
	
	/**
	 * 添加需要授权的客户端
	 * @param client
	 */
	public static void addAuthClient(AuthClient client) {
		if(AuthCons.clientMap.get(client.getId()) == null) {
			AuthCons.clientMap.put(client.getId(), client);
		}
	}
	
	/**
	 * 跟新需要授权的客户端
	 * @param client
	 */
	public static void updateAuthClient(AuthClient client) {
		AuthCons.clientMap.put(client.getId(), client);
	}
	
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
		AuthClient client = AuthCons.getId(clientId);
		if(client == null) {
			return false;
		}
		String newSign = auth(clientId, time, client.getSercret());
		return newSign.equalsIgnoreCase(sign);
	}
	
	/**
	 * 获取第一项的内容
	 * @return
	 */
	public static AuthClient getFirst() {
		return AuthCons.getFirst();
	}
}