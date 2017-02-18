package com.system.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.system.auth.model.AuthClient;

/**
 * 惊讶SSO
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.itoujing.com
 * @date    2015年11月18日 下午3:09:29 
 * @version 1.0.0
 */
public class AuthCons {

	private static List<AuthClient> clientList = new ArrayList<AuthClient>();
	protected static Map<String, AuthClient> clientMap = new HashMap<String, AuthClient>();
	
	/**
	 * 根据ID获取对象
	 * @param sercret
	 * @return
	 */
	public static AuthClient getId(String id) {
		return clientMap.get(id);
	}
	
	/**
	 * 根据域名获取
	 * @param domain
	 * @return
	 */
	public static AuthClient getDomain(String domain) {
		for (AuthClient client : clientList) {
			if(client.getDomain().equals(domain)) {
				return client;
			}
		}
		return null;
	}
}