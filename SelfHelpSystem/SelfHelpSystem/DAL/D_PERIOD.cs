using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data.SQLite;
using System.Data;
using SelfHelpSystem.Tool;
using tbims.rpc.entity;
using log4net;

namespace SelfHelpSystem.DAL
{
    /// <summary>
    /// 预售期表数据库访问类
    /// </summary>
    class D_PERIOD
    {

        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        /// <summary>
        /// 根据预售期ID号判断是否有记录
        /// </summary>
        /// <param name="varPeriodId">预售期编号</param>
        /// <returns>1 存在 2 不存在 3 查询失败</returns>
        /// <Remark>郝毅志，2017/06/26</ Remark >
        public int IsExist(String varPeriodId)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("SELECT SALE_PERIOD_ID,TICKET_TYPE_ID,SALE_PERIOD_NAME,BEGIN_DT,END_DT,DISCOUNT_PRICE,VERSION_NO ");
            tmpStrSql.Append("FROM SL_PERIOD ");
            tmpStrSql.Append("WHERE SALE_PERIOD_ID=@SALE_PERIOD_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@SALE_PERIOD_ID", DbType.String,60)
					};
            tmpParameters[0].Value = varPeriodId;
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
                _log.Error(typeof(D_PERIOD), ex);
                return 3;
            }

            if (tmpDtable.Rows.Count > 0)
            {
                return 1;
            }
            return 2;
        }

        /// <summary>
        /// 获取预售期表最新版本号
        /// </summary>
        /// <returns>最新版本号</returns>
        /// <Remark>郝毅志，2017/06/26</ Remark >
        public DateTime GetLasterVersion()
        {
            SQLiteCommand tmpCmd = new SQLiteCommand("SELECT MAX(VERSION_NO) FROM SL_PERIOD");
            DataSet tmpDtable = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, tmpCmd);
            String tmpVersion = (String)tmpDtable.Tables[0].Rows[0].ItemArray[0];
            return Convert.ToDateTime(tmpVersion, T_Date.DateFormatSet());
        }

        /// <summary>
        /// 更新预售期信息(同步下载处理)
        /// </summary>
        /// <param name="varPeriodModel">预售期信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/26</ Remark >
        public bool UpdatePeriod(SL_PERIOD varPeriodModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("UPDATE SL_PERIOD ");
            tmpStrSql.Append("SET TICKET_TYPE_ID=@TICKET_TYPE_ID,SALE_PERIOD_NAME=@SALE_PERIOD_NAME,BEGIN_DT=@BEGIN_DT,END_DT=@END_DT,DISCOUNT_PRICE=@DISCOUNT_PRICE,VERSION_NO=@VERSION_NO ");
            tmpStrSql.Append("WHERE SALE_PERIOD_ID=@SALE_PERIOD_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@SALE_PERIOD_ID", DbType.String,60),
					new SQLiteParameter("@TICKET_TYPE_ID", DbType.String,3),
					new SQLiteParameter("@SALE_PERIOD_NAME", DbType.String,100),
                    new SQLiteParameter("@BEGIN_DT", DbType.DateTime),
					new SQLiteParameter("@END_DT", DbType.DateTime),
                    new SQLiteParameter("@DISCOUNT_PRICE", DbType.Int64),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varPeriodModel.SalePeriodId;
            tmpParameters[1].Value = varPeriodModel.TicketTypeId;
            tmpParameters[2].Value = varPeriodModel.SalePeriodName;
            tmpParameters[3].Value = T_Date.ConvertLongToDateTime(varPeriodModel.BeginDt);
            tmpParameters[4].Value = T_Date.ConvertLongToDateTime(varPeriodModel.EndDt);
            tmpParameters[5].Value = varPeriodModel.DiscountPrice;
            tmpParameters[6].Value = T_Date.ConvertLongToDateTime(varPeriodModel.VersionNo);
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
                _log.Error(typeof(D_PERIOD), ex);
                return false;
            }
            if (tmpRows > 0)
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// 新增预售期信息
        /// </summary>
        /// <param name="varEmpRegisterModel">预售期信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/26</ Remark >
        public bool InsertPeriod(SL_PERIOD varPeriodModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("INSERT INTO SL_PERIOD(");
            tmpStrSql.Append("SALE_PERIOD_ID,TICKET_TYPE_ID,SALE_PERIOD_NAME,BEGIN_DT,END_DT,DISCOUNT_PRICE,VERSION_NO)");
            tmpStrSql.Append(" values (");
            tmpStrSql.Append("@SALE_PERIOD_ID,@TICKET_TYPE_ID,@SALE_PERIOD_NAME,@BEGIN_DT,@END_DT,@DISCOUNT_PRICE,@VERSION_NO)");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@SALE_PERIOD_ID", DbType.String,60),
					new SQLiteParameter("@TICKET_TYPE_ID", DbType.String,3),
					new SQLiteParameter("@SALE_PERIOD_NAME", DbType.String,100),
                    new SQLiteParameter("@BEGIN_DT", DbType.DateTime),
					new SQLiteParameter("@END_DT", DbType.DateTime),
                    new SQLiteParameter("@DISCOUNT_PRICE", DbType.Int64),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varPeriodModel.SalePeriodId;
            tmpParameters[1].Value = varPeriodModel.TicketTypeId;
            tmpParameters[2].Value = varPeriodModel.SalePeriodName;
            tmpParameters[3].Value = T_Date.ConvertLongToDateTime(varPeriodModel.BeginDt);
            tmpParameters[4].Value = T_Date.ConvertLongToDateTime(varPeriodModel.EndDt);
            tmpParameters[5].Value = varPeriodModel.DiscountPrice;
            tmpParameters[6].Value = T_Date.ConvertLongToDateTime(varPeriodModel.VersionNo);
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
                _log.Error(typeof(D_PERIOD), ex);
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
        /// 清空预售期信息
        /// </summary>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool CleartPeriod(SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("DELETE FROM SL_PERIOD");
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
    }
}
