package com.zhming.support.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Title: 工具类 <br/>
 * Description:
 * 
 * @ClassName: CommonUtil
 * @author ydc
 * @date 2016-2-5 上午10:27:09
 * 
 */
public class CommonUtil {
	/**
	 * Title: 将字符串转换为Decimal <br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static BigDecimal covertBigDecimal(String str) {
		String s = StringUtil.convertString(str);
		return BigDecimal.valueOf(Double.valueOf(s));
	}

	/**
	 * Title: 将Object转换为Decimal <br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static BigDecimal covertBigDecimal(Object str) {
		String s = StringUtil.convertString(str);
		if (StringUtil.isNull(s)) {
			return BigDecimal.ZERO;
		}
		return BigDecimal.valueOf(Double.valueOf(s));
	}

	/**
	 * Title: 判断字符串是否数字 <br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (StringUtil.isNull(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		return pattern.matcher(str).matches();

	}

	/**
	 * Title: 判断字符串是否数字 <br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(Object str) {
		String s = StringUtil.convertString(str);
		if (StringUtil.isNull(s)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		return pattern.matcher(s).matches();
	}

	/**
	 * Title: 将Object转换为int <br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static int covertInteger(Object str) {
		String s = StringUtil.convertString(str);
		if (StringUtil.isNull(s)) {
			return 0;
		}
		return Integer.valueOf(s);
	}

	/**
	 * Title: 将字符串转换为int <br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static int covertInteger(String str) {
		if (StringUtil.isNull(str)) {
			return 0;
		}
		return Integer.valueOf(str);
	}

	/**
	 * Title: 将Object转换为long <br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static long covertLong(Object str) {
		String s = StringUtil.convertString(str);
		if (StringUtil.isNull(s)) {
			return 0;
		}
		return Long.valueOf(s);
	}

	/**
	 * Title: 将字符串转换为long <br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static long covertLong(String str) {
		if (StringUtil.isNull(str)) {
			return 0;
		}
		return Long.valueOf(str);
	}

	/**
	 * Title: 判断对象是否null <br/>
	 * Description:
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNull(Object o) {
		if (o == null) {
			return true;
		}
		return false;
	}

	/**
	 * Title: 判断对象是否不为null <br/>
	 * Description:
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNotNull(Object o) {
		if (o == null) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(CommonUtil.isNumeric("0"));
	}

}
