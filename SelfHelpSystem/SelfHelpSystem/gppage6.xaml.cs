using Common;
using SenderCardLibrary;
using System;
using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Speech.Synthesis;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Threading;
using System.Data;
using SelfHelpSystem.Tool;
using SelfHelpSystem.Model;
using SelfHelpSystem.DAL;
using System.Threading;
//******读卡
using TBIMS.SCRLib.Pcsc.Readers;
using TBIMS.SCRLib.Pcsc.Cards;
using TBIMS.SCRLib.Pcsc.Cards.Acos6;
using TBIMS.SCRLib.Pcsc;
using TBIMS.SCRLib;
//******读卡
using log4net;
using SelfHelpSystem.BLL;

using System.Runtime.InteropServices;//出票部分
using System.Configuration;

namespace SelfHelpSystem
{
    public partial class GPPage6 : Page, IComponentConnector
	{
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");
        private Thread wkThread;
        private RfidTicketHandler rfidReader;
        private volatile bool _shoudStop = false;
        //public B_CallBackEventArg arg;//事件类
        private M_TicketType mticketType;//票种实体
        public ProgressReport reportChange;
        public Int32 Yu_Price = -1;//预售期票价
        private DateTime BeginDate, EndDate;

        //***************读卡发卡
        private int num = 100;
        private int curBoxNumber = 0;//1表示A票箱 2表示B票箱
      

        //private StringBuilder strbuilder;

		//private OldSenderCardControl api;

		private MainWindow parent;

		private string cardNo = string.Empty;

		private string ErrorInfo = string.Empty;

		private DispatcherTimer counttimer;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

		private int cardCount = 1;
        private int OutCardCount = 1;//待出票张数
        private string XiaoMX;//销售明细单号
        private string CurTicketID = "";//当前读到的票号  每出一张票就上传时用到

		private bool locktag = false;

		private bool isError = false;

        private bool isre = false;

        ////*******************出票部分*****************
        //[DllImport("hhjttpu.dll", CharSet = CharSet.Unicode, SetLastError = true)]
        //private static extern uint TPU_OPEN(byte[] lpbytSerialnumber);
        //[DllImport("hhjttpu.dll", CharSet = CharSet.Unicode, SetLastError = true)]
        //private static extern uint TPU_CLOSE();
        //[DllImport("hhjttpu.dll", CharSet = CharSet.Unicode, SetLastError = true)]
        //private static extern uint TPU_RST(byte Mod);
        //[DllImport("hhjttpu.dll", CharSet = CharSet.Unicode, SetLastError = true)]
        //private static extern uint TPU_GET_ST(byte[] recbuf);
        //[DllImport("hhjttpu.dll", CharSet = CharSet.Unicode, SetLastError = true)]
        //private static extern uint TPU_SEND_TK(byte BoxId);
        //[DllImport("hhjttpu.dll", CharSet = CharSet.Unicode, SetLastError = true)]
        //private static extern uint TPU_OUT(byte OutWay);
        //[DllImport("hhjttpu.dll", CharSet = CharSet.Unicode, SetLastError = true)]
        //private static extern uint TPU_GET_VERSION(byte[] recbuf);
        //[DllImport("hhjttpu.dll", CharSet = CharSet.Unicode, SetLastError = true)]
        //private static extern uint TPU_GET_BOXID(byte BoxId, byte[] recbuf);
        //[DllImport("hhjttpu.dll", CharSet = CharSet.Unicode, SetLastError = true)]
        //private static extern uint TPU_GET_TKNUM(byte BoxId, byte[] recbuf);
        //[DllImport("hhjttpu.dll", CharSet = CharSet.Unicode, SetLastError = true)]
        //private static extern uint TPU_SET_TKNUM(byte BoxId, short num);
        //[DllImport("hhjttpu.dll", CharSet = CharSet.Unicode, SetLastError = true)]
        //private static extern uint TPU_TEST(byte TestMod);
        ////*******************出票部分*****************


