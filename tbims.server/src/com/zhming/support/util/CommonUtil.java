package com.zhming.support.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;

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
		if(StringUtil.isNull(s)){
			return BigDecimal.ZERO;
		}
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
	 * Title: 将Object转换为long <br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static int covertInt(Object str) {
		return covertBigDecimal(str).intValue();
	}

	/**
	 * Title: 将字符串转换为long <br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static int covertInt(String str) {
		return covertBigDecimal(str).intValue();
	}

	/**
	 * Title: 将字符串转换为double <br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static double covertDouble(String str) {
		return covertBigDecimal(str).doubleValue();
	}

	/**
	 * Title: 将Object转换为double<br/>
	 * Description:
	 * 
	 * @param str
	 * @return
	 */
	public static double covertDouble(Object str) {
		return covertBigDecimal(str).doubleValue();
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

	/**
	 * 判断数组中是否包含
	 * 
	 * @param i
	 * @param ints
	 * @return
	 */
	public static boolean isContainsByInt(int i, int[] ints) {
		for (int j : ints) {
			if (i == j) {
				return true;// 包含
			}
		}
		return false;// 不包含
	}

	/**
	 * 判断字符串是否对象的 json格式
	 * 
	 * @param json
	 * @return
	 */
	public static boolean isJsonObjStr(String json) {
		try {
			if (StringUtil.isNull(json)) {
				return false;
			}
			JSONArray.parseObject(json);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否对象的 json格式
	 * 
	 * @param json
	 * @return
	 */
	public static boolean isJsonArrayStr(String json) {
		try {
			if (StringUtil.isNull(json)) {
				return false;
			}
			JSONArray.parseArray(json);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(CommonUtil.isNumeric("0"));
	}

}
