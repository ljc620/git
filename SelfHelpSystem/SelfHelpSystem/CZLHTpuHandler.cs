using System;
using System.Collections.Generic;
using System.IO.Ports;
using System.Threading;
using log4net;

namespace SelfHelpSystem
{
    public static class CZLHTpuHandler
    {

        //CMD_RUN_SUCCESS	    0x00	命令完成成功
        //CMD_RUN_ERROR	        0x01	命令完成失败
        //CMD_CHECK_ERROR	    0x10	命令校验失败
        //CMD_ERROR	            0x11	命令错误（没有该命令）
        //CMD_PARAMETER_ERROR	0x12	命令参数错误


        private static SerialPort _serialPort;
        private static byte[] _recvBuffer = new byte[1024];
        private static int _recvLen = 0;
        private static int _procLen = 0;
        private static List<byte[]> _atrList;
        private volatile static int _retAtrIndex;

        private static int _timeOut;
        private static bool _isError = false;
        private static int _retryTimes = 3;
        private static ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");

        /// <summary>
        /// 连接出票机构控制板
        ///    0:	命令完成成功
        ///   -1:	命令完成失败
        ///  -16:	命令校验失败
        ///  -17:	命令错误（没有该命令）
        ///  -18:	命令参数错误
        ///   -5：COM口未连接
        ///   -6：COM口读错误
        ///   -7：控制板无响应或返回数据不合法
        /// </summary>
        /// <param name="strComPort">串口号</param>
        /// <param name="iTmrOut">超时时间（毫秒）</param>
        /// <returns>
        ///  </returns>
        public static int Open(string strComPort, int iTmrOut)
        {
            _serialPort = new SerialPort(strComPort, 19200, Parity.None, 8, StopBits.One);
            try
            {
                _serialPort.ReadTimeout = iTmrOut;
                _serialPort.WriteTimeout = iTmrOut;
                _timeOut = iTmrOut;
                _serialPort.DataReceived += new SerialDataReceivedEventHandler(SerialPortReceived);
                _serialPort.Open();
                return 0;
            }
            catch (Exception ex)
            {
                _log.Error("出票机构控制板连接错误！", ex);
                return -1;
            }
        }

        /// <summary>
        /// 断开出票机构控制板连接
        ///    0:	命令完成成功
        ///   -1:	命令完成失败
        ///  -16:	命令校验失败
        ///  -17:	命令错误（没有该命令）
        ///  -18:	命令参数错误
        ///   -5：COM口未连接
        ///   -6：COM口读错误
        ///   -7：控制板无响应或返回数据不合法
        /// </summary>
        public static void Close()
        {
            if (_serialPort != null && _serialPort.IsOpen)
            {
                _serialPort.Close();
            }
            _serialPort = null;
        }

        /// <summary>
        /// 重置控制板
        ///    0:	命令完成成功
        ///   -1:	命令完成失败
        ///  -16:	命令校验失败
        ///  -17:	命令错误（没有该命令）
        ///  -18:	命令参数错误
        ///   -5：COM口未连接
        ///   -6：COM口读错误
        ///   -7：控制板无响应或返回数据不合法
        /// </summary>
        /// <returns></returns>
        public static int Reset()
        {
            if (_serialPort == null || !_serialPort.IsOpen)
                return -5; // COM口未连接

            byte[] cmd = new byte[3] { 0x40, 0x00, 0x00 }; 

            int rst = SendCommand(cmd);
            return rst;
        }

