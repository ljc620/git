using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace SelfHelpSystem.Model
{
    /// <summary>
    /// 配置信息
    /// </summary>
    class M_Configuration
    {
        #region Model
        private static Int64  _Client_Id;
        private static string _host;
        private static Int32  _port;
        private static Int32  _timeout;
        private static string _rfidReadername;
        private static string _samreadername;
        private static string _pin;
        private static Int32 _teamtickettime;
        private static string _AreaCode;
        private static string _fontName;
        private static float _fontSize;
        private static TimeSpan _startSaleTime;
        private static TimeSpan _endSaleTime;
        #endregion Model

        /// <summary>
        /// 终端号
        /// </summary>
        public static Int64 CLIENTID
        {
            set { _Client_Id = value; }
            get { return _Client_Id; }
        }
        
        /// <summary>
        /// RPC服务器IP
        /// </summary>
        public static string HOST
        {
            set { _host = value; }
            get { return _host; }
        }

        /// <summary>
        /// RPC服务器端口
        /// </summary>
        public static Int32 PORT
        {
            set { _port = value; }
            get { return _port; }
        }

        /// <summary>
        /// 超时时间
        /// </summary>
        public static Int32 TIMEOUT
        {
            set { _timeout = value; }
            get { return _timeout; }
        }
        /// <summary>
        /// Rfid读卡器
        /// </summary>
        public static string RfidReadername
        {
            get { return M_Configuration._rfidReadername; }
            set { M_Configuration._rfidReadername = value; }
        }
        /// <summary>
        /// SAM读卡器
        /// </summary>
        public static string Samreadername
        {
            get { return M_Configuration._samreadername; }
            set { M_Configuration._samreadername = value; }
        }
        /// <summary>
        /// PIN码
        /// </summary>
        public static string Pin
        {
            get { return M_Configuration._pin; }
            set { M_Configuration._pin = value; }
        }

        /// <summary>
        /// 团体票有效时长
        /// </summary>
        public static Int32 Teamtickettime
        {
            get { return M_Configuration._teamtickettime; }
            set { M_Configuration._teamtickettime = value; }
        }

        /// <summary>
        /// 可查询并控制的闸机
        /// </summary>
        public static string AreaCode
        {
            get { return M_Configuration._AreaCode; }
            set { M_Configuration._AreaCode = value; }
        }

        /// <summary>
        /// 字体类型
        /// </summary>
        public static string FontName
        {
            get { return M_Configuration._fontName; }
            set { M_Configuration._fontName = value; }
        }

        /// <summary>
        /// 字体大小
        /// </summary>
        public static float FontSize
        {
            get { return M_Configuration._fontSize; }
            set { M_Configuration._fontSize = value; }
        }
        /// <summary>
        /// 开始销售时间
        /// </summary>
        public static TimeSpan StartSaleTime
        {
            set { _startSaleTime = value; }
            get { return _startSaleTime; }
        }
        /// <summary>
        /// 截至销售时间
        /// </summary>
        public static TimeSpan EndSaleTime
        {
            set { _endSaleTime = value; }
            get { return _endSaleTime; }
        }
        /// <summary>
        /// 票箱票种1  因为自助售票机上最多放两种票
        /// </summary>
        public static string TicketTypeID_1;

        /// <summary>
        /// 票箱1中的票种名称
        /// </summary>
        public static string TicketID_AName;
        /// <summary>
        /// 票箱票种2
        /// </summary>
        public static string TicketTypeID_2;
        /// <summary>
        /// 票箱2中的票种名称
        /// </summary>
        public static string TicketID_BName;

        /// <summary>
        /// 网点编号
        /// </summary>
        public static Int64 OUTLET_ID;

        /// <summary>
        /// 网点名称
        /// </summary>
        public static string OutletName;

        /// <summary>
        /// 所属部门
        /// </summary>
        public static string DEPARTMENT;

        /// <summary>
        /// 授权码
        /// </summary>
        public static string TOKEN;

        /// <summary>
        /// 售票(购票)模式 0表示实际出票的 1表示身份证购票的
        /// </summary>
        public static Int32 SaleMode;

        /// <summary>
        /// 身份证号
        /// </summary>
        public static string IDCard;

        /// <summary>
        /// 自助售票机运行状态
        /// </summary>
        public static Int32 RunState;

        /// <summary>
        /// A票箱说明
        /// </summary>
        public static string boxA_info;
        /// <summary>
        /// B票箱说明
        /// </summary>
        public static string boxB_info;

        /// <summary>
        /// 身份证取票 0表示启用 1表示不启用
        /// </summary>
        public static string IDCard_IsUse;

    }
}
