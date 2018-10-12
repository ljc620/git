using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;

namespace SelfHelpSystem
{
    public partial class QPPage4 : Page, IComponentConnector
	{
		private MainWindow parent;

		public QPPage4(MainWindow parentWindow)
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

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/qpoperationpage/qppage4.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //        case 1:
        //            this.dockfull = (StackPanel)target;
        //            break;
        //        case 2:
        //            this.web = (WebBrowser)target;
        //            break;
        //        case 3:
        //            this.btn_SY = (Button)target;
        //            this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //            break;
        //        case 4:
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
