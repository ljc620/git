package com.zhming.support.init;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tbims.entity.SysLog;
import com.tbims.pay.PayUtil;
import com.tbims.pay.bean.BillOrderRequest;
import com.tbims.pay.bfbank.config.SwiftpassConfig;
import com.zhming.support.common.MSG;
import com.zhming.support.common.OperType;
import com.zhming.support.db.DBUtil;
import com.zhming.support.util.DateUtil;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

@Component
public class BillByPayTask {
	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	DBUtil dbUtil;

	/**
	 * 支付接口 payUtilByBfBank:浦发银行接口实现
	 * 
	 */
	@Autowired
	@Qualifier("payUtilByBfBank")
	PayUtil payUtil;

	/**
	 * Title:定时和银行对账 <br/>
	 * Description:定时每天12点5分开始对账
	 */
	@Scheduled(cron = "0 5 12 * * ?")
	public void initAuthBean() {
		SysLog sysLog = new SysLog();
		try {
			String logId = UUIDGenerator.getPK();
			String navigation = logId + "-" + "定时对账";
			MDC.put("navigation", navigation);
			MDC.put("logid", logId);
			MDC.put("opertype", OperType.SCHEDULER);

			sysLog.setUserId("autobill");
			sysLog.setUserName("com.zhming.support.init.BillByPayTask");

			sysLog.setClientId(0L);
			sysLog.setLogId(logId);
			sysLog.setLogType(Long.valueOf(OperType.SCHEDULER));
			sysLog.setMenuName("com.zhming.support.init.BillByPayTask");
			sysLog.setLogTime(DateUtil.getNowDate());

			Date prevDate = DateUtil.getPrevDate();//获取当前日期的前一天 
			BillOrderRequest billOrderBean = new BillOrderRequest();
			billOrderBean.setMch_id(SwiftpassConfig.mch_id);
			billOrderBean.setBill_date(DateUtil.formatDateToString(prevDate, "yyyyMMdd"));
			billOrderBean.setBill_type("ALL");

			payUtil.billOrder(billOrderBean);

			sysLog.setLogStat(StringUtil.convertString(MSG.OK));
			sysLog.setVldDesc("对账成功");
		} catch (Exception e) {
			logger.error("对账错误", e);
			sysLog.setLogStat(StringUtil.convertString(MSG.BF_ERROR));
			sysLog.setVldDesc("对账失败");
		} finally {
			dbUtil.saveEntity("保存日志", sysLog);
		}

		// 清空log4j的mdc信息
		@SuppressWarnings("rawtypes")
		Map map = MDC.getContext();
		if (map != null) {
			map.clear();
		}
	}

}
