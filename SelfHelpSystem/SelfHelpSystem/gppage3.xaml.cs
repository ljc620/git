using Common;
using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Speech.Synthesis;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using SelfHelpSystem.Tool;
using System.Configuration;
using SelfHelpSystem.Model;

namespace SelfHelpSystem
{
    public partial class GPPage3 : Page, IComponentConnector
	{
		private int MaxCount = 10;

		private MainWindow parent;

		private int nCount = 1;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

        //internal StackPanel dockfull;

        //internal StackPanel TicketImg;

        //internal TextBlock tb_selectdate;

        //internal TextBlock tb_selecttype;

        //internal Button btn_J;

        //internal TextBlock tb_Count;

        //internal Button btn_Z;

        //internal TextBlock tb_ShowTotal;

        //internal Button btn_GQ;

        //internal Button btn_SY;

        //internal Button btn_FH;

		public GPPage3(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
			//this.tb_selectdate.Text = this.parent.SelfHelpInfo.SaleTime;
           // this.tb_selectdate.Text = this.parent.mSelfHelpClass.SaleTime;//������Ʊ�� ����ʱ�� ����ʱ��
			ImageBrush imageBrush = new ImageBrush();
			//imageBrush.ImageSource = new BitmapImage(new Uri(AppDomain.CurrentDomain.BaseDirectory + "\\pic\\bn" + this.parent.SelfHelpInfo.TicketType + ".png"));
			imageBrush.Stretch = Stretch.Fill;
			this.TicketImg.Background = imageBrush;
			//this.tb_selectdate.Text = this.parent.SelfHelpInfo.SaleTime;
           // this.tb_selectdate.Text = this.parent.mSelfHelpClass.SaleTime;//������Ʊ��
			//this.tb_selecttype.Text = ConfigHelper.GetConfigValue("ticket", "t" + this.parent.SelfHelpInfo.TicketType);
            //*****��ȡƱ������
            this.tb_selecttype.Text = T_SellTicketRecord.GetTicketTypeName(this.parent.mSelfHelpClass.TicketTypeID);
            if ((this.tb_selecttype.Text.Length > 3) && (this.tb_selecttype.Text.Length < 6))
            {
                this.tb_selecttype.FontSize = 50;
            }
            else if (this.tb_selecttype.Text.Length > 6)
            {
                this.tb_selecttype.FontSize = 40;
            }
            else if (this.tb_selecttype.Text.Length < 3)
            {
                this.tb_selecttype.FontSize = 60;
            }

			//this.tb_ShowTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();
            this.tb_ShowTotal.Text = this.parent.mSelfHelpClass.RealSum.ToString();//������Ʊ��
            //*************��ʾƱ��ͼƬ
            string str = "";                  
            if (this.parent.mSelfHelpClass.TicketTypeID == M_Configuration.TicketTypeID_1)
            {
                 str = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\" + M_Configuration.TicketTypeID_1 + ".png";
                 if (System.IO.File.Exists(str))
                 {
                   //  BitmapImage imagetemp = new BitmapImage(new Uri(str, UriKind.Absolute));
                     this.img_show.Source = null;
                     this.img_show.Source = this.parent.GetImage(str); //imagetemp;
                 }
                 else
                 {
                     str = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\default.png";
                    // BitmapImage imagetemp = new BitmapImage(new Uri(str, UriKind.Absolute));
                     this.img_show.Source = null;
                     this.img_show.Source = this.parent.GetImage(str); //imagetemp;
                 }
                 this.txt_show.Text = M_Configuration.boxA_info;//Ʊ��˵��
            }
            else if (this.parent.mSelfHelpClass.TicketTypeID == M_Configuration.TicketTypeID_2)
            {
                str = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\" + M_Configuration.TicketTypeID_2 + ".png";
                if (System.IO.File.Exists(str))
                {
                   // BitmapImage imagetemp = new BitmapImage(new Uri(str, UriKind.Absolute));
                    this.img_show.Source = null;
                    this.img_show.Source = this.parent.GetImage(str); // imagetemp;
                }
                else
                {
                    str = AppDomain.CurrentDomain.BaseDirectory + "\\Ticket\\default.png";
                    //BitmapImage imagetemp = new BitmapImage(new Uri(str, UriKind.Absolute));
                    this.img_show.Source = null;
                    this.img_show.Source = this.parent.GetImage(str); //imagetemp;
                }
                this.txt_show.Text = M_Configuration.boxB_info;//Ʊ��˵��
            }
		}