        /// <summary>
        /// 获取控制板状态:
        /// 应答数据	HEX	1	西安发卡机芯运行状态
        ///             HEX	1	西安发卡机芯错误代码
        ///             HEX	1	西安发卡机芯票盒A状态
        ///             HEX	1	西安发卡机芯票盒B状态
        ///             HEX	1	西安发卡机芯废票盒状态
        ///             HEX	3	西安发卡机芯传感器状态
        /// 发卡机芯运行状态描述:
        ///         51H	单程票已出票
        ///         52H 单程票已进废票箱
        ///         53H 票停在天线处等待上位机处理
        ///         54H 机芯故障（查看错误代码）
        ///         55H 走票机构空闲
        /// 发卡机芯错误代码
        ///         00H 无错误
        ///         61H 天线到出票口之间发生塞卡故障
        ///         62H A票箱到天线之间发生塞卡故障
        ///         63H B票箱到天线之间发生塞卡故障
        ///         64H 进废票箱故障
        ///         66H 光电传感器异常
        ///         68H 两票盒均异常
        ///         69H 废票箱满
        ///	票盒状态	A/B
        ///	    BIT0
        ///	    BIT1 票箱托盘在底部     票盒停止工作
        ///	    BIT2 票箱预空           票盒允许工作
        ///	    BIT3 票箱票空           票盒停止工作
        ///	    BIT4 票箱不在位          票盒停止工作
        ///	    BIT5 票箱盖子关闭         票盒停止工作
        ///	    BIT6 票箱RFID读卡器通讯故障   票盒停止工作
        ///	    BIT7 票箱RFID卡故障      票盒停止工作

        ///    0:	命令完成成功
        ///   -1:	命令完成失败
        ///  -16:	命令校验失败
        ///  -17:	命令错误（没有该命令）
        ///  -18:	命令参数错误
        ///   -5：COM口未连接
        ///   -6：COM口读错误
        ///   -7：控制板无响应或返回数据不合法
        /// </summary>
        /// <param name="status"></param>
        /// <returns></returns>
        public static int GetStatus(out byte[] status)
        {
            status = new byte[8];
            if (_serialPort == null || !_serialPort.IsOpen)
                return -5; // COM口未连接

            byte[] cmd = new byte[2] { 0x41, 0x00 };

            int rst = SendCommand(cmd);
            if (rst == 0)
            {
                Buffer.BlockCopy(_atrList[_retAtrIndex], 5, status, 0, 8);
            }
            return rst;

        }
        /// <summary>
        /// 刮票机刮票，0-A票箱，1-B票箱
        ///    0:	命令完成成功
        ///   -1:	命令完成失败
        ///  -16:	命令校验失败
        ///  -17:	命令错误（没有该命令）
        ///  -18:	命令参数错误
        ///   -5：COM口未连接
        ///   -6：COM口读错误
        ///   -7：控制板无响应或返回数据不合法
        /// </summary>
        /// <param name="boxid"></param>
        /// <returns></returns>
        public static int SendTicket(byte boxid)
        {
            if (_serialPort == null || !_serialPort.IsOpen)
                return -5; // COM口未连接

            byte[] cmd = new byte[3] { 0x42, 0x00, boxid };

            int rst = SendCommand(cmd);
            return rst;
        }

        /// <summary>
        /// 出票或回收票，0-出票，1-回收废票
        ///    0:	命令完成成功
        ///   -1:	命令完成失败
        ///  -16:	命令校验失败
        ///  -17:	命令错误（没有该命令）
        ///  -18:	命令参数错误
        ///   -5：COM口未连接
        ///   -6：COM口读错误
        ///   -7：控制板无响应或返回数据不合法
        /// </summary>
        /// <param name="OutWay"></param>
        /// <returns></returns>
        public static int OutTicket(byte OutWay)
        {
            if (_serialPort == null || !_serialPort.IsOpen)
                return -5; // COM口未连接

            byte[] cmd = new byte[3] { 0x43, 0x00, OutWay };

            int rst = SendCommand(cmd);
            return rst;
        }

        /// <summary>
        /// 控制板测试指令，0-输票点击正转，1-出票电机反转，2-读票电机正转，3-读票电机反转，4-票盒A电机正转，5-票盒B电机正转
        ///    0:	命令完成成功
        ///   -1:	命令完成失败
        ///  -16:	命令校验失败
        ///  -17:	命令错误（没有该命令）
        ///  -18:	命令参数错误
        ///   -5：COM口未连接
        ///   -6：COM口读错误
        ///   -7：控制板无响应或返回数据不合法
        /// </summary>
        /// <param name="TestMod">
        /// 0x00(输票电机正传)
        /// 0x01(输票电机反传)
        /// 0x02(读票电机正传)
        /// 0x03(读票电机反传)
        /// 0x04(票盒A电机正传)
        /// 0x05(票盒B电机正传)
        /// </param>
        /// <returns></returns>
        public static int Test(byte TestMod)
        {
            if (_serialPort == null || !_serialPort.IsOpen)
                return -5; // COM口未连接

            byte[] cmd = new byte[3] { 0x45, 0x00, TestMod };

            int rst = SendCommand(cmd);
            return rst;
        }

