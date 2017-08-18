package com.task.schedule.comm.utils;

import java.util.List;

import com.system.comm.utils.FrameMailUtil;
import com.system.comm.utils.FrameStringUtil;

/**
 * 发送邮件
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年4月3日 下午4:01:02 
 * @version 1.0.0
 */
public class SendMailUtil {

	/**
	 * 发送邮件
	 * @param toMails	接收人，多个用,分隔
	 * @param title		邮件标题
	 * @param content	邮件正文
	 */
	public static void sendMail(String smtp, String from, String username, String password, String toMails, String title, String content) {
		List<String> tos = FrameStringUtil.toArray(toMails, ",");
		//初始化Email
		FrameMailUtil mailUtil = new FrameMailUtil(smtp, from, username, password, true);
		for (String to : tos) {
			if(FrameStringUtil.isEmpty(to)) {
				continue;
			}
			mailUtil.send(to, title, content);
		}
	}
}
