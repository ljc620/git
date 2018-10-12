using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SelfHelpSystem.Model
{
    /// <summary>
    /// 自助售票机中各种设备运行状态
    /// </summary>
    /// 

    /// <summary>
    /// 设备运行状态
    /// </summary>
    public enum DeviceStatus
    {
        /// <summary>
        /// 设备正常
        /// </summary>
        IsOK = 0,
        /// <summary>
        /// 读卡器故障
        /// </summary>
        CardReaderFault = 4,
        /// <summary>
        /// 身份证阅读器故障
        /// </summary>
        IDCardFault = 5,
        /// <summary>
        /// 系统故障
        /// </summary>
        SystemFault = 8,
        /// <summary>
        /// 扫描二维码故障
        /// </summary>
        ScanFault = 11,
        /// <summary>
        /// 出票机故障
        /// </summary>
        OutCardFault = 12
    }

}