		public GPPage6(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
			base.Background = new ImageBrush
			{
				ImageSource = new BitmapImage(new Uri(AppDomain.CurrentDomain.BaseDirectory + "\\pic\\bg1.png")),
				Stretch = Stretch.Fill
			};
            //this.api = new OldSenderCardControl(ConfigHelper.GetConfigValue("comport", "senderPort"), int.Parse(ConfigHelper.GetConfigValue("comport", "senderBaudRate")), ConfigHelper.GetConfigValue("comport", "CardPort"), int.Parse(ConfigHelper.GetConfigValue("comport", "CardBaudRate")));
            //this.api.ReadCardDataEvent += new OldSenderCardControl.ReadDataHanlder(this.Api_ReadCardDataEvent);
            //this.api.RunErrorEvent += new OldSenderCardControl.RunErrorHanlder(this.Api_RunErrorEvent);
            //this.api.ReceiveControlEvent += new OldSenderCardControl.ReceiveControlHanlder(this.Api_ReceiveControlEvent);
			this.counttimer = new DispatcherTimer();
			this.counttimer.Interval = new TimeSpan(0, 0, 0, 1, 0);
			this.counttimer.Tick += new EventHandler(this.timer_Tick);
            //****************打开串口******************************
            string strCOM = ConfigurationManager.AppSettings["COM"].ToString();//出票机串口号 //ConfigHelper.GetConfigValue("comport", "senderPort").ToString();
            try
            {
                //TPU_CLOSE();
                Thread.Sleep(200);
                num = CZLHTpuHandler.Open(strCOM.Trim(), 1500);//打开串口
                if(num !=0)
                {
                    _log.Error("出票机控制板连接失败，状态码：" + num.ToString());
                }
                //if (this.parent.mSelfHelpClass.BoxNumber == "1")//表示A票箱
                //{
                //    curBoxNumber = 0;
                //}
                //else if (this.parent.mSelfHelpClass.BoxNumber == "2")//表示B票箱
                //{
                //    curBoxNumber = 1;
                //}
            }
            catch (Exception ex)
            {
                MessageBox.Show("串口打开失败！不能进行出票操作.");
                _log.Error(typeof(GPPage6),ex);
            }
            //****************打开串口******************************
		}

		//private void Api_ReceiveControlEvent(string receiveData, string runCode, string errorInfo)
		//{
		//	bool flag = errorInfo != string.Empty;
		//	if (flag)
		//	{
  //              //LogHelper.WriteLog(new LogHelper
  //              //{
  //              //    Message = "ReceiveControlEvent Error",
  //              //    StackTrace = errorInfo
  //              //});
		//	}
		//}

		//private void Api_RunErrorEvent(string Model, string SendCode, string ReceiveCode, string ErrorString)
		//{
		//	StringBuilder stringBuilder = new StringBuilder();
		//	stringBuilder.AppendLine("发送代码：" + SendCode);
		//	stringBuilder.AppendLine("接收代码：" + ReceiveCode);
		//	stringBuilder.AppendLine("错误信息：\r\n" + ErrorString);
		//	this.ErrorInfo = stringBuilder.ToString();
  //          //LogHelper.WriteLog(new LogHelper
  //          //{
  //          //    Message = "RunErrorEvent Error",
  //          //    StackTrace = stringBuilder.ToString()
  //          //});
		//}

		//private void Api_ReadCardDataEvent(string cardStr, string blockStr, string baseStr, string errorStr)
		//{
  //          //*******************记录出票信息  即销售明细的来源*************
  //          //
  //          //*******************记录出票信息  即销售明细的来源*************

		//	bool flag = errorStr != string.Empty;
		//	if (flag)
		//	{
  //              //LogHelper.WriteLog(new LogHelper
  //              //{
  //              //    Message = "ReadCardDataEvent Error",
  //              //    StackTrace = errorStr
  //              //});
		//	}
		//}

		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
			this.locktag = false;
			this.isError = false;
			//this.cardCount = this.parent.SelfHelpInfo.TicketCount;
            this.cardCount = this.parent.mSelfHelpClass.TicketCount;//票数 自助售票类
            this.OutCardCount = this.parent.mSelfHelpClass.TicketCount;//票数  待出票数 自助售票类
			//this.web.Source = new Uri(AppDomain.CurrentDomain.BaseDirectory + "flash\\f6.swf");
			this.counttimer.Start();
			this.cardNo = string.Empty;
			this.parent.ResumeSecond();
			//this.ss.SelectVoiceByHints(VoiceGender.Female);
			//this.ss.SpeakAsync("正在出票请稍后！");
		//	this.parent.SelfHelpInfo.TicketNumberList = new List<string>();

            //strbuilder = new StringBuilder();
            
           // ****************读卡线程*************
            string strSAM = M_Configuration.Samreadername;
            string strRFID = M_Configuration.RfidReadername;
            string strPIN = M_Configuration.Pin;
            try
            {
                rfidReader = TicketHandlerAdapter.getRfidTicketHandler(Convert.ToInt32(M_Configuration.CLIENTID), strRFID, strSAM, Encoding.ASCII.GetBytes(strPIN));
            }
            catch (Exception ex)
            {
                _log.Error(typeof(GPPage6), ex);
                // MessageBox.Show("初始化失败，请确认读卡器选择正确！");
                // this.parent.EndOperation();
                this.parent.Err_Info = "设备故障，请到人工窗口取票。";//"读卡器初始化失败！\r\n请确认读卡器选择是否正确！";
                this.parent.Operation("error");
                return;
            }
            wkThread = new Thread(new ThreadStart(OutPutTicket)); //new Thread(new ThreadStart(DoWork));
            wkThread.IsBackground = true;
            wkThread.Start();
            
