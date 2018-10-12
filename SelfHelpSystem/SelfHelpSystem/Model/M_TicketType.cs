using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SelfHelpSystem.Model
{
    /// <summary>
    /// 票种表
    /// </summary>
    [Serializable]
   public class M_TicketType
    {
        //public M_TicketType() { }

		#region Model
        private String   _ticket_type_id;
        private String   _ticket_type_name;
        private String   _team_flag;
        private Int32    _validate_times;
        private String   _less_flag;
        private String   _day_night_flag;
        private String   _day_validate_flag;
        private Int32    _price;
        private Int32 _realprice;//实际单价
        private String   _ope_user_id;
        private DateTime _ope_time;
        private DateTime _version;

        /// <summary>
        /// 票种编号
        /// </summary>
        public String TICKET_TYPE_ID
        {
            set { _ticket_type_id = value; }
            get { return _ticket_type_id; }
        }

        /// <summary>
        /// 票种名称
        /// </summary>
        public String TICKET_TYPE_NAME
        {
            set { _ticket_type_name = value; }
            get { return _ticket_type_name; }
        }

        /// <summary>
        /// 是否团体
        /// </summary>
        public String TEAM_FLAG
        {
            set { _team_flag = value; }
            get { return _team_flag; }
        }

        /// <summary>
        /// 可用次数
        /// </summary>
        public Int32 VALIDATE_TIMES
        {
            set { _validate_times = value; }
            get { return _validate_times; }
        }

        /// <summary>
        /// 是否优惠票
        /// </summary>
        public String LESS_FLAG
        {
            set { _less_flag = value; }
            get { return _less_flag; }
        }

        /// <summary>
        /// 日夜场
        /// </summary>
        public String DAY_NIGHT_FLAG
        {
            set { _day_night_flag = value; }
            get { return _day_night_flag; }
        }

        /// <summary>
        /// 是否销售日当日有效
        /// </summary>
        public String DAY_VALIDATE_FLAG
        {
            set { _day_validate_flag = value; }
            get { return _day_validate_flag; }
        }

        /// <summary>
        /// 票价
        /// </summary>
        public Int32 PRICE
        {
            set { _price = value; }
            get { return _price; }
        }
        /// <summary>
        /// 实际票价
        /// </summary>
        public Int32 Realprice
        {
            get { return _realprice; }
            set { _realprice = value; }
        }
        /// <summary>
        /// 操作人
        /// </summary>
        public String OPE_USER_ID
        {
            set { _ope_user_id = value; }
            get { return _ope_user_id; }
        }

        /// <summary>
        /// 操作时间
        /// </summary>
        public DateTime OPE_TIME
        {
            set { _ope_time = value; }
            get { return _ope_time; }
        }

        /// <summary>
        /// 版本号
        /// </summary>
        public DateTime VERSION
        {
            set { _version = value; }
            get { return _version; }
        }
        #endregion Model
    }
}
