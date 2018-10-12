using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SelfHelpSystem.DAL;
using SelfHelpSystem.Tool;
using SelfHelpSystem.Model;
using tbims.rpc.entity;
using System.Data.SQLite;
using System.Data;
using log4net;
using System.IO;

namespace SelfHelpSystem.BLL
{
    /// <summary>
    /// 基础信息同步服务类
    /// </summary>
    public class B_BaseDataSync
    {
        private AUTHORIZATION _auth;
        private T_RpcClient   _rpcClient;
        private string _dbConnString;
        //同步数据版本数据库访问类
        D_SYNCRecord m_SyncRecord = new D_SYNCRecord();
        //员工登记表数据库访问类
        D_EmpRegister m_EmpRegister = new D_EmpRegister();
        //员工通行场馆表数据库访问类
        D_EmpVenue m_EmpVenue = new D_EmpVenue();
        //票种表数据库访问类
        D_TicketType m_TicketType = new D_TicketType();
        //票种检票规则表数据库访问类
        D_TicketTypeRule m_TicketTypeRule = new D_TicketTypeRule();
        //票种场馆表数据库访问类
        D_TicketTyptVenue m_TicketTypeVenue = new D_TicketTyptVenue();
        //黑名单表数据库访问类
        D_BlackList m_BlackList = new D_BlackList();
        //字典表数据库访问类
        D_Dictionary m_Dictionary = new D_Dictionary();
        //参数表数据库访问类
        D_Paremeter m_Paremeter = new D_Paremeter();
        //预售期表数据库访问类
        D_PERIOD m_Period = new D_PERIOD();
        //终端表数据库访问类
        D_Client m_Client = new D_Client();
        //场馆表数据库访问类
        D_Venue m_Venue = new D_Venue();
        //日志类
        D_Log m_log = new D_Log();

        //日志
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        

        public B_BaseDataSync(AUTHORIZATION auth, String var_host, int var_port, int var_timeout, string dbConstring)
        {
            _auth = auth;
            _dbConnString = dbConstring;
            _rpcClient = new T_RpcClient(_auth, var_host, var_port, var_timeout);
        }

        /// <summary>
        ///  同步处理内网总控
        /// </summary>
        /// <returns>处理结果字符串</returns>
        /// <Remark>郝毅志，2017/06/23</ Remark > 
        public String StartSyncInProcess() {
            String tmpResultMsg="\r\n";
            //if (EmpRegisterSync())
            //{
            //    tmpResultMsg += DateTime.Now + " [1]员工卡同步成功...\r\n";
            //}
            //else
            //{
            //    tmpResultMsg += DateTime.Now + " [1]员工卡同步失败...\r\n";
            //}
            if (TicketTypeSync())
            {
                tmpResultMsg += DateTime.Now + " [2]票种同步成功...\r\n";
            }
            else
            {
                tmpResultMsg += DateTime.Now + " [2]票种同步失败...\r\n";
            }
            //if (BlackListSync())
            //{
            //    tmpResultMsg += DateTime.Now + " [3]黑名单同步成功...\r\n";
            //}
            //else
            //{
            //    tmpResultMsg += DateTime.Now + " [3]黑名单同步失败...\r\n";
            //}
            //if (DictionarySync())
            //{
            //    tmpResultMsg += DateTime.Now + " [4]字典同步成功...\r\n";
            //}
            //else
            //{
            //    tmpResultMsg += DateTime.Now + " [4]字典同步失败...\r\n";
            //}
            //if (ParemeterSync())
            //{
            //    tmpResultMsg += DateTime.Now + " [5]参数同步成功...\r\n";
            //}
            //else
            //{
            //    tmpResultMsg += DateTime.Now + " [5]参数同步失败...\r\n";
            //}
            if (SalePeriodSync())
            {
                tmpResultMsg += DateTime.Now + " [6]预售期同步成功...\r\n";
            }
            else
            {
                tmpResultMsg += DateTime.Now + " [6]预售期同步失败...\r\n";
            }
            //if (SysVenueSync())
            //{
            //    tmpResultMsg += DateTime.Now + " [7]场馆表同步成功...\r\n";
            //}
            //else
            //{
            //    tmpResultMsg += DateTime.Now + " [7]场馆表同步失败...\r\n";
            //}
            if (UpLogFileInfo())
            {
                tmpResultMsg += DateTime.Now + " [8]日志上传成功...\r\n";
            }
            else
            {
                tmpResultMsg += DateTime.Now + " [8]日志上传失败...\r\n";
            }
            if (SetSystemDT())
            {
                tmpResultMsg += DateTime.Now + " [9]日期同步成功...\r\n";
            }
            else
            {
                tmpResultMsg += DateTime.Now + " [9]日期同步失败...\r\n";
            }
            _log.Info(tmpResultMsg);
            return tmpResultMsg;
        }

