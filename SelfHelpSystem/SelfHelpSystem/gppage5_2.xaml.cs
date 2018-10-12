using Common;
using SmartPay.Model;
using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Speech.Synthesis;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Windows.Media.Imaging;
using System.Windows.Threading;
using SelfHelpSystem.Tool;
using SelfHelpSystem.Model;
using SelfHelpSystem.BLL;
using System.Configuration;
using System.Threading;
using System.Windows.Input;
using log4net;


namespace SelfHelpSystem
{
    public partial class GPPage5_2 : Page, IComponentConnector
	{
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");
		private DispatcherTimer counttimer;
        private DispatcherTimer timer_test;
		private MainWindow parent;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

		private bool firstRun = true;

		private string refNo = string.Empty;

		private string txnId = string.Empty;

		private string odNo = string.Empty;

        private bool isre = false;
        private bool istest = false;

		public GPPage5_2(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
		}

		private void btn_SY_Click(object sender, RoutedEventArgs e)
		{
			this.parent.EndOperation();
		}

		private void btn_FH_Click(object sender, RoutedEventArgs e)
		{
			this.parent.Operation("gp4");
		}

        private void TextBlock_GotFocus(object sender, RoutedEventArgs e)
        {
            this.inputBox.Focus();
        }

        private void Page_GotFocus(object sender, RoutedEventArgs e)
        {
            this.inputBox.Focus();
        }
        private void inputBox_LostFocus(object sender, RoutedEventArgs e)
        {
        }

        private void inputBox_LostKeyboardFocus(object sender, KeyboardFocusChangedEventArgs e)
        {
            this.inputBox.Text = "";
            this.inputBox.Focus();

        }

        private void inputBox_TextInput(object sender, TextCompositionEventArgs e)
        {
        }

        private void inputBox_KeyDown(object sender, KeyEventArgs e)
        {
            bool flag = e.Key.ToString() == "Return";
            if (flag)
            {
            }
        }


		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
            //this.parent.SelfHelpInfo.PayType = "weixin";            
            //this.parent.SelfHelpInfo.PayodNo = string.Empty;
            //this.parent.SelfHelpInfo.PayRefNo = string.Empty;
            //this.parent.SelfHelpInfo.PayTxnId = string.Empty;
            //this.parent.SelfHelpInfo.PaySuccess = false;
            //this.parent.SelfHelpInfo.PayReturnInfo = string.Empty;
            //this.ticketDate.Text = this.parent.SelfHelpInfo.SaleTime;
            //this.ticketCount.Text = this.parent.SelfHelpInfo.TicketCount.ToString();
            //this.ticketType.Text = ConfigHelper.GetConfigValue("ticket", "t" + this.parent.SelfHelpInfo.TicketType);
            //this.ticketPriceTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();
            //this.ticketTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();//wjl


            //**********************支付部分*********************
            this.parent.mSelfHelpClass.OperType = "0";//取票还是购票 0表示购票 1表示取票 自助售票类
            this.parent.mSelfHelpClass.PayType = "03";//支付方式 自助售票类 01-现金,02-POS付款,03-微信，04-支付宝，05-代理
            this.parent.mSelfHelpClass.PayStat = "1";//支付状态(1-待支付 2-已支付 3-支付失败)
            this.parent.mSelfHelpClass.PayTypeID = "";//支付单号暂不生成 T_SellTicketRecord.CreateTableUID(M_Configuration.CLIENTID, T_SellTicketRecord.LocalPayType_Prefix, 1);//支付单号 自助售票类
            this.parent.mSelfHelpClass.PayID = string.Empty;//第三方支付单号 自助售票类
            this.parent.mSelfHelpClass.PaySuccess = false;//是否支付成功 自助售票类
          //  this.ticketDate.Text = this.parent.mSelfHelpClass.SaleTime;//销售时间 自助售票类
            this.ticketCount.Text = this.parent.mSelfHelpClass.TicketCount.ToString();//票数 自助售票类
            this.ticketType.Text = T_SellTicketRecord.GetTicketTypeName(this.parent.mSelfHelpClass.TicketTypeID);//票种 自助售票类
            this.ticketPriceTotal.Text = this.parent.mSelfHelpClass.RealSum.ToString();//销售金额 自助售票类
           // this.ticketTotal.Text = this.parent.mSelfHelpClass.RealSum.ToString();//销售金额 自助售票类

