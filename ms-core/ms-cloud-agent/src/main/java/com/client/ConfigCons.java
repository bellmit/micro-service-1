package com.client;

public class ConfigCons {
	
	/**
	 * 执行发布shell卡住后，过指定时间kill对应的进程。单位:分钟
	 */
	public static Integer clientShellFailTime;

	/**
	 * 客户端编号
	 */
	public static String clientId;
	
	/**
	 * 版本保存的目录
	 */
	public static String versionDir;
	
	/**
	 * 客户端的token
	 */
	public static String clientToken;
	/**
	 * 服务端地址
	 */
	public static String clientServerHost;
	
	public static String getClientId() {
		return clientId;
	}
	public static void setClientId(String clientId) {
		ConfigCons.clientId = clientId;
	}
	public static String getVersionDir() {
		return versionDir;
	}
	public static void setVersionDir(String versionDir) {
		ConfigCons.versionDir = versionDir;
	}
	public static String getClientToken() {
		return clientToken;
	}
	public static void setClientToken(String clientToken) {
		ConfigCons.clientToken = clientToken;
	}
	public static String getClientServerHost() {
		return clientServerHost;
	}
	public static void setClientServerHost(String clientServerHost) {
		ConfigCons.clientServerHost = clientServerHost;
	}
	public static Integer getClientShellFailTime() {
		return clientShellFailTime;
	}
	public static void setClientShellFailTime(Integer clientShellFailTime) {
		ConfigCons.clientShellFailTime = clientShellFailTime;
	}
}