        /// <summary>
        ///  同步处理外网总控
        /// </summary>
        /// <returns>处理结果字符串</returns>
        /// <Remark>郝毅志，2017/06/23</ Remark > 
        public String StartSyncOutProcess()
        {
            String tmpResultMsg = "";
            if (TicketTypeSync())
            {
                tmpResultMsg += DateTime.Now + " [1]票种同步成功...\r\n";
            }
            else
            {
                tmpResultMsg += DateTime.Now + " [1]票种同步失败...\r\n";
            }
            if (DictionarySync())
            {
                tmpResultMsg += DateTime.Now + " [2]字典同步成功...\r\n";
            }
            else
            {
                tmpResultMsg += DateTime.Now + " [2]字典同步失败...\r\n";
            }
            if (ParemeterSync())
            {
                tmpResultMsg += DateTime.Now + " [3]参数同步成功...\r\n";
            }
            else
            {
                tmpResultMsg += DateTime.Now + " [3]参数同步失败...\r\n";
            }
            if (SalePeriodSync())
            {
                tmpResultMsg += DateTime.Now + " [4]预售期同步成功...\r\n";
            }
            else
            {
                tmpResultMsg += DateTime.Now + " [4]预售期同步失败...\r\n";
            }
            _log.Info(tmpResultMsg);
            return tmpResultMsg;
        }

        /// <summary>
        ///  员工卡登记表下载(增量)
        /// </summary>
        /// <Remark>郝毅志，2017/06/22</ Remark > 
        public bool EmpRegisterSync()
        {

            _log.Info("员工卡登记表同步开始...");
            //查询员工卡登记表版本号
            M_SYNCRecord tmpSyncRecord= m_SyncRecord.QueryVersion("SYS_EMP_REGISTER");
            long tmpVersion = T_Date.ConvertDataTimeToLong(tmpSyncRecord.Version_no);
            //调用rpc员工卡登记表下载接口
            List<SYS_EMP_REGISTER> tmpEmpRegister = null;
            try
            {
                tmpEmpRegister = _rpcClient.EmpRegisterSyncRpc(tmpVersion);
            }
            catch (Exception ex)
            {
                _log.Error("同步员工登记表错误", ex);
                return false;
            }
            if (tmpEmpRegister.Count < 1 || tmpEmpRegister == null)
            {
                _log.Info("没有需要同步的员工数据!");
                return true;
            }

            //同步数据，只要有一条记录没有同步成功则失败,写到一个事务里
            SQLiteConnection tmpConnect = null;
            SQLiteCommand tmpCommand = null;
            SQLiteTransaction tmpTrans = null;
            try {
                tmpConnect = new SQLiteConnection(SQLiteHelper.LocalDbConnectionString);
                if (tmpConnect.State != ConnectionState.Open)
                    tmpConnect.Open();
                tmpCommand = new SQLiteCommand();
                tmpTrans = tmpConnect.BeginTransaction(IsolationLevel.ReadCommitted);
                tmpCommand.Transaction = tmpTrans;
            }
            catch (Exception ex) {
                _log.Error("同步员工数据时开启事务失败", ex);
                return false;
            }

            for (int i = 0; i < tmpEmpRegister.Count; i++) {
                //判断员工是否在表中存在
                int tmpIsExist=m_EmpRegister.IsExist(tmpEmpRegister[i].EmpId);
                if (tmpIsExist == 1)
                {
                    //存在，则更新
                    if (!m_EmpRegister.UpdateEmpRegister(tmpEmpRegister[i], tmpCommand))
                    {
                        _log.Error(
                            "员工卡登记表登录更新失败! 员工编号[" + tmpEmpRegister[i].EmpId+"]"
                            );
                        tmpTrans.Rollback();
                        tmpConnect.Close();
                        return false;
                    }
                }
                else if (tmpIsExist == 2)
                {
                    //不存在，则插入
                    if (!m_EmpRegister.InsertEmpRegister(tmpEmpRegister[i], tmpCommand))
                    {
                        _log.Error(
                            "员工卡登记表登录插入失败! 员工编号[" + tmpEmpRegister[i].EmpId + "]"
                            );
                        tmpTrans.Rollback();
                        tmpConnect.Close();
                        return false;
                    }
                }
                else {
                    //查询出错
                    _log.Error("查询SYS_EMP_REGISTER表失败");
                    tmpTrans.Rollback();
                    tmpConnect.Close();
                    return false;
                }
                //通行场馆删除再插入
                if (!m_EmpVenue.DeleteEmpVenue(tmpEmpRegister[i].EmpId, tmpCommand))
                {
                    _log.Error("员工通行场馆表删除失败! 员工编号[" + tmpEmpRegister[i].EmpId + "]");
                    tmpTrans.Rollback();
                    tmpConnect.Close();
                    return false;
                }
                if (tmpEmpRegister[i].SysEmpVenuelist.Count > 0)
                {
                    for (int j = 0; j < tmpEmpRegister[i].SysEmpVenuelist.Count; j++)
                    {
                        if (!m_EmpVenue.InsertEmpVenue(tmpEmpRegister[i].SysEmpVenuelist[j], tmpCommand))
                        {
                            _log.Error(
                                "员工通行场馆插入失败! 场馆编号[" + tmpEmpRegister[i].SysEmpVenuelist[j].EmpVenueId + "]"
                                );
                            tmpTrans.Rollback();
                            tmpConnect.Close();
                            return false;
                        }
                    }
                }
            }
            tmpTrans.Commit();
            tmpConnect.Close();

            //更新版本号
            DateTime tmpLasterVersion = m_EmpRegister.GetLasterVersion();
            m_SyncRecord.UpdateVersion("SYS_EMP_REGISTER", tmpLasterVersion);
            _log.Error("员工卡登记表同步成功...");
            return true;
        }

