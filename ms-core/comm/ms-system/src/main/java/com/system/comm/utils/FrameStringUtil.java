package com.system.comm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类<br>
 * 示例：
 * 		String str = (char)1+"dasda"+(char)2+"sdasd"+(char)1+"asdasdas"+(char)1+(char)1+(char)1;
		System.out.println(str);
		System.out.println(trim(str, (char)3));
		System.out.println("isEmpty: " + isEmpty(null));
		System.out.println("isNotEmpty: " + isNotEmpty("s"));
		System.out.println("isEqualArr: " + isEqualArr(2, 20521, 20513, 10024));
		System.out.println("isNotEqualArr: " + isNotEqualArr(null, 1, 2, 3));
		System.out.println("getStrsRandomStr: " + getStrsRandomStr("哈哈;嗯嗯;", ";"));
		
		String urlStr = "这是一个url链接http://www-test.company.com/view/1_2.html?a=%B8&f=%E4+%D3#td http://www.suyunyou.com/aid15.html需要转化成可点击";
		System.out.println(parseUrl(urlStr));
		System.out.println("包含http链接：" + existUrl("爱，http://sdfdf"));
		
 * @author yuejing
 * @date 2016年4月30日 下午7:15:59
 * @version V1.0.0
 */
public class FrameStringUtil {
	