            //****************读卡线程*************
            this.btn_FH.Visibility = Visibility.Hidden;
            this.btn_FH.IsEnabled = false;
            this.btn_SY.Visibility = Visibility.Hidden;
            this.btn_SY.IsEnabled = false;
		}

		private void timer_Tick(object sender, EventArgs e)
		{
            //this.counttimer.Stop();
            //bool flag = !this.locktag;
            //if (flag)
            //{
            //    this.locktag = true;
            //    while (this.cardCount > 0)
            //    {
            //        string empty = "票号？";// string.Empty;
            //        bool flag2 = false;//!this.api.ReadCard(out empty);//wjl
            //        if (flag2)
            //        {
            //            this.isError = true;
            //            break;
            //        }
            //        bool flag3 = !this.api.OutputCard();//wjl
            //        if (flag3)
            //        {
            //            this.isError = true;
            //            break;
            //        }
            //        this.cardCount--;
            //        this.parent.SelfHelpInfo.TicketNumberList.Add(empty);
            //        System.Threading.Thread.Sleep(1000);
            //    }
            //}
            //bool flag4 = false;// this.cardCount == 0;//this.cardCount > 0 && this.isError;
			if (isError)
			{
				StringBuilder stringBuilder = new StringBuilder();
                //stringBuilder.AppendLine("景区票种：" + ConfigHelper.GetConfigValue("ticket", "t" + this.parent.SelfHelpInfo.TicketType));
                //stringBuilder.AppendLine("购买数量：" + this.parent.SelfHelpInfo.TicketCount);
                //stringBuilder.AppendLine("已付金额：" + this.parent.SelfHelpInfo.TicketPriceTotal);
                //stringBuilder.AppendLine("已出票数：" + (this.parent.SelfHelpInfo.TicketCount - this.cardCount).ToString());
                //stringBuilder.AppendLine("购买日期：" + DateTime.Now.ToString("yyyy年MM月dd日 HH:mm:ss"));
                //stringBuilder.AppendLine("");
                //stringBuilder.AppendLine("请联系工作人员取票！");

                stringBuilder.AppendLine("景区票种：" + T_SellTicketRecord.GetTicketTypeName(this.parent.mSelfHelpClass.TicketTypeID));
                stringBuilder.AppendLine("购买数量：" + this.parent.mSelfHelpClass.TicketCount);
                stringBuilder.AppendLine("已付金额：" + this.parent.mSelfHelpClass.RealSum);
                stringBuilder.AppendLine("已出票数：" + (this.parent.mSelfHelpClass.TicketCount - this.cardCount).ToString());
                stringBuilder.AppendLine("购买日期：" + DateTime.Now.ToString("yyyy年MM月dd日 HH:mm:ss"));
                stringBuilder.AppendLine("");
                stringBuilder.AppendLine("请联系工作人员取票！");                

				this.parent.WarmOperation(stringBuilder.ToString(), "error", false);
                //LogHelper.WriteLog(new LogHelper
                //{
                //    Message = "已付款，但是无法出票！",
                //    StackTrace = stringBuilder.ToString()
                //});
			}
			else
			{
                if (this.cardCount == 0)// wjl  add 
                {
                    //**************改为出一张票就上传一次的                    
                    //this.web.Source = new Uri(AppDomain.CurrentDomain.BaseDirectory + "flash\\f7.swf");
                  //  this.ss.SelectVoiceByHints(VoiceGender.Female);
                  //  this.ss.SpeakAsync("请取票！");
                    this.Dispatcher.BeginInvoke((Action)delegate()
                    {
                        if (this.parent.ticketbox_isErr != 1)//票种放反
                        {
                            this.txt_show.Text = "购票信息-出票完毕，请取票！！";
                        }
                        isre = true;
                    });
                    if (isre)
                    {
                        System.Threading.Thread.Sleep(3000);
                        this.cardCount = -1;
                        this.parent.EndOperation();
                        isre = false;
                    }
                }
                else if (this.cardCount == -99999)
                {
                    this.Dispatcher.BeginInvoke((Action)delegate()
                    {
                        this.txt_show.Foreground = System.Windows.Media.Brushes.Red;
                        this.txt_show.Text = "购票信息-刮票失败！请到人工窗口取票！";
                        isre = true;
                    });
                    if (isre)
                    {
                       // System.Threading.Thread.Sleep(300);
                        this.cardCount = -1;
                       // this.parent.EndOperation();
                        if (this.parent.ticketbox_isErr == 0)
                        {
                            this.parent.Err_Info = "出票失败！请到人工售票窗口取票。";
                            this.parent.Operation("error");
                        }
                        else if (this.parent.ticketbox_isErr == 1)//票种放反的
                        {
                            this.parent.Err_Info = "出票失败！请到人工售票窗口取票。";//"所购票种与实际出票种不符！\r\n请到人工售票窗口取票！";
                            this.parent.Operation("error");
                        }
                        else if (this.parent.ticketbox_isErr == 100)//上传失败
                        {
                            this.parent.Err_Info = "出票失败！请到人工售票窗口取票。";// "系统提示：\r\n数据上传失败！\r\n请到人工售票窗口取票！";
                            this.parent.Operation("error");
                        }
                        isre = false;
                    }
                }
				//LogHelper.WriteLog(this.parent.SelfHelpInfo.ToString(), AppDomain.CurrentDomain.BaseDirectory + "pay" + DateTime.Now.ToString("yyyyMMdd") + ".log");
			}
		}