        /// <summary>
        ///  票种表下载(全量)
        /// </summary>
        /// <Remark>郝毅志，2017/06/23</ Remark > 
        public bool TicketTypeSync()
        {
            _log.Info("票种表同步开始...");
            //查询员票种表版本号
            M_SYNCRecord tmpSyncRecord = m_SyncRecord.QueryVersion("SYS_TICKET_TYPE");
            long tmpVersion = T_Date.ConvertDataTimeToLong(tmpSyncRecord.Version_no);
            //调用rpc票种表下载接口
            List<SYS_TICKET_TYPE> tmpTicketType = null;
            try
            {
                tmpTicketType = _rpcClient.TicketTypeSyncRpc(tmpVersion);
            }
            catch (Exception ex)
            {
                _log.Error("票种表同步错误", ex);
                return false;
            }
            if (tmpTicketType.Count < 1 || tmpTicketType==null)
            {
                _log.Info("没有需要同步的数据!");
                return true;
            }

            //同步数据，只要有一条记录没有同步成功则失败,写到一个事务里
            SQLiteConnection tmpConnect = null;
            SQLiteCommand tmpCommand = null;
            SQLiteTransaction tmpTrans = null;
            try
            {
                tmpConnect = new SQLiteConnection(SQLiteHelper.LocalDbConnectionString);
                if (tmpConnect.State != ConnectionState.Open)
                    tmpConnect.Open();
                tmpCommand = new SQLiteCommand();
                tmpTrans = tmpConnect.BeginTransaction(IsolationLevel.ReadCommitted);
                tmpCommand.Transaction = tmpTrans;
            }
            catch (Exception ex)
            {
                _log.Error("同步票种时创建事务出错", ex);
                return false;
            }

            //先清空再插入
            if (!m_TicketType.ClearTicketType(tmpCommand))
            {
                _log.Error("票种表清空失败! ");
                tmpTrans.Rollback();
                tmpConnect.Close();
                return false;
            }
            if (!m_TicketTypeRule.ClearTicketType(tmpCommand))
            {
                _log.Error("票种检票规则清空失败!");
                tmpTrans.Rollback();
                tmpConnect.Close();
                return false;
            }
            if (!m_TicketTypeVenue.ClearTicetTypeVenue(tmpCommand))
            {
                _log.Error("票种场馆清空失败! ");
                tmpTrans.Rollback();
                tmpConnect.Close();
                return false;
            }

            for (int i = 0; i < tmpTicketType.Count; i++)
            {
                if (!m_TicketType.InsertTicketType(tmpTicketType[i], tmpCommand))
                {
                    _log.Error("票种表登录插入失败! 票种编号[" + tmpTicketType[i].TicketTypeId + "]");
                    tmpTrans.Rollback();
                    tmpConnect.Close();
                    return false;
                }
                //同步处理票种检票规则
                if (tmpTicketType[i].SysTicketTypeRulelist.Count > 0)
                {
                    for (int j = 0; j < tmpTicketType[i].SysTicketTypeRulelist.Count; j++)
                    {
                        if (!m_TicketTypeRule.InsertTicketTypeRule(tmpTicketType[i].SysTicketTypeRulelist[j], tmpCommand))
                        {
                            _log.Error(
                                "票种检票规则插入失败! 规则编号[" + tmpTicketType[i].SysTicketTypeRulelist[j].RuleId + "]"
                                );
                            tmpTrans.Rollback();
                            tmpConnect.Close();
                            return false;
                        }
                    }
                }
                //同步处理票种场馆
                if (tmpTicketType[i].SysTicketTypeVenuelist.Count > 0)
                {
                    for (int j = 0; j < tmpTicketType[i].SysTicketTypeVenuelist.Count; j++)
                    {
                        if (!m_TicketTypeVenue.InsertTicetTypeVenue(tmpTicketType[i].SysTicketTypeVenuelist[j], tmpCommand))
                        {
                            _log.Error(
                                "票种场馆插入失败! 场馆编号[" + tmpTicketType[i].SysTicketTypeVenuelist[j].VenueId + "]"
                                );
                            tmpTrans.Rollback();
                            tmpConnect.Close();
                            return false;
                        }
                    }
                }
            }
            tmpTrans.Commit();
            tmpConnect.Close();

            //更新版本号
            DateTime tmpLasterVersion = m_TicketType.GetLasterVersion();
            m_SyncRecord.UpdateVersion("SYS_TICKET_TYPE", tmpLasterVersion);
            _log.Info("票种表同步成功...");
            return true;
        }

