using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SelfHelpSystem.Model
{
    /// <summary>
    /// 终端信息
    /// </summary>
    class M_Client
    {
        #region Model
        private  Int64    _ClientId;
        private  String   _ClientName;
        private  String   _ClientType;
        private  Int64    _RegionId;
        private  Int64    _OutletId;
        private  string   _IpAddr;
        private  string   _Port;
        private  string   _Stat;
        private  Int32    _GateMode;
        private  Int32    _RunStat;
        private  DateTime _ReportTime;
        private  string   _Token;
        private  string   _OpeUserId;
        private  DateTime _OpeTime;
        #endregion Model

        /// <summary>
        /// 终端编号
        /// </summary>
        public  Int64 ClientId
        {
            set { _ClientId = value; }
            get { return _ClientId; }
        }

        /// <summary>
        /// 终端名称
        /// </summary>
        public  String ClientName
        {
            set { _ClientName = value; }
            get { return _ClientName; }
        }

        /// <summary>
        /// 终端类型(3-缓存服务器1-网点,2-闸机)
        /// </summary>
        public  String ClientType
        {
            set { _ClientType = value; }
            get { return _ClientType; }
        }

        /// <summary>
        /// 区域编号(场馆,闸机客户端使用)
        /// </summary>
        public Int64 RegionId
        {
            get { return _RegionId; }
            set { _RegionId = value; }
        }

        /// <summary>
        /// 网点编号
        /// </summary>
        public Int64 OutletId
        {
            get { return _OutletId; }
            set { _OutletId = value; }
        }

        /// <summary>
        /// IP地址
        /// </summary>
        public string IpAddr
        {
            get { return _IpAddr; }
            set { _IpAddr = value; }
        }

        /// <summary>
        /// 端口
        /// </summary>
        public string Port
        {
            get { return _Port; }
            set { _Port = value; }
        }

        /// <summary>
        /// 状态(Y启用N停用)
        /// </summary>
        public string Stat
        {
            get { return _Stat; }
            set { _Stat = value; }
        }

        /// <summary>
        /// 运行模式0-正常模式,1-紧急模式,2-落杆模式,3-关闭模式,4-A向关闭,B向不控制,5-A向不控制,B向关闭
        /// </summary>
        public Int32 GateMode
        {
            get { return _GateMode; }
            set { _GateMode = value; }
        }

        /// <summary>
        /// 运行状态(1-正常,2-停用,3-压印设备故障,4-闸机异常,5-员工卡读卡器异常,6-票据读卡器异常,7-IO输出异常8-网络异常)
        /// </summary>
        public Int32 RunStat
        {
            get { return _RunStat; }
            set { _RunStat = value; }
        }

        /// <summary>
        /// 报告时间
        /// </summary>
        public DateTime ReportTime
        {
            get { return _ReportTime; }
            set { _ReportTime = value; }
        }

        /// <summary>
        /// 授权码(闸机客户端、缓存服务器使用)
        /// </summary>
        public string Token
        {
            get { return _Token; }
            set { _Token = value; }
        }

        /// <summary>
        /// 操作人
        /// </summary>
        public string OpeUserId
        {
            get { return _OpeUserId; }
            set { _OpeUserId = value; }
        }

        /// <summary>
        /// 操作时间
        /// </summary>
        public DateTime OpeTime
        {
            get { return _OpeTime; }
            set { _OpeTime = value; }
        }
    }
}
