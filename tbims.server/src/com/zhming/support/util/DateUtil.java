package com.zhming.support.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.zhming.support.common.MSG;
import com.zhming.support.exception.ServiceException;

/**
 * Title: 日期工具类 <br/>
 * Description:
 * 
 * @ClassName: DateUtil
 * @author ydc
 * @date 2016-1-7 下午5:16:04
 * 
 */
public class DateUtil {
	/**
	 * 将日期转换成全中文的时间戳(1988年10月17日 11时11分11秒)
	 * 
	 * @param date
	 * @return 格式化的日期字符串
	 */
	public static String convertAllChinaDate(Date date) {
		SimpleDateFormat formateDate = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return formateDate.format(date);
	}

	/**
	 * @description: 将日期字符串转换成指定格式的字符串
	 * @author:ydc
	 * @return:String
	 * @param source
	 * @param pattern
	 * @return
	 * @throws Exception
	 **/
	public static String formatDateString(String source, String pattern) throws Exception {
		SimpleDateFormat formateDate = new SimpleDateFormat(pattern);
		Date sourceDate = formateDate.parse(source);
		return formateDate.format(sourceDate);
	}

	/**
	 * @description: 将日期转换成指定格式的字符串
	 * @author:ydc
	 * @return:String
	 * @param source
	 * @param pattern
	 * @return
	 * @throws Exception
	 **/
	public static String formatDateToString(Date source, String pattern) {
		SimpleDateFormat formateDate = new SimpleDateFormat(pattern);
		return formateDate.format(source);
	}

	/**
	 * @description: 将日期字符串转换为指定的格式的日期类型
	 * @author:ydc
	 * @return:Date
	 * @param source
	 * @param pattern
	 * @return
	 * @throws Exception
	 **/
	public static Date formatStringToDate(String source, String pattern) throws Exception {
		SimpleDateFormat formateDate = new SimpleDateFormat(pattern);
		return formateDate.parse(source);
	}

	/**
	 * @description: 得到当前时间戳
	 * @author:ydc
	 * @return:String
	 * @param formate 日期格式化字符串:yyyyMMddHHmmss
	 * @return yyyyMMddHHmmss
	 **/

	public static String getNowDate(String formate) {
		SimpleDateFormat formateDate = new SimpleDateFormat(formate);
		return formateDate.format(new Date());
	}
	
	/**
	 * @description: 得到当前时间戳
	 * @author:ydc
	 * @return:String
	 * @param formate 日期格式化字符串:yyyyMMddHHmmss
	 * @return yyyyMMddHHmmss
	 **/

	public static String getDate(String formate,int num) {
		return DateUtil.addDay(formate,DateUtil.getNowDate("yyyy-MM-dd"), num);
	}

	/**
	 * @description: 得到当前时间
	 * @author:ydc
	 * @param formate 日期格式化字符串:yyyy-MM-dd HH:mm:ss
	 * @return
	 **/
	public static String getNowDate() {
		SimpleDateFormat formateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formateDate.format(new Date());
	}

	/**
	 * @description:比较传入的时间[time],与今天[-:前,+:后]days天的时间大小,如果time大则返回true.即已过期.
	 * @author:ltf
	 * @return:boolean
	 * @param time
	 * @param days [-:前,+:后].如 days为-30则为今天的30天之前的日期.
	 * @return
	 **/

