using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections.Generic;
using System.Runtime.InteropServices;

namespace SelfHelpSystem.Tool
{
    class T_System
    {
        /// <summary>  
        /// 获取系统时间  
        /// </summary>  
        /// <param name="st"></param>  
        [DllImport("Kernel32.dll")]
        public static extern void GetLocalTime(SystemTime st);


        /// <summary>  
        /// 设置系统时间  
        /// </summary>  
        /// <param name="st"></param>  
        [DllImport("Kernel32.dll")]
        public static extern void SetLocalTime(SystemTime st);

        [StructLayout(LayoutKind.Sequential)]
        public class SystemTime
        {
            public ushort wYear;
            public ushort wMonth;
            public ushort wDayOfWeek;
            public ushort wDay;
            public ushort Whour;
            public ushort wMinute;
            public ushort wSecond;
            public ushort wMilliseconds;

        }

        public static SystemTime ConvertDateTimeToSystemTime(DateTime time)
        {
            SystemTime st = new SystemTime();
            st.wYear = (ushort)time.Year;
            st.wMonth = (ushort)time.Month;
            st.wDay = (ushort)time.Day;
            st.Whour = (ushort)time.Hour;
            st.wMinute = (ushort)time.Minute;
            st.wSecond = (ushort)time.Second;
            return st;
        }

        public static DateTime ConverSystemTimeToDateTime(SystemTime time)
        {
            return new DateTime(time.wYear, time.wMonth, time.wDay, time.Whour, time.wMinute, time.wSecond);
        }  
    }
}
 
