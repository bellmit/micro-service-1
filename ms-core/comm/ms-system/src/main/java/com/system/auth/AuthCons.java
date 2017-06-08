package com.system.auth;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	 * 获取第一项的内容
	 * @return
	 */
	public static AuthClient getFirst() {
		if(clientMap.size() > 0) {
			Iterator<Entry<String, AuthClient>> entryKeyIterator = clientMap.entrySet().iterator();
			while (entryKeyIterator.hasNext()) {
				Entry<String, AuthClient> e = entryKeyIterator.next();
				return e.getValue();
			}
		}
		return null;
	}
}