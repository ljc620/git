package com.tbims.util;

import java.util.Map;

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
	public static String prefix(Map<String, String> info, String logId, String functionName) {
		if (info == null) {
			return logId + "-" + functionName + "";
		}
		return logId + "-" + info.get("CLIENTID") + "-" + info.get("USERID") + "-" + info.get("USERNAME") + "-" + functionName + "";
	}

}
