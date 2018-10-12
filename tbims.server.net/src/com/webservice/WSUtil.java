package com.webservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tbims.entity.SysLog;
import com.zhming.support.db.DBUtil;
import com.zhming.support.db.impl.DBUtilImpl;
import com.zhming.support.init.SpringContextUtil;
import com.zhming.support.util.DateUtil;
import com.zhming.support.util.MsgUtil;
import com.zhming.support.util.StringUtil;

public class WSUtil {
	private static final Log logger = LogFactory.getLog(WSUtil.class);

	/**
	 * Title:保存日志 <br/>
	 * Description:
	 * 
	 * @param logId
	 * @param orgId
	 * @param logType
	 * @param menuId
	 * @param errorCode
	 * @param errorMsg
	 * @param tranSq
	 */
	public static void saveLog(String logId, String orgId, int logType, String menuId, int errorCode, String errorMsg, String tranSq) {
		try {
			DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);
			SysLog sysLog = new SysLog();
			sysLog.setClientId(0L);
			sysLog.setUserId(orgId);
			sysLog.setLogId(logId);
			sysLog.setLogType(Long.valueOf(logType));
			sysLog.setMenuId(menuId);
			sysLog.setMenuName(menuId);
			sysLog.setLogTime(DateUtil.getNowDate());
			sysLog.setLogStat(String.valueOf(errorCode));
			sysLog.setLogCnt(tranSq);// 第三方流水号
			if (StringUtil.isNull(errorMsg)) {
				sysLog.setVldDesc(MsgUtil.getMsg(errorCode));
			} else {
				sysLog.setVldDesc(errorMsg);
			}
			dbUtil.saveEntity("保存日志", sysLog);
		} catch (Exception e) {
			logger.error("保存日志错误", e);
		}
	}
}
