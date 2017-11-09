package com.system.auth;

import java.util.List;
import java.util.Map;

import com.system.auth.model.AuthClient;
import com.system.comm.utils.FrameMd5Util;
import com.system.comm.utils.FrameStringUtil;

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
	 * 获取对象
	 * @param clientId
	 * @return
	 */
	public static AuthClient get(String clientId) {
		return AuthCons.getId(clientId);
	}

	/**
	 * 获取第一项的内容
	 * @return
	 */
	public static AuthClient getFirst() {
		return AuthCons.getFirst();
	}

	public static boolean authVerifyReqUrl(String clientId, String reqUrl) {
		AuthClient client = get(clientId);
		if(client == null) {
			return false;
		}
		Map<String, List<String>> urlMap = client.getUrlMap();
		if(urlMap == null || urlMap.size() == 0) {
			return true;
		}
		//获取项目的过滤地址
		int index = reqUrl.indexOf("/", 1);
		if(index == -1) {
			return false;
		}
		String prjCode = reqUrl.substring(1, index);
		String curUrl = reqUrl.substring(index);
		List<String> urls = urlMap.get(prjCode);
		for (String url : urls) {
			if(FrameStringUtil.isEqualArr(url, "/*", "/**", "/*/*", "/*/*/*", "/*/*/*/*")) {
				//处理通配符
				//处理 /test/index => /*
				//处理 /test/index => /**
				//处理 /test/index => /*/*
				//处理 /test/index => /*/*/*
				//处理 /test/index => /*/*/*/*
				return true;
			}
			if(curUrl.equals(url) || curUrl.startsWith(url)) {
				//处理 /test/index == /test/index
				return true;
			}
			if(isMatch(curUrl, url)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 匹配字符串规则是否一致
	 * @param curStr
	 * @param matchStr
	 * @return
	 */
	private static boolean isMatch(String curStr, String matchStr) {
		List<String> curList = FrameStringUtil.toArray(curStr, "/");
		List<String> matchList = FrameStringUtil.toArray(matchStr, "/");
		if(curList.size() < matchList.size()) {
			//长度不一致
			return false;
		}
		String match = null;
		for (int i = 0; i < curList.size(); i ++) {
			String cur = curList.get(i);
			if(i < matchList.size()) {
				match = matchList.get(i);
				if(cur.equals(match) || match.equals("*") || match.equals("**")) {
					//处理 /test/index => /test/**
					//处理 /test/index => /test/*
					continue;
				} else {
					return false;
				}
			} else if(i >= matchList.size() && "**".equals(match)) {
				//处理 /test/index/1 => /test/**
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(isMatch("/test/index", 		"/**"));
		System.out.println(isMatch("/test/index", 		"/test/index"));
		System.out.println(isMatch("/test/index", 		"/test/**"));
		System.out.println(isMatch("/test/index/1", 	"/test/**"));
		System.out.println(isMatch("/test/index/1/1", 	"/test/*/1/**"));
	}
}