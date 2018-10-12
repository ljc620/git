using Common;
using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Speech.Synthesis;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using SelfHelpSystem.Tool;
using System.Configuration;
using SelfHelpSystem.Model;

namespace SelfHelpSystem
{
    public partial class GPPage3 : Page, IComponentConnector
	{
		private int MaxCount = 10;

		private MainWindow parent;

		private int nCount = 1;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

        //internal StackPanel dockfull;

        //internal StackPanel TicketImg;

        //internal TextBlock tb_selectdate;

        //internal TextBlock tb_selecttype;

        //internal Button btn_J;

        //internal TextBlock tb_Count;

        //internal Button btn_Z;

        //internal TextBlock tb_ShowTotal;

        //internal Button btn_GQ;

        //internal Button btn_SY;

        //internal Button btn_FH;

		public GPPage3(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
			//this.tb_selectdate.Text = this.parent.SelfHelpInfo.SaleTime;
           // this.tb_selectdate.Text = this.parent.mSelfHelpClass.SaleTime;//自助售票类 销售时间 即当时间
			ImageBrush imageBrush = new ImageBrush();
			//imageBrush.ImageSource = new BitmapImage(new Uri(AppDomain.CurrentDomain.BaseDirectory + "\\pic\\bn" + this.parent.SelfHelpInfo.TicketType + ".png"));
			imageBrush.Stretch = Stretch.Fill;
			this.TicketImg.Background = imageBrush;
			//this.tb_selectdate.Text = this.parent.SelfHelpInfo.SaleTime;
           // this.tb_selectdate.Text = this.parent.mSelfHelpClass.SaleTime;//自助售票类
			//this.tb_selecttype.Text = ConfigHelper.GetConfigValue("ticket", "t" + this.parent.SelfHelpInfo.TicketType);
            //*****获取票种名称
            this.tb_selecttype.Text = T_SellTicketRecord.GetTicketTypeName(this.parent.mSelfHelpClass.TicketTypeID);
            if ((this.tb_selecttype.Text.Length > 3) && (this.tb_selecttype.Text.Length < 6))
            {
                this.tb_selecttype.FontSize = 50;
            }
            else if (this.tb_selecttype.Text.Length > 6)
            {
                this.tb_selecttype.FontSize = 40;
            }
            else if (this.tb_selecttype.Text.Length < 3)
            {
                this.tb_selecttype.FontSize = 60;
            }

			//this.tb_ShowTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();
            this.tb_ShowTotal.Text = this.parent.mSelfHelpClass.RealSum.ToString();//自助售票类
            //*************显示票种图片
            string str = "";                  
            if (this.parent.mSelfHelpClass.TicketTypeID == M_Configuration.TicketTypeID_1)
            {
                 str = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\" + M_Configuration.TicketTypeID_1 + ".png";
                 if (System.IO.File.Exists(str))
                 {
                   //  BitmapImage imagetemp = new BitmapImage(new Uri(str, UriKind.Absolute));
                     this.img_show.Source = null;
                     this.img_show.Source = this.parent.GetImage(str); //imagetemp;
                 }
                 else
                 {
                     str = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\default.png";
                    // BitmapImage imagetemp = new BitmapImage(new Uri(str, UriKind.Absolute));
                     this.img_show.Source = null;
                     this.img_show.Source = this.parent.GetImage(str); //imagetemp;
                 }
                 this.txt_show.Text = M_Configuration.boxA_info;//票箱说明
            }
            else if (this.parent.mSelfHelpClass.TicketTypeID == M_Configuration.TicketTypeID_2)
            {
                str = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\" + M_Configuration.TicketTypeID_2 + ".png";
                if (System.IO.File.Exists(str))
                {
                   // BitmapImage imagetemp = new BitmapImage(new Uri(str, UriKind.Absolute));
                    this.img_show.Source = null;
                    this.img_show.Source = this.parent.GetImage(str); // imagetemp;
                }
                else
                {
                    str = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\default.png";
                    //BitmapImage imagetemp = new BitmapImage(new Uri(str, UriKind.Absolute));
                    this.img_show.Source = null;
                    this.img_show.Source = this.parent.GetImage(str); //imagetemp;
                }
                this.txt_show.Text = M_Configuration.boxB_info;//票箱说明
            }
		}