	public static boolean compareByDays(String time, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, days);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastdat = sdf.format(calendar.getTime());
		if (time.compareTo(lastdat) < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @description:比较传入的时间[time],与今天[-:前,+:后]months月的时间大小,如果time大则返回true.
	 * @author:ltf
	 * @return:boolean
	 * @param time
	 * @param months [-:前,+:后].如 months为-12则为今天的12个月之前的日期.
	 * @return
	 **/

	public static boolean compareByMonths(String time, int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, months);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastMonth = sdf.format(calendar.getTime());
		if (time.compareTo(lastMonth) < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * Title: checkDate
	 * </p>
	 * <p>
	 * Description: 检查日期是否合法
	 * </p>
	 * 
	 * @return
	 */
	public static boolean checkDate(String date, String partern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(partern);
			sdf.setLenient(false);
			sdf.parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * <p>
	 * Title: checkDateNormal
	 * </p>
	 * <p>
	 * Description: 检出日期是否合法默认格式 "yyyyMMdd"
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static boolean checkDateNormal(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			sdf.setLenient(false);
			sdf.parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取系统日期 Title: <br/>
	 * Description:
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * 
	 * 方法描述：取得当前日期的上月或下月日期 ,amount=-1为上月日期，amount=1为下月日期
	 * 
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static String getFrontBackStrDate(String strDate, int amount) {
		if (null == strDate) {
			return null;
		}
		try {
			DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			Calendar c = Calendar.getInstance();
			c.setTime(fmt.parse(strDate));
			c.add(Calendar.MONTH, amount);
			return fmt.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String addDay(String date, int days) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		try {
			trialTime = myFormatter.parse(date);
		} catch (ParseException e) {
		}
		calendar.setTime(trialTime);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return myFormatter.format(calendar.getTime());
	}
	
	public static String addDay(String formate,String date, int days) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat retFormatter = new SimpleDateFormat(formate);
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		try {
			trialTime = myFormatter.parse(date);
		} catch (ParseException e) {
		}
		calendar.setTime(trialTime);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return retFormatter.format(calendar.getTime());
	}

	public static String getYestoday() {
		return DateUtil.addDay(DateUtil.getNowDate(), -1);
	}

	/**
	 * 
	 * Title:获取当前时间的前后几小时 <br/>
	 * Description:
	 * 
	 * @param partern
	 * @param hour
	 * @return
	 */
	public static String beforeOrAfterHour(String partern, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 3);
		SimpleDateFormat sdf = new SimpleDateFormat(partern);
		String newDate = sdf.format(calendar.getTime());
		return newDate;
	}

	public static String addHour(Date date, int hour) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		String newDate = myFormatter.format(calendar.getTime());
		return newDate;
	}

	public static String addDayMinute(String dateStr, String partern, int minute) {
		SimpleDateFormat myFormatter = new SimpleDateFormat(partern);
		Date date = null;
		try {
			date = myFormatter.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		String newDate = myFormatter.format(calendar.getTime());
		return newDate;

	}

	public static String getNearMinute(String oldMinute, int interval, String begOrEnd) {
		String ohour = oldMinute.substring(0, 2);
		String omin = oldMinute.substring(3, 5);
		int v = Integer.valueOf(omin);
		String ret = "00:00";
		if ("b".equals(begOrEnd)) {
			ret = ohour + ":" + StringUtil.stringFillOrCut(String.valueOf(v - v % interval), "0", 2, 1);
		} else {
			if (v - v % interval + interval >= 60) {
				ret = StringUtil.stringFillOrCut((String.valueOf(Long.valueOf(ohour) + 1)), "0", 2, 1) + ":" + "00";
			}
			ret = ohour + ":" + StringUtil.stringFillOrCut(String.valueOf(v - v % interval + interval), "0", 2, 1);
		}
		return ret;
	}

	/**
	 * Title: <br/>
	 * Description: 将HH：MM形式的时间转化成分钟，返回int类型
	 * 
	 * @param time HH:MM
	 * @return
	 * @throws ServiceException
	 */
	public static int timeToNumber(String time) throws ServiceException {
		if (StringUtil.isNull(time)) {
			return 0;
		}
		int ret = 0;
		String[] timeArray = time.split(":");
		if (timeArray == null) {
			return 0;
		} else {
			if (timeArray.length != 2) {
				throw new ServiceException(MSG.ERROR, "时间格式不正确");
			}
			ret = Integer.valueOf(timeArray[0]) * 60 + Integer.valueOf(timeArray[1]);
			return ret;
		}
	}

	/**
	 * Title:获取当前日期的前一天 <br/>
	 * Description:
	 * 
	 * @return
	 */
	public static Date getPrevDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * Title:获取当前日期的加一天 <br/>
	 * Description:
	 * 
	 * @return
	 */
	public static Date getNextDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	/**
	 * java转换为jdbc日期 
	 * @param date
	 * @return
	 */
	public static java.sql.Date covertSqlDate(Date date){
		if(date==null){
			return null;
		}else{
			return new java.sql.Date(date.getTime());
		}
	}

	/**
	 * java转换为jdbc日期 
	 * @param date
	 * @return
	 */
	public static java.sql.Timestamp covertSqlTimeStamp(Date date){
		if(date==null){
			return null;
		}else{
			return new java.sql.Timestamp(date.getTime());
		}
	}

	public static void main(String[] args) throws Exception {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = format.parse("2015-1-1");
//		String[] times = new String[3];
//		for (int i = 0; i < 3; i++) {
//			times[i] += DateUtil.addDayMinute("08:00", "HH:mm", 15);
//			System.out.println("Format To Date:" + times[i]);
//		}
	 
		System.out.println(DateUtil.getDate("yyyy-MM-dd", -2));
	}
}
