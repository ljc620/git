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
    /// 黑名单表数据库访问类
    /// </summary>
    class D_BlackList
    {

        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        /// <summary>
        /// 根据黑名单ID号判断是否有记录
        /// </summary>
        /// <param name="varBlackListId">黑名单编号</param>
        /// <returns>1 存在 2 不存在 3 查询失败</returns>
        /// <Remark>郝毅志，2017/06/23</ Remark >
        public int IsExist(String varBlackListId)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("SELECT BLACK_LIST_ID,CARD_TYPE,LOSS_DT,TICKET_ID,CHIP_ID,LOSS_REASON,OPE_USER_ID,OPE_TIME,VERSION_NO ");
            tmpStrSql.Append("FROM SYS_BLACK_LIST ");
            tmpStrSql.Append("WHERE BLACK_LIST_ID=@BLACK_LIST_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@BLACK_LIST_ID", DbType.String,50)
					};
            tmpParameters[0].Value = varBlackListId;
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
                _log.Error(typeof(D_BlackList), ex);
                return 3;
            }

            if (tmpDtable.Rows.Count > 0)
            {
                return 1;
            }
            return 2;
        }

        /// <summary>
        /// 获取黑名单表新版本号
        /// </summary>
        /// <returns>最新版本号</returns>
        /// <Remark>郝毅志，2017/06/23</ Remark >
        public DateTime GetLasterVersion()
        {
            SQLiteCommand tmpCmd = new SQLiteCommand("SELECT MAX(VERSION_NO) FROM SYS_BLACK_LIST");
            DataSet tmpDtable = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, tmpCmd);
            String tmpVersion = (String)tmpDtable.Tables[0].Rows[0].ItemArray[0];
            return Convert.ToDateTime(tmpVersion, T_Date.DateFormatSet());
        }

        /// <summary>
        /// 更新黑名单信息(同步下载处理)
        /// </summary>
        /// <param name="varBlackListModel">黑名单实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/22</ Remark >
        public bool UpdateBlackList(SYS_BLACK_LIST varBlackListModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("UPDATE SYS_BLACK_LIST ");
            tmpStrSql.Append("SET CARD_TYPE=@CARD_TYPE,LOSS_DT=@LOSS_DT,TICKET_ID=@TICKET_ID,STAT=@STAT,CHIP_ID=@CHIP_ID,LOSS_REASON=@LOSS_REASON,");
            tmpStrSql.Append("OPE_USER_ID=@OPE_USER_ID,OPE_TIME=@OPE_TIME,VERSION_NO=@VERSION_NO ");
            tmpStrSql.Append("WHERE BLACK_LIST_ID=@BLACK_LIST_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@BLACK_LIST_ID", DbType.String,50),
					new SQLiteParameter("@CARD_TYPE", DbType.String,1),
					new SQLiteParameter("@LOSS_DT", DbType.DateTime),
                    new SQLiteParameter("@TICKET_ID", DbType.String,10),
                    new SQLiteParameter("@STAT", DbType.String,1),
					new SQLiteParameter("@CHIP_ID", DbType.String,20),
                    new SQLiteParameter("@LOSS_REASON", DbType.String,200),
					new SQLiteParameter("@OPE_USER_ID", DbType.String,50),
                    new SQLiteParameter("@OPE_TIME", DbType.DateTime),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varBlackListModel.BlackListId;
            tmpParameters[1].Value = varBlackListModel.CardType;
            tmpParameters[2].Value = T_Date.ConvertLongToDateTime(varBlackListModel.LossDt);
            tmpParameters[3].Value = varBlackListModel.TicketId;
            tmpParameters[4].Value = varBlackListModel.Stat;
            tmpParameters[5].Value = varBlackListModel.ChipId;
            tmpParameters[6].Value = varBlackListModel.LossReason;
            tmpParameters[7].Value = varBlackListModel.OpeUserId;
            tmpParameters[8].Value = T_Date.ConvertLongToDateTime(varBlackListModel.OpeTime);
            tmpParameters[9].Value = T_Date.ConvertLongToDateTime(varBlackListModel.VersionNo);
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
                _log.Error(typeof(D_BlackList), ex);
                return false;
            }
            if (tmpRows > 0)
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// 新增黑名单信息
        /// </summary>
        /// <param name="varEmpRegisterModel">黑名单信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/22</ Remark >
        public bool InsertBlackList(SYS_BLACK_LIST varEmpRegisterModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("INSERT INTO SYS_BLACK_LIST(");
            tmpStrSql.Append("BLACK_LIST_ID,CARD_TYPE,LOSS_DT,TICKET_ID,STAT,CHIP_ID,LOSS_REASON,OPE_USER_ID,OPE_TIME,VERSION_NO)");
            tmpStrSql.Append(" values (");
            tmpStrSql.Append("@BLACK_LIST_ID,@CARD_TYPE,@LOSS_DT,@TICKET_ID,@STAT,@CHIP_ID,@LOSS_REASON,@OPE_USER_ID,@OPE_TIME,@VERSION_NO)");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@BLACK_LIST_ID", DbType.String,50),
					new SQLiteParameter("@CARD_TYPE", DbType.String,1),
					new SQLiteParameter("@LOSS_DT", DbType.DateTime),
                    new SQLiteParameter("@TICKET_ID", DbType.String,10),
                    new SQLiteParameter("@STAT", DbType.String,1),
					new SQLiteParameter("@CHIP_ID", DbType.String,20),
                    new SQLiteParameter("@LOSS_REASON", DbType.String,200),
					new SQLiteParameter("@OPE_USER_ID", DbType.String,50),
                    new SQLiteParameter("@OPE_TIME", DbType.DateTime),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varEmpRegisterModel.BlackListId;
            tmpParameters[1].Value = varEmpRegisterModel.CardType;
            tmpParameters[2].Value = T_Date.ConvertLongToDateTime(varEmpRegisterModel.LossDt);
            tmpParameters[3].Value = varEmpRegisterModel.TicketId;
            tmpParameters[4].Value = varEmpRegisterModel.Stat;
            tmpParameters[5].Value = varEmpRegisterModel.ChipId;
            tmpParameters[6].Value = varEmpRegisterModel.LossReason;
            tmpParameters[7].Value = varEmpRegisterModel.OpeUserId;
            tmpParameters[8].Value = T_Date.ConvertLongToDateTime(varEmpRegisterModel.OpeTime);
            tmpParameters[9].Value = T_Date.ConvertLongToDateTime(varEmpRegisterModel.VersionNo);
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
                _log.Error(typeof(D_BlackList), ex);
                return false;
            }
            if (tmpRows > 0)
            {
                return true;
            }
            else
                return false;
        }
    }
}
