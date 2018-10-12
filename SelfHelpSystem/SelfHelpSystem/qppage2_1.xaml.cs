using GSFramework;
using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.Speech.Synthesis;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using SelfHelpSystem.Model;
//using ShockwaveFlashObjects;
using Microsoft.Win32;


namespace SelfHelpSystem
{
    public partial class QPPage2_1 : Page, IComponentConnector
	{
		private delegate void SetPlayHandler(string inputinfo);

		private MainWindow parent;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

		public QPPage2_1(MainWindow parentWindow)
		{
			this.parent = parentWindow;
            this.InitializeComponent();
            string str = this.parent.mSelfHelpClass.OperType;
			this.readcard.OnDataBind += new ReadCardActiveX.ReadDataHandler(this.Readcard_OnDataBind);
		}

		private void Readcard_OnDataBind(string Name, string Gender, string Folk, string BirthDay, string Code, string Address, string Agency, string ExpireStart, string ExpireEnd, string ImageBase64String)
		{
			byte[] buffer = new byte[ImageBase64String.Length];
			buffer = Convert.FromBase64String(ImageBase64String);
			MemoryStream memoryStream = new MemoryStream(buffer);
			this.parent.SelfHelpInfo.GetTicketUserNumber = Code;
            this.parent.mSelfHelpClass.GetTicketUserNumber = Code;//自助售票类
            M_Configuration.IDCard = Code;//身份证号
			this.RefMain(Code);
		}

		private void RefMain(string inputinfo)
		{
			base.Dispatcher.BeginInvoke(new QPPage2_1.SetPlayHandler(this.CheckFunc), new object[]
			{
				inputinfo
			});
		}

		private void CheckFunc(string inputInfo)
		{
			bool flag = this.CheckInfo(inputInfo);
			if (flag)
			{
                if (M_Configuration.SaleMode == 1)//身份证购票的模式
                {
                    this.parent.StartOperation("gp2");
                }
                else
                {
                    this.parent.Operation("qp3");
                }
			}
			else
			{
				SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer();
				speechSynthesizer.SelectVoiceByHints(VoiceGender.Female);
				speechSynthesizer.SpeakAsync("未找到相应的信息，请重新输入！");
				this.parent.WarmOperation("未找到相应的信息！", "qp1");
			}
		}

		private bool CheckInfo(string inputInfo)
		{
			return true;
		}

		private void btn_SFZ_Click(object sender, RoutedEventArgs e)
		{
		}

		private void btn_SRXX_Click(object sender, RoutedEventArgs e)
		{
		}

		private void btn_SMEWM_Click(object sender, RoutedEventArgs e)
		{
		}

		private void btn_SY_Click(object sender, RoutedEventArgs e)
		{
			this.parent.EndOperation();
		}

		private void btn_FH_Click(object sender, RoutedEventArgs e)
		{
			this.parent.Operation("qp1");
		}


		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
		//	this.ss.SelectVoiceByHints(VoiceGender.Female);
			this.ss.Volume = 100;
			//this.ss.SpeakAsync("请在指定位置刷身份证");
            string str = AppDomain.CurrentDomain.BaseDirectory + "flash" + "\\f3.swf";
           // this.web.Source = new Uri(str); //new Uri(AppDomain.CurrentDomain.BaseDirectory + "flash\\f3.swf");
            
			this.readcard.Start();
		}

		private void Page_Unloaded(object sender, RoutedEventArgs e)
		{
			this.ss.SpeakAsyncCancelAll();
			this.readcard.Stop();
		}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/qpoperationpage/qppage2_1.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //    case 1:
        //        ((QPPage2_1)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        ((QPPage2_1)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
        //        break;
        //    case 2:
        //        this.dockfull = (StackPanel)target;
        //        break;
        //    case 3:
        //        this.web = (WebBrowser)target;
        //        break;
        //    case 4:
        //        this.readcard = (ReadCardActiveX)target;
        //        break;
        //    case 5:
        //        this.btn_SY = (Button)target;
        //        this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //        break;
        //    case 6:
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
