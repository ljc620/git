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
    ///员工通行场馆表数据库访问类
    /// </summary>
    class D_EmpVenue
    {
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        /// <summary>
        /// 更新员工通行场馆信息(同步下载处理)
        /// </summary>
        /// <param name="varBlackListModel">员工通行场馆实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool UpdateEmpVenue(SYS_EMP_VENUE varEmpVenue, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("UPDATE SYS_EMP_VENUE ");
            tmpStrSql.Append("SET EMP_ID=@EMP_ID,VENUE_ID=@VENUE_ID,VERSION_NO=@VERSION_NO ");
            tmpStrSql.Append("WHERE EMP_VENUE_ID=@EMP_VENUE_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@EMP_VENUE_ID", DbType.String,50),
					new SQLiteParameter("@EMP_ID", DbType.Int64),
					new SQLiteParameter("@VENUE_ID", DbType.Int32),
                    new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varEmpVenue.EmpVenueId;
            tmpParameters[1].Value = varEmpVenue.EmpId;
            tmpParameters[2].Value = varEmpVenue.VenueId;
            tmpParameters[3].Value = T_Date.ConvertLongToDateTime(varEmpVenue.VersionNo);
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
                _log.Error(typeof(D_EmpVenue), ex);
                return false;
            }
            if (tmpRows > 0)
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// 新增员工通行场馆信息
        /// </summary>
        /// <param name="varEmpVenue">员工通行场馆信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/22</ Remark >
        public bool InsertEmpVenue(SYS_EMP_VENUE varEmpVenue, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("INSERT INTO SYS_EMP_VENUE(");
            tmpStrSql.Append("EMP_VENUE_ID,EMP_ID,VENUE_ID,VERSION_NO)");
            tmpStrSql.Append(" values (");
            tmpStrSql.Append("@EMP_VENUE_ID,@EMP_ID,@VENUE_ID,@VERSION_NO)");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@EMP_VENUE_ID", DbType.String,50),
					new SQLiteParameter("@EMP_ID", DbType.Int64),
					new SQLiteParameter("@VENUE_ID", DbType.Int32),
                    new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varEmpVenue.EmpVenueId;
            tmpParameters[1].Value = varEmpVenue.EmpId;
            tmpParameters[2].Value = varEmpVenue.VenueId;
            tmpParameters[3].Value = T_Date.ConvertLongToDateTime(varEmpVenue.VersionNo);
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
                _log.Error(typeof(D_EmpVenue), ex);
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
        /// 删除员工通行场馆信息
        /// </summary>
        /// <param name="varTicketTypeModel">票种信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool DeleteEmpVenue(Int64 varEmpId, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("DELETE FROM SYS_EMP_VENUE WHERE EMP_ID=@EMP_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@EMP_ID", DbType.Int64)};
            tmpParameters[0].Value = varEmpId;
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
    }
}