            //********************支付部分***********************

            T_SellTicketRecord.slpaytype.OrderId = this.parent.mSelfHelpClass.OrderID;//销售单号 
            T_SellTicketRecord.slpaytype.PayTypeId = this.parent.mSelfHelpClass.PayTypeID;//支付单号
            T_SellTicketRecord.slpaytype.PayType = this.parent.mSelfHelpClass.PayType;//支付方式
            T_SellTicketRecord.slpaytype.Amt = this.parent.mSelfHelpClass.RealSum;//支付金额
            T_SellTicketRecord.slpaytype.VersionNo = T_Date.ConvertDataTimeToLong(DateTime.Now);//版本号

            //***************获取支付方式*****************

            //*******************写入数据***************************
            this.parent.mSelfHelpClass.PayTypeID = T_SellTicketRecord.CreateTableUID(M_Configuration.CLIENTID, T_SellTicketRecord.LocalPayType_Prefix, 1);//支付单号 自助售票类
            this.parent.mSelfHelpClass.OrderID = T_SellTicketRecord.CreateTableUID(M_Configuration.CLIENTID, T_SellTicketRecord.LocalTicket_Prefix_ZG, 1);//销售单号
            this.parent.mSelfHelpClass.PayStat = "2";//支付状态(1-待支付 2-已支付 3-支付失败)
            if (!T_SellTicketRecord.Insert_GP_Data(this.parent.mSelfHelpClass))
            {
                _log.Error("微信支付数据写入失败！");
                return;
            }
             //*******************写入数据***************************

			//this.ss.SelectVoiceByHints(VoiceGender.Female);
			//this.ss.SpeakAsync("使用微信扫一扫，扫描右侧二维码");
		//	this.parent.SelfHelpInfo.PaySuccess = false;
			this.counttimer = new DispatcherTimer();
			this.counttimer.Interval = new TimeSpan(0, 0, 0, 1, 0);
			this.counttimer.Tick += new EventHandler(this.timer_Tick);
			this.counttimer.Start();
            this.inputBox.Focus();

