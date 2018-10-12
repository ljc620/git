using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Globalization;

namespace SelfHelpSystem.Tool
{
    /// <summary>
    /// 日期处理类
    /// </summary>
    public class T_Date
    {

        public static DateTimeFormatInfo m_DtFormat = new DateTimeFormatInfo();
        /// <summary>
        /// 将日期型转换为Long型
        /// </summary>
        /// <param name="varDt">日期型变量</param>
        /// <returns> 日期long型</returns>
        /// <Remark>郝毅志，2017/06/17</ Remark > 
        public static long ConvertDataTimeToLong(DateTime varDt)
        {
            DateTime dtStart = TimeZone.CurrentTimeZone.ToLocalTime(new DateTime(1970, 1, 1));
            TimeSpan toNow = varDt.Subtract(dtStart);
            return toNow.Ticks / 10000;
            /*long timeStamp = toNow.Ticks/10000;
            timeStamp = long.Parse(timeStamp.ToString().Substring(0, timeStamp.ToString().Length - 4));
            return timeStamp;*/
        }

        /// <summary>
        /// 将Long型转换为日期型
        /// </summary>
        /// <param name="varDt">long型</param>
        /// <result> 日期型</result>
        /// <Remark>郝毅志，2017/06/17</ Remark > 
        public static DateTime ConvertLongToDateTime(long varD)
        {
            DateTime dtStart = TimeZone.CurrentTimeZone.ToLocalTime(new DateTime(1970, 1, 1));
            long lTime = long.Parse(varD + "0000");
            TimeSpan toNow = new TimeSpan(lTime);
            DateTime dtResult = dtStart.Add(toNow);
            return dtResult;
        }

        /// <summary>
        /// 日期格式设置
        /// </summary>
        /// <Remark>郝毅志，2017/06/21</ Remark > 
        public static DateTimeFormatInfo DateFormatSet()
        {
            m_DtFormat.ShortDatePattern = "yyyy/MM/dd HH:mm:ss";
            return m_DtFormat;
        }
    }
}
