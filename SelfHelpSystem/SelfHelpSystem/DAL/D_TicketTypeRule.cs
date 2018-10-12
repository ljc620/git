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
    /// 票种检票规则表数据库访问类
    /// </summary>
    class D_TicketTypeRule
    {
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        /// <summary>
        /// 更新票种检票规则信息(同步下载处理)
        /// </summary>
        /// <param name="varTicketTypeRule">票种检票规则实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool UpdateTicketTypeRule(SYS_TICKET_TYPE_RULE varTicketTypeRule, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("UPDATE SYS_TICKET_TYPE_RULE ");
            tmpStrSql.Append("SET TICKET_TYPE_ID=@TICKET_TYPE_ID,BEGIN_DT=@BEGIN_DT,END_DT=@END_DT,BEGIN_TIME=@BEGIN_TIME,END_TIME=@END_TIME,");
            tmpStrSql.Append("OPE_USER_ID=@OPE_USER_ID,OPE_TIME=@OPE_TIME,VERSION_NO=@VERSION_NO ");
            tmpStrSql.Append("WHERE RULE_ID=@RULE_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@RULE_ID", DbType.String,60),
					new SQLiteParameter("@TICKET_TYPE_ID", DbType.String,3),
					new SQLiteParameter("@BEGIN_DT", DbType.DateTime),
                    new SQLiteParameter("@END_DT", DbType.DateTime),
					new SQLiteParameter("@BEGIN_TIME", DbType.Int64),
                    new SQLiteParameter("@END_TIME", DbType.Int64),
					new SQLiteParameter("@OPE_USER_ID", DbType.String,50),
                    new SQLiteParameter("@OPE_TIME", DbType.DateTime),
                    new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varTicketTypeRule.RuleId;
            tmpParameters[1].Value = varTicketTypeRule.TicketTypeId;
            tmpParameters[2].Value = T_Date.ConvertLongToDateTime(varTicketTypeRule.BeginDt);
            tmpParameters[3].Value = T_Date.ConvertLongToDateTime(varTicketTypeRule.EndDt);
            tmpParameters[4].Value = varTicketTypeRule.BeginTime;
            tmpParameters[5].Value = varTicketTypeRule.EndTime;
            tmpParameters[6].Value = varTicketTypeRule.OpeUserId;
            tmpParameters[7].Value = T_Date.ConvertLongToDateTime(varTicketTypeRule.OpeTime);
            tmpParameters[8].Value = T_Date.ConvertLongToDateTime(varTicketTypeRule.VersionNo);
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
                _log.Error(typeof(D_TicketTypeRule), ex);
                return false;
            }
            if (tmpRows > 0)
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// 新增票种检票规则信息
        /// </summary>
        /// <param name="varTicketTypeRule">票种检票规则信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool InsertTicketTypeRule(SYS_TICKET_TYPE_RULE varTicketTypeRule, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("INSERT INTO SYS_TICKET_TYPE_RULE(");
            tmpStrSql.Append("RULE_ID,TICKET_TYPE_ID,BEGIN_DT,END_DT,BEGIN_TIME,END_TIME,OPE_USER_ID,OPE_TIME,VERSION_NO)");
            tmpStrSql.Append(" values (");
            tmpStrSql.Append("@RULE_ID,@TICKET_TYPE_ID,@BEGIN_DT,@END_DT,@BEGIN_TIME,@END_TIME,@OPE_USER_ID,@OPE_TIME,@VERSION_NO)");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@RULE_ID", DbType.String,60),
					new SQLiteParameter("@TICKET_TYPE_ID", DbType.String,3),
					new SQLiteParameter("@BEGIN_DT", DbType.DateTime),
                    new SQLiteParameter("@END_DT", DbType.DateTime),
					new SQLiteParameter("@BEGIN_TIME", DbType.Int64),
                    new SQLiteParameter("@END_TIME", DbType.Int64),
					new SQLiteParameter("@OPE_USER_ID", DbType.String,50),
                    new SQLiteParameter("@OPE_TIME", DbType.DateTime),
                    new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varTicketTypeRule.RuleId;
            tmpParameters[1].Value = varTicketTypeRule.TicketTypeId;
            tmpParameters[2].Value = T_Date.ConvertLongToDateTime(varTicketTypeRule.BeginDt);
            tmpParameters[3].Value = T_Date.ConvertLongToDateTime(varTicketTypeRule.EndDt);
            tmpParameters[4].Value = varTicketTypeRule.BeginTime;
            tmpParameters[5].Value = varTicketTypeRule.EndTime;
            tmpParameters[6].Value = varTicketTypeRule.OpeUserId;
            tmpParameters[7].Value = T_Date.ConvertLongToDateTime(varTicketTypeRule.OpeTime);
            tmpParameters[8].Value = T_Date.ConvertLongToDateTime(varTicketTypeRule.VersionNo);
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
                _log.Error(typeof(D_TicketTypeRule), ex);
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
        /// 删除票种检票规则信息
        /// </summary>
        /// <param name="varTicketTypeModel">检票规则ID</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool DeleteTicketType(String varTicketTypeId, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("DELETE FROM SYS_TICKET_TYPE_RULE WHERE TICKET_TYPE_ID=@TICKET_TYPE_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@TICKET_TYPE_ID", DbType.String,3)};
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
        /// 清空票种检票规则信息
        /// </summary>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool ClearTicketType(SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("DELETE FROM SYS_TICKET_TYPE_RULE");
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
