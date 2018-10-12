using Common;
using Model;
using SenderCardLibrary;
using System;
using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Shapes;
using System.Windows.Threading;
using System.Configuration;
using tbims.rpc.entity;
using SelfHelpSystem.Tool;
using SelfHelpSystem.Model;
using log4net;
using System.Threading;
using TBIMS.SCRLib;
using System.Text;
using System.Windows.Media.Imaging;

namespace SelfHelpSystem
{
    partial class MainWindow : Window, IComponentConnector
	{
        //RPC
        public T_RpcClient Client = null;//购票取票业务数据上传
        //public T_RpcClient StateClient = null;//时时上传自助售票机运行状态及票箱余票的RPC
        //public T_RpcClient HeartClient = null;//心跳
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");

        private Thread _DataSyncThread;
        private T_ThreadDeal _theadDeal = new T_ThreadDeal();

        

        //public T_TicketCountTread TicketcountThread = null;
        //public Thread StateThread = null;

        public T_GCThread TGCThread = null;
        public Thread GCThread = null;
		
		public OldSenderCardControl senderCardControl;

        public string Err_Info = null;

		private string MacStr = string.Empty;

		private string currentErrString = string.Empty;

		public int countSecond = 0;

		private DispatcherTimer countdowntimer;

		private DateTime shutdownTime;

		private int totaltimeout = 120;

		public SelfHelpClass SelfHelpInfo;

        public M_SelfHelpClass mSelfHelpClass;//自助售票类

		private VideoPage videopage = null;

		private ImageShowPage imagepage = null;

		private Page operationpage = null;
        private Page pageTest = null;

		private NoticePage notice = null;


		private Dictionary<int, Ellipse> movingEllipses = new Dictionary<int, Ellipse>();

		private Point startPositon;

		private Point endPositon;

		private int escCount = 0;

        public RfidTicketHandler rfidReaderTest;
        public int ticketbox_isErr = 0;//两票种放反为1


        //private Thread thread_test = null;//
        //internal Canvas mainCanvas;

        //internal Label lblSecond;

        //internal Grid mainGrid;

        //internal RowDefinition row1;

        //internal RowDefinition row2;

        //internal RowDefinition row3;

        //internal WrapPanel TopPanel;

        //internal Frame topFrame;

        //internal WrapPanel MiddlePanel;

        //internal Frame middleFrame;

        //internal DockPanel BottomPanel;

        //internal Frame mainFrame;


		public event PropertyChangedEventHandler PropertyChanged;

		private void StartSecond()
		{
			this.countSecond = this.totaltimeout;
			this.lblSecond.Visibility = Visibility.Visible;
		}

		private void StopSecond()
		{
            this.lblSecond.Content = "";
            this.lblSecond.Visibility = Visibility.Hidden;
			this.countSecond = this.totaltimeout;
		}

		public void ResumeSecond()
		{
			this.lblSecond.Visibility = Visibility.Hidden;
		}

		public void RestartSecond()
		{
			this.lblSecond.Visibility = Visibility.Visible;
		}

