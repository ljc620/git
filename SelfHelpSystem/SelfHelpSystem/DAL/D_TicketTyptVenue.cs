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
    /// 票种场馆表数据库访问类
    /// </summary>
    class D_TicketTyptVenue
    {
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        /// <summary>
        /// 更新票种场馆信息(同步下载处理)
        /// </summary>
        /// <param name="varTicketTypeVenueModel">票种场馆实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool UpdateTicketTypeVenue(SYS_TICKET_TYPE_VENUE varTicketTypeVenueModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("UPDATE SYS_TICKET_TYPE_VENUE ");
            tmpStrSql.Append("SET VERSION_NO=@VERSION_NO,OPE_USER_ID=@OPE_USER_ID,OPE_TIME=@OPE_TIME ");
            tmpStrSql.Append("WHERE TICKET_TYPE_ID=@TICKET_TYPE_ID AND VENUE_ID=@VENUE_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@TICKET_TYPE_ID", DbType.String,3),
					new SQLiteParameter("@VENUE_ID", DbType.Int32),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime),
                    new SQLiteParameter("@OPE_USER_ID", DbType.String,50),
					new SQLiteParameter("@OPE_TIME", DbType.DateTime)};
            tmpParameters[0].Value = varTicketTypeVenueModel.TicketTypeId;
            tmpParameters[1].Value = varTicketTypeVenueModel.VenueId;
            tmpParameters[2].Value = T_Date.ConvertLongToDateTime(varTicketTypeVenueModel.VersionNo);
            tmpParameters[3].Value = varTicketTypeVenueModel.OpeUserId;
            tmpParameters[4].Value = T_Date.ConvertLongToDateTime(varTicketTypeVenueModel.OpeTime);
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
                _log.Error(typeof(D_TicketTyptVenue), ex);
                return false;
            }
            if (tmpRows > 0)
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// 新增票种场馆信息
        /// </summary>
        /// <param name="varTicketTypeVenueModel">票种场馆信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool InsertTicetTypeVenue(SYS_TICKET_TYPE_VENUE varTicketTypeVenueModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("INSERT INTO SYS_TICKET_TYPE_VENUE(");
            tmpStrSql.Append("TICKET_TYPE_ID,VENUE_ID,VERSION_NO,OPE_USER_ID,OPE_TIME)");
            tmpStrSql.Append(" values (");
            tmpStrSql.Append("@TICKET_TYPE_ID,@VENUE_ID,@VERSION_NO,@OPE_USER_ID,@OPE_TIME)");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@TICKET_TYPE_ID", DbType.String,3),
					new SQLiteParameter("@VENUE_ID", DbType.Int32),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime),
                    new SQLiteParameter("@OPE_USER_ID", DbType.String,50),
					new SQLiteParameter("@OPE_TIME", DbType.DateTime)};
            tmpParameters[0].Value = varTicketTypeVenueModel.TicketTypeId;
            tmpParameters[1].Value = varTicketTypeVenueModel.VenueId;
            tmpParameters[2].Value = T_Date.ConvertLongToDateTime(varTicketTypeVenueModel.VersionNo);
            tmpParameters[3].Value = varTicketTypeVenueModel.OpeUserId;
            tmpParameters[4].Value = T_Date.ConvertLongToDateTime(varTicketTypeVenueModel.OpeTime);
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
                _log.Error(typeof(D_TicketTyptVenue), ex);
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
        /// 删除票种场馆信息
        /// </summary>
        /// <param name="varTicketTypeModel">票种场馆ID</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool DeleteTicetTypeVenue(String varTicketTypeId, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("DELETE FROM SYS_TICKET_TYPE_VENUE WHERE TICKET_TYPE_ID=@TICKET_TYPE_ID");
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
        /// 清空票种场馆信息
        /// </summary>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool ClearTicetTypeVenue(SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("DELETE FROM SYS_TICKET_TYPE_VENUE");
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