		private void btn_SY_Click(object sender, RoutedEventArgs e)
        {
            //bool flag = this.cardCount > 0;
            //if (!flag)
            //{
            //    this.parent.EndOperation();
            //}
            this.parent.EndOperation();
            _log.Debug("返回首面！");
		}

		private void btn_FH_Click(object sender, RoutedEventArgs e)
        {
            //bool flag = this.cardCount > 0;
            //if (!flag)
            //{
            //    this.parent.Operation("qp2");
            //}
            this.parent.EndOperation();
            _log.Debug("返回上一页！");
		}

		private void Page_Unloaded(object sender, RoutedEventArgs e)
		{
            //TPU_CLOSE();
            CZLHTpuHandler.Close();
            rfidReader.Dispose();
            rfidReader = null;
			//this.parent.RestartSecond();
			//this.parent.SelfHelpInfo.TicketNumberList = new List<string>();
        }

        //***************读卡函数************
//        public void DoWork()
//        {
//            B_CallBackEventArg arg = new B_CallBackEventArg();
//            arg.message = "";
//            while (!_shoudStop)
//            {

//                #region
//                try
//                {
//                    rfidReader.WaitForTicketPresent(-1);

//                    //读取发行信息
//                    TicketPublishInfo tpInfo = rfidReader.readTicketPublishInfo();
//                    arg.TicketNumber = tpInfo.ID.ToString();//票号
//                    //读取销售信息
//                    TicketSaleInfo? tsInfo = rfidReader.readTicketSaleInfo();
//                    if (!tsInfo.HasValue)//未做过销售的
//                    {
//                        #region //验证出票与购票的票种是否相同
//                        if (tpInfo.TicketClass.ToString() == this.parent.mSelfHelpClass.TicketTypeID)
//                        {
//                            //获到票种实体
//                            mticketType = D_TicketType.GetTicketTypeInfo(tpInfo.TicketClass.ToString());

//                            #region//首先判断票种是否存在
//                            if (mticketType.TICKET_TYPE_ID != null)//当前票票种存在的
//                            {
//                                Yu_Price = T_SellTicketRecord.Get_sl_period_Price(tpInfo.TicketClass.ToString());//获取预售期票价
//                                #region//是否团体票
//                                if (mticketType.TEAM_FLAG == "Y")//团体票
//                                {
//                                    BeginDate = DateTime.Now;
//                                    EndDate = DateTime.Now.AddMinutes(M_Configuration.Teamtickettime);//从参数表中取出结束时间//Convert.ToDateTime(DateTime.Now.ToString("yyyy-MM-dd 16:00:00"));                                                     
//                                    arg.message = "此门票为团体票,不能在此销售！";
//                                    //  reportChange(-1, arg, null);
//                                    wkThread_progressReport(-1, arg, null);
//                                    rfidReader.LongBeep();
//                                }
//                                else
//                                {
//                                    if (mticketType.DAY_VALIDATE_FLAG == "Y")//当日有效
//                                    {
//                                        BeginDate = Convert.ToDateTime(DateTime.Now.ToString("yyyy-MM-dd 00:00:00"));
//                                        EndDate = Convert.ToDateTime(DateTime.Now.ToString("yyyy-MM-dd 23:59:59"));
//                                    }
//                                    else //非当日有效
//                                    {
//                                        BeginDate = DateTime.Now;
//                                        EndDate = DateTime.Now.AddYears(2);
//                                    }
//                                }
//                                #endregion
//                                #region//  循环写票
//                                if (this.cardCount > 0)//未出完票的
//                                {
//                                    //************************写入售票内容*****************
//                                    #region//写票
//                                    if (rfidReader.writeTicketSaleInfo(BeginDate, EndDate, 1))
//                                    {
//                                        if (this.parent.mSelfHelpClass.OperType == "0")//购票时生成销售明细单号
//                                        {
//                                            XiaoMX = T_SellTicketRecord.CreateTableUID(M_Configuration.CLIENTID, T_SellTicketRecord.LocalTicketMX_Prefix_ZG, this.cardCount);
//                                        }
//                                        //保存数据
//                                        #region//保存
//                                        if (T_SellTicketRecord.Insert_SL_ORDER_DETAIL(mticketType, tpInfo.ID.ToString(), Convert.ToInt32(this.parent.mSelfHelpClass.OperType), this.parent.mSelfHelpClass, XiaoMX,this.cardCount))
//                                        {
//                                            rfidReader.Beep();
//                                            try
//                                            {
//                                                if (this.api.OutputCard())//出票
//                                                {
//                                                    this.cardCount--;//读取成功且出票成功再计数
//                                                }
//                                                else//出票不成功
//                                                {
//                                                    isError = true;
//                                                    arg.message = "未成功出票！";
//                                                    wkThread_progressReport(-1, arg, null);
//                                                }
//                                            }
//                                            catch (Exception ex)
//                                            {
//                                                _log.Error(typeof(GPPage6), ex);
//                                            }
//                                        }
//                                        else
//                                        {
//                                            arg.message = "保存票据信息失败！";
//                                            wkThread_progressReport(-1, arg, null);
//                                        }
//#endregion
                                        
//                                    }
//                                    else//写票失败
//                                    {
//                                        arg.message = "写入票据信息失败！";
//                                        wkThread_progressReport(-1, arg, null);
//                                    }
//#endregion
//                                }
//                                else
//                                {
//                                    rfidReader.Beep();
//                                    arg.message = "已完成售票，请取票！";
//                                    wkThread_progressReport(-1, arg, null);
//                                }
//                                #endregion

//                            }
//                            else//该票的票种不存在
//                            {
//                                arg.message = "系统中不存在当前票种！";
//                                //  reportChange(-1, arg, null);
//                                wkThread_progressReport(-1, arg, null);
//                                rfidReader.LongBeep();
//                            }
//                            #endregion
//                        }
//                        else//购票与出票 票种不符
//                        {
//                            arg.message = "当前门票同所购门票的票种不符！";
//                            // reportChange(-1, arg, null);
//                            wkThread_progressReport(-1, arg, null);
//                            rfidReader.LongBeep();
//                        }
//                        #endregion
//                    }
//                    else//已做过销售的
//                    {
//                        arg.message = "当前门票已做过销售处理！";
//                        // reportChange(-1, arg, null);
//                        wkThread_progressReport(-1, arg, null);
//                        rfidReader.LongBeep();
//                    }
//                }
//                catch (Exception ex)
//                {
//                    if (_shoudStop)
//                        return;
//                    arg.message = ex.Message;
//                    //  reportChange(-1, arg, ex);
//                    wkThread_progressReport(-1, arg, ex);
//                    _log.Error(typeof(GPPage6), ex);
//                }
//                finally
//                {
//                    rfidReader.WaitForTicketRemoval(-1);
//                }
//                #endregion


//            }
//        }
        //void wkThread_progressReport(int pProgressPercentage, object UserState, Exception ex)
        //{
        //    arg = (B_CallBackEventArg)UserState;
        //    //this.Invoke(new ShowinfoDelegate(ShowInfo));
        //    if (pProgressPercentage >= 0)
        //    {
        //      //  str_ChangTicket = arg.TicketNumber;
        //    }
        //    else
        //    {
        //      //  MessageBox.Show("提示信息："+arg.message, T_SellTicketRecord.str_Apption,MessageBoxButton.OK,MessageBoxImage.Information);               
        //    }
        //}
        //***************读卡函数************