		private void btn_SY_Click(object sender, RoutedEventArgs e)
		{
			this.parent.EndOperation();
		}

		private void btn_FH_Click(object sender, RoutedEventArgs e)
		{
			this.parent.Operation("gp2");
		}

		private void btn_J_Click(object sender, RoutedEventArgs e)
		{
			this.nCount--;
            if (this.nCount < T_SellTicketRecord.MinTicketCount)//单次最小购票数
            {
                this.nCount = 1;
            }
           // this.txshow.Text = "购票信息-选择数量";
         //   this.txshow.Foreground = System.Windows.Media.Brushes.White;
            this.la_show.Visibility = Visibility.Hidden;
            //bool flag = this.nCount < 1;
            //if (flag)
            //{
            //    this.nCount = 1;
            //}
			this.tb_Count.Text = this.nCount.ToString().PadLeft(2, '0');
		//	this.parent.SelfHelpInfo.TicketCount = this.nCount;
            this.parent.mSelfHelpClass.TicketCount = this.nCount;//自助售票类
			//this.tb_ShowTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();
            this.tb_ShowTotal.Text = this.parent.mSelfHelpClass.RealSum.ToString();//自助售票类
		}

		private void btn_Z_Click(object sender, RoutedEventArgs e)
		{
            //this.tb_selecttype.FontSize = 50;
            if (T_SellTicketRecord.Judge_TicketAlarmCount(this.parent.mSelfHelpClass.TicketTypeID, this.nCount))
            {
                this.la_show.Visibility = Visibility.Visible;
                this.la_show.Content = "余票不足！";
              //  MessageBox.Show("所购票数已超过当前票箱允许的售票数！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }
           
            if (nCount >= T_SellTicketRecord.MaxTicketCount)
            {
              //  MessageBox.Show("票数已达单次最大购票数量！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
              //  this.txshow.Text = "购票信息-票数已达单次最大购票数量！";
              //  this.txshow.Foreground = System.Windows.Media.Brushes.Red;
                this.la_show.Visibility = Visibility.Visible;
                this.la_show.Content = "单次限购10张！";
                this.nCount = T_SellTicketRecord.MaxTicketCount;//单次最大购票数
            }
            else
            {
                this.nCount++;
              //  this.txshow.Text = "购票信息-选择数量";
             //   this.txshow.Foreground = System.Windows.Media.Brushes.White;
                if (this.nCount >= T_SellTicketRecord.MaxTicketCount)
                {
                    this.la_show.Visibility = Visibility.Visible;
                    this.la_show.Content = "单次限购10张！";
                }
                else
                {
                    this.la_show.Visibility = Visibility.Hidden;
                }
            }
            //bool flag = this.nCount > this.MaxCount;
            //if (flag)
            //{
            //    this.nCount = this.MaxCount;
            //}
            
			this.tb_Count.Text = this.nCount.ToString().PadLeft(2, '0');
			//this.parent.SelfHelpInfo.TicketCount = this.nCount;
            this.parent.mSelfHelpClass.TicketCount = this.nCount;//自助售票类
			//this.tb_ShowTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();
            this.tb_ShowTotal.Text = this.parent.mSelfHelpClass.RealSum.ToString();//自助售票类
		}

		private void btn_GQ_Click(object sender, RoutedEventArgs e)
		{
            //*************************将所选票种张数，金额写入临时销售主表中**************
            this.parent.mSelfHelpClass.OrderID = ""; //销售单号暂不生成 T_SellTicketRecord.CreateTableUID(M_Configuration.CLIENTID, T_SellTicketRecord.LocalTicket_Prefix_ZG, 1);//销售单号
            T_SellTicketRecord.slorder.OrderId = this.parent.mSelfHelpClass.OrderID;//销售单号
            T_SellTicketRecord.slorder.OrderType = T_SellTicketRecord.LocalTicket_Prefix_ZG;//销售类型 ZG 自助购票
         //   T_SellTicketRecord.slorder.TicketCount = this.parent.SelfHelpInfo.TicketCount;//销售张数

            T_SellTicketRecord.slorder.DueSum = this.parent.mSelfHelpClass.DueSum; //应收金额
            T_SellTicketRecord.slorder.RealSum = this.parent.mSelfHelpClass.RealSum; //实收金额
            T_SellTicketRecord.slorder.Remark = "自助售票";//订单说明
            T_SellTicketRecord.slorder.PayStat = "1";//支付状态(1-待支付 2-已支付 3-支付失败)
            T_SellTicketRecord.slorder.SaleTime = T_Date.ConvertDataTimeToLong(DateTime.Now);//售票时间
            T_SellTicketRecord.slorder.VersionNo = T_Date.ConvertDataTimeToLong(DateTime.Now);//版本号
            T_SellTicketRecord.slorder.SaleUserId =Convert.ToString(M_Configuration.CLIENTID);//售票人存为终端编号

            //*************************将所选票种张数，金额写入临时销售主表中**************

            //******************将销售信息写入临时销售主表
            #region//待删除
            //if (T_SellTicketRecord.Inert_SL_ORDER_Temp(T_SellTicketRecord.slorder))
            //{
            //    this.parent.Operation("gp4");
            //}
            //else
            //{
            //    MessageBox.Show("票据信息保存失败！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
            //    return;
            //}
#endregion
            if (T_SellTicketRecord.Judge_TicketAlarmCount(this.parent.mSelfHelpClass.TicketTypeID, this.nCount))
            {
                MessageBox.Show("所购票数已超过当前票箱允许的售票数！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }
            else
            {
                this.parent.Operation("gp4");
            }
		}

		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
			this.MaxCount = 10;
			//this.parent.SelfHelpInfo.TicketCount = this.nCount;
            this.parent.mSelfHelpClass.TicketCount = this.nCount;//自助售票类
			this.nCount = 1;
			//this.tb_ShowTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();
            this.tb_ShowTotal.Text = this.parent.mSelfHelpClass.RealSum.ToString();//自助售票类
			this.tb_Count.Text = this.nCount.ToString().PadLeft(2, '0');
			//this.ss.SelectVoiceByHints(VoiceGender.Female);
			//this.ss.SpeakAsync(ConfigHelper.GetConfigValue("speechqp", "ticketcount"));
		}

		private void Page_Unloaded(object sender, RoutedEventArgs e)
		{
			this.ss.SpeakAsyncCancelAll();
		}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/gpoperationpage/gppage3.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //    case 1:
        //        ((GPPage3)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        ((GPPage3)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
        //        break;
        //    case 2:
        //        this.dockfull = (StackPanel)target;
        //        break;
        //    case 3:
        //        this.TicketImg = (StackPanel)target;
        //        break;
        //    case 4:
        //        this.tb_selectdate = (TextBlock)target;
        //        break;
        //    case 5:
        //        this.tb_selecttype = (TextBlock)target;
        //        break;
        //    case 6:
        //        this.btn_J = (Button)target;
        //        this.btn_J.Click += new RoutedEventHandler(this.btn_J_Click);
        //        break;
        //    case 7:
        //        this.tb_Count = (TextBlock)target;
        //        break;
        //    case 8:
        //        this.btn_Z = (Button)target;
        //        this.btn_Z.Click += new RoutedEventHandler(this.btn_Z_Click);
        //        break;
        //    case 9:
        //        this.tb_ShowTotal = (TextBlock)target;
        //        break;
        //    case 10:
        //        this.btn_GQ = (Button)target;
        //        this.btn_GQ.Click += new RoutedEventHandler(this.btn_GQ_Click);
        //        break;
        //    case 11:
        //        this.btn_SY = (Button)target;
        //        this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //        break;
        //    case 12:
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
