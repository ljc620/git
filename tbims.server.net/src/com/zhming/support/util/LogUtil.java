package com.zhming.support.util;

import com.zhming.support.bean.UserBean;

/**
 * Title: 日志输出工具类 <br/>
 * Description:
 * @ClassName: LogUtil
 * @author ydc
 * @date 2016-1-16 下午5:34:13
 * 
 */
public class LogUtil {

	/**
	 * Title: 获取日志输出前缀<br/>
	 * Description:
	 * @param userBean
	 * @param navigation
	 * @return
	 */
	public static String prefix(String logCd, String navigation, UserBean userBean) {
		if (userBean == null) {
			return logCd + "-" + navigation;
		}
		String user_name = userBean.getSysUser().getUserName();

		return logCd + "-" + userBean.getUserId() + "-" + user_name + "-" + navigation;
	}

}
