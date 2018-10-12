using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using log4net;
using SelfHelpSystem.Model;
using SelfHelpSystem.BLL;


namespace SelfHelpSystem
{
    public partial class FirstPage : Page, IComponentConnector//
	{
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");
		private MainWindow parent;
        //internal StackPanel dock;

        //internal Button btn_GP;

        //internal Button btn_QP;

		public FirstPage(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
            if (this.parent.rfidReaderTest == null)
            {
                this.btn_GP.Content = "读卡器配置错误";
                this.btn_GP.IsEnabled = false;
                this.btn_QP.IsEnabled = false;
            }
            if (this.parent.ticketbox_isErr == 1)
            {
                this.btn_GP.FontSize = 55;
                this.btn_GP.Content = "出票失败窗口取票";
                this.btn_GP.IsEnabled = false;
                this.btn_QP.IsEnabled = false;
            }
		}

		private void ButtonGP_Click(object sender, RoutedEventArgs e)
		{
            try
            {
                if (DateTime.Now.TimeOfDay < M_Configuration.StartSaleTime)
                {
                    this.parent.WarmOperation("暂未开始售票！\r\n\r\n售票开始时间为：" + M_Configuration.StartSaleTime.ToString("c"), "error");
                    return;
                }
                else if (DateTime.Now.TimeOfDay > M_Configuration.EndSaleTime)
                {
                    this.parent.WarmOperation("今日售票已截止！\r\n\r\n售票截止时间为：" + M_Configuration.EndSaleTime.ToString("c"), "error");
                    return;
                }
                else
                {
                    bool flag = this.parent.row2.Height.Value == 0.0;
                    if (flag)
                    {
                        this.parent.EndOperation();
                    }
                    else
                    {
                        // this.parent.SelfHelpInfo.OperType = "购票";
                        this.parent.mSelfHelpClass.OperType = "0";//取票还是购票 0表示购票 1表示取票 自助售票类
                        if (M_Configuration.SaleMode == 0)//出实体票的模式
                        {
                            this.parent.StartOperation("gp2");
                        }
                        else if (M_Configuration.SaleMode == 1)//身份证购票模式
                        {
                            this.parent.StartOperation("qp1");
                            // this.parent.Operation("qp2_1");
                        }
                        // this.parent.StartOperation("setting");
                    }

                    //base.Background = new ImageBrush
                    //{
                    //    ImageSource =new BitmapImage(new Uri(AppDomain.CurrentDomain.BaseDirectory + "\\pic\\bg1.png")),
                    //    Stretch = Stretch.Fill
                    //};
                }
            }
            catch (Exception ex)
            {
                _log.Error("错误信息：" + ex.Message);
            }

       
		}

		private void ButtonQP_Click(object sender, RoutedEventArgs e)
		{
            try
            {
                if (M_Configuration.IDCard_IsUse == "1")//未启用身份证取票的
                {
                    this.parent.Err_Info = "取票功能暂未开放！";
                    this.parent.Operation("error");
                }
                else//启用身份证取票的
                {
                    bool flag = this.parent.row2.Height.Value == 0.0;
                    if (flag)
                    {
                        this.parent.EndOperation();
                    }
                    else
                    {
                      //  this.parent.SelfHelpInfo.OperType = "取票";
                        this.parent.mSelfHelpClass.OperType = "1";//取票还是购票 0表示购票 1表示取票 自助售票类
                        this.parent.StartOperation("qp1");
                    }
                    //base.Background = new ImageBrush
                    //{
                    //    ImageSource = new BitmapImage(new Uri(AppDomain.CurrentDomain.BaseDirectory + "\\pic\\bg1.png")),
                    //    Stretch = Stretch.Fill
                    //};
                }
            }
            catch (Exception ex)
            {
                _log.Error("取票错误信息："+ex.Message);
            }
 
		}

        private void btTest_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                this.parent.Operation("setting");
            }
            catch (Exception ex)
            {
                _log.Error(typeof(FirstPage), ex);
            }

        }

        private void Page_KeyDown(object sender, System.Windows.Input.KeyEventArgs e)
        {
            //Ctrl+Q 全选
            try
            {
                if ((e.KeyboardDevice.IsKeyDown(System.Windows.Input.Key.RightCtrl) || e.KeyboardDevice.IsKeyDown(System.Windows.Input.Key.LeftCtrl)) && e.KeyboardDevice.IsKeyDown(System.Windows.Input.Key.Q))
                {
                    btTest_Click(null, null);
                }
            }
            catch (Exception ex)
            {
                _log.Error(typeof(FirstPage), ex);
            }
        }

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/firstpage.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //        case 1:
        //            this.dock = (StackPanel)target;
        //            break;
        //        case 2:
        //            this.btn_GP = (Button)target;
        //            this.btn_GP.Click += new RoutedEventHandler(this.ButtonGP_Click);
        //            break;
        //        case 3:
        //            this.btn_QP = (Button)target;
        //            this.btn_QP.Click += new RoutedEventHandler(this.ButtonQP_Click);
        //            break;
        //        default:
        //            this._contentLoaded = true;
        //            break;
        //    }
        //}

    }
}
