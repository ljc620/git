using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data.SQLite;
using System.Data;
using SelfHelpSystem.Model;
using tbims.rpc.entity;
using log4net;
using SelfHelpSystem.Tool;


namespace SelfHelpSystem.DAL
{
    /// <summary>
    /// 票种表数据库访问类
    /// </summary>
    class D_TicketType
    {
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");
        /// <summary>
        /// 根据票种ID号判断是否有记录
        /// </summary>
        /// <param name="varEmpId">票种编号</param>
        /// <returns>1 存在 2 不存在 3 查询失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public int IsExist(String varTicketTypeId)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("SELECT TICKET_TYPE_ID,TICKET_TYPE_NAME,TEAM_FLAG,VALIDATE_TIMES,LESS_FLAG,DAY_NIGHT_FLAG,DAY_VALIDATE_FLAG,PRICE,OPE_USER_ID,OPE_TIME,VERSION_NO ");
            tmpStrSql.Append("FROM SYS_TICKET_TYPE ");
            tmpStrSql.Append("WHERE TICKET_TYPE_ID=@TICKET_TYPE_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@TICKET_TYPE_ID", DbType.String,2)
					};
            tmpParameters[0].Value = varTicketTypeId;
            DataTable tmpDtable = null;
            try
            {
                tmpDtable = SQLiteHelper.ExecuteDataSet(
                    SQLiteHelper.LocalDbConnectionString,
                    tmpStrSql.ToString(),
                    CommandType.Text,
                    tmpParameters).Tables[0];
            }
            catch (Exception ex)
            {
                _log.Error(typeof(D_EmpRegister), ex);
                return 3;
            }

