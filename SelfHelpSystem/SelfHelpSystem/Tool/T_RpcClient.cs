using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Thrift;
using Thrift.Transport;
using Thrift.Protocol;
using Thrift.Collections;
using System.Configuration;
using tbims.rpc.service;
using tbims.rpc.entity;
using log4net;

namespace SelfHelpSystem.Tool
{
    /// <summary>
    /// Rpc通讯客户端
    /// </summary>
    public class T_RpcClient
    {
        private TTransport transport = null;
        private TProtocol protocol = null;
        private SercurityService.Client SercurityClient = null;
        private SaleService.Client SaleClient = null;
        private DataSyncService.Client DataSyncClient = null;
        private SystemService.Client SystemClient = null;
        private AUTHORIZATION _auth;
        private String m_host;
        private int m_port;
        private int m_timeout;
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        public String HOST
        {
            get { return m_host; }
        }

        //初始化
        public bool Init()
        {
            lock (this)
            {
                try
                {
                    transport = new TSocket(m_host, m_port, m_timeout);
                    protocol = new TBinaryProtocol(transport);
                    TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "SercurityService");
                    SercurityClient = new SercurityService.Client(mp);
                    TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol, "SaleService");
                    SaleClient = new SaleService.Client(mp2);
                    TMultiplexedProtocol mp3 = new TMultiplexedProtocol(protocol, "DataSyncService");
                    DataSyncClient = new DataSyncService.Client(mp3);
                    if (transport != null && !transport.IsOpen)
                    {
                        transport.Open();
                    }
                    return true;
                }
                catch (Exception ex)
                {
                    if (transport != null)
                        transport.Dispose();
                    if (protocol != null)
                        protocol.Dispose();
                    if (DataSyncClient != null)
                        DataSyncClient.Dispose();
                    if (SaleClient != null)
                        SaleClient.Dispose();
                    if (SercurityClient != null)
                        SercurityClient.Dispose();
                    if (SystemClient != null)
                        SystemClient.Dispose();
                    transport = null;
                    protocol = null;
                    SercurityClient = null;
                    SystemClient = null;
                    SaleClient = null;
                    DataSyncClient = null;
                    _log.Error(ex.Message.ToString());
                    return false;
                }
            }
        }

        //构造函数
        public T_RpcClient(AUTHORIZATION auth, String var_host, int var_port, int var_timeout)
        {
            this._auth = auth;
            this.m_host = var_host;
            this.m_port = var_port;
            this.m_timeout = var_timeout;
            if (!Init()) {
                throw new Exception("服务器连接失败!");
            }
        }

        //释放链接
        public void Dispose() {
            lock (this)
            {
                try
                {
                    if (transport != null && transport.IsOpen)
                        transport.Close();
                    if (transport != null)
                        transport.Dispose();
                    if (protocol != null)
                        protocol.Dispose();
                    if (DataSyncClient != null)
                        DataSyncClient.Dispose();
                    if (SaleClient != null)
                        SaleClient.Dispose();
                    if (SercurityClient != null)
                        SercurityClient.Dispose();
                    if (SystemClient != null)
                        SystemClient.Dispose();
                    transport = null;
                    protocol = null;
                    SercurityClient = null;
                    SystemClient = null;
                    SaleClient = null;
                    DataSyncClient = null;
                }
                catch (Exception ex)
                {
                    throw new Exception(ex.Message.ToString());
                }
            }
        }

        /// <summary>
        /// Transport属性
        /// </summary>
        public TTransport Transport
        {
            get { return transport; }
        }


        /// <summary>
        /// 检查链接
        /// </summary>
        public void checkConn() {
            bool pingRst = false; int cnt = 0;
            do
            {
                try
                {
                    pingRst = pingRpc();
                }
                catch (TTransportException ex)
                {
                    try
                    {
                        Dispose();
                    }
                    catch { }
                    break;
                }
                catch (Exception ex) { }
                cnt++;
            } while (!pingRst && cnt < 2);
            if (transport == null || !transport.IsOpen)
            {
                if (!Init())
                {
                    throw new Exception("服务器连接失败!");
                }
            }

        }

        /// <summary>
        /// 登录接口调用
        /// </summary>
        /// <param name="varOutLetId">终端编号</param>
        /// <param name="varUserId">用户编号</param>
        /// <param name="varPassword">用户密码</param>
        /// <Remark>郝毅志，2017/06/16</ Remark > 
        public LOGIN_USER_INFO LoginRpc(Int32 varClientId, String varUserId, String varPassword)
        {
            LOGIN_USER_INFO tmpLoginUserInfo = new LOGIN_USER_INFO();
            checkConn();
            try
            {
                tmpLoginUserInfo = SercurityClient.login(varClientId, varUserId, varPassword);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpLoginUserInfo;
        }


        /// <summary>
        /// 修改密码接口调用
        /// </summary>
        /// <param name="varOldPassword">旧密码</param>
        /// <param name="varNewPassword">新密码</param>
        /// <Remark>郝毅志，2017/06/17</ Remark > 
        public bool ChangePasswordRpc(String varUserId,String varOldPassword, String varNewPassword)
        {
            bool tmpIsSuccess = false;
            checkConn();
            try
            {  
                tmpIsSuccess = SercurityClient.changePass(_auth, varUserId, varOldPassword, varNewPassword);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpIsSuccess;
        }

        /// <summary>
        /// 自营票票号是否连续接口调用
        /// </summary>
        /// <param name="auth">客户端信息</param>
        /// <param name="begin_ticket_id">起始票号</param>
        /// <param name="ticket_num">数量</param>
        /// <param name="end_ticket_id">终止票号</param>
        /// <returns></returns>
        /// <Remark>王建立，2017/06/17</ Remark > 
        public bool CheckTicketNumberIsContinuity(Int64 begin_ticket_id, Int64 ticket_num, Int64 end_ticket_id)
        {
            bool IsResult = false;
            checkConn();
            try
            {
                IsResult = SaleClient.checkSaleTicketBYzy(_auth, begin_ticket_id, ticket_num, end_ticket_id);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }

        /// <summary>
        /// 查询团体申请单信息
        /// </summary>
        /// <param name="apply_id">申请单号</param>
        /// <returns></returns>
        public SL_TEAM_ORDER SearchTeamTicketID(string apply_id)
        {
            SL_TEAM_ORDER slteamorder = new SL_TEAM_ORDER();
            checkConn();
            try
            {
                slteamorder = SaleClient.teamOrderQuery(_auth, apply_id);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return slteamorder;

        }

        /// <summary>
        /// 从服务器端获取网络代理商信息
        /// </summary>
        /// <param name="orgType"></param>
        /// <param name="outletId"></param>
        /// <returns></returns>
        public List<SL_ORG> GetSL_ORGInfo(string orgType, long outletId)
        {
            List<SL_ORG> listslorg = new List<SL_ORG>();
            checkConn();
            try
            {
                listslorg = SaleClient.querySlOrg(_auth, orgType, outletId);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return listslorg;
        }

        /// <summary>
        /// 上传网络代票售票信息
        /// </summary>
        /// <param name="slnet">网代实体</param>
        /// <returns></returns>
        public bool NetTicketRecordTOUP(SL_NETAGENT_ORDER slnet)
        {
            bool isok = false;
            checkConn();
            try
            {
                isok = SaleClient.changeTicket(_auth, slnet);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return isok;
        }

        /// <summary>
        /// 上传客户端售票信息
        /// </summary>
        /// <param name="auth">客户端信息</param>
        /// <param name="upSL_Order">要上传的售票实体</param>
        /// <returns></returns>
        /// <Remark>王建立，2017/06/17</ Remark > 
        public bool UPSellTicketRecord(List<SL_ORDER> upSL_Order)
        {
            bool IsResult = false;
            checkConn();
            try
            {
                IsResult = SaleClient.saleTicket(_auth, upSL_Order);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }


        /// <summary>
        /// 门票配送申请接口调用
        /// </summary>
        /// <param name="auth">通用参数</param>
        /// <param name="varDeliveryApply">配送申请信息</param>
        /// <Remark>郝毅志，2017/06/17</ Remark > 
        public bool DeliveryApplyRpc(STR_DELIVERY_APPLY varDeliveryApply)
        {
            bool tmpIsSuccess = false;
            checkConn();
            try
            {
                tmpIsSuccess = SaleClient.ticketApply(_auth, varDeliveryApply);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpIsSuccess;
        }

        /// <summary>
        /// 配送申请查询接口调用
        /// </summary>
        /// <param name="auth">通用参数</param>
        /// <param name="varBeginDate">起始申请时间</param>  
        /// <param name="varEndDate">终止申请时间</param>  
        /// <param name="varExamState">审核状态</param>  
        /// <returns>配送申请实体</returns>  
        /// <Remark>郝毅志，2017/06/19</ Remark > 
        public List<STR_DELIVERY_APPLY> DeliveryQueryRpc(
            String varApplyId,
            long varBeginDate,
            long varEndDate,
            String varExamState,
            Int64 varOutletId)
        {
            List<STR_DELIVERY_APPLY> tmpDeviApplyResList = new List<STR_DELIVERY_APPLY>();
            checkConn();
            try
            {
                tmpDeviApplyResList = SaleClient.ticketApplyQuery(
                    _auth, 
                    varApplyId, 
                    varBeginDate, 
                    varEndDate, 
                    varExamState, 
                    varOutletId);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpDeviApplyResList;
        }

        /// <summary>
        /// 售票接口调用
        /// </summary>
        /// <param name="auth">通用参数</param>
        /// <param name="varDeliveryApply">售票表信息</param>
        /// <Remark>王建立，2017/06/17</ Remark > 
        public bool SaleTicketRpc(List<SL_ORDER> varSaleTicket)
        {
            bool tmpIsSuccess = false;
            checkConn();
            try
            {
                tmpIsSuccess = SaleClient.saleTicket(_auth, varSaleTicket);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpIsSuccess;
        }

        /// <summary>
        /// 门票配送确认接口调用
        /// </summary>
        /// <param name="varOldPassword">旧密码</param>
        /// <param name="varNewPassword">新密码</param>
        /// <Remark>郝毅志，2017/06/21</ Remark > 
        public bool ConfirmDeliveryRpc(String varApplyId, String varExamStat)
        {
            bool tmpIsSuccess = false;
            checkConn();
            try
            {
                tmpIsSuccess = SaleClient.ticketConfirm(_auth, varApplyId, varExamStat);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpIsSuccess;
        }

        /// <summary>
        /// 网点员工销售统计表
        /// </summary>
        /// <param name=""></param>  
        /// <param name=""></param>  
        /// <param name=""></param> 
        /// <Remark>郝毅志，2017/06/22</ Remark > 
        public List<RPT_SALE_DATA_USER> SaleDataUserRpc(
            long varDate,
            long varOutLetID)
        {
            List<RPT_SALE_DATA_USER> tmpSaleData = new List<RPT_SALE_DATA_USER>();
            checkConn();
            try
            {
                tmpSaleData = SaleClient.rptSaleDataUser(
                    _auth,
                    varDate,
                    varOutLetID
                    );
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpSaleData;
        }

        /// <summary>
        /// 网点销售统计表
        /// </summary>
        /// <param name=""></param>  
        /// <param name=""></param>  
        /// <param name=""></param> 
        /// <Remark>郝毅志，2017/06/22</ Remark > 
        public List<RPT_SALE_DATA_OUTLET> SaleDataOutletRpc(long varDate, long varOutLetId)
        {
            List<RPT_SALE_DATA_OUTLET> tmpSaleDataOutlet = new List<RPT_SALE_DATA_OUTLET>();
            checkConn();
            try
            {
                tmpSaleDataOutlet = SaleClient.rptSaleDataOutlet(
                    _auth, 
                    varDate, 
                    varOutLetId
                    );
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpSaleDataOutlet;
        }

        /// <summary>
        /// 网点库存统计表
        /// </summary>
        /// <param name=""></param>  
        /// <param name=""></param>  
        /// <param name=""></param> 
        /// <Remark>郝毅志，2017/06/22</ Remark > 
        public List<RPT_STR_DATA> StrDataRpc(long varDate, long varOutLetId)
        {
            List<RPT_STR_DATA> tmpStrData = new List<RPT_STR_DATA>();
            checkConn();
            try
            {
                tmpStrData = SaleClient.rptStrData(
                    _auth,
                    varDate,
                    varOutLetId
                    );
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpStrData;
        }

        /// <summary>
        /// 当日网点库存统计表
        /// </summary>
        /// <param name=""></param>  
        /// <param name=""></param>  
        /// <param name=""></param> 
        /// <Remark>郝毅志，2017/06/22</ Remark > 
        public List<RPT_STR_DATA> StrDataNowRpc(long varOutLetId)
        {
            List<RPT_STR_DATA> tmpData = new List<RPT_STR_DATA>();
            checkConn();
            try
            {
                tmpData = SaleClient.rptStrDataNow(
                    _auth,
                    varOutLetId
                    );
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpData;
        }

        /// <summary>
        /// 上传闸机检票信息
        /// </summary>
        /// <param name="auth">客户端信息</param>
        /// <param name="upSL_Order">要上传的检票实体</param>
        /// <returns></returns>
        /// <Remark></ Remark > 
        public bool UPTicketCheckRecord(List<SL_CHECK> upSL_Check)
        {
            bool IsResult = false;
            checkConn();
            try
            {
                IsResult = SaleClient.checkInfo(_auth, upSL_Check);
            }
            catch (RPCException ex)
            {
                _log.Error("上传检票信息错误", ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error("上传检票信息错误", ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }

        /// <summary>
        /// 员工卡登记表同步下载
        /// </summary>
        /// <param name="tmpVersion">版本号</param>  
        /// <Remark>郝毅志，2017/06/22</ Remark > 
        public  List<SYS_EMP_REGISTER> EmpRegisterSyncRpc(long tmpVersion)
        {
         
            List<SYS_EMP_REGISTER> tmpEmpRegister = new List<SYS_EMP_REGISTER>();
            checkConn();
            try
            {
                tmpEmpRegister = DataSyncClient.empRegisterSnyc(_auth, tmpVersion);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpEmpRegister;
        }

        /// <summary>
        /// 票种表同步下载
        /// </summary>
        /// <param name="tmpVersion">版本号</param>  
        /// <Remark>郝毅志，2017/06/23</ Remark > 
        public  List<SYS_TICKET_TYPE> TicketTypeSyncRpc(long tmpVersion)
        {
            List<SYS_TICKET_TYPE> tmpTicketType = new List<SYS_TICKET_TYPE>();
            checkConn();
            try
            {
                tmpTicketType = DataSyncClient.ticketTypeSnyc(_auth, tmpVersion);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpTicketType;
        }

        /// <summary>
        /// 黑名单表同步下载
        /// </summary>
        /// <param name="tmpVersion">版本号</param>  
        /// <Remark>郝毅志，2017/06/23</ Remark > 
        public  List<SYS_BLACK_LIST> BlackListSyncRpc(long tmpVersion)
        { 
            List<SYS_BLACK_LIST> tmpBlackList = new List<SYS_BLACK_LIST>();
            checkConn();
            try
            {
                tmpBlackList = DataSyncClient.blacklistSnyc(_auth, tmpVersion);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpBlackList;
        }

        /// <summary>
        /// 字典表同步下载
        /// </summary>
        /// <param name="tmpVersion">版本号</param>  
        /// <Remark>郝毅志，2017/06/23</ Remark > 
        public  List<SYS_DICTIONARY> DictionarySyncRpc(long tmpVersion)
        { 
            List<SYS_DICTIONARY> tmpDictionary = new List<SYS_DICTIONARY>();
            checkConn();
            try
            {
                tmpDictionary = DataSyncClient.sysDictionarySnyc(_auth, tmpVersion);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpDictionary;
        }

        /// <summary>
        /// 参数表同步下载
        /// </summary>
        /// <param name="tmpVersion">版本号</param>  
        /// <Remark>郝毅志，2017/06/23</ Remark > 
        public  List<SYS_PAREMETER> ParemeterSyncRpc(long tmpVersion)
        { 
            List<SYS_PAREMETER> tmpParemeter = new List<SYS_PAREMETER>();
            checkConn();
            try
            {
                tmpParemeter = DataSyncClient.sysParemeterSnyc(_auth, tmpVersion);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpParemeter;
        }

        /// <summary>
        /// 预售期信息同步下载
        /// </summary>
        /// <param name="tmpVersion">版本号</param>  
        /// <Remark>郝毅志，2017/06/26</ Remark > 
        public  List<SL_PERIOD> SalePeriodSyncRpc(long tmpVersion)
        {
            List<SL_PERIOD> tmpSalePeriod = new List<SL_PERIOD>();
            checkConn();
            try
            {
                tmpSalePeriod = DataSyncClient.salePeriodQuery(_auth, tmpVersion);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpSalePeriod;
        }

        /// <summary>
        /// 闸机信息上传
        /// </summary>
        /// <param name=""></param>  
        /// <Remark></ Remark > 
        public bool GateStateSnycRpc(SYS_CLIENT varSysClient)
        {
            bool IsResult = false;
            checkConn();
            try
            {
                IsResult = DataSyncClient.gateStateSnyc(_auth, varSysClient);
            }
            catch (RPCException ex)
            {
                _log.Error("上传闸机信息错误", ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error("上传闸机信息错误", ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }

        /// <summary>
        /// 补票信息上传
        /// </summary>
        /// <param name=""></param>  
        /// <Remark></ Remark > 
        public bool SupplyTicketRpc(SL_SUPPLY varSupply)
        {
            bool IsResult = false;
            checkConn();
            try
            {
                IsResult = SaleClient.supplyTicket(_auth, varSupply);
            }
            catch (RPCException ex)
            {
                _log.Error("上传补票信息错误", ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error("上传补票信息错误", ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }

        /// <summary>
        /// 票据信息查询
        /// </summary>
        /// <param name="tmpVersion">版本号</param>  
        /// <Remark>郝毅志，2017/06/23</ Remark > 
        public STR_TICKET_INFO TicketInfoQueryRpc(long ticket_id,long outlet_id)
        {
            STR_TICKET_INFO tmpTicket = new STR_TICKET_INFO();
            checkConn();
            try
            {
                tmpTicket = SaleClient.ticketInfoQuery(_auth, ticket_id, outlet_id);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpTicket;
        }

        /// <summary>
        /// 检票信息查询
        /// </summary>
        /// <param name="tmpVersion">版本号</param>  
        /// <Remark>郝毅志，2017/06/23</ Remark > 
        public List<SL_CHECK> CheckInfoQueryRpc(long ticket_id)
        {
            List<SL_CHECK> tmpCheck = new List<SL_CHECK>();
            checkConn();
            try
            {
                tmpCheck = SaleClient.checkInfoQuery(_auth, ticket_id);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpCheck;
        }

        /// <summary>
        /// 获取服务器时间,精确到秒
        /// </summary>
        /// <param name="tmpVersion">版本号</param>  
        /// <Remark>郝毅志，2017/06/23</ Remark > 
        public long GetSystemDateTimeRpc()
        {
            long systemTime = 0;
            checkConn();
            try
            {
                systemTime = DataSyncClient.getSystemDateTime(_auth);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return systemTime;
        }

        /// <summary>
        /// 终端信息查询
        /// </summary>
        /// <param name="tmpVersion">版本号</param>  
        /// <Remark>郝毅志，2017/06/23</ Remark > 
        public List<SYS_CLIENT> QueryClientRpc(
            String varClientId,
            String varClientName,
            String varClientType,
            String varRegionIds)
        {
            List<SYS_CLIENT> tmpClient = new List<SYS_CLIENT>();
            checkConn();
            try
            {
                tmpClient = DataSyncClient.queryClient(
                    _auth,
                    varClientId,
                    varClientName,
                    varClientType,
                    varRegionIds
                    );
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpClient;
        }

        /// <summary>
        /// 心跳包
        /// </summary>

        /// <Remark>郝毅志，2017/06/21</ Remark > 
        public bool pingRpc()
        {
            if (transport == null || !transport.IsOpen)
            {
                Init();
            }
            return SercurityClient.ping(_auth);
            //checkConn();
            //return SercurityClient.ping(_auth);
        }

        /// <summary>
        /// 场馆表同步下载
        /// </summary>        
        /// <returns></returns>
        public List<SYS_VENUE> VebueSyncRpc()
        {
            List<SYS_VENUE> tmpParemeter = new List<SYS_VENUE>();
            checkConn();
            try
            {
                tmpParemeter = DataSyncClient.sysVenueSnyc(_auth);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return tmpParemeter;
        }


        /// <summary>
        /// 请求支付信息上传
        /// </summary>
        /// <param name="sl_order">销售实体</param>
        /// <param name="sl_order_tickettype_detail">销售票种明细实体</param>
        /// <returns></returns>
        public bool saleTicketByZGRPC(SL_ORDER sl_order, SL_ORDER_TICKETTYPE_DETAIL sl_order_tickettype_detail)
        {
            bool IsResult = false;
            checkConn();
            try
            {
                IsResult = SaleClient.saleTicketByZG(_auth, sl_order, sl_order_tickettype_detail);
            }
            catch (RPCException ex)
            {
                _log.Error("支付通讯错误", ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error("支付通讯错误", ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }
        /// <summary>
        /// 身份证购票信息上传支付
        /// </summary>
        /// <param name="sl_order">销售实体</param>
        /// <returns></returns>
        public bool saleTicketByIdenttyZG_RPC(SL_ORDER sl_order)
        {
            bool IsResult = false;
            checkConn();
            try
            {
                IsResult = SaleClient.saleTicketByIdenttyZG(_auth, sl_order);
            }
            catch (RPCException ex)
            {
                _log.Error("身份证购票失败！", ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error("身份证购票失败！", ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }

        /// <summary>
        /// 身份证取票查询
        /// </summary>
        /// <param name="identty_id">身份证号</param>
        /// <returns></returns>
        public List<SL_ORDER_DETAIL> queryTicketByIdenttyId_RPC(string identty_id)
        {
            List<SL_ORDER_DETAIL> listorderdetail = new List<SL_ORDER_DETAIL>();
            checkConn();
            try
            {
                listorderdetail = SaleClient.queryTicketByIdenttyId(_auth, identty_id);
            }
            catch (RPCException ex)
            {
                _log.Error("身份证取票失败！", ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error("身份证取票失败！", ex);
                throw new Exception(ex.Message.ToString());
            }
            return listorderdetail;
        }

        /// <summary>
        /// 查询售票单的支付状态,返回支付状态(1-待支付 2-已支付 3-支付失败),联机,调用6次,每5秒调用一次，非2-已支付 则调用取消交易接口
        /// </summary>
        /// <param name="order_id">销售单号</param>
        /// <returns></returns>
        public int querTicketPayStatus_RPC(string order_id)
        {
            int IsResult = -1;
            checkConn();
            try
            {
                IsResult = SaleClient.querTicketPayStatus(_auth, order_id);
            }
            catch (RPCException ex)
            {
                _log.Error("查询支付状态失败！", ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error("查询支付状态失败！", ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }

        /// <summary>
        /// 撤消交易,支付超时或未知状态,并通过查询确认后调用
        /// </summary>
        /// <param name="order_id">销售单号</param>
        /// <returns></returns>
        public bool cancelTicketPay_RPC(string order_id)
        {
            bool IsResult = false;
            checkConn();
            try
            {
                IsResult = SaleClient.cancelTicketPay(_auth, order_id);
            }
            catch (RPCException ex)
            {
                _log.Error("撤销交易失败！", ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error("撤销交易失败！", ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }

        /// <summary>
        /// 出票信息上传-自助售票机 (ZG-自助购票、ZQ-自助取票),联机上传
        /// </summary>
        /// <param name="sl_order_detail_list">销售明细实体</param>
        /// <returns></returns>
        public bool ejectTicket_RPC(List<SL_ORDER_DETAIL> sl_order_detail_list)
        {
             bool IsResult = false;
            checkConn();
            try
            {
                IsResult = SaleClient.ejectTicket(_auth, sl_order_detail_list);
            }
            catch (RPCException ex)
            {
                _log.Error("出票信息上传失败！", ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error("出票信息上传失败！", ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }


        /// <summary>
        /// 自助售票机状态和票数同步,联机上传
        /// </summary>
        /// <param name="auth">通用参数</param>
        /// <param name="state">状态</param>
        /// <param name="ticket_num">票数</param>
        public bool ejectTicketStatSync_RPC(int state, string ticket_num)
        {
            bool IsResult = false;
            checkConn();
            try
            {
                IsResult =DataSyncClient.ejectTicketStatSync(_auth, state, ticket_num);
            }
            catch (RPCException ex)
            {
                _log.Error("运行状态同步失败！", ex);
                throw new Exception("错误码[" + ex.ErrorCode + "] " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error("运行状态同步失败！", ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }




        /// <summary>
        /// 日志上传
        /// </summary>        
        /// <returns></returns>
        public bool UploadClientLogRpc(string fileName, byte[] file)
        {
            bool IsResult = false;
            checkConn();
            try
            {
                IsResult = DataSyncClient.uploadClientLog(_auth, fileName, file);
            }
            catch (RPCException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("错误码[" + ex.ErrorCode + "]  " + "错误信息[" + ex.ErrorMess + "]");
            }
            catch (System.IO.IOException ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception("服务器连接超时,请稍后重新操作!");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_RpcClient), ex);
                throw new Exception(ex.Message.ToString());
            }
            return IsResult;
        }

    }
}
