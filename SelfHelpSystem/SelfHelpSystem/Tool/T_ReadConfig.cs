using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;
using SelfHelpSystem.Model;
using System.Data;
using System.Data.SQLite;
using SelfHelpSystem.DAL;
using SelfHelpSystem.Tool;
using System.Drawing;
using Common;
using log4net;

namespace SelfHelpSystem.Tool
{
    /// <summary>
    /// 系统初始化操作类
    /// </summary>
    class T_ReadConfig
    {
        private static ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");
        /// <summary>
        /// 读取配置文件信息
        /// </summary>
        /// <returns></returns>
        /// <Remark>郝毅志，2017/06/27</ Remark > 
        public static void ReadConfig()
        {
            try {
                M_Configuration.CLIENTID = Int32.Parse(ConfigurationManager.AppSettings["ClientID"].ToString());//终端号
                M_Configuration.TOKEN = ConfigurationManager.AppSettings["Token"].ToString();//授权码
                M_Configuration.HOST = ConfigurationManager.AppSettings["ServerAddress"].ToString();//服务地址
                M_Configuration.PORT = Int32.Parse(ConfigurationManager.AppSettings["ServerPort"].ToString());//端口号
                M_Configuration.TIMEOUT = Int32.Parse(ConfigurationManager.AppSettings["SyncTimeOut"].ToString());//超时
                M_Configuration.TicketTypeID_1 = ConfigurationManager.AppSettings["TicketTypeID_1"].ToString();//票箱票种1
                M_Configuration.TicketTypeID_2 = ConfigurationManager.AppSettings["TicketTypeID_2"].ToString();//票箱票种2
                M_Configuration.SaleMode = Convert.ToInt32(ConfigurationManager.AppSettings["SaleMode"]);//购票模式 0表示出实体票 1表示身份证购票
                M_Configuration.IDCard_IsUse = ConfigurationManager.AppSettings["IDCard_isuse"].ToString();//身份证取票 0表示启用 1表示不启用
                M_Configuration.StartSaleTime = TimeSpan.Parse(ConfigurationManager.AppSettings["StartSaleTime"]);//开始销售时间
                M_Configuration.EndSaleTime = TimeSpan.Parse(ConfigurationManager.AppSettings["EndSaleTime"]);//开始销售时间
                if (ConfigurationManager.AppSettings["SalTicket_Max"].ToString() != "")
                {
                    T_SellTicketRecord.MaxTicketCount = Convert.ToInt32(ConfigurationManager.AppSettings["SalTicket_Max"]);
                }
                else
                {
                    T_SellTicketRecord.MaxTicketCount = 10;
                }
                if (ConfigurationManager.AppSettings["SalTicket_Min"].ToString() != "")
                {
                    T_SellTicketRecord.MinTicketCount = Convert.ToInt32(ConfigurationManager.AppSettings["SalTicket_Min"]);
                }
                else
                {
                    T_SellTicketRecord.MinTicketCount = 1;
                }
                M_Configuration.RfidReadername = ConfigurationManager.AppSettings["RfidReadername"].ToString();//RFID读卡器
                M_Configuration.Samreadername = ConfigurationManager.AppSettings["Samreadername"].ToString();//SAM读卡器
                M_Configuration.boxA_info = ConfigurationManager.AppSettings["box_A"].ToString();//A票箱说明
                M_Configuration.boxB_info = ConfigurationManager.AppSettings["box_B"].ToString();//A票箱说明
                //M_Configuration.AreaCode = ConfigurationManager.AppSettings["AreaCode"].ToString();
                //M_Configuration.FontName = ConfigurationManager.AppSettings["FontName"].ToString();
                //M_Configuration.FontSize = float.Parse(ConfigurationManager.AppSettings["FontSize"].ToString());
            }
            catch (Exception ex){
                throw new Exception(ex.Message.ToString());
            }
            
        }

        /// <summary>
        /// 获取PIN码
        /// </summary>
        public static void GetPIN()
        {
            try
            {
                string str = " SELECT PAREMETER_VAL FROM SYS_PAREMETER WHERE PAREMETER_ID='sam_card_pin' ";
                SQLiteCommand cmd = new SQLiteCommand(str);
                DataSet ds = new DataSet();
                ds = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, cmd);
                if (ds.Tables[0].Rows.Count > 0)
                {
                    M_Configuration.Pin = Convert.ToString(ds.Tables[0].Rows[0]["PAREMETER_VAL"]);
                }
            }
            catch (Exception ex)
            {
                _log.Error(typeof(T_ReadConfig), ex);
            }
        }

        #region//本系统暂时不用的功能
        /*
        /// <summary>
        /// 字体设置
        /// </summary>
        /// <returns></returns>
        /// <Remark>郝毅志，2017/06/27</ Remark > 
        public static void SetFont() { 
            //设置控件字体
            DevExpress.XtraEditors.WindowsFormsSettings.DefaultFont = new Font(M_Configuration.FontName, M_Configuration.FontSize);
            //设置菜单和工具栏字体
            //DevExpress.XtraEditors.WindowsFormsSettings.DefaultMenuFont = new Font(M_Configuration.FontName, M_Configuration.FontSize);
            //设置打印控件字体
            //DevExpress.XtraEditors.WindowsFormsSettings.DefaultPrintFont = new Font(M_Configuration.FontName, M_Configuration.FontSize);
        }


        /// <summary>
        /// 获取团体票时长
        /// </summary>
        public static void Get_TeamTicketTime()
        {
            try
            {
                string str = " SELECT PAREMETER_VAL FROM SYS_PAREMETER WHERE PAREMETER_ID='team_validate_time' ";
                SQLiteCommand cmd = new SQLiteCommand(str);
                DataSet ds = new DataSet();
                ds = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, cmd);
                M_Configuration.Teamtickettime = Convert.ToInt32(ds.Tables[0].Rows[0]["PAREMETER_VAL"]);
            }
            catch (Exception ex)
            {
                T_LogisTrac.WriteLog(typeof(T_ReadConfig), ex);
            }
        }
        */
        #endregion

    }
}