            if (tmpDtable.Rows.Count > 0)
            {
                return 1;
            }
            return 2;
        }

        /// <summary>
        /// 获取票种表最新版本号
        /// </summary>
        /// <returns>最新版本号</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public DateTime GetLasterVersion()
        {
            SQLiteCommand tmpCmd = new SQLiteCommand("SELECT MAX(VERSION_NO) FROM SYS_TICKET_TYPE");
            DataSet tmpDtable = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, tmpCmd);
            String tmpVersion = (String)tmpDtable.Tables[0].Rows[0].ItemArray[0];
            return Convert.ToDateTime(tmpVersion, T_Date.DateFormatSet());
        }

        /// <summary>
        /// 更新票种信息(同步下载处理)
        /// </summary>
        /// <param name="varTicketTypeModel">票种信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool UpdateTicketType(SYS_TICKET_TYPE varTicketTypeModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("UPDATE SYS_TICKET_TYPE ");
            tmpStrSql.Append("SET TICKET_TYPE_NAME=@TICKET_TYPE_NAME,TEAM_FLAG=@TEAM_FLAG,VALIDATE_TIMES=@VALIDATE_TIMES,LESS_FLAG=@LESS_FLAG,DAY_NIGHT_FLAG=@DAY_NIGHT_FLAG,DAY_VALIDATE_FLAG=@DAY_VALIDATE_FLAG,");
            tmpStrSql.Append("PRICE=@PRICE,OPE_USER_ID=@OPE_USER_ID,OPE_TIME=@OPE_TIME,VERSION_NO=@VERSION_NO ");
            tmpStrSql.Append("WHERE TICKET_TYPE_ID=@TICKET_TYPE_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@TICKET_TYPE_ID", DbType.String,2),
					new SQLiteParameter("@TICKET_TYPE_NAME", DbType.String,200),
					new SQLiteParameter("@TEAM_FLAG", DbType.String,1),
                    new SQLiteParameter("@VALIDATE_TIMES", DbType.Int32),
					new SQLiteParameter("@LESS_FLAG", DbType.String,1),
                    new SQLiteParameter("@DAY_NIGHT_FLAG", DbType.String,1),
					new SQLiteParameter("@DAY_VALIDATE_FLAG", DbType.String,1),
                    new SQLiteParameter("@PRICE", DbType.Int32),
					new SQLiteParameter("@OPE_USER_ID", DbType.String,50),
                    new SQLiteParameter("@OPE_TIME", DbType.DateTime),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varTicketTypeModel.TicketTypeId;
            tmpParameters[1].Value = varTicketTypeModel.TicketTypeName;
            tmpParameters[2].Value = varTicketTypeModel.TeamFlag;
            tmpParameters[3].Value = varTicketTypeModel.ValidateTimes;
            tmpParameters[4].Value = varTicketTypeModel.LessFlag;
            tmpParameters[5].Value = varTicketTypeModel.DayNightFlag;
            tmpParameters[6].Value = varTicketTypeModel.DayValidateFlag;
            tmpParameters[7].Value = varTicketTypeModel.Price;
            tmpParameters[8].Value = varTicketTypeModel.OpeUserId;
            tmpParameters[9].Value = T_Date.ConvertLongToDateTime(varTicketTypeModel.OpeTime);
            tmpParameters[10].Value = T_Date.ConvertLongToDateTime(varTicketTypeModel.VersionNo);
            //执行命令配置
            varCommand.CommandText = tmpStrSql.ToString();
            varCommand.CommandType = CommandType.Text;
            foreach (SQLiteParameter parm in tmpParameters)
                varCommand.Parameters.Add(parm);

            int tmpRows = 0;
            try
            {
                tmpRows = varCommand.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                _log.Error(typeof(D_TicketType), ex);
                return false;
            }
            if (tmpRows > 0)
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// 新增票种信息
        /// </summary>
        /// <param name="varTicketTypeModel">票种信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool InsertTicketType(SYS_TICKET_TYPE varTicketTypeModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("INSERT INTO SYS_TICKET_TYPE(");
            tmpStrSql.Append("TICKET_TYPE_ID,TICKET_TYPE_NAME,TEAM_FLAG,VALIDATE_TIMES,LESS_FLAG,DAY_NIGHT_FLAG,DAY_VALIDATE_FLAG,PRICE,OPE_USER_ID,OPE_TIME,VERSION_NO)");
            tmpStrSql.Append(" values (");
            tmpStrSql.Append("@TICKET_TYPE_ID,@TICKET_TYPE_NAME,@TEAM_FLAG,@VALIDATE_TIMES,@LESS_FLAG,@DAY_NIGHT_FLAG,@DAY_VALIDATE_FLAG,@PRICE,@OPE_USER_ID,@OPE_TIME,@VERSION_NO)");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@TICKET_TYPE_ID", DbType.String,2),
					new SQLiteParameter("@TICKET_TYPE_NAME", DbType.String,200),
					new SQLiteParameter("@TEAM_FLAG", DbType.String,1),
                    new SQLiteParameter("@VALIDATE_TIMES", DbType.Int32),
					new SQLiteParameter("@LESS_FLAG", DbType.String,1),
                    new SQLiteParameter("@DAY_NIGHT_FLAG", DbType.String,1),
					new SQLiteParameter("@DAY_VALIDATE_FLAG", DbType.String,1),
                    new SQLiteParameter("@PRICE", DbType.Int32),
					new SQLiteParameter("@OPE_USER_ID", DbType.String,50),
                    new SQLiteParameter("@OPE_TIME", DbType.DateTime),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varTicketTypeModel.TicketTypeId;
            tmpParameters[1].Value = varTicketTypeModel.TicketTypeName;
            tmpParameters[2].Value = varTicketTypeModel.TeamFlag;
            tmpParameters[3].Value = varTicketTypeModel.ValidateTimes;
            tmpParameters[4].Value = varTicketTypeModel.LessFlag;
            tmpParameters[5].Value = varTicketTypeModel.DayNightFlag;
            tmpParameters[6].Value = varTicketTypeModel.DayValidateFlag;
            tmpParameters[7].Value = varTicketTypeModel.Price;
            tmpParameters[8].Value = varTicketTypeModel.OpeUserId;
            tmpParameters[9].Value = T_Date.ConvertLongToDateTime(varTicketTypeModel.OpeTime);
            tmpParameters[10].Value = T_Date.ConvertLongToDateTime(varTicketTypeModel.VersionNo);
            //执行命令配置
            varCommand.CommandText = tmpStrSql.ToString();
            varCommand.CommandType = CommandType.Text;
            foreach (SQLiteParameter parm in tmpParameters)
                varCommand.Parameters.Add(parm);

            int tmpRows = 0;
            try
            {
                tmpRows = varCommand.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                _log.Error(typeof(D_TicketType), ex);
                return false;
            }
            if (tmpRows > 0)
            {
                return true;
            }
            else
                return false;
        }

        /// <summary>
        /// 删除票种信息
        /// </summary>
        /// <param name="varTicketTypeModel">票种信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool DeleteTicketType(String varTicketTypeId, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("DELETE FROM SYS_TICKET_TYPE WHERE TICKET_TYPE_ID=@TICKET_TYPE_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@TICKET_TYPE_ID", DbType.String,2)};
            tmpParameters[0].Value = varTicketTypeId;
            //执行命令配置
            varCommand.CommandText = tmpStrSql.ToString();
            varCommand.CommandType = CommandType.Text;
            foreach (SQLiteParameter parm in tmpParameters)
                varCommand.Parameters.Add(parm);
            int tmpRows = 0;
            try
            {
                tmpRows = varCommand.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                _log.Error(typeof(D_TicketType), ex);
                return false;
            }
            return true;
        }

        /// <summary>
        /// 清空票种信息
        /// </summary>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool ClearTicketType(SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("DELETE FROM SYS_TICKET_TYPE");
            //执行命令配置
            varCommand.CommandText = tmpStrSql.ToString();
            varCommand.CommandType = CommandType.Text;
            int tmpRows = 0;
            try
            {
                tmpRows = varCommand.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                _log.Error(typeof(D_TicketType), ex);
                return false;
            }
            return true;
        }

        /// <summary>
        /// 根据票种编号返回一个票种实体
        /// </summary>
        /// <param name="ticket_type_id">票种编号</param>
        /// <returns>票种实体</returns>
        public static M_TicketType GetTicketTypeInfo(string ticket_type_id)
        {
            M_TicketType mticketype = new M_TicketType();
            #region //具体操作
            if (ticket_type_id != "")
            {
                string strsel = "select * from SYS_TICKET_TYPE where TICKET_TYPE_ID='" + ticket_type_id + "'";
                SQLiteCommand cmdsel = new SQLiteCommand(strsel);
                DataSet ds = new DataSet();
                ds = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, cmdsel);
                if (ds.Tables[0].Rows.Count > 0)
                {
                    mticketype.TICKET_TYPE_ID = ds.Tables[0].Rows[0]["ticket_type_id"].ToString();//票种编号
                    mticketype.TICKET_TYPE_NAME = ds.Tables[0].Rows[0]["ticket_type_name"].ToString();//票种名称
                    mticketype.TEAM_FLAG = ds.Tables[0].Rows[0]["team_flag"].ToString();//是否团体
                    if (ds.Tables[0].Rows[0]["validate_times"].ToString() != "")
                    {
                        mticketype.VALIDATE_TIMES = Convert.ToInt32(ds.Tables[0].Rows[0]["validate_times"]);//可用次数
                    }
                    else
                    {
                        mticketype.VALIDATE_TIMES = 0;
                    }
                    mticketype.LESS_FLAG = ds.Tables[0].Rows[0]["LESS_FLAG"].ToString();//是否优惠
                    mticketype.DAY_NIGHT_FLAG = ds.Tables[0].Rows[0]["DAY_NIGHT_FLAG"].ToString();//日夜场
                    mticketype.DAY_VALIDATE_FLAG = ds.Tables[0].Rows[0]["DAY_VALIDATE_FLAG"].ToString();//是否销售日当日有效(Y是N否)
                    if (ds.Tables[0].Rows[0]["PRICE"].ToString() != "")
                    {
                        mticketype.PRICE = Convert.ToInt32(ds.Tables[0].Rows[0]["PRICE"]);//票价
                    }
                    else
                    {
                        mticketype.PRICE = 0;
                    }
                    mticketype.OPE_USER_ID = ds.Tables[0].Rows[0]["OPE_USER_ID"].ToString();//操作人
                    if (ds.Tables[0].Rows[0]["OPE_TIME"].ToString() != "")
                    {
                        mticketype.OPE_TIME = Convert.ToDateTime(ds.Tables[0].Rows[0]["OPE_TIME"]);//操作时间
                    }
                    else
                    {
                        mticketype.OPE_TIME = DateTime.Now;
                    }
                    if (ds.Tables[0].Rows[0]["version_no"].ToString() != "")
                    {
                        mticketype.VERSION = Convert.ToDateTime(ds.Tables[0].Rows[0]["version_no"]);//版本
                    }
                    else
                    {
                        mticketype.VERSION = DateTime.Now;
                    }
                }
            }
            #endregion

            return mticketype;
        }
    }
}