        //*********************刮票、写票、出票操作函数****************
        private void OutPutTicket()
        {
            //刮票 写票 出票部分
            #region
            bool isok=false;
            bool isup = false;//上传
            try
            {
                while (this.cardCount > 0)//未出完票的
                {
                    if (this.parent.mSelfHelpClass.OperType == "1")//取票还是购票 0表示购票 1表示取票 自助售票类
                    {
                        displayErrorMessage("暂不支持自助取票！", "error");
                        break;
                        //取票
                        #region
                        //*****************实际取票时 需要判定的情况比较多
                        //if (M_Configuration.TicketTypeID_1 != M_Configuration.TicketTypeID_2)//不同票种
                        //{
                            //不同票种出票
                            //#region
                        //    if (M_Configuration.TicketTypeID_1 == this.parent.mSelfHelpClass.listorderdetail[this.cardCount - 1].TicketTypeId)
                        //    {
                        //        curBoxNumber = 0;//A票箱号
                        //        this.parent.mSelfHelpClass.BoxNumber = "A";
                        //        _log.Info("出A票箱!");
                        //    }
                        //    else if (M_Configuration.TicketTypeID_2 == this.parent.mSelfHelpClass.listorderdetail[this.cardCount - 1].TicketTypeId)
                        //    {
                        //        curBoxNumber = 1;//B票箱号
                        //        this.parent.mSelfHelpClass.BoxNumber = "B";
                        //        _log.Info("出B票箱!");
                        //    }
                        //    else
                        //    {
                        //        _log.Error("票箱内不含当前票种！");
                        //    }
                        //    #endregion
                        //}
                        //else if (M_Configuration.TicketTypeID_1 == M_Configuration.TicketTypeID_2)//同一票种的
                        //{
                        //    if (T_SellTicketRecord.Judge_TicketAlarmCount("A", 1))//A箱不够出B票箱
                        //    {
                        //        curBoxNumber = 1;//B票箱号
                        //        this.parent.mSelfHelpClass.BoxNumber = "B";
                        //        _log.Info("出B票箱!");
                        //    }
                        //    else if (T_SellTicketRecord.Judge_TicketAlarmCount("B", 1))//B箱不够出A票箱
                        //    {
                        //        curBoxNumber = 0;//A票箱号
                        //        this.parent.mSelfHelpClass.BoxNumber = "A";
                        //        _log.Info("出A票箱!");
                        //    }
                        //}
                        #endregion
                    }
                    else if (this.parent.mSelfHelpClass.OperType == "0")//取票还是购票 0表示购票 1表示取票 自助售票类
                    {
                        DataTable dt = T_SellTicketRecord.Get_BoxTicketInfo();
                        for (int i = 0; i < dt.Rows.Count; i++)
                        {
                            if (dt.Rows[i]["ticket_type_id"].ToString() == this.parent.mSelfHelpClass.TicketTypeID &&
                                (int)dt.Rows[i]["surplusCount"] >= this.cardCount + (int)dt.Rows[i]["alarmCount"])
                            {
                                if (dt.Rows[i]["boxNumber"].ToString() == "A")
                                {
                                    this.parent.mSelfHelpClass.BoxNumber = "A";
                                    curBoxNumber = 0;//A票箱号
                                    break;
                                }
                                else
                                {
                                    this.parent.mSelfHelpClass.BoxNumber = "B";
                                    curBoxNumber = 1;//B票箱号
                                    break;
                                }
                            }
                        }
                    }
                    Thread.Sleep(100);
                    //1.刮票
                    //num = TPU_SEND_TK(Convert.ToByte(curBoxNumber));//0表示A票箱 1表示B票箱
                    num = CZLHTpuHandler.SendTicket((byte)curBoxNumber);
                                                                    //  num =0;//测试代码
                    _log.Info("刮票结果:" + num.ToString());
                    Thread.Sleep(200);
                    if (num == 0)
                    {
                        _log.Info("刮票成功！");
                        isok = T_SellTicketRecord.Update_SL_TicketCount(this.parent.mSelfHelpClass.BoxNumber);
                        if (isok)
                        {
                            _log.Info("更新票箱余票成功！");
                        }
                        else
                        {
                            _log.Error("更新票箱余票失败！");
                        }
                        //2.写票
                        #region                   
                        if (WriteTicket() == true)
                        //if(1==1)//测试代码
                        {
                            Thread.Sleep(100);
                            _log.Info("写票成功！");
                            //********************写票成功后是各种更新及上传********

                            #region//各种更新上传
                            //******************出一张票就上传一次
                            for (int i = 0; i < 3; i++)
                            {
                                try
                                {
                                    isup = B_SellTicketUpData.SellTicketDataUP_MX(this.parent.Client, this.parent.mSelfHelpClass.OrderID, this.parent.mSelfHelpClass, CurTicketID);// CurTicketID
                                    if (isup == true)   //上传成功
                                    {
                                        break;
                                    }
                                    else
                                        _log.Warn("上传出票数据失败，开始重试。上传计数：" + (i + 1).ToString());
                                }
                                catch (Exception ex)
                                {
                                    _log.Error("上传出票数据失败，开始重试。上传计数：" + (i + 1).ToString(), ex);
                                }
                            }
                            if (isup)
                            {
                                _log.Info("出票上传成功！");
                                //******开始出票**
                                //num = TPU_OUT(0);//出票   TPU_OUT返回1为出票成功
                                num = CZLHTpuHandler.OutTicket(0);
                                                 //   num = 1;//测试代码
                                if (num != 0)
                                {
                                    //num = TPU_OUT(0);//再出一次
                                    num = CZLHTpuHandler.OutTicket(0);
                                }
                                if (num == 0)//出票成功
                                {
                                    _log.Info("出票成功！");
                                    this.cardCount--;
                                }
                                else//出票失败
                                {
                                    #region//出票失败的
                                    _log.Error("出票操作失败！");
                                    M_Configuration.RunState = Convert.ToInt32(DeviceStatus.OutCardFault);//出票机故障
                                    this.cardCount = -99999;//返回首页
                                    displayErrorMessage("出票卡票！请联系工作人员取走卡票，并到人工售票窗口补取余票。", "error");
                                    #endregion
                                }
                                //*******开始出票**
                            }
                            else
                            {
                                //*******************************数据上传失败就回收票*********
                                try
                                {
                                    rfidReader.clearTicketSaleInfo();
                                }
                                catch { }
                                //num = TPU_OUT(1);//出不了就回收   废票回收无法判断是否执行成功
                                num = CZLHTpuHandler.OutTicket(1);
                                if (num == 0)//回收成功
                                    _log.Info("出票上传失败，门票回收成功！");
                                else
                                    _log.Error("出票上传失败，门票回收失败！状态码：" + num.ToString());

                                //*****更新废票箱
                                isok = T_SellTicketRecord.Update_SL_AbolishCount(false);//更新废票箱票数;
                                if (isok)
                                {
                                    _log.Info("更新废票箱成功！");
                                }
                                else
                                {
                                    _log.Error("更新废票箱失败！");
                                }
                                //*******************************数据上传失败就回收票*********

                                this.cardCount = -99999;
                                this.parent.ticketbox_isErr = 100;
                            }
                            #endregion
                            
                        }
                        else
                        {
                            #region 写票失败的
                            try
                            {
                                rfidReader.clearTicketSaleInfo();
                            }
                            catch { }
                            num = CZLHTpuHandler.OutTicket(1);
                            if (num == 0)//回收成功
                                _log.Info("写票操作失败，门票回收成功！");
                            else
                                _log.Error("写票操作失败，门票回收失败！状态码：" + num.ToString());

                            //          废票回收无法获取到是否回收成功状态
                            //*****更新废票箱
                            isok = T_SellTicketRecord.Update_SL_AbolishCount(false);//更新废票箱票数;
                            if (isok)
                            {
                                _log.Info("更新废票箱成功！");
                            }
                            else
                            {
                                _log.Error("更新废票箱失败！");
                            }

                            if (this.parent.ticketbox_isErr == 1)//票种放反
                            {
                                this.cardCount = 0;
                            }
                            #endregion
                        }
                        #endregion
                    }
                    else
                    {
                        //_log.Error("刮票操作失败！");
                        //TPU_RST(0);
                        num = CZLHTpuHandler.Reset();
                        if (num == 0)//回收成功
                            _log.Error("刮票操作失败，控制板重置成功！");
                        else
                            _log.Error("刮票操作失败，控制板重置失败！状态码：" + num.ToString());

                        M_Configuration.RunState = Convert.ToInt32(DeviceStatus.OutCardFault);//出票机故障
                        this.cardCount = -99999;//返回首页
                        displayErrorMessage("出票失败！请到人工售票窗口取票。", "error");
                    }
                }
                #endregion
                if (this.cardCount == 0)
                {
                    displayErrorMessage("出票完成，请取走门票。", "INFO", true);
                }
            }
            catch(Exception ex)
            {
                _log.Error("出票失败，未知异常。", ex);
                displayErrorMessage("出票失败！请到人工售票窗口取票。", "error");
            }
            finally
            {
                CZLHTpuHandler.Close();
            }
        }