            this.timer_test = new DispatcherTimer();
            this.timer_test.Interval = new TimeSpan(0, 0, 0, 1, 0);
            this.timer_test.Tick += new EventHandler(this.test);
            this.timer_test.Start();
            this.isre = false;
            this.istest = false;
            //string str = AppDomain.CurrentDomain.BaseDirectory + "flash" + "\\f3.swf";
            //this.web.Navigate(str);
		}
        private void test(object sender, EventArgs e)
        {
            if (isre)
            {
                this.Dispatcher.BeginInvoke((Action)delegate()
                {
                    this.ts.Foreground = System.Windows.Media.Brushes.Red;
                    this.ts.Text = "购票信息-微信支付失败！";
                    istest = true;
                });
                if (istest)
                {                   
                  //  System.Threading.Thread.Sleep(300);
                    istest = false;
                    this.timer_test.Stop();
                 //   this.parent.EndOperation();
                    this.parent.Err_Info = "微信支付失败！请重试或至人工窗口购票！";
                    this.parent.Operation("error");
                }
            }
        }
		private void timer_Tick(object sender, EventArgs e)
		{
            //bool flag = this.firstRun;
            //if (flag)
            //{
            //    bool flag2 = !this.CreateCode();
            //    if (flag2)
            //    {
            //        this.parent.WarmOperation("暂时无法使用此功能！请重新选择其他付款方式！", "gp4");
            //    }
            //    this.firstRun = false;
            //}
            Int32 PayState = -1;//查询支付状态
            bool flag3 = true; // PayHelper.IsLogin && this.refNo != string.Empty;//wjl
            if (flag3)
            {
                this.inputBox.Focus();
                if (this.inputBox.Text != "")
                {
                    #region
                    if (this.inputBox.Text.Length < 18)
                    {
                        this.parent.mSelfHelpClass.PayodNo = this.inputBox.Text;
                    }
                    else
                    {
                        this.parent.mSelfHelpClass.PayodNo = this.inputBox.Text.Substring(0, 18);
                    }
                    bool flag5 = false;
                    if (M_Configuration.SaleMode == 1)//身份证购票模式 直接上传
                    {
                        if (this.parent.mSelfHelpClass.OperType == "0")//取票还是购票 0表示购票 1表示取票
                        {
                            try
                            {
                                this.parent.mSelfHelpClass.GetTicketUserNumber = M_Configuration.IDCard;//身份证号
                                flag5 = B_SellTicketUpData.SellTicketDataUP_IDCard(this.parent.Client, this.parent.mSelfHelpClass.OrderID, this.parent.mSelfHelpClass);
                                if (flag5)
                                {
                                    _log.Error("1");
                                    this.parent.EndOperation();
                                    this.counttimer.Stop();
                                }
                            }
                            catch (Exception ex)
                            {//  查询售票单的支付状态,返回支付状态(1-待支付 2-已支付 3-支付失败),联机,调用6次,每5秒调用一次，非2-已支付 则调用取消交易接口
                                this.counttimer.Stop();
                                for (int i = 0; i < 6; i++)
                                {
                                    try
                                    {
                                        PayState = B_SellTicketUpData.Search_PayStat(this.parent.Client, this.parent.mSelfHelpClass.OrderID);
                                    }
                                    catch { }
                                    if (PayState == -2)
                                    {
                                        isre = true;
                                        break;
                                    }
                                    if (PayState == 2)//已支付
                                    {
                                        this.parent.Operation("gp6");
                                        break;
                                    }
                                    else if (PayState == 3)//撤销支付
                                    {
                                        try
                                        {
                                            bool isok = B_SellTicketUpData.Cancel_Pay(this.parent.Client, this.parent.mSelfHelpClass.OrderID);
                                            isre = true;
                                        }
                                        catch { }
                                        break;
                                        //MessageBox.Show("         【      温      馨      提      示      】\r\n\r\n您当前购票交易失败！请重新购票支付或到人工售票网点购票！\r\n\r\n给您造成的不便深表歉意！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                                        //this.parent.EndOperation();
                                        //break;
                                    }
                                    if (i == 5)
                                    {
                                        if (PayState == 1)
                                        {
                                            try
                                            {
                                                bool isok = B_SellTicketUpData.Cancel_Pay(this.parent.Client, this.parent.mSelfHelpClass.OrderID);
                                                isre = true;
                                            }
                                            catch { }
                                            break;
                                            //MessageBox.Show("         【      温      馨      提      示      】\r\n\r\n您当前购票交易失败！请重新购票支付或到人工售票网点购票！\r\n\r\n给您造成的不便深表歉意！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                                            //this.parent.EndOperation();
                                            //break;
                                        }
                                    }
                                    Thread.Sleep(5000);
                                }
                            }
                        }
                        else if (this.parent.mSelfHelpClass.OperType == "1")//取票还是购票 0表示购票 1表示取票
                        {
                            flag5 = B_SellTicketUpData.SellTicketDataUP(this.parent.Client, this.parent.mSelfHelpClass);
                            if (flag5)
                            {
                                this.parent.mSelfHelpClass.PaySuccess = true;//自助售票类
                                this.counttimer.Stop();
                                this.parent.Operation("gp6");
                            }

                        }
                    }
                    else if (M_Configuration.SaleMode == 0) //出实体票的
                    {
                        if (this.parent.mSelfHelpClass.OperType != null)
                        {
                            try
                            {
                                flag5 = B_SellTicketUpData.SellTicketDataUP(this.parent.Client, this.parent.mSelfHelpClass);
                                if (flag5)
                                {
                                    this.parent.mSelfHelpClass.PaySuccess = true;//自助售票类
                                    this.counttimer.Stop();
                                    this.parent.Operation("gp6");
                                }

                            }
                            catch (Exception ex)
                            {
                                //  查询售票单的支付状态,返回支付状态(1-待支付 2-已支付 3-支付失败),联机,调用6次,每5秒调用一次，非2-已支付 则调用取消交易接口
                                this.counttimer.Stop();
                                for (int i = 0; i < 6; i++)
                                {
                                    Thread.Sleep(5000);
                                    try
                                    {
                                        PayState = B_SellTicketUpData.Search_PayStat(this.parent.Client, this.parent.mSelfHelpClass.OrderID);
                                    }
                                    catch { }
                                    if (PayState == -2)
                                    {
                                         isre = true;
                                         break;
                                     }
                                    if (PayState == 2)//已支付
                                    {
                                        this.parent.Operation("gp6");
                                        break;
                                    }
                                    else if (PayState == 3)//撤销支付
                                    {
                                        try
                                        {
                                            bool isok = B_SellTicketUpData.Cancel_Pay(this.parent.Client, this.parent.mSelfHelpClass.OrderID);
                                            isre = true;
                                        }
                                        catch { }
                                        break;
                                    }
                                    if (i == 5)
                                    {
                                        if (PayState == 1)
                                        {
                                            try
                                            {
                                                bool isok = B_SellTicketUpData.Cancel_Pay(this.parent.Client, this.parent.mSelfHelpClass.OrderID);
                                                isre = true;
                                            }
                                            catch { }
                                            break;
                                            //MessageBox.Show("         【      温      馨      提      示      】\r\n\r\n您当前购票交易失败！请重新购票支付或到人工售票网点购票！\r\n\r\n给您造成的不便深表歉意！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                                            //this.parent.EndOperation();
                                            //break;
                                        }
                                    }
                                }
                                //
                            }

                        }
                    }
                    //***************************支付成功后写入数据表中*********************
                    //this.parent.mSelfHelpClass.PayTypeID = T_SellTicketRecord.CreateTableUID(M_Configuration.CLIENTID, T_SellTicketRecord.LocalPayType_Prefix, 1);//支付单号 自助售票类
                    //this.parent.mSelfHelpClass.OrderID = T_SellTicketRecord.CreateTableUID(M_Configuration.CLIENTID, T_SellTicketRecord.LocalTicket_Prefix_ZG, 1);//销售单号
                    //this.parent.mSelfHelpClass.PayStat = "2";//支付状态(1-待支付 2-已支付 3-支付失败)
                    //flag5 = T_SellTicketRecord.Insert_GP_Data(this.parent.mSelfHelpClass);

                    //***************************支付成功后写入数据表中*********************
#endregion
                }
                else
                {
                    this.inputBox.Focus();
                }
            }
		}

