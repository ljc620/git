using Common;
using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Speech.Synthesis;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;

namespace SelfHelpSystem
{
    public partial class GPPage5_1 : Page, IComponentConnector
	{
		private MainWindow parent;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

		public GPPage5_1(MainWindow parentWindow)
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

		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
			this.parent.SelfHelpInfo.PayType = "bank";
			this.ticketDate.Text = this.parent.SelfHelpInfo.SaleTime;
			this.ticketCount.Text = this.parent.SelfHelpInfo.TicketCount.ToString();
			this.ticketType.Text = ConfigHelper.GetConfigValue("ticket", "t" + this.parent.SelfHelpInfo.TicketType);
			this.ticketPriceTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();
			bool flag = !this.CheckMachine();
			if (flag)
			{
				this.parent.WarmOperation("暂时无法使用此功能！请重新选择其他付款方式！", "gp4");
			}
			else
			{
			//	this.ss.SelectVoiceByHints(VoiceGender.Female);
			//	this.ss.SpeakAsync(ConfigHelper.GetConfigValue("speechqp", "paybankcard"));
				//this.web.Source = new Uri(AppDomain.CurrentDomain.BaseDirectory + "flash\\f1.swf");
			}
		}

		public bool CheckMachine()
		{
			return false;
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
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/gpoperationpage/gppage5_1.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //    case 1:
        //        ((GPPage5_1)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        ((GPPage5_1)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
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
        //        this.web = (WebBrowser)target;
        //        break;
        //    case 8:
        //        this.btn_SY = (Button)target;
        //        this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //        break;
        //    case 9:
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