		private void timer_Tick(object sender, EventArgs e)
		{
            try
            {
                this.currentErrString = string.Empty;
                bool flag = this.lblSecond.Visibility != Visibility.Hidden;
                if (flag)
                {
                    bool flag2 = this.countSecond == 0;
                    if (flag2)
                    {
                        this.EndOperation();
                    }
                    else
                    {
                        bool flag3 = this.lblSecond.Dispatcher.CheckAccess();
                        if (flag3)
                        {
                            this.lblSecond.Content = this.countSecond.ToString();
                        }
                        else
                        {
                            this.lblSecond.Dispatcher.BeginInvoke(DispatcherPriority.Normal, new Action(delegate
                            {
                                this.lblSecond.Content = this.countSecond.ToString();
                            }));
                        }
                        this.countSecond--;
                    }
                }
            }
            catch(Exception ex)
            {
                this._log.Error(this.GetType().ToString(), ex);
            }
            //bool flag4 = DateTime.Now > this.shutdownTime;
            //if (flag4)
            //{
            //}
            //string empty = string.Empty;
            //bool flag5 = true;// !this.CheckSystemState(out empty);
            //if (flag5)
            //{
            //    this.currentErrString += empty;
            //}
            //bool flag6 = true;// !this.senderCardControl.CheckMachine(out empty);
            //if (flag6)
            //{
            //    this.currentErrString += "发卡机故障，无法使用";
            //}
            //bool flag7 = this.currentErrString != string.Empty;
            //if (!flag7)
            //{
            //    this.operationpage = new FirstPage(this);
            //    this.mainFrame.Content = this.operationpage;
            //}
            #region//心跳 暂屏
            /*
            bool flag8 = false;
            try
            {
                flag8 = HeartClient.pingRpc();
            }
            catch (Exception ex)
            {
                _log.Error(ex.Message.ToString());
            }
            if (!flag8)
            {
                _log.Error("服务连接断开！");
            }*/
           #endregion
		}

		public void OnPropertyChanged(string propertyName)
		{
			bool flag = this.PropertyChanged != null;
			if (flag)
			{
				this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
			}
		}

		//[DllImport("user32.dll")]
		//private static extern bool ShowWindowAsync(IntPtr hWnd, int nCmdShow);

		//[DllImport("user32.dll", SetLastError = true)]
		//private static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

		//[DllImport("user32.dll", SetLastError = true)]
		//private static extern long SetParent(IntPtr hWndChild, IntPtr hWndNewParent);

		//[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		//private static extern int SendMessage(IntPtr hwnd, uint wMsg, int wParam, int lParam);

		public MainWindow()
		{
			this.InitializeComponent();
		//	this.SelfHelpInfo = new SelfHelpClass();
            this.mSelfHelpClass = new M_SelfHelpClass();//自助售票类
            string configValue = ConfigHelper.GetConfigValue("window", "autoPowerOff");
            bool flag = !DateTime.TryParse(configValue, out this.shutdownTime);
            if (flag)
            {
                this.shutdownTime = DateTime.Parse(DateTime.Now.ToString("yyyy-MM-dd 18:00:00"));
            }
         //   this.senderCardControl = new OldSenderCardControl(ConfigHelper.GetConfigValue("comport", "senderPort"), int.Parse(ConfigHelper.GetConfigValue("comport", "senderBaudRate")), ConfigHelper.GetConfigValue("comport", "CardPort"), int.Parse(ConfigHelper.GetConfigValue("comport", "CardBaudRate")));
            _log.Info("MainWindow启动");
        }

