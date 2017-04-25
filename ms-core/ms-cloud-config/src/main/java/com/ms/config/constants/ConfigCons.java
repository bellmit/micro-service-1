package com.ms.config.constants;

public class ConfigCons {
	
	/**
	 * config的本地加载路径
	 */
	public static String configSearchLocations;

	/**
	 * 客户端编号
	 */
	public static String clientId;
	
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
	public static String getConfigSearchLocations() {
		return configSearchLocations;
	}
	public static void setConfigSearchLocations(String configSearchLocations) {
		ConfigCons.configSearchLocations = configSearchLocations;
	}
}