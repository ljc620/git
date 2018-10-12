using Common;
using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Speech.Synthesis;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using SelfHelpSystem.Tool;
using System.Configuration;
using SelfHelpSystem.Model;

namespace SelfHelpSystem
{
    public partial class GPPage4 : Page, IComponentConnector
	{
		private MainWindow parent;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

		public GPPage4(MainWindow parentWindow)
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
			this.parent.Operation("gp3");
		}

		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
         //   this.parent.SelfHelpInfo.PayType = "";
            this.parent.mSelfHelpClass.PayType = "";//支付方式 自助售票类
			//this.ticketDate.Text = this.parent.SelfHelpInfo.SaleTime;
          //  this.ticketDate.Text = this.parent.mSelfHelpClass.SaleTime;//销售时间 自助售票类
		//	this.ticketType.Text = ConfigHelper.GetConfigValue("ticket", "t" + this.parent.SelfHelpInfo.TicketType);
            if (this.parent.mSelfHelpClass.BoxNumber == "A")
            {
                this.ticketType.Text = T_SellTicketRecord.GetTicketTypeName(M_Configuration.TicketTypeID_1);//票种 自助售票类\
            }
            else if (this.parent.mSelfHelpClass.BoxNumber == "B")
            {
                this.ticketType.Text = T_SellTicketRecord.GetTicketTypeName(M_Configuration.TicketTypeID_2);//票种 自助售票类\
            }
		//	this.ticketCount.Text = this.parent.SelfHelpInfo.TicketCount.ToString();
            this.ticketCount.Text = this.parent.mSelfHelpClass.TicketCount.ToString();//票数 自助售票类
			//this.ticketPriceTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();
            this.ticketPriceTotal.Text = this.parent.mSelfHelpClass.RealSum.ToString();//销售金额 自助售票类
			//this.ss.SelectVoiceByHints(VoiceGender.Female);
		//	string configValue = ConfigHelper.GetConfigValue("speechqp", "paytype");
		//	this.ss.SpeakAsync(configValue);

		}

		private void btn_Click(object sender, RoutedEventArgs e)
		{
			Button button = sender as Button;
            if (Convert.ToInt32(button.Tag) != 1)//微信支付与支付宝支付情况
            {
               // this.parent.SelfHelpInfo.PayType = ConfigHelper.GetConfigValue("pay", "p" + button.Tag.ToString());
                this.parent.mSelfHelpClass.PayType = ConfigurationManager.AppSettings["P" + Convert.ToString((Convert.ToInt32(button.Tag) - 1))].ToString();//支付方式 自助售票类
                this.parent.Operation("gp5_" + button.Tag.ToString());
            }
            else if (Convert.ToInt32(button.Tag) == 1)//身份证购票的情况
            {
                this.parent.Operation("qp2_" + button.Tag.ToString());
            }
            if (this.parent.countSecond < 90)
            {
                this.parent.countSecond = 90;
            }
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
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/gpoperationpage/gppage4.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //    case 1:
        //        ((GPPage4)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        ((GPPage4)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
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
        //        this.btn_1 = (Button)target;
        //        this.btn_1.Click += new RoutedEventHandler(this.btn_Click);
        //        break;
        //    case 8:
        //        this.btn_2 = (Button)target;
        //        this.btn_2.Click += new RoutedEventHandler(this.btn_Click);
        //        break;
        //    case 9:
        //        this.btn_3 = (Button)target;
        //        this.btn_3.Click += new RoutedEventHandler(this.btn_Click);
        //        break;
        //    case 10:
        //        this.btn_SY = (Button)target;
        //        this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //        break;
        //    case 11:
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
