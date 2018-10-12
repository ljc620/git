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
    /// 参数表数据库访问类
    /// </summary>
    class D_Paremeter
    {
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        /// <summary>
        /// 根据参数ID号判断是否有记录
        /// </summary>
        /// <param name="varEmpId">参数编号</param>
        /// <returns>1 存在 2 不存在 3 查询失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public int IsExist(String varParemeterId)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("SELECT PAREMETER_ID,PAREMETER_NAME,PAREMETER_VAL,OPE_USER_ID,OPE_TIME,PAREMETER_TYPE,VERSION_NO ");
            tmpStrSql.Append("FROM SYS_PAREMETER ");
            tmpStrSql.Append("WHERE PAREMETER_ID=@PAREMETER_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@PAREMETER_ID", DbType.String,60)
					};
            tmpParameters[0].Value = varParemeterId;
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
                _log.Error(typeof(D_Paremeter), ex);
                return 3;
            }

            if (tmpDtable.Rows.Count > 0)
            {
                return 1;
            }
            return 2;
        }

        /// <summary>
        /// 获取参数表最新版本号
        /// </summary>
        /// <returns>最新版本号</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public DateTime GetLasterVersion()
        {
            SQLiteCommand tmpCmd = new SQLiteCommand("SELECT MAX(VERSION_NO) FROM SYS_PAREMETER");
            DataSet tmpDtable = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, tmpCmd);
            String tmpVersion = (String)tmpDtable.Tables[0].Rows[0].ItemArray[0];
            return Convert.ToDateTime(tmpVersion, T_Date.DateFormatSet());
        }

        /// <summary>
        /// 更新参数信息(同步下载处理)
        /// </summary>
        /// <param name="varParemeterModel">参数信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool UpdateParemeter(SYS_PAREMETER varParemeterModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("UPDATE SYS_PAREMETER ");
            tmpStrSql.Append("SET PAREMETER_NAME=@PAREMETER_NAME,PAREMETER_VAL=@PAREMETER_VAL,OPE_USER_ID=@OPE_USER_ID,");
            tmpStrSql.Append("OPE_TIME=@OPE_TIME,PAREMETER_TYPE=@PAREMETER_TYPE,VERSION_NO=@VERSION_NO ");
            tmpStrSql.Append("WHERE PAREMETER_ID=@PAREMETER_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@PAREMETER_ID", DbType.String,60),
					new SQLiteParameter("@PAREMETER_NAME", DbType.String,100),
					new SQLiteParameter("@PAREMETER_VAL", DbType.String,100),
                    new SQLiteParameter("@OPE_USER_ID", DbType.String,50),
					new SQLiteParameter("@OPE_TIME", DbType.DateTime),
                    new SQLiteParameter("@PAREMETER_TYPE", DbType.String,1),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varParemeterModel.ParemeterId;
            tmpParameters[1].Value = varParemeterModel.ParemeterName;
            tmpParameters[2].Value = varParemeterModel.ParemeterVal;
            tmpParameters[3].Value = varParemeterModel.OpeUserId;
            tmpParameters[4].Value = T_Date.ConvertLongToDateTime(varParemeterModel.OpeTime);
            tmpParameters[5].Value = varParemeterModel.ParemeterType;
            tmpParameters[6].Value = T_Date.ConvertLongToDateTime(varParemeterModel.VersionNo);
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
                _log.Error(typeof(D_Paremeter), ex);
                return false;
            }
            if (tmpRows > 0)
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// 新增参数信息
        /// </summary>
        /// <param name="varParemeterModel">参数信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool InsertParemeter(SYS_PAREMETER varParemeterModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("INSERT INTO SYS_PAREMETER(");
            tmpStrSql.Append("PAREMETER_ID,PAREMETER_NAME,PAREMETER_VAL,OPE_USER_ID,OPE_TIME,PAREMETER_TYPE,VERSION_NO)");
            tmpStrSql.Append(" values (");
            tmpStrSql.Append("@PAREMETER_ID,@PAREMETER_NAME,@PAREMETER_VAL,@OPE_USER_ID,@OPE_TIME,@PAREMETER_TYPE,@VERSION_NO)");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@PAREMETER_ID", DbType.String,60),
					new SQLiteParameter("@PAREMETER_NAME", DbType.String,100),
					new SQLiteParameter("@PAREMETER_VAL", DbType.String,100),
                    new SQLiteParameter("@OPE_USER_ID", DbType.String,50),
					new SQLiteParameter("@OPE_TIME", DbType.DateTime),
                    new SQLiteParameter("@PAREMETER_TYPE", DbType.String,1),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varParemeterModel.ParemeterId;
            tmpParameters[1].Value = varParemeterModel.ParemeterName;
            tmpParameters[2].Value = varParemeterModel.ParemeterVal;
            tmpParameters[3].Value = varParemeterModel.OpeUserId;
            tmpParameters[4].Value = T_Date.ConvertLongToDateTime(varParemeterModel.OpeTime);
            tmpParameters[5].Value = varParemeterModel.ParemeterType;
            tmpParameters[6].Value = T_Date.ConvertLongToDateTime(varParemeterModel.VersionNo);
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
                _log.Error(typeof(D_Paremeter), ex);
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
        /// 清空参数信息
        /// </summary>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool ClearParemeter(SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("DELETE FROM SYS_PAREMETER");
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
