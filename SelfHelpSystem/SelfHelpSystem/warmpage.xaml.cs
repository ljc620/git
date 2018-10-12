using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Speech.Synthesis;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Windows.Threading;
using System.Windows.Media;

namespace SelfHelpSystem
{
    partial class WarmPage : Page, IComponentConnector//, ControlPage
	{
		private DispatcherTimer countdowntimer;

		private int countSecond = 5;

		private MainWindow parent;

		private string pagename = string.Empty;

		private bool coutdown = true;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

        //internal StackPanel dockfull;

        //internal TextBlock showtext;

        //internal Button btn_SY;

        //internal Button btn_FH;

		private void timer_Tick(object sender, EventArgs e)
		{
			bool flag = this.countSecond == 0;
			if (flag)
			{
				this.countdowntimer.Stop();
				//this.parent.Operation(this.pagename);
                this.parent.EndOperation();
			}
			else
			{
				this.countSecond--;
			}
		}

		public WarmPage(MainWindow parentWindow, string warmInfo, string pageName)
		{
			this.parent = parentWindow;
			//this.pagename = pageName;
			InitializeComponent();
			this.ss.SpeakAsyncCancelAll();
			this.showtext.Text = warmInfo;
            if (pageName == "INFO")
                this.showtext.Foreground = Brushes.DarkGreen;
            else
                this.showtext.Foreground = Brushes.Red;
            //this.ss.SelectVoiceByHints(VoiceGender.Female);
            //this.ss.SpeakAsync(warmInfo);
            this.countdowntimer = new DispatcherTimer();
            this.countdowntimer.Interval = new TimeSpan(0, 0, 0, 1, 0);
            this.countdowntimer.Tick += new EventHandler(this.timer_Tick);
		}

		public WarmPage(MainWindow parentWindow, string warmInfo, string pageName, bool isCountDown)
		{
			this.coutdown = isCountDown;
			this.parent = parentWindow;
			this.pagename = pageName;
			InitializeComponent();
			this.ss.SpeakAsyncCancelAll();
			//this.ss.SelectVoiceByHints(VoiceGender.Female);
		//	this.ss.SpeakAsync(warmInfo);
			this.showtext.Text = warmInfo;
            if (pageName.ToUpper() == "INFO")
                this.showtext.Foreground = Brushes.DarkGreen;
            else
                this.showtext.Foreground = Brushes.Red;
            this.countdowntimer = new DispatcherTimer();
			this.countdowntimer.Interval = new TimeSpan(0, 0, 0, 1, 0);
			this.countdowntimer.Tick += new EventHandler(this.timer_Tick);
		}

		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
			this.parent.ResumeSecond();
			this.countSecond = 5;
			bool flag = this.coutdown;
			if (flag)
			{
				this.countdowntimer.Start();
			}
		}

		private void Page_Unloaded(object sender, RoutedEventArgs e)
		{
			this.countdowntimer.Stop();
			this.ss.SpeakAsyncCancelAll();
			//this.parent.RestartSecond();
		}

		private void btn_FH_Click(object sender, RoutedEventArgs e)
		{
            this.parent.EndOperation();
		//	this.parent.Operation(this.pagename);
		}

		private void btn_SY_Click(object sender, RoutedEventArgs e)
		{
			this.parent.EndOperation();
		}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/warmpage.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //        case 1:
        //            ((WarmPage)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //            ((WarmPage)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
        //            break;
        //        case 2:
        //            this.dockfull = (StackPanel)target;
        //            break;
        //        case 3:
        //            this.showtext = (TextBlock)target;
        //            break;
        //        case 4:
        //            this.btn_SY = (Button)target;
        //            this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //            break;
        //        case 5:
        //            this.btn_FH = (Button)target;
        //            this.btn_FH.Click += new RoutedEventHandler(this.btn_FH_Click);
        //            break;
        //        default:
        //            this._contentLoaded = true;
        //            break;
        //    }
        //}
	}
}
