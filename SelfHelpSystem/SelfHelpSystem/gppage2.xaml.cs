using Common;
using Model;
using System;
using System.Text;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Speech.Synthesis;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using SelfHelpSystem.Tool;
using SelfHelpSystem.Model;
using System.Windows.Media.Imaging;

namespace SelfHelpSystem
{
    public partial class GPPage2 : Page, IComponentConnector
	{
		private MainWindow parent;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

        //internal StackPanel dockfull;

        //internal TextBlock selectdate;

        //internal Button button1;

        //internal Button button2;

        //internal Button button3;

        //internal Button btn_SY;

        //internal Button btn_FH;

        private TextBlock txt_A = null;
        private Label la_A = null;
        
        private TextBlock txt_B = null;
        private Label la_B = null;

		public GPPage2(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
         
		}

		private void btn_SY_Click(object sender, RoutedEventArgs e)
		{
			this.parent.EndOperation();
		}

		private void btn_FH_Click(object sender, RoutedEventArgs e)
		{
			this.parent.EndOperation();
		}

		private void my_MouseEnter(object sender, MouseEventArgs e)
		{
			Button button = sender as Button;
			button.BorderBrush = Brushes.Orange;
			button.BorderThickness = new Thickness(1.0);
			button.Opacity = 0.9;
		}

		private void my_MouseLeave(object sender, MouseEventArgs e)
		{
			Button button = sender as Button;
			button.BorderBrush = null;
			button.BorderThickness = new Thickness(0.0);
			button.Opacity = 1.0;
		}

		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
			this.selectdate.Text = DateTime.Now.ToString("购票日期：MM月dd日");
		//	this.parent.SelfHelpInfo = new SelfHelpClass();
            this.parent.mSelfHelpClass = new M_SelfHelpClass();//自助售票类
			//this.parent.SelfHelpInfo.SaleTime = DateTime.Now.ToString("MM月dd日");
            this.parent.mSelfHelpClass.SaleTime = DateTime.Now.ToString("MM月dd日");//自助售票类
			//this.parent.SelfHelpInfo.TicketType = "";
			//this.parent.SelfHelpInfo.TicketPrice = decimal.Zero;
			//this.ss.SelectVoiceByHints(VoiceGender.Male, VoiceAge.Child);
			//this.ss.SpeakAsync(ConfigHelper.GetConfigValue("speechqp", "tickettype"));

            txt_A = this.button2.Template.FindName("txt_showA", button2) as TextBlock;
            txt_A.Text = M_Configuration.boxA_info;//A票箱说明
            la_A = this.button2.Template.FindName("la_A", button2) as Label;
            la_A.Content = M_Configuration.TicketID_AName;//票种名称
            if ((M_Configuration.TicketID_AName.Length > 3) && (M_Configuration.TicketID_AName.Length < 6))
            {
                la_A.FontSize = 40;
            }
            else if (M_Configuration.TicketID_AName.Length > 6)
            {
                la_A.FontSize = 20;
            }
            else if (M_Configuration.TicketID_AName.Length < 3)
            {
                la_A.FontSize = 60;
            }

            txt_B = this.button3.Template.FindName("txt_showB", button3) as TextBlock;
            txt_B.Text = M_Configuration.boxB_info;//B票箱说明            
            la_B = this.button3.Template.FindName("la_B", button3) as Label;
            la_B.Content = M_Configuration.TicketID_BName;//票种名称
            if ((M_Configuration.TicketID_BName.Length > 3) && (M_Configuration.TicketID_BName.Length < 6))
            {
                la_B.FontSize = 40;
            }
            else if (M_Configuration.TicketID_BName.Length > 6)
            {
                la_B.FontSize = 20;
            }
            else if (M_Configuration.TicketID_BName.Length < 3)
            {
                la_B.FontSize = 60;
            }

            //ImageBrush imageBrush = new ImageBrush();
            //imageBrush.ImageSource = new BitmapImage(new Uri(@"C:\Users\john\Desktop\SelfHelpSystem\SelfHelpSystem\Ticket\n13.png", UriKind.Absolute));
            //imageBrush.Stretch = Stretch.Fill;//设置图像的显示格式  
            //ImageBrush brush = new ImageBrush(new BitmapImage(new Uri(@"C:\Users\john\Desktop\SelfHelpSystem\SelfHelpSystem\Ticket\n13.png", UriKind.Relative)));
           // ImageBrush br = this.button2.Template.FindName("ImageBrush", button2) as ImageBrush;

