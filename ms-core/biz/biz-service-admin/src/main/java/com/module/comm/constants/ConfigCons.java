package com.module.comm.constants;

public class ConfigCons {

	/**
	 * 项目名称
	 */
	public static String projectName;
	public static String adminClientId;
	public static String adminSercret;
	public static String apiBizUrl;
	
	
	public static String getProjectName() {
		return projectName;
	}
	public static void setProjectName(String projectName) {
		ConfigCons.projectName = projectName;
	}
	public static String getAdminClientId() {
		return adminClientId;
	}
	public static void setAdminClientId(String adminClientId) {
		ConfigCons.adminClientId = adminClientId;
	}
	public static String getAdminSercret() {
		return adminSercret;
	}
	public static void setAdminSercret(String adminSercret) {
		ConfigCons.adminSercret = adminSercret;
	}
	public static String getApiBizUrl() {
		return apiBizUrl;
	}
	public static void setApiBizUrl(String apiBizUrl) {
		ConfigCons.apiBizUrl = apiBizUrl;
	}
}