        /// <summary>
        ///  黑名单表下载(增量)
        /// </summary>
        /// <Remark>郝毅志，2017/06/23</ Remark >
        public bool BlackListSync()
        {
            _log.Info("黑名单表同步开始...");
            //查询黑名单表版本号
            M_SYNCRecord tmpSyncRecord = m_SyncRecord.QueryVersion("SYS_BLACK_LIST");
            long tmpVersion = T_Date.ConvertDataTimeToLong(tmpSyncRecord.Version_no);
            //调用rpc黑名单表下载接口
            List<SYS_BLACK_LIST> tmpBlackList = null;
            try
            {
                tmpBlackList = _rpcClient.BlackListSyncRpc(tmpVersion);
            }
            catch (Exception ex)
            {
                _log.Error("同步黑名单表出错", ex);
                return false;
            }
            if (tmpBlackList.Count < 1 || tmpBlackList==null)
            {
                _log.Info("没有需要同步的数据!");
                return true;
            }

            //同步数据，只要有一条记录没有同步成功则失败,写到一个事务里
            SQLiteConnection tmpConnect = null;
            SQLiteCommand tmpCommand = null;
            SQLiteTransaction tmpTrans = null;
            try
            {
                tmpConnect = new SQLiteConnection(SQLiteHelper.LocalDbConnectionString);
                if (tmpConnect.State != ConnectionState.Open)
                    tmpConnect.Open();
                tmpCommand = new SQLiteCommand();
                tmpTrans = tmpConnect.BeginTransaction(IsolationLevel.ReadCommitted);
                tmpCommand.Transaction = tmpTrans;
            }
            catch (Exception ex)
            {
                _log.Error("同步黑名单开启事务", ex);
                return false;
            }

            for (int i = 0; i < tmpBlackList.Count; i++)
            {
                //判断黑名单编号是否在表中存在
                int tmpIsExist = m_BlackList.IsExist(tmpBlackList[i].BlackListId);
                if (tmpIsExist == 1)
                {
                    //存在，则更新
                    if (!m_BlackList.UpdateBlackList(tmpBlackList[i], tmpCommand))
                    {
                        _log.Error("黑名单更新失败! 黑名单编号[" + tmpBlackList[i].BlackListId + "]");
                        tmpTrans.Rollback();
                        tmpConnect.Close();
                        return false;
                    }
                }
                else if (tmpIsExist == 2)
                {
                    //不存在，则插入
                    if (!m_BlackList.InsertBlackList(tmpBlackList[i], tmpCommand))
                    {
                        _log.Error("黑名单插入失败! 黑名单编号[" + tmpBlackList[i].BlackListId + "]");
                        tmpTrans.Rollback();
                        tmpConnect.Close();
                        return false;
                    }
                }
                else
                {
                    //查询出错
                    _log.Error("查询SYS_BLACK_LIST表失败");
                    tmpTrans.Rollback();
                    tmpConnect.Close();
                    return false;
                }
            }
            tmpTrans.Commit();
            tmpConnect.Close();

            //更新版本号
            DateTime tmpLasterVersion = m_BlackList.GetLasterVersion();
            m_SyncRecord.UpdateVersion("SYS_BLACK_LIST", tmpLasterVersion);
            _log.Info("黑名单表同步成功...");
            return true;
        }

        /// <summary>
        ///  字典表下载(全量)
        /// </summary>
        /// <Remark>郝毅志，2017/06/24</ Remark > 
        public bool DictionarySync()
        {
            _log.Info("字典表同步开始...");
            //查询字典表版本号
            M_SYNCRecord tmpSyncRecord = m_SyncRecord.QueryVersion("SYS_DICTIONARY");
            long tmpVersion = T_Date.ConvertDataTimeToLong(tmpSyncRecord.Version_no);
            //调用rpc字典表下载接口
            List<SYS_DICTIONARY> tmpDictionary = null;
            try
            {
                tmpDictionary = _rpcClient.DictionarySyncRpc(tmpVersion);
            }
            catch (Exception ex)
            {
                _log.Error("同步字典表出错", ex);
                return false;
            }
            if (tmpDictionary.Count < 1 || tmpDictionary==null)
            {
                _log.Info("没有需要同步的数据!");
                return true;
            }

            //同步数据，只要有一条记录没有同步成功则失败,写到一个事务里
            SQLiteConnection tmpConnect = null;
            SQLiteCommand tmpCommand = null;
            SQLiteTransaction tmpTrans = null;
            try
            {
                tmpConnect = new SQLiteConnection(SQLiteHelper.LocalDbConnectionString);
                if (tmpConnect.State != ConnectionState.Open)
                    tmpConnect.Open();
                tmpCommand = new SQLiteCommand();
                tmpTrans = tmpConnect.BeginTransaction(IsolationLevel.ReadCommitted);
                tmpCommand.Transaction = tmpTrans;
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_BaseDataSync), ex);
                return false;
            }


            if (!m_Dictionary.ClearDictionary(tmpCommand))
            {
                _log.Error("字典表清空失败!");
                tmpTrans.Rollback();
                tmpConnect.Close();
                return false;
            }

            for (int i = 0; i < tmpDictionary.Count; i++)
            {
                if (!m_Dictionary.InsertDictionary(tmpDictionary[i], tmpCommand))
                {
                    _log.Error("字典表插入失败! 字典编号[" + tmpDictionary[i].DictionaryId + "]");
                    tmpTrans.Rollback();
                    tmpConnect.Close();
                    return false;
                }
            }
            tmpTrans.Commit();
            tmpConnect.Close();

