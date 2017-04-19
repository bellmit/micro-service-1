package com.system.comm.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

/**
 * base64的类
 * @author jing.yue
 * @version 2012/08/29 1.0.0
 */
@SuppressWarnings("restriction")
public class FrameBase64Util {

	private static final Logger logger = LoggerFactory.getLogger(FrameBase64Util.class);
	private static final String UTF_8 = "UTF-8";
	private static FrameBase64Util base64Class;

	private FrameBase64Util() {
	}

	public static synchronized FrameBase64Util getInstance() {
		if (base64Class == null)
			base64Class = new FrameBase64Util();
		return base64Class;
	}

	/**
	 * 编码
	 * @param str
	 * @return String
	 */
	public String encode(String str){
		return new sun.misc.BASE64Encoder().encode(str.getBytes());
	}

	/**
	 * 解码
	 * @param str
	 * @return string
	 */
	public String decode(String str){
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(bt);
	}

	/**
	 * 把经过BASE64编码的字符串转换为Map对象
	 * @param str
	 * @return
	 */
	public Map<String, String> convertBase64StringtoMap(String str) {
		if (str == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		String keyValues = null;
		try {
			keyValues = new String(decoder.decodeBuffer(str), UTF_8);

		} catch (IOException e) {
			logger.error(str + "不是一个合法的BASE64编码字符串");
			return null;
		}
		if (keyValues == null || keyValues.length() == 0) {
			return null;
		}

		String[] keyValueArray = keyValues.split("&");
		Map<String, String> map = new HashMap<String, String>();
		for (String keyValue : keyValueArray) {
			String[] s = keyValue.split("=");
			if (s == null || s.length != 2) {
				return null;
			}
			map.put(s[0], s[1]);
		}
		return map;
	}

	public static void main(String[] args) {
		/*Map<String, String> res = convertBase64StringtoMap("ZXhwaXJlc19pbj04NjQwMCZpZnJhbWU9MSZyMV9leHBpcmVzX2luPTg2NDAwJnIyX2V4cGlyZXNfaW49ODY0MDAmcmVfZXhwaXJlc19pbj04NjQwMCZyZWZyZXNoX3Rva2VuPTYxMDBiMTFlZTI0MTlkNTM4ZjMxNWQ2M2Y4MmYxYzdmYzc0NjQ1ODhlMjA1YWM5NDQ0MDg0OTYzJnRzPTEzNzExMjY5OTA0NTgmdmlzaXRvcl9pZD00NDQwODQ5NjMmdmlzaXRvcl9uaWNrPXl1ZWppbmdqaWFob25nJncxX2V4cGlyZXNfaW49ODY0MDAmdzJfZXhwaXJlc19pbj04NjQwMA==");
		System.out.println(res);
		System.out.println("过期时间(s): " + res.get("expires_in"));*/
	}
}