package com.zhming.support.tag;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.zhming.support.util.DateUtil;
import com.zhming.support.util.FunctionUtil;
import com.zhming.support.util.MsgUtil;
import com.zhming.support.util.StringUtil;

/**
 * Title: 自定义el表达式方法 <br/>
 * Description:
 * @ClassName: ELFunctions
 * @author ydc
 * @date 2016-1-7 下午5:09:39
 * 
 */
public class ELFunctions {

	/**
	 * Title: 数组是否包含 <br/>
	 * Description:
	 * @param obj
	 * @param array
	 * @return
	 */
	public static boolean isContain(Object obj, Object[] array) {
		if (obj == null || array == null) {
			return false;
		}
		for (Object temp : array) {
			if (temp.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Title: 日期格式<br/>
	 * Description:
	 * @param date
	 * @param formate
	 * @return
	 */
	public static String formateDate(Date date, String formate) {
		if (date == null) {
			return "";
		}
		return DateUtil.formatDateToString(date, formate);
	}

	/**
	 * Title: 日期格式<br/>
	 * Description:
	 * @param dateStr
	 * @param formate
	 * @return
	 */
	public static String formateDate(String dateStr, String formate) {
		if (dateStr == null) {
			return "";
		}
		try {
			return DateUtil.formatDateString(dateStr, formate);
		} catch (Exception e) {
			e.printStackTrace();
			return "日期错误";
		}
	}

	/**
	 * Title: 获取当前日期<br/>
	 * Description:
	 * @param formate
	 * @return
	 */
	public static String getNow(String formate) {
		try {
			return DateUtil.getNowDate(formate);
		} catch (Exception e) {
			e.printStackTrace();
			return "日期错误";
		}
	}

	/**
	 * Title: 获取状态信息<br/>
	 * Description:
	 * @param msg
	 * @return
	 */
	public static String getMsg(Object msg) {
		String msg1 = String.valueOf(msg);
		if (StringUtil.isNull(msg1)) {
			return "";
		}
		if (StringUtils.isNumeric(msg1)) {
			return MsgUtil.getMsg(Integer.parseInt(msg1));
		} else {
			return msg1;
		}
	}

	/**
	 * Title: 权限控制<br/>
	 * Description:
	 * @param function_cd
	 * @param functionMap
	 * @return
	 */
	public static boolean hasPriv(String function_cd, Set<String> functionSet) {
		if (functionSet == null) {
			return false;
		}
		return functionSet.contains(function_cd);
	}

	/**
	 * Title: 获取权限名称<br/>
	 * Description:
	 * @param function_cd
	 * @param functionMap
	 * @return
	 */
	/*public static String getFunNm(String function_cd) {
		if (FunctionUtil.getFunction(function_cd) != null) {
			return FunctionUtil.getFunction(function_cd).getResNm();
		}
		return "--";
	}*/

	/**
	 * Title: 获取权限图标<br/>
	 * Description:
	 * @param function_cd
	 * @param functionMap
	 * @return
	 */
	/*public static String getFunIcon(String function_cd) {
		if (FunctionUtil.getFunction(function_cd) != null && StringUtil.isNotNull(FunctionUtil.getFunction(function_cd).getImgPath())) {
			return FunctionUtil.getFunction(function_cd).getImgPath();
		}
		return "icon-bfmenu-delfaut";
	}*/

	/**
	 * Title: 获取导航信息<br/>
	 * Description:
	 * @param function_cd
	 * @param functionMap
	 * @return
	 */
	public static String getNavigation(String function_cd) {
		if (StringUtil.isNotNull(FunctionUtil.getNavigation(function_cd))) {
			return FunctionUtil.getNavigation(function_cd);
		}
		return "--";
	}
}
