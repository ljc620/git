using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;

namespace SelfHelpSystem
{
    public partial class GPPage1 : Page, IComponentConnector
	{
		private MainWindow parent;

		public GPPage1(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
		}

		private void btn_GPRQ_Click(object sender, RoutedEventArgs e)
		{
			this.parent.Operation("gp2");
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
			this.parent.SelfHelpInfo.OperType = "1";
			TextBlock textBlock = this.btn_GPRQ.Template.FindName("datemouth", this.btn_GPRQ) as TextBlock;
			textBlock.Text = DateTime.Now.ToString("MM月");
			TextBlock textBlock2 = this.btn_GPRQ.Template.FindName("dateday", this.btn_GPRQ) as TextBlock;
			textBlock2.Text = DateTime.Now.ToString("dd");
			TextBlock textBlock3 = this.btn_GPRQ.Template.FindName("dateweek", this.btn_GPRQ) as TextBlock;
			string[] array = new string[]
			{
				"星期日",
				"星期一",
				"星期二",
				"星期三",
				"星期四",
				"星期五",
				"星期六"
			};
			string text = array[Convert.ToInt32(DateTime.Now.DayOfWeek.ToString("d"))].ToString();
			textBlock3.Text = text;
		}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/gpoperationpage/gppage1.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //    case 1:
        //        ((GPPage1)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        break;
        //    case 2:
        //        this.dockfull = (StackPanel)target;
        //        break;
        //    case 3:
        //        this.btn_GPRQ = (Button)target;
        //        this.btn_GPRQ.Click += new RoutedEventHandler(this.btn_GPRQ_Click);
        //        break;
        //    case 4:
        //        this.btn_SY = (Button)target;
        //        this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //        break;
        //    case 5:
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
