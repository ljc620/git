using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Data.SQLite;
using tbims.rpc.entity;
using log4net;
using SelfHelpSystem.Tool;
using SelfHelpSystem.DAL;
using SelfHelpSystem.Model;

namespace SelfHelpSystem.BLL
{
    public class B_SellTicketUpData
    {
        //日志实例化
        private static ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");
        public static bool Is_UPOK = false;

        /// <summary>
        /// 支付前上传销售主表,支付方式表及销售单票种明细表 三个表的数据
        /// </summary>
        /// <param name="SaleClient">RPC</param>
        /// <param name="XiaoNumber">销售单号</param>
        /// <param name="selfhelpcalss">自助售票类</param>
        /// <returns></returns>
        public static bool SellTicketDataUP(T_RpcClient SaleClient, M_SelfHelpClass selfhelpcalss)//string XiaoNumber,
        {
            bool isresult = false;
            try
            {
                //1.创建实体
                SL_ORDER slorder = new SL_ORDER();//销售主表
                SL_ORDER_TICKETTYPE_DETAIL slorderticket = new SL_ORDER_TICKETTYPE_DETAIL();//销售单票种明细表
                List<SL_PAY_TYPE> listsl_pay_type = new List<SL_PAY_TYPE>();
                SL_PAY_TYPE slpaytype = new SL_PAY_TYPE();//支付方式表

                //2.根据单号获取要上传的销售主表信息
                string strOrder = "select * from SL_ORDER where ORDER_ID='" + selfhelpcalss.OrderID + "'";
                SQLiteCommand cmdOrder = new SQLiteCommand(strOrder);
                DataSet dsOrder = new DataSet();
                dsOrder = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, cmdOrder);
                if (dsOrder.Tables[0].Rows.Count > 0)
                {
                    slorder.OrderId = dsOrder.Tables[0].Rows[0]["ORDER_ID"].ToString();//销售单号
                    slorder.OrderType = dsOrder.Tables[0].Rows[0]["ORDER_TYPE"].ToString();//销售类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票)
                    slorder.TicketCount = Convert.ToInt64(dsOrder.Tables[0].Rows[0]["TICKET_COUNT"]);//销售张数
                    slorder.DueSum = Convert.ToInt64(dsOrder.Tables[0].Rows[0]["DUE_SUM"]);//应收金额
                    slorder.RealSum = Convert.ToInt64(dsOrder.Tables[0].Rows[0]["REAL_SUM"]);//实收金额
                    slorder.Remark = dsOrder.Tables[0].Rows[0]["REMARK"].ToString();//订单说明
                    slorder.PayStat = dsOrder.Tables[0].Rows[0]["PAY_STAT"].ToString();//支付状态
                    slorder.SaleUserId = dsOrder.Tables[0].Rows[0]["SALE_USER_ID"].ToString();//出票人 在此指终端号
                    slorder.SaleTime = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsOrder.Tables[0].Rows[0]["SALE_TIME"]));//售票时间
                    slorder.VersionNo = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsOrder.Tables[0].Rows[0]["VERSION_NO"]));//版本号
                    //2.根据销售单号获取支付信息
                    string strPay = "select * from SL_PAY_TYPE where ORDER_ID='" + selfhelpcalss.OrderID + "'";
                    SQLiteCommand cmdPay = new SQLiteCommand(strPay);
                    DataSet dsPay = new DataSet();
                    dsPay = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, cmdPay);
                    slpaytype.PayTypeId = dsPay.Tables[0].Rows[0]["PAY_TYPE_ID"].ToString();//支付单号
                    slpaytype.OrderId = dsPay.Tables[0].Rows[0]["ORDER_ID"].ToString();//销售单号
                    slpaytype.PayType = dsPay.Tables[0].Rows[0]["PAY_TYPE"].ToString();//支付方式
                    slpaytype.Amt = Convert.ToInt64(dsPay.Tables[0].Rows[0]["AMT"]);//支付金额
                    slpaytype.PayId = "";//第三方支付单号
                    slpaytype.PaymentCode = selfhelpcalss.PayodNo;   //"283324266658117012";//130266842860345964//微信
                    slpaytype.VersionNo = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsPay.Tables[0].Rows[0]["VERSION_NO"]));//版本号
                    listsl_pay_type.Add(slpaytype);
                    slorder.SlPayTypelist = listsl_pay_type;//支付方式
                    //3.销售单票种明细表
                    slorderticket.OrderId = dsOrder.Tables[0].Rows[0]["ORDER_ID"].ToString();//销售单号
                    slorderticket.TicketTypeId = selfhelpcalss.TicketTypeID;//票种 
                    slorderticket.TicketCount = Convert.ToInt64(dsOrder.Tables[0].Rows[0]["TICKET_COUNT"]);//销售张数
                    slorderticket.EjectTicketCount = 0;//出票张数
                }
                if (slorder != null)
                {
                    isresult = SaleClient.saleTicketByZGRPC( slorder, slorderticket);
                }
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_SellTicketUpData), ex);
                throw new Exception(ex.Message.ToString());
            }
            return isresult;
        }

        /// <summary>
        /// 上传身份证购票的信息数据
        /// </summary>
        /// <param name="SaleClient">RPC</param>
        /// <param name="XiaoNumber">销售单号</param>
        /// <returns></returns>
        public static bool SellTicketDataUP_IDCard(T_RpcClient SaleClient, string XiaoNumber,M_SelfHelpClass selfhelpclass)
        {
            bool isresult = false;
            M_TicketType mticketType;//票种实体
            string XiaoMX = "";//明细单号
            try
            {
                //1.创建实体
                SL_ORDER slorder = new SL_ORDER();//销售主表
                SL_ORDER_TICKETTYPE_DETAIL slorderticket = new SL_ORDER_TICKETTYPE_DETAIL();//销售单票种明细表
                List<SL_PAY_TYPE> listsl_pay_type = new List<SL_PAY_TYPE>();
                SL_PAY_TYPE slpaytype = new SL_PAY_TYPE();//支付方式表
                List<SL_ORDER_DETAIL> listorderdetial = new List<SL_ORDER_DETAIL>();
                

                //2.根据单号获取要上传的销售主表信息
                string strOrder = "select * from SL_ORDER where ORDER_ID='" + XiaoNumber + "'";
                SQLiteCommand cmdOrder = new SQLiteCommand(strOrder);
                DataSet dsOrder = new DataSet();
                dsOrder = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, cmdOrder);
                if (dsOrder.Tables[0].Rows.Count > 0)
                {
                    slorder.OrderId = dsOrder.Tables[0].Rows[0]["ORDER_ID"].ToString();//销售单号
                    slorder.OrderType = dsOrder.Tables[0].Rows[0]["ORDER_TYPE"].ToString();//销售类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票)
                    slorder.TicketCount = Convert.ToInt64(dsOrder.Tables[0].Rows[0]["TICKET_COUNT"]);//销售张数
                    slorder.DueSum = Convert.ToInt64(dsOrder.Tables[0].Rows[0]["DUE_SUM"]);//应收金额
                    slorder.RealSum = Convert.ToInt64(dsOrder.Tables[0].Rows[0]["REAL_SUM"]);//实收金额
                    slorder.Remark = dsOrder.Tables[0].Rows[0]["REMARK"].ToString();//订单说明
                    slorder.PayStat = dsOrder.Tables[0].Rows[0]["PAY_STAT"].ToString();//支付状态
                    slorder.SaleUserId = dsOrder.Tables[0].Rows[0]["SALE_USER_ID"].ToString();//出票人 在此指终端号
                    slorder.SaleTime = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsOrder.Tables[0].Rows[0]["SALE_TIME"]));//售票时间
                    slorder.VersionNo = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsOrder.Tables[0].Rows[0]["VERSION_NO"]));//版本号
                    //2.根据销售单号获取支付信息
                    string strPay = "select * from SL_PAY_TYPE where ORDER_ID='" + XiaoNumber + "'";
                    SQLiteCommand cmdPay = new SQLiteCommand(strPay);
                    DataSet dsPay = new DataSet();
                    dsPay = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, cmdPay);
                    slpaytype.PayTypeId = dsPay.Tables[0].Rows[0]["PAY_TYPE_ID"].ToString();//支付单号
                    slpaytype.OrderId = dsPay.Tables[0].Rows[0]["ORDER_ID"].ToString();//销售单号
                    slpaytype.PayType = dsPay.Tables[0].Rows[0]["PAY_TYPE"].ToString();//支付方式
                    slpaytype.Amt = Convert.ToInt64(dsPay.Tables[0].Rows[0]["AMT"]);//支付金额
                    slpaytype.PayId = "";//第三方支付单号
                    slpaytype.PaymentCode = selfhelpclass.PayodNo;//付款码
                    slpaytype.VersionNo = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsPay.Tables[0].Rows[0]["VERSION_NO"]));//版本号
                    listsl_pay_type.Add(slpaytype);
                    slorder.SlPayTypelist = listsl_pay_type;//支付方式
                    //3.销售单明细表
                    //string strDetail = "select * from SL_PAY_TYPE where ORDER_ID='" + XiaoNumber + "'";
                    //SQLiteCommand cmdMX = new SQLiteCommand(strDetail);
                    //DataSet dsMX = new DataSet();
                    //dsMX = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, cmdMX);
                    //获到票种实体
                    mticketType = D_TicketType.GetTicketTypeInfo(selfhelpclass.TicketTypeID);
                    for (int j = 0; j <= selfhelpclass.TicketCount-1; j++)
                    {
                        XiaoMX = T_SellTicketRecord.CreateTableUID(M_Configuration.CLIENTID, T_SellTicketRecord.LocalTicketMX_Prefix_ZG, j);
                        SL_ORDER_DETAIL slorderdetail = new SL_ORDER_DETAIL();
                        slorderdetail.OrderDetailId = XiaoMX; //dsMX.Tables[0].Rows[j]["Order_detail_id"].ToString();//销售明细单号
                        slorderdetail.OrderId = XiaoNumber; //dsMX.Tables[0].Rows[j]["Order_id"].ToString();//销售单号
                        slorderdetail.TicketClass = "2";//dsMX.Tables[0].Rows[j]["TICKET_CLASS"].ToString();//门票类型(1-FRID、2-身份证、3-二维码)
                        slorderdetail.IdenttyId = selfhelpclass.GetTicketUserNumber; //dsMX.Tables[0].Rows[j]["IDENTTY_ID"].ToString();//身份证号码
                        slorderdetail.TicketId = 0;// Convert.ToInt64(dsMX.Tables[0].Rows[j]["Ticket_id"]);//票号
                        slorderdetail.TicketUid = "";//票种唯一号
                        slorderdetail.TicketTypeId = selfhelpclass.TicketTypeID; //dsMX.Tables[0].Rows[j]["Ticket_type_id"].ToString();//票种编号
                        slorderdetail.ValidateTimes = mticketType.VALIDATE_TIMES;//可用次数
                        slorderdetail.OriginalPrice = selfhelpclass.OriginalPrice;//原单价
                        slorderdetail.SalePrice = selfhelpclass.SalePrice;//销售价
                        slorderdetail.CheckFlag ="N";// 是否检票(Y是N否)
                        slorderdetail.UselessFlag ="N";//是否作废(Y是N否)
                        // slorderdetail.OpeUserId = dsMX.Tables[0].Rows[j]["Ope_user_id"].ToString();//操作人
                        //  slorderdetail.OpeTime = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsMX.Tables[0].Rows[j]["Ope_time"]));//操作时间
                        slorderdetail.OutletId =Convert.ToInt64(M_Configuration.CLIENTID.ToString().Substring(1,3));  //Convert.ToInt64(dsMX.Tables[0].Rows[j]["OUTLET_ID"]);//出票网点编号
                        slorderdetail.ClientId = M_Configuration.CLIENTID; //Convert.ToInt64(dsMX.Tables[0].Rows[j]["CLIENT_ID"]);//出票终端编号
                        //slorderdetail.EjectUserId = dsMX.Tables[0].Rows[j]["EJECT_USER_ID"].ToString();//出票人
                        slorderdetail.EjectTicketStat = "1";//dsMX.Tables[0].Rows[j]["EJECT_TICKET_STAT"].ToString();//出票状态(1-待出票 2-已出票)
                        //slorderdetail.EjectTicketTime = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsMX.Tables[0].Rows[j]["EJECT_TICKET_TIME"]));//出票时间
                        slorderdetail.VersionNo = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsOrder.Tables[0].Rows[0]["VERSION_NO"]));//版本号;//版本号
                        listorderdetial.Add(slorderdetail);
                    }
                    slorder.SlOrderDetaillist = listorderdetial;//销售明细表
                }
                if (slorder != null)
                {
                    isresult = SaleClient.saleTicketByIdenttyZG_RPC(slorder);//身份证购票信息上传
                }
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_SellTicketUpData), ex);
                throw new Exception(ex.Message.ToString());
            }
            return isresult;
        }

        /// <summary>
        /// 自助购票上传明细信息
        /// </summary>
        /// <param name="SaleClient">RPC</param>
        /// <param name="XiaoNumber">销售单号</param>
        /// <param name="TicketID">要上传的票号</param>
        /// <returns></returns>
        public static bool SellTicketDataUP_MX(T_RpcClient SaleClient, string XiaoNumber,M_SelfHelpClass selfhelpclass,string TicketID)
        {
            bool isok = false;
            try
            {
                string strDetail = "";
                List<SL_ORDER_DETAIL> listorderdetial = new List<SL_ORDER_DETAIL>();
                if (selfhelpclass.OperType == "0")//购票
                {
                    strDetail = "select * from SL_ORDER_DETAIL where TICKET_CLASS='1' and ORDER_ID='" + XiaoNumber + "' and TICKET_ID='"+TicketID+"'";
                }
                else if (selfhelpclass.OperType == "1")//取票
                {//身份证取票时 实际返回的销售明细可能是多个销售单号的
                    strDetail = "select * from SL_ORDER_DETAIL where TICKET_CLASS='2' and EJECT_TICKET_STAT='2' and (";
                    string strsel = "";
                    for (int i = 0; i < selfhelpclass.listorderdetail.Count; i++)
                    {
                        strsel = strsel + "or ORDER_ID='" + selfhelpclass.listorderdetail[i].OrderId + "'and TICKET_ID='" + TicketID + "'";
                    }
                    strsel = strsel.Substring(2, strsel.Length - 2);
                    strDetail = strDetail + strsel + ")";
                }
                SQLiteCommand cmdMX = new SQLiteCommand(strDetail);
                DataSet dsMX = new DataSet();
                dsMX = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, cmdMX);
                for (int j = 0; j <= dsMX.Tables[0].Rows.Count - 1; j++)
                {
                    SL_ORDER_DETAIL slorderdetail = new SL_ORDER_DETAIL();
                    slorderdetail.OrderDetailId = dsMX.Tables[0].Rows[j]["Order_detail_id"].ToString();//销售明细单号
                    slorderdetail.OrderId = dsMX.Tables[0].Rows[j]["Order_id"].ToString();//销售单号
                    slorderdetail.TicketClass = dsMX.Tables[0].Rows[j]["TICKET_CLASS"].ToString();//门票类型(1-FRID、2-身份证、3-二维码)
                    slorderdetail.IdenttyId = dsMX.Tables[0].Rows[j]["IDENTTY_ID"].ToString();//身份证号码
                    slorderdetail.TicketId = Convert.ToInt64(dsMX.Tables[0].Rows[j]["Ticket_id"]);//票号
                    slorderdetail.TicketUid = "";//票种唯一号
                    slorderdetail.TicketTypeId = dsMX.Tables[0].Rows[j]["Ticket_type_id"].ToString();//票种编号
                    slorderdetail.ValidateTimes = Convert.ToInt64(dsMX.Tables[0].Rows[j]["Validate_times"]);//可用次数
                    slorderdetail.OriginalPrice = Convert.ToInt64(dsMX.Tables[0].Rows[j]["Original_price"]);//原单价
                    slorderdetail.SalePrice = Convert.ToInt64(dsMX.Tables[0].Rows[j]["Sale_price"]);//销售价
                    slorderdetail.CheckFlag = dsMX.Tables[0].Rows[j]["Check_flag"].ToString();// 是否检票(Y是N否)
                    slorderdetail.UselessFlag = dsMX.Tables[0].Rows[j]["Useless_flag"].ToString();//是否作废(Y是N否)
                    // slorderdetail.OpeUserId = dsMX.Tables[0].Rows[j]["Ope_user_id"].ToString();//操作人
                    //  slorderdetail.OpeTime = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsMX.Tables[0].Rows[j]["Ope_time"]));//操作时间
                    slorderdetail.OutletId = Convert.ToInt64(dsMX.Tables[0].Rows[j]["OUTLET_ID"]);//出票网点编号
                    slorderdetail.ClientId = Convert.ToInt64(dsMX.Tables[0].Rows[j]["CLIENT_ID"]);//出票终端编号
                    slorderdetail.EjectUserId = dsMX.Tables[0].Rows[j]["EJECT_USER_ID"].ToString();//出票人
                    slorderdetail.EjectTicketStat = dsMX.Tables[0].Rows[j]["EJECT_TICKET_STAT"].ToString();//出票状态(1-待出票 2-已出票)
                    slorderdetail.EjectTicketTime = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsMX.Tables[0].Rows[j]["EJECT_TICKET_TIME"]));//出票时间
                    slorderdetail.VersionNo = T_Date.ConvertDataTimeToLong(Convert.ToDateTime(dsMX.Tables[0].Rows[j]["Ope_time"]));//版本号
                    listorderdetial.Add(slorderdetail);
                }

                if (listorderdetial.Count > 0)
                {
                    isok = SaleClient.ejectTicket_RPC(listorderdetial);//出票信息上传
                }
 
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_SellTicketUpData), ex);
            }
            return isok;
        }

        /// <summary>
        ///  查询售票单的支付状态,返回支付状态(1-待支付 2-已支付 3-支付失败),联机,调用6次,每5秒调用一次，非2-已支付 则调用取消交易接口
        /// </summary>
        /// <param name="SaleClient">RPC</param>
        /// <param name="XiaoNumber">销售单号</param>
        /// <returns></returns>
        public static Int32 Search_PayStat(T_RpcClient SaleClient, string XiaoNumber)
        {
            int result=-2;
            try
            {
                result = SaleClient.querTicketPayStatus_RPC(XiaoNumber);
            }
            catch (Exception ex)
            {
                
                _log.Error(typeof(B_SellTicketUpData), ex);
                //throw new Exception(ex.Message.ToString());
            }
            return result;
        }

        /// <summary>
        /// 撤销支付交易
        /// </summary>
        /// <param name="SaleClient">RPC</param>
        /// <param name="XiaoNumber">销售单号</param>
        /// <returns></returns>
        public static bool Cancel_Pay(T_RpcClient SaleClient, string XiaoNumber)
        {
            bool isok = false;
            try
            {
                isok = SaleClient.cancelTicketPay_RPC(XiaoNumber);
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_SellTicketUpData), ex);
            }
            return isok;
        }

        /// <summary>
        /// 身份证取票 发送身份证号返票据明细 
        /// </summary>
        /// <param name="SaleClient"></param>
        /// <param name="idcard"></param>
        /// <returns></returns>
        public static List<SL_ORDER_DETAIL> GetTicketInfo_IDCard(T_RpcClient SaleClient, string idcard)
        {
            List<SL_ORDER_DETAIL> listOrderDetail = new List<SL_ORDER_DETAIL>();
            try
            {
                listOrderDetail = SaleClient.queryTicketByIdenttyId_RPC(idcard);
            }
            catch (Exception ex)
            {
                _log.Error(typeof(B_SellTicketUpData), ex);
            }
            return listOrderDetail;
        }

    }
}
