package com.tbims.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.service.spi.ServiceException;

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
	 * @throws ParseException
	 * @throws Exception
	 **/
	public static Date formatStringToDate(String source, String pattern) throws ParseException {
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
	 * Title:获取指定日期的整型数值 <br/>
	 * Description:
	 * 
	 * @param date
	 * @return
	 */
	public static long getDateTime(Date date) {
		if (date != null) {
			return date.getTime();
		}
		return new Date().getTime();
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
		calendar.add(Calendar.MONTH, months);
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

	/**
	 * Title:时间转换成数字 <br/>
	 * Description:
	 * 
	 * @param time 14:04:06
	 * @return
	 * @throws ServiceException
	 */
	public static Long timeToNumber(String time) throws ServiceException {
		if (StringUtil.isNull(time)) {
			return 0l;
		}
		Long ret = 0l;
		String[] timeArray = time.split(":");
		if (timeArray == null) {
			return 0l;
		} else {
			if (timeArray.length != 3) {
				throw new ServiceException("时间格式不正确");
			}
			ret = Long.valueOf(timeArray[0]) * 3600 + Long.valueOf(timeArray[1]) * 60 + Long.valueOf(timeArray[2]);
			return ret;
		}
	}

	/**
	 * Title:数字转换成时间 <br/>
	 * Description:
	 * 
	 * @param time
	 * @return
	 */
	public static String numberToTime(Long time) {
		String timeStr = null;
		Long hour = 0l;
		Long minute = 0l;
		Long second = 0l;
		if (time <= 0)
			return "00:00:00";
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

	/**
	 * Title:数字转换成时间 <br/>
	 * Description:
	 * 
	 * @param i
	 * @return
	 */
	private static String unitFormat(Long i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Long.toString(i);
		else
			retStr = "" + i;
		return retStr;
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
		cal.add(Calendar.DATE, -1);
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
		cal.add(Calendar.DATE, 1);
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse("2015-1-1");
		System.out.println("Format To Date:" + date);
		System.out.println(DateUtil.getPrevDate());
	}
}
