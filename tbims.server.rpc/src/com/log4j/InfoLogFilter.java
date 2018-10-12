package com.log4j;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import com.tbims.util.StringUtil;

/**
 * Title: 定时任务日志输出过滤 <br/>
 * Description:
 * 
 * @ClassName: TasklLogFilter
 * @author ydc
 * @date 2017年8月3日 下午3:07:10
 * 
 */
public class InfoLogFilter extends Filter {

	String operTypeEnable;

	@Override
	public int decide(LoggingEvent event) {
		String opertype = StringUtil.convertString(event.getMDC("opertype"));
		if (StringUtil.isNull(opertype) || StringUtil.isContain(opertype, operTypeEnable.split(","))) {// 日志是否输出
			return Filter.ACCEPT;// 输出日志
		}
		return Filter.DENY;// 不输出日志
	}

	public String getOperTypeEnable() {
		return operTypeEnable;
	}

	public void setOperTypeEnable(String operTypeEnable) {
		this.operTypeEnable = operTypeEnable;
	}

}