            //ImageBrush br = new ImageBrush(new BitmapImage(new Uri(@"C:\Users\john\Desktop\SelfHelpSystem\SelfHelpSystem\bin\Debug\Ticket\n11.png", UriKind.Absolute)));
            //br.Stretch = Stretch.Fill;
    
            //this.button2.Background = br;
            //var mainPanel = new StackPanel();
            //mainPanel.Background = br;
            //this.button2.DataContext = mainPanel;

            //this.button3.DataContext = mainPanel;
            //this.button3.Content = mainPanel;


            Image im = this.button2.Template.FindName("img_A", button2) as Image;
            string str_A = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\" + M_Configuration.TicketTypeID_1 + ".png"; // AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\n" + M_Configuration.TicketTypeID_1 + ".png";
            if (System.IO.File.Exists(str_A))
            {
               // BitmapImage imagetemp = new BitmapImage(new Uri(str_A, UriKind.Absolute));
                im.Source = null;
                im.Source = this.parent.GetImage(str_A); // imagetemp;
            }
            else
            {
                str_A = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\default.png";
               // BitmapImage imagetemp = new BitmapImage(new Uri(str_A, UriKind.Absolute));
                im.Source = null;
                im.Source = this.parent.GetImage(str_A); //imagetemp;
            }

            Image im_B = this.button3.Template.FindName("img_B", button3) as Image;            
            string str_B = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\" + M_Configuration.TicketTypeID_2 + ".png"; // .StartupPath + @"Ticket\n" + M_Configuration.TicketTypeID_1 + ".png";
            if (System.IO.File.Exists(str_B))
            {
               // BitmapImage imagetemp_B = new BitmapImage(new Uri(str_B, UriKind.Absolute));
                im_B.Source = null;
                im_B.Source = this.parent.GetImage(str_B); //imagetemp_B;
            }
            else
            {
                str_B = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\default.png";
              //  BitmapImage imagetemp_B = new BitmapImage(new Uri(str_B, UriKind.Absolute));
                im_B.Source = null;
                im_B.Source = this.parent.GetImage(str_B); //imagetemp_B;
            }
         

            //两件票箱都是一种票的情况
            if (M_Configuration.TicketTypeID_1 == M_Configuration.TicketTypeID_2)
            {
                this.button3.Visibility = System.Windows.Visibility.Hidden;
            }
		}

