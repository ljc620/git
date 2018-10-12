using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Data.SQLite;
using SelfHelpSystem.BLL;
using tbims.rpc.entity;
using System.Configuration;
using SelfHelpSystem.Model;
using SelfHelpSystem.DAL;
using log4net;
using System.Threading;
using System.Configuration;

namespace SelfHelpSystem.Tool
{
  public  class T_ThreadDeal
    {
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        private volatile bool _bStopDataSync = false;
        /// <summary>
        /// 数据同步工作线程方法（基础信息下载）
        /// </summary>
        public void DataSyncThread()
        {
            int cnt = 0;
            SQLiteConnection sqliteConn = new SQLiteConnection(SQLiteHelper.LocalDbConnectionString);
            B_BaseDataSync dataSync;
            try
            {
                AUTHORIZATION auth = new AUTHORIZATION();
                auth.ClientId = M_Configuration.CLIENTID;//终端号
                auth.Token = M_Configuration.TOKEN;//授权码
                string serverAddr = ConfigurationManager.AppSettings["ServerAddress"];
                int serverPort = int.Parse(ConfigurationManager.AppSettings["ServerPort"]);
                int syncTimeOut = int.Parse(ConfigurationManager.AppSettings["SyncTimeOut"]);
                dataSync = new B_BaseDataSync(
                    auth, 
                    serverAddr, 
                    serverPort, 
                    syncTimeOut, 
                    SQLiteHelper.LocalDbConnectionString
                    );
            }
            catch (Exception ex)
            {
                _log.Error("初始化数据同步线程错误！", ex);
                return;
            }
            while (!_bStopDataSync)
            {
                try
                {
                    if (cnt == 0)
                    {
                        // 下载基础数据（票种信息、检票规则、黑名单、员工信息）
                        // 每6分钟同步一次基础信息

                        dataSync.StartSyncInProcess();
                        // 修改客户端日期时间
                        if (!dataSync.SetSystemDT())
                        {
                            this._log.Info("更新服务器时间失败！");
                        }
                    }
                    if (cnt % 10 == 0)
                    {                           // 报告运行状态，每1分钟
                        //B_SellTicketUpData.ejectTicketStatSync_RPC(SaleClient, M_Configuration.RunState);
                        dataSync.ejectTicketStatSync_RPC(M_Configuration.RunState);
                        // 修改客户端日期时间
                        if (!dataSync.SetSystemDT())
                        {
                            this._log.Info("更新服务器时间失败！");
                        }
                    }

                    cnt = (cnt + 1) % 60;
                }
                catch (Exception ex)
                {
                    this._log.Error("数据同步异常！", ex);
                }
                Thread.Sleep(6000);
            }
        }

        public void Stop()
        {
            _bStopDataSync = true;
        }
    }
}