        /// <summary>
        /// 发送命令
        ///    0:	命令完成成功
        ///   -1:	命令完成失败
        ///  -16:	命令校验失败
        ///  -17:	命令错误（没有该命令）
        ///  -18:	命令参数错误
        ///   -5：COM口未连接
        ///   -6：COM口读错误
        ///   -7：控制板无响应或返回数据不合法
        /// </summary>
        /// <param name="cmd">命令字节（不包括STX、Len、CRC和ETX）</param>
        /// <returns>
        /// </returns>
        private static int SendCommand(byte[] cmd)
        {
            int cmdLen = cmd.Length + 4;
            byte[] srcCmd = new byte[cmdLen];
            srcCmd[0] = 0x02;   // STX
            srcCmd[1] = (byte)cmd.Length;
            Buffer.BlockCopy(cmd, 0, srcCmd, 2, cmd.Length);
            srcCmd[cmdLen - 1] = 0x03;  // ETX

            _isError = false;
            _atrList = new List<byte[]>(10);
            _recvLen = 0;
            _procLen = 0;
            _retAtrIndex = -1;

            byte cnt = 0;
            byte lastAtrCode = 0;
            do
            {
                srcCmd[3] = cnt;
                srcCmd[cmdLen - 2] = 0;
                for (int i = 1; i < cmdLen - 2; i++)
                    srcCmd[cmdLen - 2] += srcCmd[i];    // 计算校验码

                byte[] sendBuffer = CMDUtil.EncodeCommand(srcCmd);

                try
                {
                    _serialPort.Write(sendBuffer, 0, sendBuffer.Length);//发送指令
                    int timeOut = _timeOut;
                    do
                    {
                        Thread.Sleep(50);
                        timeOut -= 50;
                        ProcessRecvData();
                        for (int i = 0; i < _atrList.Count; i++)
                        {
                            byte[] atrData = _atrList[i];
                            if (atrData[2] != srcCmd[2])
                                continue;

                            if (atrData[4] == 0x00)
                            {
                                _retAtrIndex = i;
                                return 0;
                            }
                            else
                                lastAtrCode = atrData[4];
                        }
                    }
                    while (!_isError && timeOut > 0);
                }
                catch (Exception ex)
                {
                    _log.Error("出票机构控制板指令发送错误！指令：" + BitConverter.ToString(sendBuffer).Replace('-', ' '), ex);
                }

                cnt++;
            } while (cnt <= _retryTimes);

            if (lastAtrCode != 0x00)
                return -lastAtrCode;
            else if (_isError)
                return -6; //COM口读错误
            else
                return -7;   //IO板无响应或返回异常
        }

        /// <summary>
        /// 处理串口接受到的数据，如果有完整且有效的返回数据，则存入_atrList<T>变量
        /// </summary>
        private static void ProcessRecvData()
        {
            if (_recvLen - _procLen < 6)
                return;

            int tmpPtr;
            do
            {
                tmpPtr = _procLen;
                bool hasSTX = false;
                int i = 0;
                while (i < _recvLen - tmpPtr)
                {
                    if (_recvBuffer[tmpPtr + i] == 0x02) //查找起始位（0x02）
                    {
                        hasSTX = true;
                        _procLen = tmpPtr + i;
                    }
                    else if (_recvBuffer[tmpPtr + i] == 0x03 && hasSTX) //查找结束位（0x03）,且存在起始位
                    {
                        i++;
                        byte[] atrData = CMDUtil.DecodeRecvData(_recvBuffer, _procLen, tmpPtr + i - _procLen);
                        _procLen = tmpPtr + i;

                        if (atrData.Length > 4 || atrData[1] + 4 == atrData.Length) // 返回数据长度正常
                        {
                            byte crc = 0;
                            for (int j = 1; j < atrData[1] + 2; j++)
                            {
                                crc = (byte)(crc + atrData[j]);
                            }
                            if (atrData[atrData[1] + 2] == crc) // 校验和比对
                            {
                                _atrList.Add(atrData);
                            }
                        }
                        break;
                    }
                    else if (_recvBuffer[tmpPtr + i] == 0x10) // 跳过数据位
                        i++;

                    i++;
                }
                tmpPtr += i;
            } while (tmpPtr < _recvLen);
        }

