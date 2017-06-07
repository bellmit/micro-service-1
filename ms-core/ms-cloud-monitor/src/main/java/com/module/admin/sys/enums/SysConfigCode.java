package com.module.admin.sys.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.system.comm.model.KvEntity;

/**
 * 系统配置
 * @author yuejing
 * @date 2015年4月5日 下午10:05:22
 * @version V1.0.0
 */
public enum SysConfigCode {
	MAIL_SMTP			("mail.smtp", 			"发送邮箱的smtp"),
	MAIL_FROM			("mail.from", 			"发送邮件的邮箱"),
	MAIL_USERNAME		("mail.username", 		"发送邮件的用户名"),
	MAIL_PASSWORD		("mail.password", 		"发送邮件的密码"),
	PRJ_FILE_PATH		("prj.file.path", 		"项目上传的目录"),

	PRJ_MONITOR_FAIL_EMAIL	("prj.monitor.fail.email", "项目检测失败接收的邮箱"),

	/*CONFIG_CLIENT_ID		("config.client.id", 		"配置文件的客户ID"),
	CONFIG_CLIENT_TOKEN		("config.client.token", 	"配置文件的token"),*/
	;

	public static final String KEY = "sys_config_code";
	
	private String code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<String, String> map = new HashMap<String, String>();

	private SysConfigCode(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		Set<SysConfigCode> set = EnumSet.allOf(SysConfigCode.class);
		for(SysConfigCode e : set){
			map.put(e.getCode(), e.getName());
			list.add(new KvEntity(e.getCode().toString(), e.getName()));
		}
	}

	/**
	 * 根据Code获取对应的汉字
	 * @param code
	 * @return
	 */
	public static String getText(String code) {
		return map.get(code);
	}
	
	/**
	 * 获取集合
	 * @return
	 */
	public static List<KvEntity> getList() {
		return list;
	}

	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
