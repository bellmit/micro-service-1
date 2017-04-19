package com.system.comm.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拼音工具类<br>
 * public static void main(String[] args) {
		System.out.println(getSimplePinyin("大中华"));
		System.out.println(getFullPinyin("大中华"));
	}
 * @author 岳静
 * @date 2016年5月3日 下午7:21:27 
 * @version V1.0
 */
public class FramePinyinUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FramePinyinUtil.class);
	
	/**
	 * 获取汉字的首字母简拼<br>
	 * 如：大中华，得到结果是dzh
	 * @param string
	 * @return
	 */
	public static String getSimplePinyin(String string) {
		char[] stringChars = string.toCharArray();
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat pinyinFormat = new HanyuPinyinOutputFormat();
		pinyinFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pinyinFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder pinyinSb = new StringBuilder();
		try {
			for (int i =0; i < stringChars.length; i++) {
				// 判断能否为汉字字符
				if (Character.toString(stringChars[i]).matches("[\\u4E00-\\u9FA5]+")) {
					//将汉字的几种全拼都存到pinyinArray数组中
					String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(stringChars[i], pinyinFormat);
					//取出该汉字全拼的第一种读音并连接到字符串pinyinSb后(.charAt(0)是取第一个字符)
					pinyinSb.append(pinyinArray[0].charAt(0));
					//t4 += t2[0] + " ";
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					pinyinSb.append(Character.toString(stringChars[i]));
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			LOGGER.error("获取拼音异常: " + e.getMessage(), e);
		}
		return pinyinSb.toString();
	}
	
	/**
	 * 获取汉字的拼音<br>
	 * 如：大中华，得到结果是dazhonghua
	 * @param string
	 * @return
	 */
	public static String getFullPinyin(String string) {
		char[] stringChars = string.toCharArray();
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat pinyinFormat = new HanyuPinyinOutputFormat();
		pinyinFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pinyinFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder pinyinSb = new StringBuilder();
		try {
			for (int i =0; i < stringChars.length; i++) {
				// 判断能否为汉字字符
				if (Character.toString(stringChars[i]).matches("[\\u4E00-\\u9FA5]+")) {
					//将汉字的几种全拼都存到pinyinArray数组中
					String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(stringChars[i], pinyinFormat);
					//取出该汉字全拼的第一种读音并连接到字符串pinyinSb后(.charAt(0)是取第一个字符)
					pinyinSb.append(pinyinArray[0]);
					//t4 += t2[0] + " ";
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					pinyinSb.append(Character.toString(stringChars[i]));
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			LOGGER.error("获取拼音异常: " + e.getMessage(), e);
		}
		return pinyinSb.toString();
	}
}