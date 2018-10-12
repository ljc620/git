using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using tbims.rpc.entity;

namespace SelfHelpSystem.Model
{
    /// <summary>
    /// 自助售票类
    /// </summary>
    public class M_SelfHelpClass
    {
        public List<string> TicketNumberList = new List<string>();

		public List<string> TicketBlockNumberList = new List<string>();

		public List<string> TicketBaseNumberList = new List<string>();

        public List<SL_ORDER_DETAIL> listorderdetail = new List<SL_ORDER_DETAIL>();//身份取票时用于存储从服务端回传的销售明细信息
        public List<string> listtypeid = new List<string>();//取票时的所有票种
        public List<Int32> listticketcount = new List<Int32>();//取票时的各个票种的票数
        /// <summary>
        /// 取票还是购票 0表示购票 1表示取票
        /// </summary>
		public string OperType
		{
			get;
			set;
		}
        /// <summary>
        /// 箱号
        /// </summary>
        public string BoxNumber
        {
            get;
            set;
        }
        /// <summary>
        /// 销售时间
        /// </summary>
		public string SaleTime
		{
			get;
			set;
		}
        /// <summary>
        /// 票种
        /// </summary>
		public string TicketTypeID
		{
			get;
			set;
		}
        /// <summary>
        /// 原票价
        /// </summary>
        public Int64 OriginalPrice
        {
            get;
            set;
        }
        /// <summary>
        /// 销售价
        /// </summary>
        public Int64 SalePrice
        {
            get;
            set;
        }
        /// <summary>
        /// 票数
        /// </summary>
		public int TicketCount
		{
			get;
			set;
		}
        /// <summary>
        /// 应收金额
        /// </summary>
        public Int64 DueSum
        {
            get
            {
                return OriginalPrice * TicketCount;
            }
        }
        /// <summary>
        /// 实收金额
        /// </summary>
        public Int64 RealSum
        {
            get 
            {
                return SalePrice * TicketCount;
            }
        }
        /// <summary>
        /// 销售单号
        /// </summary>
        public string OrderID
        {
            get;
            set;
        }
        /// <summary>
        /// 支付方式
        /// </summary>
		public string PayType
		{
			get;
			set;
		}
        /// <summary>
        /// 支付单号
        /// </summary>
        public string PayTypeID
        {
            get;
            set;
        }
        /// <summary>
        /// 第三方支付单号
        /// </summary>
        public string PayID
        {
            get;
            set;
        }
        /// <summary>
        /// 是否支付成功 true成功 false失败
        /// </summary>
		public bool PaySuccess
		{
			get;
			set;
		}
        /// <summary>
        /// 支付状态(1-待支付 2-已支付 3-支付失败)
        /// </summary>
        public string PayStat
        {
            get;
            set;
        }

        /// <summary>
        /// 存储扫描上来的值
        /// </summary>
		public string PayodNo
		{
			get;
			set;
		}

		public string PayRefNo
		{
			get;
			set;
		}

		public string PayTxnId
		{
			get;
			set;
		}

		public string PayReturnInfo
		{
			get;
			set;
		}

		public string AbutmentInfo
		{
			get;
			set;
		}

		public string AbutmentReturnInfo
		{
			get;
			set;
		}
        /// <summary>
        /// 取票方式 1身份证取票 2手机号取票 3扫描二维码取票
        /// </summary>
		public string GetTicketType
		{
			get;
			set;
		}

        /// <summary>
        /// 身份证号
        /// </summary>
		public string GetTicketUserNumber
		{
			get;
			set;
		}

		public DateTime CreateTime
		{
			get;
			set;
		}

        public M_SelfHelpClass()
		{
			this.CreateTime = DateTime.Now;
		}
    }
}
