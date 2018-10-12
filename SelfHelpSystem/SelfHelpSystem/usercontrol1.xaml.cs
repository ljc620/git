using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media.Imaging;


namespace SelfHelpSystem
{
	partial class UserControl1 : UserControl, IComponentConnector
	{
        public BitmapImage bitTest;
		public UserControl1()
		{
			this.InitializeComponent();
		}

		public void SetBackgroungImage(BitmapImage image)
		{
            this.image.Source = null;
            this.image.Source = image; 
		}

		private void UserControl_Unloaded(object sender, RoutedEventArgs e)
		{
		}

	}
}
