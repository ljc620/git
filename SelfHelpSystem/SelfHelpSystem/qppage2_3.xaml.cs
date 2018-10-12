using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Speech.Synthesis;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Markup;

namespace SelfHelpSystem
{
    public partial class QPPage2_3 : Page, IComponentConnector
	{
		private MainWindow parent;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

		public QPPage2_3(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
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
			//this.ss.SelectVoiceByHints(VoiceGender.Female);
			//this.ss.SpeakAsync("请将有效二维码放置在扫描区扫描");
			//this.web.Source = new Uri(AppDomain.CurrentDomain.BaseDirectory + "flash\\f4.swf");
			this.inputBox.Focus();
		}

		private void Page_Unloaded(object sender, RoutedEventArgs e)
		{
			this.ss.SpeakAsyncCancelAll();
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

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/qpoperationpage/qppage2_3.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //    case 1:
        //        ((QPPage2_3)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        ((QPPage2_3)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
        //        break;
        //    case 2:
        //        this.dockfull = (StackPanel)target;
        //        break;
        //    case 3:
        //        this.web = (WebBrowser)target;
        //        break;
        //    case 4:
        //        this.inputBox = (TextBox)target;
        //        this.inputBox.LostKeyboardFocus += new KeyboardFocusChangedEventHandler(this.inputBox_LostKeyboardFocus);
        //        this.inputBox.TextInput += new TextCompositionEventHandler(this.inputBox_TextInput);
        //        this.inputBox.KeyDown += new KeyEventHandler(this.inputBox_KeyDown);
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
