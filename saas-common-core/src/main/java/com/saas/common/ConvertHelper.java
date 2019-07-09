package com.saas.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 类型转换工具类
 * @author tanjun
 *
 */
public class ConvertHelper {

	public static String toString(Object obj) {
		if (obj != null)
			return obj.toString();
		return "";
	}

	public static String toString(String s) {
		return toString(s, "");
	}

	public static String toString(String s, String defaultString) {
		if (s == null)
			return defaultString;

		return s.toString();
	}

	public static String toString(Object s, String defaultString) {
		if (s == null)
			return defaultString;

		return s.toString();
	}

	public static int toInt(String s, int defaultValue) {
		try {
			return Integer.parseInt(s);
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	public static int toInt(String s) {
		return toInt(s, 0);
	}

	public static int toInt(Object obj) {
		return toInt(obj, 0);
	}

	public static int toInt(Object obj, int defaultValue) {
		String s = toString(obj);
		return toInt(s, defaultValue);
	}

	public static float toFloat(String s, float defaultValue) {
		try {
			return Float.parseFloat(s);
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	public static float toFloat(String s) {
		return toFloat(s, 0);
	}

	public static float toFloat(Object obj) {
		return toFloat(obj, 0);
	}

	public static float toFloat(Object obj, float defaultValue) {
		String s = toString(obj);
		return toFloat(s, defaultValue);
	}

	public static BigDecimal toDecimal(String s) {
		return toDecimal(s, new BigDecimal("0.00"));
	}

	public static BigDecimal toDecimal(Object obj) {
		return toDecimal(obj, new BigDecimal("0.00"));
	}

	public static BigDecimal toDecimal(Object obj, BigDecimal defaultValue) {
		String s = toString(obj);
		if(s.isEmpty())
			s="0";
		BigDecimal b = new BigDecimal(s);
		return b;
	}

	public static Date toDate(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date da = null;
		try {
			da = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return da;
	}

	public static Date toDatetime(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		if (str.trim().length()<=10) {
			str=str+" 00:00:00";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date da = null;
		try {
			da = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return da;
	}
}
