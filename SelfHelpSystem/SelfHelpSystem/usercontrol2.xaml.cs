using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Windows.Media.Imaging;

namespace SelfHelpSystem
{
	partial class UserControl2 : UserControl, IComponentConnector
	{

		public UserControl2()
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
