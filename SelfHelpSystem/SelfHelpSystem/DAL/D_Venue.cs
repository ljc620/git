using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Data.SQLite;
using log4net;
using tbims.rpc.entity;
using SelfHelpSystem.Tool;

namespace SelfHelpSystem.DAL
{
    /// <summary>
    /// 场馆表
    /// </summary>
    class D_Venue
    {
        //日志
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");

        /// <summary>
        /// 写入场馆信息
        /// </summary>
        /// <param name="sysvenue">场馆实体</param>
        /// <param name="cmd">语句</param>
        /// <returns>成功true 失败false</returns>
        public bool Inert_SYS_Venue(SYS_VENUE sysvenue, SQLiteCommand cmd)
        {            
            StringBuilder strbulid = new StringBuilder();
            strbulid.Append("Insert into sys_venue(VENUE_ID,VENUE_NAME,STAT,OPE_USER_ID,OPE_TIME)");
            strbulid.Append(" values(@VENUE_ID,@VENUE_NAME,@STAT,@OPE_USER_ID,@OPE_TIME) ");
            cmd.CommandText = strbulid.ToString();
            cmd.Parameters.Add("VENUE_ID",DbType.Int64);
            cmd.Parameters.Add("VENUE_NAME",DbType.String,100);
            cmd.Parameters.Add("STAT",DbType.String,1);
            cmd.Parameters.Add("OPE_USER_ID",DbType.String,50);
            cmd.Parameters.Add("OPE_TIME",DbType.DateTime);
            cmd.Parameters["VENUE_ID"].Value = sysvenue.VenueId;
            cmd.Parameters["VENUE_NAME"].Value = sysvenue.VenueName;
            cmd.Parameters["STAT"].Value = sysvenue.Stat;
            cmd.Parameters["OPE_USER_ID"].Value = sysvenue.OpeUserId;
            cmd.Parameters["OPE_TIME"].Value = T_Date.ConvertLongToDateTime(sysvenue.OpeTime); 

            int tmpRows = 0;
            try
            {
                tmpRows = cmd.ExecuteNonQuery();
                return true;
            }
            catch (Exception ex)
            {
                _log.Error(typeof(D_Venue),ex);
                return false;
            }
        }

        /// <summary>
        /// 清空场馆表
        /// </summary>
        /// <param name="cmd">命令</param>
        /// <returns>true 成功 false 失败</returns>
        public bool DEL_SYS_Venue(SQLiteCommand cmd)
        {
            cmd.CommandText = "delete from sys_venue";
            try
            {
                cmd.ExecuteNonQuery();
                return true;
            }
            catch (Exception ex)
            {
                _log.Error(typeof(D_Venue),ex);
                return false;
            }
        }

    }
}
