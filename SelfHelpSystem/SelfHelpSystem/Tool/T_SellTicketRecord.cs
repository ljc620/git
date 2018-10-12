using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using tbims.rpc.entity;
using System.Data;
using System.Data.SQLite;
using SelfHelpSystem.DAL;
using log4net;
using SelfHelpSystem.Model;


namespace SelfHelpSystem.Tool
{
    /// <summary>
    /// 售票记录类
    /// </summary>
     public class T_SellTicketRecord
    {
         private static ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");
         public const string str_Apption = "系统提示";      //弹框提示标题
        public  const string LocalTicket_Prefix_ZG = "ZG";     //自助购票     UID前缀
        public  const string LocalTicketMX_Prefix_ZG = "ZGMX";     //自助购票明细     UID前缀

        public const string LocalTicket_Prefix_ZQ = "ZQ";     //自助取票     UID前缀
        public const string LocalTicketMX_Prefix_ZQ = "ZQMX";     //自助取票明细     UID前缀

        public  const string LocalPayType_Prefix = "ZFFS";  //支付方式表 
        public  static Int32 MaxTicketCount = 0;//单次最大购票数量
        public  static Int32 MinTicketCount = 1;//单次最小购票数量
         /// <summary>
         /// 销售单主表
         /// </summary>
        public static SL_ORDER slorder = new SL_ORDER();
         /// <summary>
         /// 销售明细
         /// </summary>
        public static List<SL_ORDER_DETAIL> listdetial = new List<SL_ORDER_DETAIL>();

         /// <summary>
         /// 支付方式
         /// </summary>
         public static SL_PAY_TYPE slpaytype = new SL_PAY_TYPE();


         /// <summary>
         /// 生成销售单号
         /// </summary>
         /// <param name="ClientID">客户端ID</param>
         /// <param name="Rule">规则前缀</param>
         /// <param name="indx">序员</param>
         /// <returns></returns>
         public static string CreateTableUID(Int64 ClientID, string Rule, int indx)
         {
             string strUID = "";             
             strUID = Convert.ToInt32(ClientID) + Rule + DateTime.Now.ToString("yyyyMMddHHmmss") + indx.ToString();
             return strUID;

         }