		private void btn_SY_Click(object sender, RoutedEventArgs e)
		{
			this.parent.EndOperation();
		}

		private void btn_FH_Click(object sender, RoutedEventArgs e)
		{
			this.parent.Operation("gp2");
		}

		private void btn_J_Click(object sender, RoutedEventArgs e)
		{
			this.nCount--;
            if (this.nCount < T_SellTicketRecord.MinTicketCount)//������С��Ʊ��
            {
                this.nCount = 1;
            }
           // this.txshow.Text = "��Ʊ��Ϣ-ѡ������";
         //   this.txshow.Foreground = System.Windows.Media.Brushes.White;
            this.la_show.Visibility = Visibility.Hidden;
            //bool flag = this.nCount < 1;
            //if (flag)
            //{
            //    this.nCount = 1;
            //}
			this.tb_Count.Text = this.nCount.ToString().PadLeft(2, '0');
		//	this.parent.SelfHelpInfo.TicketCount = this.nCount;
            this.parent.mSelfHelpClass.TicketCount = this.nCount;//������Ʊ��
			//this.tb_ShowTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();
            this.tb_ShowTotal.Text = this.parent.mSelfHelpClass.RealSum.ToString();//������Ʊ��
		}

		private void btn_Z_Click(object sender, RoutedEventArgs e)
		{
            //this.tb_selecttype.FontSize = 50;
            if (T_SellTicketRecord.Judge_TicketAlarmCount(this.parent.mSelfHelpClass.TicketTypeID, this.nCount))
            {
                this.la_show.Visibility = Visibility.Visible;
                this.la_show.Content = "��Ʊ���㣡";
              //  MessageBox.Show("����Ʊ���ѳ�����ǰƱ���������Ʊ����", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }
           
            if (nCount >= T_SellTicketRecord.MaxTicketCount)
            {
              //  MessageBox.Show("Ʊ���Ѵﵥ�����Ʊ������", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
              //  this.txshow.Text = "��Ʊ��Ϣ-Ʊ���Ѵﵥ�����Ʊ������";
              //  this.txshow.Foreground = System.Windows.Media.Brushes.Red;
                this.la_show.Visibility = Visibility.Visible;
                this.la_show.Content = "�����޹�10�ţ�";
                this.nCount = T_SellTicketRecord.MaxTicketCount;//�������Ʊ��
            }
            else
            {
                this.nCount++;
              //  this.txshow.Text = "��Ʊ��Ϣ-ѡ������";
             //   this.txshow.Foreground = System.Windows.Media.Brushes.White;
                if (this.nCount >= T_SellTicketRecord.MaxTicketCount)
                {
                    this.la_show.Visibility = Visibility.Visible;
                    this.la_show.Content = "�����޹�10�ţ�";
                }
                else
                {
                    this.la_show.Visibility = Visibility.Hidden;
                }
            }
            //bool flag = this.nCount > this.MaxCount;
            //if (flag)
            //{
            //    this.nCount = this.MaxCount;
            //}
            
			this.tb_Count.Text = this.nCount.ToString().PadLeft(2, '0');
			//this.parent.SelfHelpInfo.TicketCount = this.nCount;
            this.parent.mSelfHelpClass.TicketCount = this.nCount;//������Ʊ��
			//this.tb_ShowTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();
            this.tb_ShowTotal.Text = this.parent.mSelfHelpClass.RealSum.ToString();//������Ʊ��
		}

		private void btn_GQ_Click(object sender, RoutedEventArgs e)
		{
            //*************************����ѡƱ�����������д����ʱ����������**************
            this.parent.mSelfHelpClass.OrderID = ""; //���۵����ݲ����� T_SellTicketRecord.CreateTableUID(M_Configuration.CLIENTID, T_SellTicketRecord.LocalTicket_Prefix_ZG, 1);//���۵���
            T_SellTicketRecord.slorder.OrderId = this.parent.mSelfHelpClass.OrderID;//���۵���
            T_SellTicketRecord.slorder.OrderType = T_SellTicketRecord.LocalTicket_Prefix_ZG;//�������� ZG ������Ʊ
         //   T_SellTicketRecord.slorder.TicketCount = this.parent.SelfHelpInfo.TicketCount;//��������

            T_SellTicketRecord.slorder.DueSum = this.parent.mSelfHelpClass.DueSum; //Ӧ�ս��
            T_SellTicketRecord.slorder.RealSum = this.parent.mSelfHelpClass.RealSum; //ʵ�ս��
            T_SellTicketRecord.slorder.Remark = "������Ʊ";//����˵��
            T_SellTicketRecord.slorder.PayStat = "1";//֧��״̬(1-��֧�� 2-��֧�� 3-֧��ʧ��)
            T_SellTicketRecord.slorder.SaleTime = T_Date.ConvertDataTimeToLong(DateTime.Now);//��Ʊʱ��
            T_SellTicketRecord.slorder.VersionNo = T_Date.ConvertDataTimeToLong(DateTime.Now);//�汾��
            T_SellTicketRecord.slorder.SaleUserId =Convert.ToString(M_Configuration.CLIENTID);//��Ʊ�˴�Ϊ�ն˱��

            //*************************����ѡƱ�����������д����ʱ����������**************

            //******************��������Ϣд����ʱ��������
            #region//��ɾ��
            //if (T_SellTicketRecord.Inert_SL_ORDER_Temp(T_SellTicketRecord.slorder))
            //{
            //    this.parent.Operation("gp4");
            //}
            //else
            //{
            //    MessageBox.Show("Ʊ����Ϣ����ʧ�ܣ�", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
            //    return;
            //}
#endregion
            if (T_SellTicketRecord.Judge_TicketAlarmCount(this.parent.mSelfHelpClass.TicketTypeID, this.nCount))
            {
                MessageBox.Show("����Ʊ���ѳ�����ǰƱ���������Ʊ����", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }
            else
            {
                this.parent.Operation("gp4");
            }
		}

		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
			this.MaxCount = 10;
			//this.parent.SelfHelpInfo.TicketCount = this.nCount;
            this.parent.mSelfHelpClass.TicketCount = this.nCount;//������Ʊ��
			this.nCount = 1;
			//this.tb_ShowTotal.Text = this.parent.SelfHelpInfo.TicketPriceTotal.ToString();
            this.tb_ShowTotal.Text = this.parent.mSelfHelpClass.RealSum.ToString();//������Ʊ��
			this.tb_Count.Text = this.nCount.ToString().PadLeft(2, '0');
			//this.ss.SelectVoiceByHints(VoiceGender.Female);
			//this.ss.SpeakAsync(ConfigHelper.GetConfigValue("speechqp", "ticketcount"));
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
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/gpoperationpage/gppage3.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //    case 1:
        //        ((GPPage3)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        ((GPPage3)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
        //        break;
        //    case 2:
        //        this.dockfull = (StackPanel)target;
        //        break;
        //    case 3:
        //        this.TicketImg = (StackPanel)target;
        //        break;
        //    case 4:
        //        this.tb_selectdate = (TextBlock)target;
        //        break;
        //    case 5:
        //        this.tb_selecttype = (TextBlock)target;
        //        break;
        //    case 6:
        //        this.btn_J = (Button)target;
        //        this.btn_J.Click += new RoutedEventHandler(this.btn_J_Click);
        //        break;
        //    case 7:
        //        this.tb_Count = (TextBlock)target;
        //        break;
        //    case 8:
        //        this.btn_Z = (Button)target;
        //        this.btn_Z.Click += new RoutedEventHandler(this.btn_Z_Click);
        //        break;
        //    case 9:
        //        this.tb_ShowTotal = (TextBlock)target;
        //        break;
        //    case 10:
        //        this.btn_GQ = (Button)target;
        //        this.btn_GQ.Click += new RoutedEventHandler(this.btn_GQ_Click);
        //        break;
        //    case 11:
        //        this.btn_SY = (Button)target;
        //        this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //        break;
        //    case 12:
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