		private void Window_Loaded(object sender, RoutedEventArgs e)
		{
            try
            {
                WinSleepCtr.PreventScreenSave();
                WinSleepCtr.PreventSleep(true);

                //*******************端口连接部分            
                T_ReadConfig.ReadConfig();//读取配置文件
                T_ReadConfig.GetPIN();//获取PIN码
                AUTHORIZATION auth = new AUTHORIZATION();
                auth.ClientId = M_Configuration.CLIENTID;//终端号
                auth.Token = M_Configuration.TOKEN;     //ConfigurationManager.AppSettings["Token"].ToString();//授权码
                Client = new T_RpcClient(auth, M_Configuration.HOST, M_Configuration.PORT, M_Configuration.TIMEOUT);
                //StateClient = new T_RpcClient(auth, M_Configuration.HOST, M_Configuration.PORT, M_Configuration.TIMEOUT);
               // HeartClient = new T_RpcClient(auth, M_Configuration.HOST, M_Configuration.PORT, M_Configuration.TIMEOUT);//心跳
                //票种名称
                 M_Configuration.RunState = 0;//自助售票运行状态  0表示正常
                 M_Configuration.TicketID_AName = T_SellTicketRecord.GetTicketTypeName(M_Configuration.TicketTypeID_1);
                 M_Configuration.TicketID_BName = T_SellTicketRecord.GetTicketTypeName(M_Configuration.TicketTypeID_2);

                #region//暂时不用
                ////获取票种信息
                //try
                //{            
                //    M_TicketTypeList.listTicketType = Client.TicketTypeSyncRpc(T_Date.ConvertDataTimeToLong(DateTime.Now));

                //}
                //catch (Exception ex)
                //{
                //    _log.Error(typeof(MainWindow), ex);
                //}
                ////获取售售期信息
                //try
                //{
                //    M_PeriodList.listPeriod = Client.SalePeriodSyncRpc(T_Date.ConvertDataTimeToLong(DateTime.Now));
                //}
                //catch (Exception ex)
                //{
                //    _log.Error(typeof(MainWindow), ex);
                //}
                #endregion
                ////启动数据同步线程
                _DataSyncThread = new Thread(new ThreadStart(_theadDeal.DataSyncThread));
                _DataSyncThread.Name = "DataSyncThread";
                _DataSyncThread.IsBackground = true;
                _DataSyncThread.Start();
                _DataSyncThread.Priority = ThreadPriority.BelowNormal;
                Thread.Sleep(10);
                if (_DataSyncThread.IsAlive)
                    _log.Info("数据同步工作线程启动成功。");
                ////*******************端口连接部分


                //强制回收线程
                TGCThread = new T_GCThread();
                GCThread = new Thread(new ThreadStart(TGCThread.DoWork));
                GCThread.IsBackground = true;
                GCThread.Start();
                if (GCThread.IsAlive)
                {
                    _log.Error("强制回收线程启动成功!");
                }

                string strSAM = M_Configuration.Samreadername;
                string strRFID = M_Configuration.RfidReadername;
                string strPIN = M_Configuration.Pin;
                try
                {
                    rfidReaderTest = TicketHandlerAdapter.getRfidTicketHandler(Convert.ToInt32(M_Configuration.CLIENTID), strRFID, strSAM, Encoding.ASCII.GetBytes(strPIN));
                }
                catch
                {
                 //   MessageBox.Show("初始化失败，请确认读卡器选择正确！");
                }

                this.totaltimeout = int.Parse(ConfigHelper.GetConfigValue("window", "totaltimeout"));//操作最长时间
                this.InitControlSize();
                base.WindowState = WindowState.Maximized;
                base.WindowStyle = WindowStyle.None;
                base.ResizeMode = ResizeMode.NoResize;
                base.Left = 0.0;
                base.Top = 0.0;
                this.notice = new NoticePage(ConfigHelper.GetConfigValue("window", "notice"), double.Parse(ConfigHelper.GetConfigValue("window", "noticefontsize")));
                this.topFrame.Content = this.notice;
                //this.videopage = new VideoPage(this);
                //this.topFrame.Content = this.videopage;
                this.imagepage = new ImageShowPage(this);
                this.middleFrame.Content = this.imagepage;
                pageTest = new FirstPage(this);
                this.operationpage = pageTest;//new FirstPage(this);
                this.mainFrame.Content = this.operationpage;
                
                //bool flag = SystemInfo.GetAllNetInfo() != null;
                //if (flag)
                //{
                //    this.MacStr = SystemInfo.GetAllNetInfo()[0].PhysAddr;
                //}
                this.countdowntimer = new DispatcherTimer();
                this.countdowntimer.Interval = new TimeSpan(0, 0, 0, 1, 0);
                this.countdowntimer.Tick += new EventHandler(this.timer_Tick);
                this.countdowntimer.Start();


            }
            catch (Exception ex)
            {                
                _log.Error("服务器连接失败!");
                _log.Error(typeof(MainWindow), ex);
                MessageBox.Show("服务器连接失败！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                base.Close();
            }
		}


		private bool CheckSystemState(out string errorInfo)
        {
            errorInfo = string.Empty;
            bool flag = SystemInfo.GetAllNetInfo() != null;
            bool result;
            if (flag)
            {
                this.MacStr = SystemInfo.GetAllNetInfo()[0].PhysAddr;
                result = true;
            }
            else
            {
                errorInfo = "未联网,系统无法运行！";
                result = false;
            }
            return result;
		}

		public void StartOperation(string step)
		{
            try
            {
                //bool flag = this.SelfHelpInfo == null;
                //if (flag)
                //{
                //    //this.SelfHelpInfo = new SelfHelpClass();
                //    this.mSelfHelpClass = new M_SelfHelpClass();//自助售票类
                //}
                bool flag1 = this.mSelfHelpClass == null;
                if (flag1)
                {
                    this.mSelfHelpClass = new M_SelfHelpClass();//自助售票类
                }
                this.StartSecond();
                this.row2.Height = new GridLength(0.0);
                this.row3.Height = new GridLength(this.mainGrid.Height - this.row1.Height.Value);
                this.Operation(step);
                this.operationpage.Height = this.row3.Height.Value;
                this.imagepage.imageThread.Stop();
                //this.videopage.Pause();
                this.topFrame.Content = this.notice;
            }
            catch(Exception ex)
            {
                this._log.Error("切换显示页面时发生错误：" + this.GetType().ToString(), ex);
            }
		}

		public void EndOperation()
		{
            try
            {
                this.InitControlSize();
                this.operationpage.Height = this.row3.Height.Value;

                this.operationpage = pageTest; //new FirstPage(this);
                this.mainFrame.Content = this.operationpage;
                this.imagepage.imageThread.Start();
                //this.topFrame.Content = this.videopage;
                //this.videopage.Resume();
                this.StopSecond();
                //this.SelfHelpInfo = new SelfHelpClass();
                this.mSelfHelpClass = new M_SelfHelpClass();//自助售票类
            }
            catch(Exception ex)
            {
                this._log.Error("返回首页时发生错误：" + this.GetType().ToString(), ex);
            }
        }

		public void WarmOperation(string warmInfo, string pageName)
		{
            try
            {
                this.operationpage = new WarmPage(this, warmInfo, pageName);
                this.row2.Height = new GridLength(0.0);
                this.row3.Height = new GridLength(this.mainGrid.Height - this.row1.Height.Value);
                this.operationpage.Height = this.row3.Height.Value;
                this.imagepage.imageThread.Stop();
                this.mainFrame.Content = this.operationpage;
            }
            catch (Exception ex)
            {
                this._log.Error("显示提示信息页时发生错误：" + this.GetType().ToString(), ex);
            }
        }

		public void WarmOperation(string warmInfo, string pageName, bool countDown)
		{
           try
           {
                this.operationpage = new WarmPage(this, warmInfo, pageName, countDown);
                this.row2.Height = new GridLength(0.0);
                this.row3.Height = new GridLength(this.mainGrid.Height - this.row1.Height.Value);
                this.operationpage.Height = this.row3.Height.Value;
                this.imagepage.imageThread.Stop();
                this.mainFrame.Content = this.operationpage;
            }
            catch (Exception ex)
            {
                this._log.Error("显示提示信息页时发生错误：" + this.GetType().ToString(), ex);
            }
        }

        public void ErrorOperation(string errlogString)
		{
			this.InitControlSize();
			string errString = "系统出现故障，暂时无法使用本设备！";
            this.operationpage.Height = this.row3.Height.Value;
            this.operationpage = new ErrorPage(this, errString, errlogString);
            this.mainFrame.Content = this.operationpage;
            this.imagepage.imageThread.Start();
            //this.topFrame.Content = this.videopage;
            //this.videopage.Resume();
			this.StopSecond();
			//this.SelfHelpInfo = new SelfHelpClass();
         //   mSelfHelpClass = new M_SelfHelpClass();//自助售票类  不重新创建呢
		}

		public void Operation(string step)
		{
            try
            {
                this.mainFrame.Content = null;
                bool flag = step == "gp1";
                if (flag)
                {
                    this.operationpage = new GPPage1(this);
                    this.mainFrame.Content = this.operationpage;
                }
                else
                {
                    bool flag2 = step == "gp2";
                    if (flag2)
                    {
                        this.operationpage = new GPPage2(this);
                        this.mainFrame.Content = this.operationpage;
                    }
                    else
                    {
                        bool flag3 = step == "gp3";
                        if (flag3)
                        {
                            this.operationpage = new GPPage3(this);
                            this.mainFrame.Content = this.operationpage;
                        }
                        else
                        {
                            bool flag4 = step == "gp4";
                            if (flag4)
                            {
                                this.operationpage = new GPPage4(this);
                                this.mainFrame.Content = this.operationpage;
                            }
                            else
                            {
                                bool flag5 = step == "gp5_1";
                                if (flag5)
                                {
                                    this.operationpage = new GPPage5_1(this);
                                    this.mainFrame.Content = this.operationpage;
                                }
                                else
                                {
                                    bool flag6 = step == "gp5_2";
                                    if (flag6)
                                    {
                                        this.operationpage = new GPPage5_2(this);
                                        this.mainFrame.Content = this.operationpage;
                                    }
                                    else
                                    {
                                        bool flag7 = step == "gp5_3";
                                        if (flag7)
                                        {
                                            this.operationpage = new GPPage5_3(this);
                                            this.mainFrame.Content = this.operationpage;
                                        }
                                        else
                                        {
                                            bool flag8 = step == "gp6";
                                            if (flag8)
                                            {
                                                this.operationpage = new GPPage6(this);
                                                this.mainFrame.Content = this.operationpage;
                                            }
                                            else
                                            {
                                                bool flag9 = step == "gp7";
                                                if (flag9)
                                                {
                                                    this.operationpage = new GPPage7(this);
                                                    this.mainFrame.Content = this.operationpage;
                                                }
                                                else
                                                {
                                                    bool flag10 = step == "qp1";
                                                    if (flag10)
                                                    {
                                                        this.operationpage = new QPPage1(this);
                                                        this.mainFrame.Content = this.operationpage;
                                                    }
                                                    else
                                                    {
                                                        bool flag11 = step == "qp2_1";
                                                        if (flag11)
                                                        {
                                                            this.operationpage = new QPPage2_1(this);
                                                            this.mainFrame.Content = this.operationpage;
                                                        }
                                                        else
                                                        {
                                                            bool flag12 = step == "qp2_2";
                                                            if (flag12)
                                                            {
                                                                this.operationpage = new QPPage2_2(this);
                                                                this.mainFrame.Content = this.operationpage;
                                                            }
                                                            else
                                                            {
                                                                bool flag13 = step == "qp2_3";
                                                                if (flag13)
                                                                {
                                                                    this.operationpage = new QPPage2_3(this);
                                                                    this.mainFrame.Content = this.operationpage;
                                                                }
                                                                else
                                                                {
                                                                    bool flag14 = step == "qp3";
                                                                    if (flag14)
                                                                    {
                                                                        this.operationpage = new QPPage3(this);
                                                                        this.mainFrame.Content = this.operationpage;
                                                                    }
                                                                    else
                                                                    {
                                                                        bool flag15 = step == "error";
                                                                        if (flag15)
                                                                        {
                                                                            // this.ErrorOperation(string.Empty);
                                                                            this.operationpage = new WarmPage(this, this.Err_Info, "");
                                                                            this.mainFrame.Content = this.operationpage;
                                                                        }
                                                                        else
                                                                        {
                                                                            bool flag16 = step == "setting";
                                                                            if (flag16)
                                                                            {
                                                                                this.operationpage = new Page_Setting(this);
                                                                                this.mainFrame.Content = this.operationpage;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                this._log.Error("切换显示页面时发生错误：" + this.GetType().ToString(), ex);
            }
        }

        private void InitControlSize()
		{
            string configValue = ConfigHelper.GetConfigValue("window", "controlsize");
            int num;
            bool flag = configValue == "" || !int.TryParse(configValue.Replace(",", ""), out num);
            if (flag)
            {
                this.mainGrid.Height = 1920.0;
                this.mainGrid.Width = 1080.0;
            }
            else
            {
                string[] array = configValue.Split(new string[]
                {
                    ","
                }, StringSplitOptions.RemoveEmptyEntries);
                bool flag2 = array.Length == 2;
                if (flag2)
                {
                    this.mainCanvas.Width = double.Parse(array[0]);
                    this.mainCanvas.Height = double.Parse(array[1]);
                    this.mainGrid.Width = double.Parse(array[0]);
                    this.mainGrid.Height = double.Parse(array[1]);
                }
                else
                {
                    this.mainGrid.Height = 1920.0;
                    this.mainGrid.Width = 1080.0;
                }
            }
            string configValue2 = ConfigHelper.GetConfigValue("window", "controlheight");
            bool flag3 = configValue2 == "" || !int.TryParse(configValue2.Replace(",", ""), out num);
            if (flag3)
            {
                this.row1.Height = new GridLength(608.0);
                this.row2.Height = new GridLength(662.0);
                this.row3.Height = new GridLength(650.0);
            }
            else
            {
                string[] array2 = configValue2.Split(new string[]
                {
                    ","
                }, StringSplitOptions.RemoveEmptyEntries);
                bool flag4 = array2.Length == 3;
                if (flag4)
                {
                    this.row1.Height = new GridLength(double.Parse(array2[0]));
                    this.row2.Height = new GridLength(double.Parse(array2[1]));
                    this.row3.Height = new GridLength(double.Parse(array2[2]));
                }
                else
                {
                    this.row1.Height = new GridLength(608.0);
                    this.row2.Height = new GridLength(662.0);
                    this.row3.Height = new GridLength(650.0);
                }
            }
           // _log.Error("InitControlSize");
		}
        //************图片释放
        public  BitmapImage GetImage(string imagePath)
        {
            BitmapImage bitmap = new BitmapImage();

            if (System.IO.File.Exists(imagePath))
            {
                bitmap.BeginInit();
                bitmap.CacheOption = BitmapCacheOption.OnLoad;
                using (System.IO.Stream ms = new System.IO.MemoryStream(System.IO.File.ReadAllBytes(imagePath)))
                {
                    bitmap.StreamSource = ms;
                    bitmap.EndInit();
                    bitmap.Freeze();
                }
            }

            return bitmap;
        } 

		private void Window_Closed(object sender, EventArgs e)
		{
            WinSleepCtr.ResotreSleep();
            WinSleepCtr.RestoreScreenSave();

            _theadDeal.Stop();
            base.Hide();
            if(this.videopage!=null)
                this.videopage.Close();
		}

		private void TopPanel_MouseDown(object sender, MouseButtonEventArgs e)
		{
			this.startPositon = e.GetPosition(this.TopPanel);
		}

		private void TopPanel_MouseUp(object sender, MouseButtonEventArgs e)
		{
			this.endPositon = e.GetPosition(this.TopPanel);
			bool flag = this.startPositon.X > 900.0 && this.startPositon.Y < 180.0;
			if (flag)
			{
				bool flag2 = this.endPositon.X < 180.0 && this.endPositon.Y < 680.0 && this.endPositon.Y > 500.0;
				if (flag2)
				{
					base.Close();
				}
			}
		}


		private void Window_KeyUp(object sender, KeyEventArgs e)
		{
			bool flag = e.Key == Key.Escape;
			if (flag)
			{
				this.escCount++;
				bool flag2 = this.escCount > 2;
				if (flag2)
				{
					base.Close();
				}
			}
			else
			{
				this.escCount = 0;
			}
            //if ((e.KeyboardDevice.IsKeyDown(System.Windows.Input.Key.RightCtrl) || e.KeyboardDevice.IsKeyDown(System.Windows.Input.Key.LeftCtrl)) && e.KeyboardDevice.IsKeyDown(System.Windows.Input.Key.Q))
            if(e.Key==Key.F1)
            {
                this.Operation("setting");
            }
		}

		private void mediaElement1_MediaOpened(object sender, RoutedEventArgs e)
		{
		}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/mainwindow.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //        case 1:
        //            ((MainWindow)target).Loaded += new RoutedEventHandler(this.Window_Loaded);
        //            ((MainWindow)target).Closed += new EventHandler(this.Window_Closed);
        //            ((MainWindow)target).KeyUp += new KeyEventHandler(this.Window_KeyUp);
        //            break;
        //        case 2:
        //            this.mainCanvas = (Canvas)target;
        //            break;
        //        case 3:
        //            this.lblSecond = (Label)target;
        //            break;
        //        case 4:
        //            this.mainGrid = (Grid)target;
        //            break;
        //        case 5:
        //            this.row1 = (RowDefinition)target;
        //            break;
        //        case 6:
        //            this.row2 = (RowDefinition)target;
        //            break;
        //        case 7:
        //            this.row3 = (RowDefinition)target;
        //            break;
        //        case 8:
        //            this.TopPanel = (WrapPanel)target;
        //            this.TopPanel.MouseDown += new MouseButtonEventHandler(this.TopPanel_MouseDown);
        //            this.TopPanel.MouseUp += new MouseButtonEventHandler(this.TopPanel_MouseUp);
        //            break;
        //        case 9:
        //            this.topFrame = (Frame)target;
        //            break;
        //        case 10:
        //            this.MiddlePanel = (WrapPanel)target;
        //            break;
        //        case 11:
        //            this.middleFrame = (Frame)target;
        //            break;
        //        case 12:
        //            this.BottomPanel = (DockPanel)target;
        //            break;
        //        case 13:
        //            this.mainFrame = (Frame)target;
        //            break;
        //        default:
        //            this._contentLoaded = true;
        //            break;
        //    }
        //}
	}

    static class WinSleepCtr
    {
        #region 阻止系统休眠
        //定义API函数
        [DllImport("kernel32.dll")]
        static extern uint SetThreadExecutionState(uint esFlags);
        const uint ES_SYSTEM_REQUIRED = 0x00000001;
        const uint ES_DISPLAY_REQUIRED = 0x00000002;
        const uint ES_CONTINUOUS = 0x80000000;

        /// <summary>
        /// 阻止系统休眠，直到线程结束恢复休眠策略
        /// </summary>
        /// <param name="includeDisplay">是否阻止关闭显示器</param>
        public static void PreventSleep(bool includeDisplay = false)
        {
            if (includeDisplay)
                SetThreadExecutionState(ES_CONTINUOUS | ES_DISPLAY_REQUIRED | ES_SYSTEM_REQUIRED);
            else
                SetThreadExecutionState(ES_CONTINUOUS | ES_SYSTEM_REQUIRED);
        }
        /// <summary>
        /// 恢复系统休眠策略
        /// </summary>
        public static void ResotreSleep()
        {
            SetThreadExecutionState(ES_CONTINUOUS);
        }
        #endregion

        #region 阻止系统屏保
        [DllImport("user32.dll")]
        static extern bool SystemParametersInfo(uint uiAction, bool uiParam, ref bool pvParam, uint fWinIni);

        const uint SPI_GETSCREENSAVEACTIVE = 0x0010;
        const uint SPI_SETSCREENSAVEACTIVE = 0x0011;
        const uint SPIF_SENDCHANGE = 0x0002;
        const uint SPIF_SENDWININICHANGE = SPIF_SENDCHANGE;

        /// <summary>
        /// 阻止系统屏保
        /// </summary>
        public static void PreventScreenSave()
        {
            //调用，其中函数内的false才是起作用的设置，active变量是在读取设置的时候使用的，这里没有实际意义。  
            bool active = false;
            SystemParametersInfo(SPI_SETSCREENSAVEACTIVE, false, ref active, SPIF_SENDWININICHANGE);
        }
        /// <summary>
        /// 恢复系统屏保策略
        /// </summary>
        public static void RestoreScreenSave()
        {
            bool active = false;
            SystemParametersInfo(SPI_SETSCREENSAVEACTIVE, true, ref active, SPIF_SENDWININICHANGE);
        }

        #endregion
    }

}