		private void button1_Click(object sender, RoutedEventArgs e)
		{
			Button button = sender as Button;
			//this.parent.SelfHelpInfo.SaleTime = DateTime.Now.ToString("MM月dd日");
			//this.parent.SelfHelpInfo.TicketType = button.Tag.ToString();
			string configValue = ConfigHelper.GetConfigValue("ticket", "t" + button.Tag.ToString(), "price");

            this.parent.mSelfHelpClass.SaleTime = DateTime.Now.ToString("MM月dd日");
            if ((Convert.ToInt32(button.Tag) - 2)==0)
            {
                this.parent.mSelfHelpClass.BoxNumber = "A";//票箱
                this.parent.mSelfHelpClass.TicketTypeID = M_Configuration.TicketTypeID_1;//默认A票箱的票种就是TicketTypeID_1
            }
            else if ((Convert.ToInt32(button.Tag) - 2) == 1)
            {
                this.parent.mSelfHelpClass.BoxNumber = "B";
                this.parent.mSelfHelpClass.TicketTypeID = M_Configuration.TicketTypeID_2;//默认B票箱的票种就是TicketTypeID_2
            }
            //********************验证下票数是否已报警*************
            if (T_SellTicketRecord.Judge_TicketAlarmCount(this.parent.mSelfHelpClass.TicketTypeID, 0))
            {
                MessageBox.Show("余票不足！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }
            //********************验证下票数是否已报警*************
            
            //***预售期价格
            Int64 ticketprice = -1;//预售期票价
            ticketprice = T_SellTicketRecord.Get_sl_period_Price(this.parent.mSelfHelpClass.TicketTypeID);//获取预售期票价
            this.parent.mSelfHelpClass.OriginalPrice = T_SellTicketRecord.GetTicketInfo(this.parent.mSelfHelpClass.TicketTypeID).Price;  //T_GetCurTicketInfo.GetCurTicketTypeIDInfo(M_Configuration.TicketTypeID_1, M_TicketTypeList.listTicketType).Price;//原票价 自助售票类
            //this.parent.mSelfHelpClass.TicketTypeID = M_Configuration.TicketTypeID_1;//票种 自助售票类
            if (ticketprice != -1)
            {
                this.parent.mSelfHelpClass.SalePrice = ticketprice;//预售期票价 自助售票类
            }
            else
            {
                this.parent.mSelfHelpClass.SalePrice = this.parent.mSelfHelpClass.OriginalPrice;//原票价  
            }

            //if (this.parent.mSelfHelpClass.BoxNumber == "A")//箱号1中的票种
            //{
            //    ticketprice = T_SellTicketRecord.Get_sl_period_Price(M_Configuration.TicketTypeID_1);//获取预售期票价
            //    this.parent.mSelfHelpClass.OriginalPrice = T_SellTicketRecord.GetTicketInfo(M_Configuration.TicketTypeID_1).Price;  //T_GetCurTicketInfo.GetCurTicketTypeIDInfo(M_Configuration.TicketTypeID_1, M_TicketTypeList.listTicketType).Price;//原票价 自助售票类
            //    this.parent.mSelfHelpClass.TicketTypeID = M_Configuration.TicketTypeID_1;//票种 自助售票类
            //    if (ticketprice != -1)
            //    {
            //        this.parent.mSelfHelpClass.SalePrice = ticketprice;//预售期票价 自助售票类
            //    }
            //    else
            //    {
            //        this.parent.mSelfHelpClass.SalePrice = this.parent.mSelfHelpClass.OriginalPrice;//原票价  
            //    }
            //}
            //else if (this.parent.mSelfHelpClass.BoxNumber == "B")//箱号2中的票种
            //{
            //    ticketprice = T_SellTicketRecord.Get_sl_period_Price(M_Configuration.TicketTypeID_2);
            //    this.parent.mSelfHelpClass.TicketTypeID = M_Configuration.TicketTypeID_2;//票种 自助售票类
            //    this.parent.mSelfHelpClass.OriginalPrice = T_SellTicketRecord.GetTicketInfo(M_Configuration.TicketTypeID_2).Price; //T_GetCurTicketInfo.GetCurTicketTypeIDInfo(M_Configuration.TicketTypeID_2, M_TicketTypeList.listTicketType).Price;//原票价 自助售票类
            //    if (ticketprice != -1)
            //    {
            //        this.parent.mSelfHelpClass.SalePrice = ticketprice;//预售期票价 自助售票类
            //    }
            //    else
            //    {
            //        this.parent.mSelfHelpClass.SalePrice = this.parent.mSelfHelpClass.OriginalPrice;//原票价
            //    }
            //}
            //*********************获取下票价---还有预售期价格**************
            //this.parent.SelfHelpInfo.TicketPrice = decimal.Parse(configValue);
            this.parent.Operation("gp3");
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
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/gpoperationpage/gppage2.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //        case 1:
        //            ((GPPage2)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //            ((GPPage2)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
        //            break;
        //        case 2:
        //            this.dockfull = (StackPanel)target;
        //            break;
        //        case 3:
        //            this.selectdate = (TextBlock)target;
        //            break;
        //        case 4:
        //            this.button1 = (Button)target;
        //            this.button1.Click += new RoutedEventHandler(this.button1_Click);
        //            break;
        //        case 5:
        //            this.button2 = (Button)target;
        //            this.button2.Click += new RoutedEventHandler(this.button1_Click);
        //            break;
        //        case 6:
        //            this.button3 = (Button)target;
        //            this.button3.Click += new RoutedEventHandler(this.button1_Click);
        //            break;
        //        case 7:
        //            this.btn_SY = (Button)target;
        //            this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //            break;
        //        case 8:
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
