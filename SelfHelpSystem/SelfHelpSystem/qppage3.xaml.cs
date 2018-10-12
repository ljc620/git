using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using SelfHelpSystem.Tool;
using SelfHelpSystem.Model;
using SelfHelpSystem.BLL;
using tbims.rpc.entity;
using System.Collections.Generic;

namespace SelfHelpSystem
{
    public partial class QPPage3 : Page, IComponentConnector
	{
		private MainWindow parent;

		public QPPage3(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
			TicketInfo.Clear();
            //TicketInfo.Add(new TicketInfo
            //{
            //    tickettype = "八里沟",
            //    ticketcount = "1",
            //    ticketdate = "2017年5月4日"
            //});
            //TicketInfo.Add(new TicketInfo
            //{
            //    tickettype = "九莲山",
            //    ticketcount = "1",
            //    ticketdate = "2017年5月5日"
            //});
            //TicketInfo.Add(new TicketInfo
            //{
            //    tickettype = "天界山",
            //    ticketcount = "1",
            //    ticketdate = "2017年5月5日"
            //});
			this.dataGrid1.ItemsSource = TicketInfo.GetInfo();
            //发送身份证取票
            List<SL_ORDER_DETAIL> listOrderDetail = new List<SL_ORDER_DETAIL>();
            listOrderDetail = B_SellTicketUpData.GetTicketInfo_IDCard(this.parent.Client, this.parent.mSelfHelpClass.GetTicketUserNumber);
            if (listOrderDetail.Count > 0)
            {
                //余票验证
                #region

                //1.先获取要取的票中各个票种的数量

                Int32 ACount = 0;//A票种数量
                Int32 BCount = 0;//B票种数量

                for (int i = 0; i < listOrderDetail.Count; i++)
                {
                    if (listOrderDetail[i].TicketTypeId == M_Configuration.TicketTypeID_1)//A票箱票种
                    {
                        ACount++;
                    }
                    else if (listOrderDetail[i].TicketTypeId == M_Configuration.TicketTypeID_2)//B票箱票种
                    {
                        BCount++;
                    }
                }
                //2.先看下两个票箱票种是一样的情况
                if (M_Configuration.TicketTypeID_1 == M_Configuration.TicketTypeID_2)//票箱放的是一种票的情况
                {
                    if ((ACount > 0) && (BCount > 0))//实际是取两种票的情况
                    {
                        MessageBox.Show("本取票网点没有您所购的全体票种！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                        return;
                    }
                    else
                    {
                        //自助售票机两个票箱是一种票时 有一个票箱的票数能满足就行或A B两票箱数量加起来够也行
                        Int32 TicketCount = T_SellTicketRecord.GetCount_SL_TicketCount();//获取票箱内全体票数
                        if (listOrderDetail.Count>TicketCount)
                        {
                            MessageBox.Show("所购票数已超过当前票箱允许的售票数！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                            return;
                        }
                    }
                }
                else//票箱是两种票的情况  这种情况下必须是两种票都满足才能出票 否则不出票
                {
                    for (int i = 0; i < listOrderDetail.Count; i++)
                    {
                        if (T_SellTicketRecord.Judge_TicketAlarmCount(listOrderDetail[i].TicketTypeId, ACount))
                        {
                            MessageBox.Show("所购票数已超过当前票箱余票数！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                            return;
                        }
                    }
                }

              #endregion

                string str1 = "";//临时存票种1
                string str2 = "";//临时存票种2
                string strcount = "";//计算票数用的
                Int32 ticket1count =0;
                //****************先根据票种获取箱号 如果是两个票箱号是同一种票种的 哪个余票多就用哪个的票箱号****************
                #region//待删除
                //if (M_Configuration.TicketTypeID_1 == M_Configuration.TicketTypeID_2)
                //{ }
                //else
                //{
                //   //
                //}
#endregion
                //****************先根据票种获取箱号 如果是两个票箱号是同一种票种的 哪个余票多就用哪个的票箱号****************

                for (int i = 0; i < listOrderDetail.Count; i++)
                {
                    TicketInfo.Add(new TicketInfo
                    {
                        tickettype = T_SellTicketRecord.GetTicketInfo(listOrderDetail[i].TicketTypeId).TicketTypeName,//票种
                        ticketcount = "1",
                        ticketdate = DateTime.Now.ToString("门票日期：MM月dd日")
                    });
                    if (str1 == "")
                    {
                        str1 = listOrderDetail[i].TicketTypeId;
                    }
                    else if (str1 != listOrderDetail[i].TicketTypeId)
                    {
                        str2 = listOrderDetail[i].TicketTypeId;
                    }
                    if (strcount == "")
                    {
                        strcount = listOrderDetail[i].TicketTypeId;
                        ticket1count = 1;
                    }
                    else if (strcount == listOrderDetail[i].TicketTypeId)
                    {
                        ticket1count = ticket1count + 1;//其中一个票种的票数
                    }                   
                    
                }
                this.parent.mSelfHelpClass.listtypeid.Add(str1);//票种1
                this.parent.mSelfHelpClass.listtypeid.Add(str2);//票种2
                this.parent.mSelfHelpClass.listticketcount.Add(ticket1count);//其中一个票种的票数 其实是第一个票种的票数
                this.parent.mSelfHelpClass.listticketcount.Add(listOrderDetail.Count-ticket1count);//其中一个票种的票数 第二个票种的票数


                //****************赋值
                if (this.parent.mSelfHelpClass.OperType != "0")//购票也用此 先区分下 取票还是购票 0表示购票 1表示取票 自助售票类
                {
                    this.parent.mSelfHelpClass.OperType = "1";//取票还是购票 0表示购票 1表示取票 自助售票类
                }
                this.parent.mSelfHelpClass.listorderdetail.Clear();
                this.parent.mSelfHelpClass.listorderdetail = listOrderDetail;//服务器回传销售明细信息
                this.parent.mSelfHelpClass.TicketTypeID = listOrderDetail[0].TicketTypeId;//票种
                this.parent.mSelfHelpClass.TicketCount = listOrderDetail.Count;//票数
                this.parent.mSelfHelpClass.SaleTime = DateTime.Now.ToString("MM月dd日");//销售时间
                this.parent.mSelfHelpClass.OrderID = listOrderDetail[0].OrderId;//销售单号
            }
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
			this.userName.Text = "订单编号：" + this.parent.SelfHelpInfo.GetTicketUserNumber;
            this.userName.Text = "订单编号：" + this.parent.mSelfHelpClass.GetTicketUserNumber;//自助售票类
		}

		private void btn_GQ_Click(object sender, RoutedEventArgs e)
		{
            if (M_Configuration.SaleMode == 1)//身份证购票模式
            {
                if (this.parent.mSelfHelpClass.OperType == "0")// 取票还是购票 0表示购票 1表示取票
                {
                    this.parent.StartOperation("gp2");//刷完身份证后进行选票界面
                }
                else if (this.parent.mSelfHelpClass.OperType == "1")// 取票还是购票 0表示购票 1表示取票
                {
                    if (this.parent.mSelfHelpClass.TicketCount > 0)
                    {
                        this.parent.Operation("gp6"); //wjl add
                    }
                }
            }
            else//身份证取票模式
            {
                if (this.parent.mSelfHelpClass.TicketCount > 0)
                {
                    this.parent.Operation("gp6"); //wjl add
                }
            }
		}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/qpoperationpage/qppage3.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //    case 1:
        //        ((QPPage3)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        break;
        //    case 2:
        //        this.dockfull = (StackPanel)target;
        //        break;
        //    case 3:
        //        this.userName = (TextBlock)target;
        //        break;
        //    case 4:
        //        this.dataGrid1 = (DataGrid)target;
        //        break;
        //    case 5:
        //        this.btn_GQ = (Button)target;
        //        this.btn_GQ.Click += new RoutedEventHandler(this.btn_GQ_Click);
        //        break;
        //    case 6:
        //        this.btn_SY = (Button)target;
        //        this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //        break;
        //    case 7:
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
