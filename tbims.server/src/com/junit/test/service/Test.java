package com.junit.test.service;

import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.DateUtil;

public class Test {

	private static Long timeToNumber(String time) throws ServiceException {
		Long ret = 0l;
		String[] timeArray = time.split(":");
		if (timeArray == null) {
			return 0l;
		} else {
			if (timeArray.length != 3) {
				throw new ServiceException(0, "时间格式不正确");
			}
			ret = Long.valueOf(timeArray[0]) * 3600 + Long.valueOf(timeArray[1]) * 60 + Long.valueOf(timeArray[2]);
			return ret;
		}
	}

	private static String numberToTime(Long time) {
		String timeStr = null;
		Long hour = 0l;
		Long minute = 0l;
		Long second = 0l;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}
	private static String unitFormat(Long i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Long.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	public static void main(String[] args) throws ServiceException {
		//在SYS_TIME_SPLIT插入时间间隔、时间段、开始时间、结束时间 字段对应值（时间间隔15、30、45）
		int splitTime=60;//时间间隔
		int timeLen=(DateUtil.timeToNumber("23:59")-DateUtil.timeToNumber("00:00"))/splitTime+1;
		String[] times=new String[timeLen+1];
		times[0]="00:00";
		for(int i=0;i<timeLen;i++){
			times[i+1]=DateUtil.addDayMinute(times[i], "HH:mm", splitTime);
		}
		
		StringBuffer sql=new StringBuffer();
		sql.append(" INSERT INTO SYS_TIME_SPLIT ");
		for(int i=0;i<timeLen;i++){
			sql.append(" SELECT "+splitTime+",'"+times[i]+"-"+times[i+1]+"' AS SJD ,'"+times[i]+"' AS A ,'"+times[i+1]+"' AS B FROM  DUAL ");
			if(i==timeLen-1){
				continue;
			} else{
				sql.append("  UNION ");
			}
		}
		System.out.println(sql.toString());
	}
}