         /// <summary>
         /// 验证当前票箱内的票数是否已达报警数量   到达报警设置后是不售票呢？还是给票务中心发信息？
         /// </summary>
         /// <param name="tickettypeid">票种</param>
         /// <param name="salecount">当前销售数量</param>
         /// <returns></returns>
         public static bool Judge_TicketAlarmCount(string tickettypeid, Int32 salecount)// string tickettypeid,
         {
             bool isok = true;
             try
             {
                 string str = "select * from sl_ticketCount where surplusCount>alarmCount+" + salecount.ToString() + " and ticket_type_id='" + tickettypeid + "'";// + "' and ticket_type_Id='" + tickettypeid 
                 SQLiteDataAdapter ada = new SQLiteDataAdapter(str, SQLiteHelper.LocalDbConnectionString);
                 DataSet ds = new DataSet();
                 ada.Fill(ds);
                 if (ds.Tables[0].Rows.Count > 0)
                 {
                     isok = false;
                 }
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             return isok;
         }

         /// <summary>
         /// 判断剩余票是否满足当前所购票数   
         /// </summary>
         /// <param name="boxnumber">箱号</param>
         /// <param name="tickettypeid">票种</param>
         /// <param name="ticketcount">购票数量</param>
         /// <param name="alarmcount">报警票数</param>
         /// <returns></returns>
         public static bool Judge_TicketCount(string boxnumber, string tickettypeid, int ticketcount,int alarmcount)
         {
             bool isok = false;
             try
             {
                 SQLiteDataAdapter ada = new SQLiteDataAdapter("select * from sl_ticketCount where ticket_type_Id='" + tickettypeid + "' and boxNumber='" + boxnumber + "'", SQLiteHelper.LocalDbConnectionString);
                 DataSet ds = new DataSet();
                 ada.Fill(ds);
                 if (ds.Tables[0].Rows.Count > 0)
                 {
                     if (ticketcount - alarmcount >Convert.ToInt32(ds.Tables[0].Rows[0]["surplusCount"]))
                     {
                         isok = true;
                     }
                 }
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             return isok;
         }

         /// <summary>
         /// 返回票箱中余票及报警票数等信息
         /// </summary>
         /// <returns></returns>
         public static DataTable Get_BoxTicketInfo()
         {
             DataTable dt = new DataTable();
             try
             {
                 SQLiteDataAdapter ada = new SQLiteDataAdapter("select * from sl_ticketCount where boxNumber='A' or boxNumber='B' order by boxNumber asc", SQLiteHelper.LocalDbConnectionString);
                 DataSet ds = new DataSet();
                 ada.Fill(ds);
                 if (ds.Tables[0].Rows.Count > 0)
                 {
                     dt = ds.Tables[0];
                 }
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             return dt;
         }

         /// <summary>
         /// 根据票种返回票种名称
         /// </summary>
         /// <param name="tickettypeid">票种</param>
         /// <returns></returns>
         public static string GetTicketTypeName(string tickettypeid)
         {
             string str = "";
             try
             {
                 SQLiteDataAdapter ada = new SQLiteDataAdapter("select *  from SYS_TICKET_TYPE  where TICKET_TYPE_ID='" + tickettypeid + "'", SQLiteHelper.LocalDbConnectionString);
                 DataSet ds = new DataSet();
                 ada.Fill(ds);
                 if (ds.Tables[0].Rows.Count > 0)
                 {
                     str = ds.Tables[0].Rows[0]["TICKET_TYPE_NAME"].ToString();
                 }
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             return str;
         }

         /// <summary>
         /// 返回指定票种的票种实体
         /// </summary>
         /// <param name="tickettypeid">票种</param>
         /// <returns></returns>
         public static SYS_TICKET_TYPE GetTicketInfo(string tickettypeid)
         {
             SYS_TICKET_TYPE systickettype = new SYS_TICKET_TYPE();
             try
             {
                 SQLiteDataAdapter ada = new SQLiteDataAdapter("select *  from SYS_TICKET_TYPE  where TICKET_TYPE_ID='" + tickettypeid + "'", SQLiteHelper.LocalDbConnectionString);
                 DataSet ds = new DataSet();
                 ada.Fill(ds);
                 if (ds.Tables[0].Rows.Count > 0)
                 {
                     systickettype.TicketTypeId = ds.Tables[0].Rows[0]["TICKET_TYPE_ID"].ToString();//票种编号
                     systickettype.TicketTypeName = ds.Tables[0].Rows[0]["TICKET_TYPE_NAME"].ToString();//票种名称
                     systickettype.TeamFlag = ds.Tables[0].Rows[0]["TEAM_FLAG"].ToString();//是否团体
                     systickettype.ValidateTimes = Convert.ToInt64(ds.Tables[0].Rows[0]["VALIDATE_TIMES"]);//可用次数
                     systickettype.LessFlag = ds.Tables[0].Rows[0]["LESS_FLAG"].ToString();//是否优惠
                     systickettype.DayNightFlag = ds.Tables[0].Rows[0]["DAY_NIGHT_FLAG"].ToString();//日夜场
                     systickettype.DayValidateFlag = ds.Tables[0].Rows[0]["DAY_VALIDATE_FLAG"].ToString();//是否销售日当日有效
                     systickettype.Price = Convert.ToInt64(ds.Tables[0].Rows[0]["PRICE"]);//票价
                 }
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             return systickettype;
         }


         /// <summary>
         /// 返回票种指定日期内的票价
         /// </summary>
         /// <param name="ticket_type_ID">票种</param>
         /// <returns></returns>
         public static Int32 Get_sl_period_Price(string ticket_type_ID)
         {
             Int32 re_price = -1;
             try
             {
                 string str = " select * from sl_period where  ticket_type_id='" + ticket_type_ID + "' and strftime('%Y-%m-%d %H:%M:%S','now','localtime') >=begin_dt and strftime('%Y-%m-%d','now','localtime')<=end_dt";
                 SQLiteCommand cmd = new SQLiteCommand(str);
                 DataSet ds = new DataSet();
                 ds = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, cmd);
                 if (ds.Tables[0].Rows.Count > 0)
                 {
                     re_price = Convert.ToInt32(ds.Tables[0].Rows[0]["discount_price"]);
                 }
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             return re_price;
         }

         /// <summary>
         /// 支付成功后写入临时销售主表  为防断电等各种异常情况
         /// </summary>
         /// <param name="slorder">销售单实体</param>
         /// <returns></returns>
         public static bool Inert_SL_ORDER_Temp(SL_ORDER slorder)
         {
             bool isok = false;
             try
             {
                 string str = "insert into SL_ORDER_Temp(ORDER_ID,ORDER_TYPE,TICKET_COUNT,DUE_SUM,REAL_SUM,SALE_USER_ID,SALE_TIME,VERSION_NO,REMARK,PAY_STAT)" +
                              " values(@ORDER_ID,@ORDER_TYPE,@TICKET_COUNT,@DUE_SUM,@REAL_SUM,@SALE_USER_ID,@SALE_TIME,@VERSION_NO,@REMARK,@PAY_STAT)";

                 SQLiteCommand cmd = new SQLiteCommand(str);
                 cmd.Parameters.Add("ORDER_ID", DbType.String);
                 cmd.Parameters.Add("ORDER_TYPE", DbType.String);
                 cmd.Parameters.Add("TICKET_COUNT", DbType.Int32);
                 cmd.Parameters.Add("DUE_SUM", DbType.Int64);
                 cmd.Parameters.Add("REAL_SUM", DbType.Int64);
                 cmd.Parameters.Add("SALE_USER_ID", DbType.String);
                 cmd.Parameters.Add("SALE_TIME", DbType.DateTime);
                 cmd.Parameters.Add("VERSION_NO", DbType.DateTime);
                 cmd.Parameters.Add("REMARK", DbType.String);
                 cmd.Parameters.Add("PAY_STAT", DbType.String);
                 cmd.Parameters["ORDER_ID"].Value = slorder.OrderId;//单号
                 cmd.Parameters["ORDER_TYPE"].Value = slorder.OrderType;//销售类型  自助购票
                 cmd.Parameters["TICKET_COUNT"].Value = slorder.TicketCount;//票数
                 cmd.Parameters["DUE_SUM"].Value = slorder.DueSum;//应收金额
                 cmd.Parameters["REAL_SUM"].Value = slorder.RealSum;//实收金额
                 cmd.Parameters["SALE_USER_ID"].Value = slorder.SaleUserId;//售票人 改为保存终端编号
                 cmd.Parameters["SALE_TIME"].Value = T_Date.ConvertLongToDateTime(slorder.SaleTime);//销售时间
                 cmd.Parameters["VERSION_NO"].Value =T_Date.ConvertLongToDateTime(slorder.VersionNo);//版本号
                 cmd.Parameters["REMARK"].Value = slorder.Remark;//订单说明
                 cmd.Parameters["PAY_STAT"].Value = slorder.PayStat;//支付状态 1待支付 2已支付 3支付失败
                 SQLiteHelper.ExecuteNonQuery(SQLiteHelper.LocalDbConnectionString, cmd);
                 isok = true;
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             return isok;
         }

         /// <summary>
         /// 支付成功后 向销售主表 支付表写入数据
         /// </summary>
         /// <param name="selfhelpcalss">自助售票类</param>
         /// <param name="selltype">0表示销售主表 1表示支付表</param>
         /// <param name="paystat">支付状态(1-待支付 2-已支付 3-支付失败)</param>
         /// <returns></returns>
         public static bool Insert_GP_Data(M_SelfHelpClass selfhelpcalss)//int selltype,,int paystat
         {
             bool isok = false;
             string strsell = "";
             string strpay="";
             SQLiteCommand cmdsell = new SQLiteCommand();
             SQLiteCommand cmdpay = new SQLiteCommand();

             strsell = " insert into SL_ORDER(ORDER_ID,ORDER_TYPE,TICKET_COUNT,DUE_SUM,REAL_SUM,SALE_USER_ID,SALE_TIME,VERSION_NO,REMARK,PAY_STAT)" +
                       " values('" + selfhelpcalss.OrderID + "','" + LocalTicket_Prefix_ZG + "','" + selfhelpcalss.TicketCount.ToString() + "','" + selfhelpcalss.DueSum.ToString() + "','" +
                         selfhelpcalss.RealSum.ToString() + "','" + M_Configuration.CLIENTID.ToString() + "',DateTime('now','localtime'),DateTime('now','localtime'),'自助售票','" + selfhelpcalss.PayStat + "')";

             strpay = " insert into SL_PAY_TYPE(PAY_TYPE_ID,ORDER_ID,PAY_TYPE,AMT,VERSION_NO) " +
                      " values('" + selfhelpcalss.PayTypeID + "','" + selfhelpcalss.OrderID + "','" + selfhelpcalss.PayType + "','" + selfhelpcalss.RealSum.ToString() + "',DateTime('now','localtime')) ";
             
             //***************起事物写入销售主表与支付表*************
             using (SQLiteConnection conn = new SQLiteConnection(SQLiteHelper.LocalDbConnectionString))
             {
                 conn.Open();
                 SQLiteTransaction tran = conn.BeginTransaction();
                 try
                 {
                     cmdsell.CommandText = strsell;
                     cmdsell.Connection = conn;
                     cmdsell.ExecuteNonQuery();

                     cmdpay.CommandText = strpay;
                     cmdpay.Connection = conn;
                     cmdpay.ExecuteNonQuery();

                     tran.Commit();
                     isok = true;
                 }
                 catch (Exception ex)
                 {
                     conn.Close();
                     tran.Rollback();
                     _log.Error(typeof(T_SellTicketRecord), ex);
                 }
                 finally
                 {
                     tran.Dispose();
                 }
             }
            //***************起事物写入销售主表与支付表*************

             return isok;
         }


         /// <summary>
         /// 将售票信息写入SL_ORDER_DETAIL_表中
         /// </summary>
         /// <param name="ticketType">票种实体</param>
         /// <param name="ticketid">票号</param>
         /// <param name="selltype">0自助购票 1自助取票</param>
         /// <param name="selfhelpclass">自助售票类</param>
         /// <param name="index">取票时表示明细的序号</param>
         /// <returns></returns>
         public static bool Insert_SL_ORDER_DETAIL(M_TicketType ticketType, string ticketid, int selltype,M_SelfHelpClass selfhelpclass,string XiaoMX,int index)
         {
             bool isok = false;
             try
             {
                string strIn = "";
                if (selltype == 0)//购票
                {
                    string strOutLet = Convert.ToString(M_Configuration.CLIENTID).Substring(1, 3);//网点编号
                    strIn = "insert into SL_ORDER_DETAIL(order_detail_id,order_id,ticket_class,ticket_id," +
                             "ticket_type_id,validate_times,remain_times,original_price,sale_price," +
                             "check_flag,useless_flag,ope_user_id,ope_time,version_no,SellType,OUTLET_ID,CLIENT_ID,EJECT_USER_ID,EJECT_TICKET_STAT,EJECT_TICKET_TIME)" +
                             " values('" + XiaoMX + "','" + selfhelpclass.OrderID + "','1','" + ticketid + "','" + selfhelpclass.TicketTypeID + "','"
                             + ticketType.VALIDATE_TIMES.ToString() + "'," + ticketType.VALIDATE_TIMES.ToString() + ",'" + selfhelpclass.OriginalPrice.ToString() + "','" + selfhelpclass.SalePrice.ToString() + "'," +
                             " 'N','N','" + M_Configuration.CLIENTID.ToString() + "',DateTime('now','localtime'),DateTime('now','localtime'),'" + selltype.ToString() + "','" + strOutLet + "','" +
                             M_Configuration.CLIENTID.ToString() + "','" + M_Configuration.CLIENTID.ToString() + "','2','" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + "') ";
                }
                else if (selltype == 1)//取票
                {
                    strIn = "insert into SL_ORDER_DETAIL(order_detail_id,order_id,ticket_class,ticket_id," +
                            "ticket_type_id,validate_times,remain_times,original_price,sale_price," +
                            "check_flag,useless_flag,ope_user_id,ope_time,version_no,SellType,OUTLET_ID,CLIENT_ID,EJECT_USER_ID,EJECT_TICKET_STAT,EJECT_TICKET_TIME)" +
                            " values('" + selfhelpclass.listorderdetail[index - 1].OrderDetailId + "','" + selfhelpclass.listorderdetail[index - 1].OrderId + "','2','" + ticketid + "','" + selfhelpclass.listorderdetail[index - 1].TicketTypeId + "','"
                            + ticketType.VALIDATE_TIMES.ToString() + "'," + ticketType.VALIDATE_TIMES.ToString() + ",'" + selfhelpclass.OriginalPrice.ToString() + "','" + selfhelpclass.SalePrice.ToString() + "'," +
                            " 'N','N','" + M_Configuration.CLIENTID.ToString() + "',DateTime('now','localtime'),DateTime('now','localtime'),'" + selltype.ToString() + "','" + selfhelpclass.OrderID + "','" +
                            M_Configuration.CLIENTID.ToString() + "','" + M_Configuration.CLIENTID.ToString() + "','2','" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + "') ";
                }
                SQLiteCommand cmd = new SQLiteCommand(strIn);
                SQLiteHelper.ExecuteNonQuery(SQLiteHelper.LocalDbConnectionString, cmd);
                isok = true;
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             return isok;
         }


         /// <summary>
         /// 时时更新指定票箱的余票
         /// </summary>
         /// <param name="boxNumber">票箱号</param>
         /// <returns></returns>
         public static bool Update_SL_TicketCount(string boxNumber)
         {
             bool isok = false;
             try
             {
                 string str = "update  SL_TicketCount set surplusCount=surplusCount-1 where boxNumber='" + boxNumber + "'";
                 SQLiteCommand cmd = new SQLiteCommand(str);
                 int i=SQLiteHelper.ExecuteNonQuery(SQLiteHelper.LocalDbConnectionString,cmd);
                 if (i > 0)
                 {
                     isok = true;
                 }
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             return isok;
         }

         /// <summary>
         /// 返回票箱内的全体票数
         /// </summary>
         /// <returns></returns>
         public static Int32 GetCount_SL_TicketCount()
         {
             Int32 reCount = 0;
             try
             {
                 string str = "select sum(ifnull(surplusCount,0))-sum(ifnull(alarmCount,0))as surplusCount from sl_Ticketcount";
                 DataSet ds = new DataSet();
                 SQLiteDataAdapter ada = new SQLiteDataAdapter(str,SQLiteHelper.LocalDbConnectionString);
                 ada.Fill(ds);
                 reCount = Convert.ToInt32(ds.Tables[0].Rows[0]["surplusCount"]);
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }

             return reCount;
         }


         /// <summary>
         /// 票箱加票时更新票箱余票
         /// </summary>
         /// <param name="boxnumber">票箱号</param>
         /// <param name="surplusCount">余票</param>
         /// <param name="alarmCount">报警数</param>
         /// <param name="ischang">是否更改票种 0没有更改 1更改</param>
         /// <returns></returns>
         public static bool UpdateCount_SL_TicketCount(string boxnumber, string ticketTypeID, Int32 surplusCount, Int32 alarmCount,string ischang)
         {
             bool isok = false;
             try
             {
                 string str = "";

                    if (ischang == "0")//票种未更改的
                    {
                        str = " update SL_TicketCount set ticket_type_id='"+ ticketTypeID + "', surplusCount=surplusCount+'" + surplusCount.ToString() + "',alarmCount='" + alarmCount.ToString() + "' where boxNumber='" + boxnumber + "' ";
                    }
                    else//更改票种的
                    {
                        str = " update SL_TicketCount set ticket_type_id='" + ticketTypeID + "', surplusCount='" + surplusCount.ToString() + "',alarmCount='" + alarmCount.ToString() + "' where boxNumber='" + boxnumber + "' ";
                    }

                 SQLiteCommand cmd = new SQLiteCommand(str);
                 int i = SQLiteHelper.ExecuteNonQuery(SQLiteHelper.LocalDbConnectionString, cmd);
                 if (i > 0)
                 {
                     isok = true;
                 }
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             //起事物的
             #region
             /*
             using (SQLiteConnection conn = new SQLiteConnection(SQLiteHelper.LocalDbConnectionString))
             {
                 conn.Open();
                 SQLiteTransaction tran = conn.BeginTransaction();
                 try
                 {
                     cmdsell.CommandText = strsell;
                     cmdsell.Connection = conn;
                     cmdsell.ExecuteNonQuery();



                     tran.Commit();
                     isok = true;
                 }
                 catch (Exception ex)
                 {
                     conn.Close();
                     tran.Rollback();
                     _log.Error(typeof(T_SellTicketRecord), ex);
                 }
                 finally
                 {
                     tran.Dispose();
                 }
             }*/
#endregion
             return isok;
         }

         /// <summary>
         /// 更新废票箱票数
         /// </summary>
         /// <param name="isReSet">票数是否重置 false 不重置 true 重置</param>
         /// <returns></returns>
         public static bool Update_SL_AbolishCount(bool isReSet)
         {
             bool isok = false;
             try
             {
                 string str = " select * from SL_AbolishCount ";
                 DataSet ds = new DataSet();
                 SQLiteDataAdapter ada = new SQLiteDataAdapter(str, SQLiteHelper.LocalDbConnectionString);
                 ada.Fill(ds);
                 if (ds.Tables[0].Rows.Count > 0)//更新
                 {
                     string updatestr = "";
                     if (isReSet == true)
                     {
                         updatestr = "  update SL_AbolishCount set AbolishCount=0,AbolishDateTime='" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + "'";
                     }
                     else//不重置的
                     {
                         updatestr = "  update SL_AbolishCount set AbolishCount=AbolishCount+1,AbolishDateTime='" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + "'";
                     }
                     
                     SQLiteCommand cmd = new SQLiteCommand(updatestr);
                     int i = SQLiteHelper.ExecuteNonQuery(SQLiteHelper.LocalDbConnectionString, cmd);
                     if (i > 0)
                     {
                         isok = true;
                     }
                 }
                 else//写入
                 {
                     string updatestr = "";
                     if (isReSet == true)//重置
                     {
                         updatestr = "insert into SL_AbolishCount(boxNumber,AbolishCount,AbolishDateTime) Values('C',0,DateTime('now','localtime')) ";
                     }
                     else//不重置
                     {
                         updatestr = "insert into SL_AbolishCount(boxNumber,AbolishCount,AbolishDateTime) Values('C',1,DateTime('now','localtime')) ";
                     }
                     
                     SQLiteCommand cmd = new SQLiteCommand(updatestr);
                     int i = SQLiteHelper.ExecuteNonQuery(SQLiteHelper.LocalDbConnectionString, cmd);
                     if (i > 0)
                     {
                         isok = true;
                     }
                 }

             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             return isok;
 
         }

         /// <summary>
         /// 获取废票箱内票数
         /// </summary>
         /// <returns></returns>
         public static Int64 GetCount_SL_AbolishCount()
         {
             Int64 iscount = 0;
             try
             {
                 string str = " select * from SL_AbolishCount ";
                 DataSet ds = new DataSet();
                 SQLiteDataAdapter ada = new SQLiteDataAdapter(str, SQLiteHelper.LocalDbConnectionString);
                 ada.Fill(ds);
                 if (ds.Tables[0].Rows.Count > 0)//更新
                 {
                     iscount = Convert.ToInt64(ds.Tables[0].Rows[0]["AbolishCount"]);
                 }
                 else
                 {
                     iscount = 0;
                 }

             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_SellTicketRecord), ex);
             }
             return iscount;
         }

    }
}
