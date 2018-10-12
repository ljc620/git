using Common;
using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Imaging;

namespace SelfHelpSystem
{
    public partial class ErrorPage : Page, IComponentConnector//, ControlPage
	{
		private MainWindow parent;

		public ErrorPage(MainWindow parentWindow, string errString, string logString)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
			this.errTxt.Text = errString;
			bool flag = !string.IsNullOrEmpty(logString);
			if (flag)
			{
				LogHelper.WriteLog(new LogHelper
				{
					Message = errString,
					StackTrace = logString
				});
			}
		}

		private void ButtonGP_Click(object sender, RoutedEventArgs e)
		{
			bool flag = this.parent.row2.Height.Value == 0.0;
			if (flag)
			{
				this.parent.EndOperation();
			}
			else
			{
				//this.parent.SelfHelpInfo.OperType = "购票";
				this.parent.StartOperation("gp2");
			}
			base.Background = new ImageBrush
			{
				ImageSource = new BitmapImage(new Uri(AppDomain.CurrentDomain.BaseDirectory + "\\pic\\bg1.png")),
				Stretch = Stretch.Fill
			};
		}

		private void ButtonQP_Click(object sender, RoutedEventArgs e)
		{
			bool flag = this.parent.row2.Height.Value == 0.0;
			if (flag)
			{
				this.parent.EndOperation();
			}
			else
			{
				//this.parent.SelfHelpInfo.OperType = "取票";
				this.parent.StartOperation("qp1");
			}
			base.Background = new ImageBrush
			{
				ImageSource = new BitmapImage(new Uri(AppDomain.CurrentDomain.BaseDirectory + "\\pic\\bg1.png")),
				Stretch = Stretch.Fill 
			};
		}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/errorpage.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    if (connectionId != 1)
        //    {
        //        if (connectionId != 2)
        //        {
        //            this._contentLoaded = true;
        //        }
        //        else
        //        {
        //            this.errTxt = (TextBlock)target;
        //        }
        //    }
        //    else
        //    {
        //        this.dock = (StackPanel)target;
        //    }
        //}
	}
}
