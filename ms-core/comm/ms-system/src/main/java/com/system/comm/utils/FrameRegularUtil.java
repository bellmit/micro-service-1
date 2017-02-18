package com.system.comm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则检验
 * @author 岳静
 * @date 2016年5月30日 上午10:11:26 
 * @version V1.0
 */
public class FrameRegularUtil {

	private static final String EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	private static final String MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,3-9]))\\d{8}$";
	
	/**
	 * 验证邮箱
	 * @param string
	 * @return
	 */
	public static boolean isEmail(String string) {
		return compile(string, EMAIL);
	}

	/**
	 * 验证手机号码
	 * @param string
	 * @return
	 */
	public static boolean isMobile(String string) {
		return compile(string, MOBILE);
	}
	
	/**
	 * 根据字符串和规则校验
	 * @param string
	 * @param regex
	 * @return
	 */
	public static boolean compile(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}
	
	public static void main(String[] args) {
		System.out.println(isEmail("23163.com"));
		System.out.println(isMobile("18306665642"));
	}
}