using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using SelfHelpSystem.DAL;
using System.Data;
using System.Data.SQLite;
using System.Configuration;
using SelfHelpSystem.Tool;
using System.Windows.Markup;
using SelfHelpSystem.Model;
using log4net;


namespace SelfHelpSystem
{
    /// <summary>
    /// Page_Setting.xaml 的交互逻辑
    /// </summary>
    public partial class Page_Setting : Page, IComponentConnector
    {

        private ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");
		private MainWindow parent;



        public Page_Setting(MainWindow parentWindow)
		{
            this.parent = parentWindow;	
            this.InitializeComponent();

		}
        public Page_Setting()
        {
            InitializeComponent();
        }

        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            try
            {
                int k1 = -1;
                int k2 = -1;
                string ticket1 = ConfigurationManager.AppSettings["TicketTypeID_1"].ToString();
                string ticket2 = ConfigurationManager.AppSettings["TicketTypeID_2"].ToString();
                SQLiteDataAdapter ada = new SQLiteDataAdapter("select TICKET_TYPE_NAME, TICKET_TYPE_ID  from SYS_TICKET_TYPE where TEAM_flag='N' ", SQLiteHelper.LocalDbConnectionString);
                DataSet ds = new DataSet();
                ada.Fill(ds);
                Dictionary<string, string> mydic = new Dictionary<string, string>();
               
                for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                {
                    if (ds.Tables[0].Rows[i]["TICKET_TYPE_ID"].ToString() == ticket1)
                    {
                        k1 = i;
                    }
                    if (ds.Tables[0].Rows[i]["TICKET_TYPE_ID"].ToString() == ticket2)
                    {
                        k2 = i;
                    }
                    mydic.Add(ds.Tables[0].Rows[i]["TICKET_TYPE_ID"].ToString(), ds.Tables[0].Rows[i]["TICKET_TYPE_NAME"].ToString());
                    //this.look_Ticket1.Items.Add(ds.Tables[0].Rows[i]["TICKET_TYPE_NAME"].ToString());
                    //this.look_Ticket2.Items.Add(ds.Tables[0].Rows[i]["TICKET_TYPE_NAME"].ToString());
                }
                //*******************获取废票数量
                this.txt_AbolishCountt.Text = Convert.ToString(T_SellTicketRecord.GetCount_SL_AbolishCount());
                this.look_Ticket1.ItemsSource = mydic;
                this.look_Ticket2.ItemsSource = mydic;
                this.look_Ticket1.SelectedValuePath = "Key";
                this.look_Ticket1.DisplayMemberPath="Value";
                this.look_Ticket2.SelectedValuePath = "Key";
                this.look_Ticket2.DisplayMemberPath = "Value";
                this.look_Ticket1.SelectedIndex = k1;
                this.look_Ticket2.SelectedIndex = k2;
            }
            catch (Exception ex)
            {
                _log.Error(ex.Message);
            }

            //**********获取余票及报警票数等信息
            DataTable dt = new DataTable();
            dt=T_SellTicketRecord.Get_BoxTicketInfo();
            if (dt.Rows.Count>0)
            {
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    if (dt.Rows[i]["boxNumber"].ToString() == "A")
                    {
                        this.txt_AalarmCount.Text = dt.Rows[i]["alarmCount"].ToString();
                        this.txt_AYuCount.Text = dt.Rows[i]["surplusCount"].ToString();
                    }
                    else if (dt.Rows[i]["boxNumber"].ToString() == "B")
                    {
                        this.txt_BalarmCount.Text = dt.Rows[i]["alarmCount"].ToString();
                        this.txt_BYuCount.Text = dt.Rows[i]["surplusCount"].ToString();
                    }
                }

            }
        }

        private void txt_ACount_KeyDown(object sender, KeyEventArgs e)
        {
            if ((e.Key >= Key.NumPad0 && e.Key <= Key.NumPad9) || (e.Key >= Key.D0 && e.Key <= Key.D9))
            {
                e.Handled = false;
            }
            else
            {
                e.Handled = true;
            }
            //if (!(Char.IsNumber(e.KeyChar)) && e.KeyChar != (char)13 && e.KeyChar != (char)8)
            //{
            //    e.Handled = true;
            //} 
        }

        private void bt_Save_Click(object sender, RoutedEventArgs e)
        {
            if (this.look_Ticket1.Text == "")
            {
                MessageBox.Show("请设置一号票箱的票种！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                    return;
            }
            if (this.look_Ticket2.Text == "")
            {
                MessageBox.Show("请设置二号票箱的票种！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }
            Configuration cfa = ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
            cfa.AppSettings.Settings["TicketTypeID_1"].Value = this.look_Ticket1.SelectedValue.ToString();
            cfa.AppSettings.Settings["TicketTypeID_2"].Value = this.look_Ticket2.SelectedValue.ToString();
           
            cfa.Save(ConfigurationSaveMode.Modified);
            //更新票箱余票
            Int32 surplusCount = 0;
            Int32 alarmCount = 0;
            bool isok = false;
            if (txt_ACount.Text.ToString() != "")
            {
                surplusCount = Convert.ToInt32(txt_ACount.Text);
            }
            if (txt_AalarmCount.Text.ToString() != "")
            {
                alarmCount = Convert.ToInt32(txt_AalarmCount.Text);
            }
            string strchang = "0";
            if (this.look_Ticket1.SelectedValue.ToString() != M_Configuration.TicketTypeID_1)
            {
                strchang = "1";//票种更改了
            }else
            {
                strchang = "0";//票种没变
            }
            if (!T_SellTicketRecord.UpdateCount_SL_TicketCount("A", this.look_Ticket1.SelectedValue.ToString(), surplusCount, alarmCount, strchang))
            {
                MessageBox.Show("A票箱余票更新失败！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                isok = true;
            }
            surplusCount = 0;
            alarmCount = 0;
            if (txt_BCount.Text.ToString() != "")
            {
                surplusCount = Convert.ToInt32(txt_BCount.Text);
            }
            if (txt_BalarmCount.Text.ToString() != "")
            {
                alarmCount = Convert.ToInt32(txt_BalarmCount.Text);
            }
            if (this.look_Ticket2.SelectedValue.ToString() != M_Configuration.TicketTypeID_2)
            {
                strchang = "1";//票种更改了
            }
            else
            {
                strchang = "0";//票种没变
            }
            if (!T_SellTicketRecord.UpdateCount_SL_TicketCount("B", this.look_Ticket2.SelectedValue.ToString(), surplusCount, alarmCount, strchang))
            {
                MessageBox.Show("B票箱余票更新失败！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                isok = true;
            }
            //废票重置
            if (this.check_Abolish.IsChecked==true)
            {
                T_SellTicketRecord.Update_SL_AbolishCount(true);
            }

            if (isok == false)
            {
                MessageBox.Show("票箱余票更新成功！系统将自动退出,重启后配置生效！", T_SellTicketRecord.str_Apption, MessageBoxButton.OK, MessageBoxImage.Information);
                this.parent.EndOperation();
                this.parent.Close();//
            }
            
        }

        private void bt_Cancel_Click(object sender, RoutedEventArgs e)
        {
            this.parent.EndOperation();
            
        }




    }
}