        //****写票函数
        private bool WriteTicket()
        {
            bool isok = false;
            try
            {
                //具体写票操作
                bool hasTicket = rfidReader.WaitForTicketPresent(1000);
                #region 
                try
                {
                    if (hasTicket)
                    {
                        //读取发行信息
                        TicketPublishInfo tpInfo = rfidReader.readTicketPublishInfo();
                        _log.Debug("已读取到门票，票号：" + tpInfo.ID.ToString());
                        CurTicketID = tpInfo.ID.ToString();//票号 上传时用到
                                                           //读取销售信息
                        TicketSaleInfo? tsInfo = rfidReader.readTicketSaleInfo();
                        if (!tsInfo.HasValue)//未做过销售的
                        {
                            #region //验证出票与购票的票种是否相同
                            if ((tpInfo.TicketClass.ToString() == M_Configuration.TicketTypeID_1) || (tpInfo.TicketClass.ToString() == M_Configuration.TicketTypeID_2))
                            {
                                //获到票种实体
                                mticketType = D_TicketType.GetTicketTypeInfo(tpInfo.TicketClass.ToString());

                                #region//首先判断票种是否存在
                                if (mticketType.TICKET_TYPE_ID != null)//当前票票种存在的
                                {
                                    #region//两个票箱票种互换的情况
                                    if ((this.parent.mSelfHelpClass.BoxNumber == "A" && tpInfo.TicketClass.ToString() != M_Configuration.TicketTypeID_1)
                                        || (this.parent.mSelfHelpClass.BoxNumber == "B" && tpInfo.TicketClass.ToString() != M_Configuration.TicketTypeID_2))
                                    {   // 票箱设置的票种与实际所放票种不一致
                                            //TPU_OUT(1);//回收票据
                                        _log.Error("票箱设置票种与实际出票票种不符！");
                                        this.parent.ticketbox_isErr = 1;//票种放反
                                        rfidReader.LongBeep();
                                        isok = false;
                                        return isok;
                                    }
                                    #endregion
                                    Yu_Price = T_SellTicketRecord.Get_sl_period_Price(tpInfo.TicketClass.ToString());//获取预售期票价
                                    #region//是否团体票
                                    if (mticketType.TEAM_FLAG == "Y")//团体票
                                    {
                                        BeginDate = DateTime.Now;
                                        EndDate = DateTime.Now.AddMinutes(M_Configuration.Teamtickettime);//从参数表中取出结束时间//Convert.ToDateTime(DateTime.Now.ToString("yyyy-MM-dd 16:00:00"));                                                     
                                        _log.Error("此门票为团体票,不能在此销售！");
                                        rfidReader.LongBeep();
                                        return false;
                                    }
                                    else
                                    {
                                        if (mticketType.DAY_VALIDATE_FLAG == "Y")//当日有效
                                        {
                                            BeginDate = Convert.ToDateTime(DateTime.Now.ToString("yyyy-MM-dd 00:00:00"));
                                            EndDate = Convert.ToDateTime(DateTime.Now.ToString("yyyy-MM-dd 23:59:59"));
                                        }
                                        else //非当日有效
                                        {
                                            BeginDate = DateTime.Now;
                                            EndDate = DateTime.Now.AddYears(2);
                                        }
                                    }
                                    #endregion

                                    //真正写票 并保存到数据库中
                                    #region 
                                    if (rfidReader.writeTicketSaleInfo(BeginDate, EndDate, 1))
                                    {
                                        _log.Debug("写票成功！");
                                        if (this.parent.mSelfHelpClass.OperType == "0")//购票时生成销售明细单号
                                        {
                                            XiaoMX = T_SellTicketRecord.CreateTableUID(M_Configuration.CLIENTID, T_SellTicketRecord.LocalTicketMX_Prefix_ZG, this.cardCount);
                                        }
                                        //保存数据
                                        #region
                                        if (T_SellTicketRecord.Insert_SL_ORDER_DETAIL(mticketType, tpInfo.ID.ToString(), Convert.ToInt32(this.parent.mSelfHelpClass.OperType), this.parent.mSelfHelpClass, XiaoMX, this.cardCount))
                                        {
                                            _log.Debug("本地缓存写入成功！");
                                            isok = true;
                                        }
                                        else
                                        {
                                            _log.Warn("本地缓存写入失败！");
                                        }
                                        #endregion
                                    }
                                    else
                                    {
                                        _log.Warn("写入票据信息失败！");
                                    }
                                    #endregion

                                }
                                else//该票的票种不存在
                                {
                                    _log.Error("系统中不存在当前票种！票种：" + tpInfo.TicketClass.ToString());
                                    rfidReader.LongBeep();
                                }
                                #endregion
                            }
                            else//购票与出票 票种不符
                            {
                                _log.Error("当前门票同所购门票的票种不符！票种：" + tpInfo.TicketClass.ToString());
                                rfidReader.LongBeep();
                            }
                            #endregion
                        }
                        else//已做过销售的
                        {
                            _log.Error("当前门票已做过销售处理！票号：" + CurTicketID.ToString());
                            rfidReader.LongBeep();
                        }
                    }
                    else
                        return false;
                }
                catch (Exception ex)
                {
                    _log.Error("门票读取错误！", ex);
                    rfidReader.LongBeep();
                }

                #endregion
            }
            catch (Exception ex)
            {
                _log.Error("读卡器异常！", ex);
                rfidReader.LongBeep();
            }
            finally
            {
                //rfidReader.WaitForTicketRemoval(0);
            }
            return isok;
        }
        //*********************刮票、写票、出票操作函数****************

        private void displayErrorMessage(string message, string pagename)
        {
            Application.Current.Dispatcher.BeginInvoke((Action)delegate
            {
                this.parent.WarmOperation(message, pagename);
            });
         }
        private void displayErrorMessage(string message, string pagename, bool countDown)
        {
            Application.Current.Dispatcher.BeginInvoke((Action)delegate
            {
                this.parent.WarmOperation(message, pagename, countDown);
            });
        }
    }
}