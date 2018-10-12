using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Data.SQLite;
using SelfHelpSystem.Model;
using SelfHelpSystem.Tool;
using tbims.rpc.entity;
using log4net;

namespace SelfHelpSystem.DAL
{
    /// <summary>
    /// 终端表数据库访问类
    /// </summary>
    class D_Client
    {
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        /// <summary>
        /// 新增终端信息
        /// </summary>
        /// <param name="varParemeterModel">终端信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool InsertClient(SYS_CLIENT varClientModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("INSERT INTO SYS_CLIENT(");
            tmpStrSql.Append("CLIENT_ID,CLIENT_NAME,CLIENT_TYPE,REGION_ID,OUTLET_ID,IP_ADDR,PORT,STAT,GATE_MODE,RUN_STAT,REPORT_TIME,TOKEN,OPE_USER_ID,OPE_TIME)");
            tmpStrSql.Append(" values (");
            tmpStrSql.Append("@CLIENT_ID,@CLIENT_NAME,@CLIENT_TYPE,@REGION_ID,@OUTLET_ID,@IP_ADDR,@PORT,@STAT,@GATE_MODE,@RUN_STAT,@REPORT_TIME,@TOKEN,@OPE_USER_ID,@OPE_TIME)");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@CLIENT_ID", DbType.Int64),
					new SQLiteParameter("@CLIENT_NAME", DbType.String,200),
					new SQLiteParameter("@CLIENT_TYPE", DbType.String,1),
                    new SQLiteParameter("@REGION_ID", DbType.Int64),
					new SQLiteParameter("@OUTLET_ID", DbType.Int64),
                    new SQLiteParameter("@IP_ADDR", DbType.String,20),
                    new SQLiteParameter("@PORT", DbType.String,20),
                    new SQLiteParameter("@STAT", DbType.String,1),
                    new SQLiteParameter("@GATE_MODE", DbType.Int32),
                    new SQLiteParameter("@RUN_STAT", DbType.Int32),
                    new SQLiteParameter("@REPORT_TIME", DbType.DateTime),
                    new SQLiteParameter("@TOKEN", DbType.String,100),
                    new SQLiteParameter("@OPE_USER_ID", DbType.String,60),
					new SQLiteParameter("@OPE_TIME", DbType.DateTime)};
            tmpParameters[0].Value = varClientModel.ClientId;
            tmpParameters[1].Value = varClientModel.ClientName;
            tmpParameters[2].Value = varClientModel.ClientType;
            tmpParameters[3].Value = varClientModel.RegionId;
            tmpParameters[4].Value = varClientModel.OutletId;
            tmpParameters[5].Value = varClientModel.IpAddr;
            tmpParameters[6].Value = varClientModel.Port;
            tmpParameters[7].Value = varClientModel.Stat;
            tmpParameters[8].Value = varClientModel.GateMode;
            tmpParameters[9].Value = varClientModel.RunStat;
            tmpParameters[10].Value = T_Date.ConvertLongToDateTime(varClientModel.ReportTime);
            tmpParameters[11].Value = varClientModel.Token;
            tmpParameters[12].Value = varClientModel.OpeUserId;
            tmpParameters[13].Value = T_Date.ConvertLongToDateTime(varClientModel.OpeTime);
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
                _log.Error(typeof(D_Client), ex);
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
        /// 清空终端信息
        /// </summary>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool ClearClient(SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("DELETE FROM SYS_CLIENT");
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
                _log.Error(typeof(D_Client), ex);
                return false;
            }
            return true;
        }
    }
}