        /// <summary>
        /// 接受串口返回数据，并验证数据是否合法，如果合法IsRecvOK为true，否则为False。
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private static void SerialPortReceived(object sender, SerialDataReceivedEventArgs e)
        {
            if (_serialPort.BytesToRead == 0)
                return;
            try
            {
                int len = _serialPort.BytesToRead;
                int num = _serialPort.Read(_recvBuffer, _recvLen, len);
                _recvLen += len;
            }
            catch (Exception ex)
            {
                _log.Error("出票机构串口数据接收错误！", ex);
                _isError = true;
            }
        }
    }

    static class CMDUtil
    {
        /// <summary>
        /// 处理发送的指令，除开头和结尾符外，如果有0x02、0x03、0x10，则在这三个字符前插入0x10
        /// </summary>
        /// <param name="cmdBuffer"></param>
        /// <returns></returns>
        public static byte[] EncodeCommand(byte[] cmdBuffer)
        {
            int newLen = cmdBuffer.Length, p = 0;
            byte[] newSendBuffer = new byte[newLen];
            for (int i = 0; i < cmdBuffer.Length; i++)
            {
                if ((cmdBuffer[i] == 0x02 || cmdBuffer[i] == 0x03 || cmdBuffer[i] == 0x10) && i > 0 && i < cmdBuffer.Length - 1)
                {
                    newLen++;
                    Array.Resize(ref newSendBuffer, newLen);
                    newSendBuffer[p] = 0x10;
                    p++;
                }
                newSendBuffer[p] = cmdBuffer[i];
                p++;
            }

            return newSendBuffer;
        }
        /// <summary>
        /// 处理接受到的应答数据，删除额外的0x10
        /// </summary>
        /// <param name="recvBuffer"></param>
        /// <returns></returns>
        public static byte[] DecodeRecvData(byte[] recvBuffer, int offset, int len)
        {
            int i = offset, p = 0;
            byte[] newRecv = new byte[len];
            while (i < len + offset)
            {
                if (i == offset || i == offset + len - 1 || recvBuffer[i] != 0x10)
                {
                    newRecv[p] = recvBuffer[i];
                    i++;
                    p++;
                }
                else
                {
                    newRecv[p] = recvBuffer[i + 1];
                    p++;
                    i += 2;
                }
            }
            Array.Resize(ref newRecv, p);
            return newRecv;
        }

    }

    /// <summary>
    /// 发卡控制板运行状态
    ///         51H	单程票已出票
    ///         52H 单程票已进废票箱
    ///         53H 票停在天线处等待上位机处理
    ///         54H 机芯故障（查看错误代码）
    ///         55H 走票机构空闲
    /// </summary>
    public enum TpuRunStatus
    {
        /// <summary>
        /// 未知
        /// </summary>
        Unkown = 0,
        /// <summary>
        /// 已出票
        /// </summary>
        TicketOuted = 0x51,
        /// <summary>
        /// 已进废票箱
        /// </summary>
        Recovered = 0x52,
        /// <summary>
        /// 票停在天线处等待上位机处理
        /// </summary>
        WaitProccess = 0x53,
        /// <summary>
        /// 机芯故障（查看错误代码）
        /// </summary>
        DeviceFault = 0x54,
        /// <summary>
        /// 走票机构空闲
        /// </summary>
        Idle = 0x55
    }
}
