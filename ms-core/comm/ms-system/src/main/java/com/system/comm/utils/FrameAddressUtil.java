package com.system.comm.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

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
	
	/**
	 * 获取客户端IP地址   
	 * @param request
	 * @return String
	 * @throws
	 */
	public static String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	           if(ip.equals("127.0.0.1")){
	               //根据网卡取本机配置的IP
	               InetAddress inet = null;
	               try {
	                   inet = InetAddress.getLocalHost();
	               } catch (UnknownHostException e) {
	                   e.printStackTrace();
	               }
	               ip= inet.getHostAddress();
	           }
	       }
	       // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
	       if(ip != null && ip.length() > 15){
	           if(ip.indexOf(",")>0){
	               ip = ip.substring(0,ip.indexOf(","));
	           }
	       }
	       return ip;
	}
	
	/*public static void main(String[] args) throws UnknownHostException {
		System.out.println(getLocalIP());
	}*/
}