            //更新版本号
            DateTime tmpLasterVersion = m_Dictionary.GetLasterVersion();
            m_SyncRecord.UpdateVersion("SYS_DICTIONARY", tmpLasterVersion);
            _log.Info("字典表同步成功...");
            return true;
        }


        /// <summary>
        ///  参数表下载(全量)
        /// </summary>
        /// <Remark>郝毅志，2017/06/24</ Remark > 
        public bool ParemeterSync()
        {
            _log.Info("参数表同步开始...");
            //查询参数表版本号
            M_SYNCRecord tmpSyncRecord = m_SyncRecord.QueryVersion("SYS_PAREMETER");
            long tmpVersion = T_Date.ConvertDataTimeToLong(tmpSyncRecord.Version_no);
            //调用rpc参数表下载接口
            List<SYS_PAREMETER> tmpParemeter = null;
            try
            {
                tmpParemeter = _rpcClient.ParemeterSyncRpc(tmpVersion);
            }
            catch (Exception ex)
            {
                _log.Error("同步参数表出错", ex);
                return false;
            }
            if (tmpParemeter.Count < 1 || tmpParemeter==null)
            {
                _log.Info("没有需要同步的数据!");
                return true;
            }

            //同步数据，只要有一条记录没有同步成功则失败,写到一个事务里
            SQLiteConnection tmpConnect = null;
            SQLiteCommand tmpCommand = null;
            SQLiteTransaction tmpTrans = null;
            try
            {
                tmpConnect = new SQLiteConnection(SQLiteHelper.LocalDbConnectionString);
                if (tmpConnect.State != ConnectionState.Open)
                    tmpConnect.Open();
                tmpCommand = new SQLiteCommand();
                tmpTrans = tmpConnect.BeginTransaction(IsolationLevel.ReadCommitted);
                tmpCommand.Transaction = tmpTrans;
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_BaseDataSync), ex);
                return false;
            }

            if (!m_Paremeter.ClearParemeter(tmpCommand))
            {
                _log.Error("参数表清空失败!");
                tmpTrans.Rollback();
                tmpConnect.Close();
                return false;
            }

            for (int i = 0; i < tmpParemeter.Count; i++)
            {
                if (!m_Paremeter.InsertParemeter(tmpParemeter[i], tmpCommand))
                {
                    _log.Error("参数表插入失败! 参数编号[" + tmpParemeter[i].ParemeterId + "]");
                    tmpTrans.Rollback();
                    tmpConnect.Close();
                    return false;
                }
            }
            tmpTrans.Commit();
            tmpConnect.Close();

            //更新版本号
            DateTime tmpLasterVersion = m_Paremeter.GetLasterVersion();
            m_SyncRecord.UpdateVersion("SYS_PAREMETER", tmpLasterVersion);
            _log.Info("参数表同步成功...");
            return true;
        }
 
        /// <summary>
        ///  预售期信息表下载(全量)
        /// </summary>
        /// <Remark>郝毅志，2017/06/26</ Remark > 
        public bool SalePeriodSync()
        {
            _log.Info("预售期表同步开始...");
            //查询预售期表版本号
            M_SYNCRecord tmpSyncRecord = m_SyncRecord.QueryVersion("SL_PERIOD");
            long tmpVersion = T_Date.ConvertDataTimeToLong(tmpSyncRecord.Version_no);
            //调用rpc预售期表下载接口
            List<SL_PERIOD> tmpPeriod = null;
            try
            {
                tmpPeriod = _rpcClient.SalePeriodSyncRpc(tmpVersion);
            }
            catch (Exception ex)
            {
                _log.Error("同步预售期表出错", ex);
                return false;
            }
            if (tmpPeriod.Count < 1 || tmpPeriod==null)
            {
                _log.Info("没有需要同步的数据!");
                return true;
            }

            //同步数据，只要有一条记录没有同步成功则失败,写到一个事务里
            SQLiteConnection tmpConnect = null;
            SQLiteCommand tmpCommand = null;
            SQLiteTransaction tmpTrans = null;
            try
            {
                tmpConnect = new SQLiteConnection(SQLiteHelper.LocalDbConnectionString);
                if (tmpConnect.State != ConnectionState.Open)
                    tmpConnect.Open();
                tmpCommand = new SQLiteCommand();
                tmpTrans = tmpConnect.BeginTransaction(IsolationLevel.ReadCommitted);
                tmpCommand.Transaction = tmpTrans;
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_BaseDataSync), ex);
                return false;
            }

            if (!m_Period.CleartPeriod(tmpCommand))
            {
                _log.Error("预售期表清空失败!");
                tmpTrans.Rollback();
                tmpConnect.Close();
                return false;
            }

            for (int i = 0; i < tmpPeriod.Count; i++)
            {
                if (!m_Period.InsertPeriod(tmpPeriod[i], tmpCommand))
                {
                    _log.Error("预售期表插入失败! 预售期编号[" + tmpPeriod[i].SalePeriodId + "]");
                    tmpTrans.Rollback();
                    tmpConnect.Close();
                    return false;
                }
            }
            tmpTrans.Commit();
            tmpConnect.Close();

            //更新版本号
            DateTime tmpLasterVersion = m_Period.GetLasterVersion();
            m_SyncRecord.UpdateVersion("SL_PERIOD", tmpLasterVersion);
            _log.Info("预售表同步成功...");
            return true;
        }

        /// <summary>
        ///  终端信息表下载(全量)
        /// </summary>
        /// <Remark>郝毅志，2017/06/26</ Remark > 
        public bool ClientSync(
            String clientId,
            String clientName,
            String clientType,
            String RegionIds)
        {
            _log.Info("终端表同步开始...");
            //调用rpc终端表下载接口
            List<SYS_CLIENT> tmpClient = null;
            try
            {
                tmpClient = _rpcClient.QueryClientRpc(
                    clientId, 
                    clientName, 
                    clientType, 
                    RegionIds);
            }
            catch (Exception ex)
            {
                _log.Error("同步终端表出错", ex);
                return false;
            }
            if (tmpClient.Count < 1 || tmpClient == null)
            {
                _log.Info("没有需要同步的数据!");
                return true;
            }

            //同步数据，只要有一条记录没有同步成功则失败,写到一个事务里
            SQLiteConnection tmpConnect = null;
            SQLiteCommand tmpCommand = null;
            SQLiteTransaction tmpTrans = null;
            try
            {
                tmpConnect = new SQLiteConnection(SQLiteHelper.LocalDbConnectionString);
                if (tmpConnect.State != ConnectionState.Open)
                    tmpConnect.Open();
                tmpCommand = new SQLiteCommand();
                tmpTrans = tmpConnect.BeginTransaction(IsolationLevel.ReadCommitted);
                tmpCommand.Transaction = tmpTrans;
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_BaseDataSync), ex);
                return false;
            }

            if (!m_Client.ClearClient(tmpCommand))
            {
                _log.Error("终端表清空失败!");
                tmpTrans.Rollback();
                tmpConnect.Close();
                return false;
            }

            for (int i = 0; i < tmpClient.Count; i++)
            {
                if (!m_Client.InsertClient(tmpClient[i], tmpCommand))
                {
                    _log.Error("终端表插入失败! 终端编号[" + tmpClient[i].ClientId + "]");
                    tmpTrans.Rollback();
                    tmpConnect.Close();
                    return false;
                }
            }
            tmpTrans.Commit();
            tmpConnect.Close();
            _log.Info("终端表同步成功...");
            return true;
        }

        /// <summary>
        /// 场馆表同步(全量)
        /// </summary>
        /// <returns></returns>
        public bool SysVenueSync()
        {
            #region
            _log.Info("场馆表同步开始...");
            //查询参数表版本号
            M_SYNCRecord tmpSyncRecord = m_SyncRecord.QueryVersion("SYS_VENUE");            
            long tmpVersion = T_Date.ConvertDataTimeToLong(tmpSyncRecord.Version_no);
            //调用rpc参数表下载接口
            List<SYS_VENUE> tmpParemeter = null;
            try
            {
                tmpParemeter = _rpcClient.VebueSyncRpc();
            }
            catch (Exception ex)
            {
                _log.Error("同步场馆表出错", ex);
                return false;
            }
            if (tmpParemeter.Count < 1 || tmpParemeter == null)
            {
                _log.Info("没有需要同步的数据!");
                return true;
            }

            //同步数据，只要有一条记录没有同步成功则失败,写到一个事务里
            SQLiteConnection tmpConnect = null;
            SQLiteCommand tmpCommand = null;
            SQLiteTransaction tmpTrans = null;
            try
            {
                tmpConnect = new SQLiteConnection(SQLiteHelper.LocalDbConnectionString);
                if (tmpConnect.State != ConnectionState.Open)
                    tmpConnect.Open();
                tmpCommand = new SQLiteCommand();
                tmpTrans = tmpConnect.BeginTransaction(IsolationLevel.ReadCommitted);
                tmpCommand.Transaction = tmpTrans;
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_BaseDataSync), ex);
                return false;
            }
                        
            if(!m_Venue.DEL_SYS_Venue(tmpCommand))
            {
                _log.Error("场馆表清空失败!");
                tmpTrans.Rollback();
                tmpConnect.Close();
                return false;
            }

            for (int i = 0; i < tmpParemeter.Count; i++)
            {                
                if(!m_Venue.Inert_SYS_Venue(tmpParemeter[i],tmpCommand))
                {
                    _log.Error("常规表插入失败! 参数编号[" + tmpParemeter[i].VenueId + "]");
                    tmpTrans.Rollback();
                    tmpConnect.Close();
                    return false;
                }
            }
            tmpTrans.Commit();
            tmpConnect.Close();

            //更新版本号
            DateTime tmpLasterVersion = m_Paremeter.GetLasterVersion();
            m_SyncRecord.UpdateVersion("SYS_Venue", tmpLasterVersion);
            _log.Info("场馆表同步成功...");
            return true;

            #endregion

        }

        /// <summary>
        /// 检票数据上传
        /// </summary>
        /// <returns></returns>
        public bool? TicketCheckSync()
        {
            DataTable dt = new DataTable();
            SQLiteConnection sqliteConn = new SQLiteConnection(_dbConnString);
            try
            {
                sqliteConn.Open();
                SQLiteDataAdapter da = new SQLiteDataAdapter("select * from SL_CHECK where version_no is null", sqliteConn);
                da.Fill(dt);
            }
            catch (Exception ex)
            {
                _log.Error("读取待上传检票数据时异常！", ex);
                return false;
            }
            finally
            {
                if (sqliteConn.State == ConnectionState.Open)
                    sqliteConn.Close();
            }
            if (dt.Rows.Count == 0)
                return null;

            bool rst = false;
            try
            {
                List<SL_CHECK> upSL_Check = new List<SL_CHECK>();
                foreach (DataRow dr in dt.Rows)
                {
                    SL_CHECK checkRec = new SL_CHECK();
                    checkRec.VenueId = long.Parse(dr["venue_id"].ToString());
                    checkRec.CheckId = dr["check_id"].ToString();
                    checkRec.ClientId = int.Parse(dr["client_id"].ToString());
                    checkRec.ErrorCode = dr["error_code"].ToString();
                    checkRec.NopassReason = dr["nopass_reason"].ToString();
                    checkRec.OpeTime = T_Date.ConvertDataTimeToLong((DateTime)dr["ope_time"]);
                    checkRec.PassFlag = dr["pass_flag"].ToString();
                    checkRec.RemainTimes = int.Parse(dr["remain_times"].ToString());
                    checkRec.TicketClass = dr["ticket_class"].ToString();
                    checkRec.TicketId = long.Parse(dr["ticket_id"].ToString());
                    checkRec.TicketUid = dr["ticket_UID"].ToString();
                    upSL_Check.Add(checkRec);
                }
                rst = _rpcClient.UPTicketCheckRecord(upSL_Check);
            }
            catch (Exception ex)
            {
                _log.Error("检票记录上传失败！", ex);
                return false;
            }


            if (rst)
            {
                try
                {
                    sqliteConn.Open();
                    SQLiteTransaction tran = sqliteConn.BeginTransaction();//实例化一个事务
                    try
                    {
                        SQLiteCommand cmd = new SQLiteCommand("update SL_CHECK set version_no=@version_no where check_id=@check_id", sqliteConn);
                        cmd.Parameters.Add("check_id", DbType.String,30);
                        cmd.Parameters.Add("version_no", DbType.DateTime).Value = DateTime.Now;
                        foreach (DataRow dr in dt.Rows)
                        {
                            cmd.Parameters["check_id"].Value = dr["check_id"];
                            cmd.ExecuteNonQuery();
                        }
                        tran.Commit();
                        return true;
                    }
                    catch (Exception ex)
                    {
                        _log.Error("检票记录上传成功，但更新本地数据库失败！", ex);
                        tran.Rollback();
                        return false;
                    }
                }
                catch (Exception ex)
                {
                    _log.Error("检票记录上传成功，但更新本地数据库失败！", ex);
                    return false;
                }
                finally
                {
                    if (sqliteConn.State == ConnectionState.Open)
                        sqliteConn.Close();
                }
            }
            return false;
        }

        /// <summary>
        /// 检票数据上传
        /// </summary>
        /// <returns></returns>
        public bool GateStateSync(int varGateMode,int runState)
        {
            SYS_CLIENT gateState = new SYS_CLIENT();
            gateState.ClientId = _auth.ClientId;
            gateState.RunStat = runState;
            gateState.GateMode = varGateMode;
            bool resRult=false;
            try
            {
                resRult = _rpcClient.GateStateSnycRpc(gateState);
    
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_BaseDataSync), ex);
                return false;
            }
            return resRult;
        }

        /// <summary>
        /// 设置系统日期为服务器日期
        /// </summary>
        /// <param name="varClientId">终端编号</param>
        /// <param name="varClientName">终端名称</param>
        public bool SetSystemDT() {
            //调用rpc获取服务器时间
            long tmpDateTime;
            try
            {
                //获取服务器日期时间
                tmpDateTime = _rpcClient.GetSystemDateTimeRpc();
                //本地日期时间与服务器日期时间相差超过1min,设置本地日期时间
                long tmpLocal = T_Date.ConvertDataTimeToLong(DateTime.Now);
                long tmpSub=Math.Abs(tmpLocal-tmpDateTime);
                if(tmpSub>60000){
                    T_System.SetLocalTime(T_System.ConvertDateTimeToSystemTime(T_Date.ConvertLongToDateTime(tmpDateTime)));
                }
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_BaseDataSync), ex);
                return false;
            }
            return true;
        }

        /// <summary>
        /// 服务器心态包
        /// </summary>
        /// <returns></returns>
        public bool PingRpc()
        {
            return _rpcClient.pingRpc();
        }


        /// <summary>
        /// 日志信息上传服务器
        /// </summary>
        /// <param name=""></param>
        /// <param name=""></param>
        public bool UpLogFileInfo()
        {
            //轮询日志目录，得到将要上传的日志
            String path = AppDomain.CurrentDomain.BaseDirectory + "Logs\\";
            DirectoryInfo dirInfo = new DirectoryInfo(path);
            List<String> fileName = new List<String>();
            FileStream fileStream = null;
            //删除10天之前日志
            try
            {
                foreach (FileInfo fileInfo in dirInfo.GetFiles("*.*"))
                {
                    string[] s = fileInfo.FullName.Split('.');
                    if (s[s.Length - 1] != "log")
                    {
                        DateTime date = DateTime.ParseExact(s[s.Length - 1], "yyyyMMdd", System.Globalization.CultureInfo.CurrentCulture);
                        DateTime CurrentDate = DateTime.Today;
                        TimeSpan timeSpan = CurrentDate.Subtract(date);
                        if (timeSpan.Days > 10)
                        {
                            fileInfo.Delete();
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                //删除，如果报错，捕获不做处理
                _log.Error(ex.Message.ToString());
            }
            //获取待上传日志
            #region
            foreach (FileInfo fileInfo in dirInfo.GetFiles("*.log"))
            {
                fileName.Add(fileInfo.FullName);
            }
            #endregion
            #region
            //foreach (FileInfo fileInfo in dirInfo.GetFiles("*.*"))
            //{
            //    string sfileName=fileInfo.FullName;
            //    if (sfileName.EndsWith(".log") 
            //        || sfileName.EndsWith(".log.1") 
            //        || sfileName.EndsWith(".log.2") 
            //        || sfileName.EndsWith(".log.3")
            //        || sfileName.EndsWith(".log.4") 
            //        || sfileName.EndsWith(".log.5") 
            //        || sfileName.EndsWith(".log.6") 
            //        || sfileName.EndsWith(".log.7")
            //        || sfileName.EndsWith(".log.8") 
            //        || sfileName.EndsWith(".log.9") 
            //        || sfileName.EndsWith(".log.10"))
            //    {
            //        fileName.Add(fileInfo.FullName);
            //    }
            //}
            #endregion
            if (fileName.Count > 0)
            {
                for (int i = 0; i < fileName.Count; i++)
                {
                    try
                    {
                        Int64 fileCurrentLocation = 0;
                        //在数据库中查找该文件是否有上传记录
                        DataTable dt = m_log.QueryLogInfo(Path.GetFileName(fileName[i]) + "." + DateTime.Today.ToString("yyyyMMdd"));
                        if (dt.Rows.Count > 0)
                        {
                            //如果有记录，获取该文件的上传位置
                            fileCurrentLocation = (Int64)dt.Compute("MAX(FILE_LOCATION)", "");
                        }
                        fileStream = new FileStream(fileName[i], FileMode.Open, FileAccess.Read, FileShare.ReadWrite);
                        fileStream.Seek(fileCurrentLocation, SeekOrigin.Begin);
                        //一次读取最好不超过1MB
                        byte[] array = new byte[1048576];
                        int readtyte = fileStream.Read(array, 0, array.Length);
                        fileCurrentLocation = fileStream.Position;
                        //传送数组
                        byte[] rpcArray = new byte[readtyte];
                        Array.Copy(array, rpcArray, rpcArray.Length);
                        //rpc通讯
                        if (readtyte != 0)
                        {
                            if (_rpcClient.UploadClientLogRpc(Path.GetFileName(fileName[i]) + "." + DateTime.Today.ToString("yyyyMMdd"), rpcArray))
                            {
                                if (dt.Rows.Count > 0)
                                {
                                    m_log.updateLogInfo(
                                       Path.GetFileName(fileName[i]) + "." + DateTime.Today.ToString("yyyyMMdd"),
                                       fileCurrentLocation,
                                       _rpcClient.HOST,
                                       readtyte.ToString()
                                   );
                                }
                                else
                                {
                                    m_log.insertLogInfo(
                                      Path.GetFileName(fileName[i]) + "." + DateTime.Today.ToString("yyyyMMdd"),
                                      fileCurrentLocation,
                                      _rpcClient.HOST,
                                      readtyte.ToString(),
                                      i
                                      );
                                }
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        _log.Error("上传日志出错！[" + ex.Message.ToString() + "]");
                        return false;
                    }
                    finally
                    {
                        if (fileStream != null)
                        {
                            fileStream.Close();
                        }
                    }
                }
            }
            return true;
        }

        /// <summary>
        /// 自助售票机状态和票数同步,联机上传
        /// </summary>
        /// <param name="state">状态</param>
        /// <param name="ticket_num">余票数量(自助售票机用) A:数量,B:数量</param>
        public bool ejectTicketStatSync_RPC(int state)//, string ticket_num
        {
            //Is_UPOK = true;
            bool isok = false;
            try
            {
                //取下票箱各票种的票数
                string str = "select * from SL_TicketCount where boxNumber='A' or boxNumber='B'";
                string sendstr = "";
                SQLiteCommand cmd = new SQLiteCommand(str);
                DataSet ds = new DataSet();
                ds = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, cmd);
                if (ds.Tables[0].Rows.Count > 0)
                {
                    for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                    {
                        if (i == 0)
                        {
                            sendstr = Convert.ToString(ds.Tables[0].Rows[i]["boxNumber"]) + ":" + ds.Tables[0].Rows[i]["surplusCount"].ToString()+ ", ";
                        }
                        else
                        {
                            sendstr = sendstr + Convert.ToString(ds.Tables[0].Rows[i]["boxNumber"]) + ":" + ds.Tables[0].Rows[i]["surplusCount"].ToString();
                        }
                    }
                }
                str = " select AbolishCount from SL_AbolishCount ";
                cmd.CommandText = str;
                object cnum = SQLiteHelper.ExecuteScalar(SQLiteHelper.LocalDbConnectionString, cmd);
                if (cnum != DBNull.Value)
                    sendstr += ", C:" + cnum.ToString();
                isok = _rpcClient.ejectTicketStatSync_RPC(state, sendstr);
                //Is_UPOK = false;//线程用
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_SellTicketUpData), ex);
            }
            return isok;
        }

    }
}
