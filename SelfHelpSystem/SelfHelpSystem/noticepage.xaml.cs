using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;

namespace SelfHelpSystem
{
    public partial class NoticePage : Page, IComponentConnector
	{

		public NoticePage(string NoticeString, double FontSize)
		{
			this.InitializeComponent();
            this.showtext.Text = NoticeString;
            this.showtext.FontSize = FontSize;
		}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/noticepage.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        }

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
    //    void IComponentConnector.Connect(int connectionId, object target)
    //    {
    //        if (connectionId != 1)
    //        {
    //            if (connectionId != 2)
    //            {
    //                this._contentLoaded = true;
    //            }
    //            else
    //            {
    //                this.showtext = (TextBlock)target;
    //            }
    //        }
    //        else
    //        {
    //            this.dockfull = (StackPanel)target;
    //        }
    //    }
    //}
}
