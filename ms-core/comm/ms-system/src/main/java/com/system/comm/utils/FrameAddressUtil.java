package com.system.comm.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * RemoteAddress的工具类
 * @author jing.yue
 * @version 1.0
 * @since 2013/1/3
 */
public class FrameAddressUtil {

	/**
	 * 获取本机的ip地址
	 * @return
	 * @throws UnknownHostException  
	 */
	public static String getLocalIP() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();

		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		return ipAddrStr;
	}
	
	/*public static void main(String[] args) throws UnknownHostException {
		System.out.println(getLocalIP());
	}*/
}