package com.system.comm.utils;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * 数字工具类
 * @author jing.yue
 * @version 1.0
 * @since 2012-10-26
 */
public class FrameNumberUtil {

	/** 保存小数的位数 */
	private static Map<Integer, String> decimalNumMap = new HashMap<Integer, String>();

	/**
	 * 校验是否为整数的数字
	 * @param string
	 * @return
	 */
	public static boolean isInteger(String string){
		String regex = "^(-?[0-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
		return string.matches(regex);
	}

	/**
	 * 将数字转为保留指定位小数的形式
	 * @param value
	 * @param decimalNum	小数位数，必须大于0
	 * @return
	 */
	public static String formatNumber(Float value, Integer decimalNum) {
		String string = formatNumberObject(value, decimalNum);
		if(FrameStringUtil.isEmpty(string)) {
			return null;
		}
		return string;
	}
	
	/**
	 * 将数字转为保留指定位小数的形式
	 * @param value
	 * @param decimalNum	小数位数，必须大于0
	 * @return
	 */
	public static String formatNumber(Double value, Integer decimalNum) {
		if(value == null) {
			return null;
		}
		String string = formatNumberObject(value, decimalNum);
		if(FrameStringUtil.isEmpty(string)) {
			return null;
		}
		return string;
	}
	
	/**
	 * 将数字转为保留指定位小数的形式
	 * @param value
	 * @param rideNum		乘指定数值
	 * @param decimalNum	小数位数，必须大于0
	 * @return
	 */
	public static String formatNumberRide(Double value, Integer rideNum, Integer decimalNum) {
		if(value == null) {
			return null;
		}
		if(rideNum == null) {
			rideNum = 1;
		}
		value = value * rideNum;
		String string = formatNumberObject(value, decimalNum);
		if(FrameStringUtil.isEmpty(string)) {
			return null;
		}
		return string;
	}
	/**
	 * 将数字转为保留指定位小数的形式
	 * @param value
	 * @param rideNum		乘指定数值
	 * @param decimalNum	小数位数，必须大于0
	 * @return
	 */
	public static String formatNumberExcept(Double value, Integer exceptNum, Integer decimalNum) {
		if(value == null) {
			return null;
		}
		if(exceptNum == null) {
			exceptNum = 1;
		}
		value = value / exceptNum;
		String string = formatNumberObject(value, decimalNum);
		if(FrameStringUtil.isEmpty(string)) {
			return null;
		}
		return string;
	}
	
	/**
	 * 将数字转为保留指定位小数的形式
	 * @param value
	 * @param decimalNum	小数位数，必须大于0
	 * @return
	 */
	public static String formatNumber(String value, Integer decimalNum) {
		if(FrameStringUtil.isEmpty(value)) {
			return null;
		}
		return formatNumberObject(Double.valueOf(value), decimalNum);
	}
	
	/**
	 * 将数字转为保留指定位小数的形式
	 * @param value
	 * @param decimalNum	小数位数，必须大于0
	 * @return
	 */
	public static String formatNumberObject(Object value, Integer decimalNum) {
		if(value == null || FrameStringUtil.isEmpty(value.toString())) {
			return null;
		}
		if(decimalNum == null) {
			decimalNum = 2;
		}
		String decimalNumString = decimalNumMap.get(decimalNum);
		if(decimalNum <= 0) {
			decimalNumString = "0";
			decimalNumMap.put(decimalNum, decimalNumString);
		} else {
			if(FrameStringUtil.isEmpty(decimalNumString)) {
				decimalNumString = "0.";
				for (int i = 0; i < decimalNum.intValue(); i++) {
					decimalNumString += "0";
				}
			}
			decimalNumMap.put(decimalNum, decimalNumString);
		}
		return new java.text.DecimalFormat(decimalNumString).format(value);
	}

	/**
	 * 将指定内容四舍五入
	 * 		如2345.567会格式化为2,345.57
	 * @param value
	 * @return
	 */
	public static String formatNumberSection(Object value) {
		return formatNumberSection(value, null, 2);
	}
	/**
	 * 将指定内容四舍五入
	 * 		如2345.567会格式化为2,345.57
	 * @param value
	 * @param minimum
	 * @param maximum
	 * @return
	 */
	public static String formatNumberSection(Object value, Integer minimum, Integer maximum) {
		NumberFormat nf = NumberFormat.getInstance();
		//设置四舍五入
		nf.setRoundingMode(RoundingMode.HALF_UP);
		//设置最小保留几位小数
		if(minimum != null) {
			nf.setMinimumFractionDigits(minimum);
		}
		//设置最大保留几位小数
		if(maximum != null) {
			nf.setMaximumFractionDigits(maximum);
		}
		return nf.format(value);
	}

	/**
	 * 对数运算
	 * @param value 
	 * @param base 底数
	 * @return double
	 */
	public static double log(double value, double base) {
		return Math.log(value) / Math.log(base);
	}

	/*public static void main(String[] args) {
		System.out.println(isInteger("0012323"));
		System.out.println(formatNumberRide(0.12345, 1, 2));
	}*/
}