	//链接正则表达式
	private static final String REGEX_URL = "(http:|https:)//[^[A-Za-z0-9\\._\\?%&+\\-=/#]]*";

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null)  = true</li>
	 * <li>SysUtils.isEmpty("")    = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		if (value == null || "".equals(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualArr(Object value, Object ...equalArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if(value.toString().equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值[不区分大小写]
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualsIcArr(Object value, Object ...equalArr) {
		if (value == null || "".equalsIgnoreCase(value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if(value.toString().equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否等于一个数组中的一个值
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isEqualArrSin(Object value, Object[] equalArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return false;
		}
		for (Object object : equalArr) {
			if(value.toString().equals(object.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个字符串是否不等于一个数组中的一个值
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isNotEqualArr(Object value, Object ...notEqualArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return true;
		}
		for (Object object : notEqualArr) {
			if(value.toString().equals(object.toString())) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断一个字符串是否不等于一个数组中的一个值[不区分大小写]
	 * @param value
	 * @param equalArr
	 * @return
	 */
	public static boolean isNotEqualIcArr(Object value, Object ...notEqualArr) {
		if (value == null || "".equals(value.toString().trim())) {
			return true;
		}
		for (Object object : notEqualArr) {
			if(value.toString().equalsIgnoreCase(object.toString())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串是否不为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null)  = false</li>
	 * <li>SysUtils.isEmpty("")    = false</li>
	 * <li>SysUtils.isEmpty("   ") = false</li>
	 * <li>SysUtils.isEmpty("abc") = true</li>
	 * </ul>
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	public static boolean isNotEmpty(String value) {
		if (value != null && !"".equals(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 检查对象是否为数字型字符串。
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		String str = obj.toString();
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 */
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}

		return join(array, separator, 0, array.length);
	}

	/**
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 */
	public static String join(Object[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int bufSize = endIndex - startIndex;
		if (bufSize <= 0) {
			return "";
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1);
		StringBuilder buf = new StringBuilder(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * 字符串以指定字符分隔,然后随机获取一个字符串
	 * @param string
	 * @param split
	 * @return
	 */
	public static String getStrsRandomStr(String string, String split) {
		if(isEmpty(string)) {
			return "";
		}
		int len = string.length();
		String curString = null;
		if(split.equals(string.substring(len -1, len))) {
			curString = string.substring(0, len - 1);
		} else {
			curString = string;
		}
		String[] arr = curString.split(split);
		if(arr.length == 0) {
			return "";
		}
		int index = (int) (Math.random() * arr.length);
		if(index >= arr.length) {
			index = arr.length - 1;
		}
		return arr[index];
	}
	
	/**
	 * 将已指定分隔符的字符串转为List
	 * @param string	字符串
	 * @param regex		分隔符
	 * @return
	 */
	public static List<String> toArray(String string, String regex) {
		List<String> list = new ArrayList<String>();
		if(isEmpty(string)) {
			return list;
		}
		String[] arr = string.split(regex);
		for (String str : arr) {
			if(isEmpty(str)) {
				continue;
			}
			list.add(str);
		}
		return list;
	}
	
	/**
	 * 正则表达式校验字符串是否包含指定规则的字符串
	 * @param str
	 * @param regex
	 * @return
	 */
	public static boolean isRegexContains(String str, String regex) {
		Pattern pat = Pattern.compile(regex);
        Matcher matcher = pat.matcher(str);
        return matcher.find();
    }
	
	/**
	 * 将字符串中的带有url链接转为可以点击的链接
	 * @param str
	 * @return
	 */
	public static String parseUrl(String str) {
        Pattern pattern = Pattern.compile(REGEX_URL);
        Matcher matcher = pattern.matcher(str);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
        	StringBuilder replace = new StringBuilder();
            replace.append("<a href=\"").append(matcher.group());
            replace.append("\" target=\"_blank\">").append(matcher.group()).append("</a>");
            matcher.appendReplacement(result, replace.toString());
        }
        matcher.appendTail(result);
		return result.toString();
	}
	
	/**
	 * 判断字符串中是否包含http链接
	 * @param str
	 * @return
	 */
	public static boolean existUrl(String str) {
		return isRegexContains(str, REGEX_URL);
	}
	
	/**
	 * 获取字符串从0开始到指定位置结束的字符串
	 * @param str
	 * @param len
	 * @return
	 */
	public static String getLenStr(String str, Integer len) {
		if(str == null) {
			return null;
		}
		if(str.length() > len) {
			return str.substring(0, len) + "...";
		}
		return str;
	}

	/**
	 * 将%E4%BD%A0转换为字符等
	 * @param s
	 * @return
	 */
	public static String unescape(String string) {
		StringBuffer sbuf = new StringBuffer();
		int l = string.length();
		int ch = -1;
		int b, sumb = 0;
		for (int i = 0, more = -1; i < l; i++) {
			/* Get next byte b from URL segment s */
			switch (ch = string.charAt(i)) {
			case '%':
				ch = string.charAt(++i);
				int hb = (Character.isDigit((char) ch) ? ch - '0'
						: 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				ch = string.charAt(++i);
				int lb = (Character.isDigit((char) ch) ? ch - '0'
						: 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				b = (hb << 4) | lb;
				break;
			case '+':
				b = ' ';
				break;
			default:
				b = ch;
			}
			/* Decode byte b as UTF-8, sumb collects incomplete chars */
			if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)   
				sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb   
				if (--more == 0)
					sbuf.append((char) sumb); // Add char to sbuf   
			} else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)   
				sbuf.append((char) b); // Store in sbuf   
			} else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)   
				sumb = b & 0x1f;
				more = 1; // Expect 1 more byte   
			} else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)   
				sumb = b & 0x0f;
				more = 2; // Expect 2 more bytes   
			} else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)   
				sumb = b & 0x07;
				more = 3; // Expect 3 more bytes   
			} else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)   
				sumb = b & 0x03;
				more = 4; // Expect 4 more bytes   
			} else /*if ((b & 0xfe) == 0xfc)*/{ // 1111110x (yields 1 bit)   
				sumb = b & 0x01;
				more = 5; // Expect 5 more bytes   
			}
			/* We don't test if the UTF-8 encoding is well-formed */
		}
		return sbuf.toString();
	}

	/**
	 * 将第一个字母转换为大写
	 * @param s
	 * @return
	 */
	public static String setFirstCharUpcase(String s){
		if(s==null||s.length()<1) return s;
		char[] c= s.toCharArray();
		if(c.length>0){
			if(c[0]>='a'&&c[0]<='z')c[0]=(char)((short)(c[0])-32);
		}
		return String.valueOf(c);
	}

	/**
	 * 设置首字母小写
	 * @param s
	 * @return
	 */
	public static String setFirstCharLowcase(String s){
		if(s==null||s.length()<1) return s;
		char[] c= s.toCharArray();
		if(c.length>0){
			if(c[0]>='A'&&c[0]<='Z')c[0]=(char)((short)(c[0])+32);
		}
		return String.valueOf(c);
	}
	
	/**
	 * 将大写字母转为_小写字母<br>
	 * 如：abc_def -> abcDef
	 * @param string
	 * @return
	 */
	public static String setUnderlineConvertUpcase(String string) {
		if(FrameStringUtil.isEmpty(string)) {
			return "";
		}
		string = string.toLowerCase();
		String fieldName = "";
		
		//转换为帕斯卡（pascal）命名，首字母大写，每一个逻辑断点都有一个大写字母来标记
		
		String[] words = string.split("[_\\.-]");
		for(int i=0; i < words.length; i++){
			if(i == 0) {
				fieldName = words[i];
			}
			else{
				fieldName += FrameStringUtil.setFirstCharUpcase(words[i]);
			}
		}
		return fieldName;
	}/**
	 * 将大写字母转为_小写字母<br>
	 * 如：abcDef -> abc_def
	 * @param string
	 * @return
	 */
	public static String setUpcaseConvertUnderline(String string) {
		if(FrameStringUtil.isEmpty(string)) {
			return "";
		}
		
		char[] cArr = string.toCharArray();
		StringBuffer sb = new StringBuffer();
		if(cArr.length > 0) {
			for (int i = 0; i < cArr.length; i ++) {
				char c = cArr[i];
				if(c >= 'A' && c <= 'Z') {
					sb.append("_");sb.append(String.valueOf(c).toString().toLowerCase());
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
		
		/*string = string.toLowerCase();
		String fieldName = "";
		
		//转换为帕斯卡（pascal）命名，首字母大写，每一个逻辑断点都有一个大写字母来标记
		
		String[] words = string.split("[_\\.-]");
		for(int i=0; i < words.length; i++){
			if(i == 0) {
				fieldName = words[i];
			}
			else{
				fieldName += FrameStringUtil.setFirstCharUpcase(words[i]);
			}
		}
		return fieldName;*/
	}
}