		private void Page_Unloaded(object sender, RoutedEventArgs e)
		{
			this.ss.SpeakAsyncCancelAll();
		}

		private bool CreateCode()
		{
			bool result;
            try
            {
                bool flag = !PayHelper.IsLogin;
                if (flag)
                {
                    LoginResponseModel loginResponseModel;
                    PayHelper.Login(out loginResponseModel);
                }
                bool isLogin = PayHelper.IsLogin;
                if (isLogin)
                {
                    this.odNo = ConfigHelper.GetConfigValue("pay", "odNoHead").PadLeft(2, '0') + ConfigHelper.GetConfigValue("window", "deviceNo").PadLeft(2, '0') + ConfigHelper.GetConfigValue("window", "busNo").PadLeft(2, '0') + DateTime.Now.ToString("yyMMddHHmmss");
                 //   PayHelper.CodeSize = (int)this.zXingImg.Height;
                    string text;
                    BitmapSource source;
                    bool flag2 = PayHelper.CsbWeixinPay(this.odNo, this.parent.SelfHelpInfo.TicketPriceTotal * 100m, out this.refNo, out this.txnId, out text, out source);
                    bool flag3 = flag2;
                    if (flag3)
                    {
                      //  this.zXingImg.Source = source;
                        result = true;
                        return result;
                    }
                  //  this.zXingImg.Source = null;
                }
                result = false;
            }
            catch (Exception var_9_103)
            {
                result = false;
            }
            return result;
		}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/gpoperationpage/gppage5_2.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //    case 1:
        //        ((GPPage5_2)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        ((GPPage5_2)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
        //        break;
        //    case 2:
        //        this.dockfull = (StackPanel)target;
        //        break;
        //    case 3:
        //        this.ticketDate = (TextBlock)target;
        //        break;
        //    case 4:
        //        this.ticketType = (TextBlock)target;
        //        break;
        //    case 5:
        //        this.ticketCount = (TextBlock)target;
        //        break;
        //    case 6:
        //        this.ticketPriceTotal = (TextBlock)target;
        //        break;
        //    case 7:
        //        this.zXingImg = (Image)target;
        //        break;
        //    case 8:
        //        this.ticketTotal = (TextBlock)target;
        //        break;
        //    case 9:
        //        this.btn_SY = (Button)target;
        //        this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //        break;
        //    case 10:
        //        this.btn_FH = (Button)target;
        //        this.btn_FH.Click += new RoutedEventHandler(this.btn_FH_Click);
        //        break;
        //    default:
        //        this._contentLoaded = true;
        //        break;
        //    }
        //}
	}
}
