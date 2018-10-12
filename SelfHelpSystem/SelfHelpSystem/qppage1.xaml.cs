using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Speech.Synthesis;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using SelfHelpSystem.Model;

namespace SelfHelpSystem
{
    public partial class QPPage1 : Page, IComponentConnector
	{
		private MainWindow parent;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

		public QPPage1(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
		}

		private void btn_Click(object sender, RoutedEventArgs e)
		{
			Button button = sender as Button;
			this.parent.SelfHelpInfo.GetTicketType = button.Tag.ToString();
            this.parent.mSelfHelpClass.GetTicketType = button.Tag.ToString();//自助售票类
			this.parent.Operation("qp2_" + button.Tag.ToString());
		}

		private void btn_SY_Click(object sender, RoutedEventArgs e)
		{
			this.parent.EndOperation();
		}

		private void btn_FH_Click(object sender, RoutedEventArgs e)
		{
			this.parent.EndOperation();
		}

		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
			this.parent.SelfHelpInfo.GetTicketType = "";
            this.parent.mSelfHelpClass.GetTicketType = "";//自助售票类
			//this.ss.SelectVoiceByHints(VoiceGender.Female);
		//	this.ss.SpeakAsync("请选择取票方式");
            if (M_Configuration.IDCard_IsUse == "1")
            {
                this.btn_SFZ.Visibility = System.Windows.Visibility.Hidden;
                this.txt_show.Visibility = Visibility.Visible;
            }
            else
            {
                this.btn_SFZ.Visibility = System.Windows.Visibility.Visible;
                this.txt_show.Visibility = System.Windows.Visibility.Hidden;
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
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/qpoperationpage/qppage1.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //    case 1:
        //        ((QPPage1)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        ((QPPage1)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
        //        break;
        //    case 2:
        //        this.dockfull = (StackPanel)target;
        //        break;
        //    case 3:
        //        this.btn_SFZ = (Button)target;
        //        this.btn_SFZ.Click += new RoutedEventHandler(this.btn_Click);
        //        break;
        //    case 4:
        //        this.btn_SRXX = (Button)target;
        //        this.btn_SRXX.Click += new RoutedEventHandler(this.btn_Click);
        //        break;
        //    case 5:
        //        this.btn_SMEWM = (Button)target;
        //        this.btn_SMEWM.Click += new RoutedEventHandler(this.btn_Click);
        //        break;
        //    case 6:
        //        this.btn_SY = (Button)target;
        //        this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //        break;
        //    case 7:
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
