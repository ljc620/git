using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data.SQLite;
using System.Data;
//using TBIMS.Client.NetLink.Tool;
using tbims.rpc.entity;
using log4net;

namespace SelfHelpSystem.DAL
{
    /// <summary>
    /// 日志表数据库访问类
    /// </summary>
    class D_Log
    {
        private ILog _log = LogManager.GetLogger("TBIMS.DataSync");
        /// <summary>
        /// 查询日志上传信息
        /// </summary>
        /// <param name=""></param>
        /// <returns></returns>
        /// <Remark></ Remark >
        public DataTable QueryLogInfo(String fileName) {
            StringBuilder sb = new StringBuilder();
            sb.Append("SELECT * FROM SYS_LOG WHERE FILE_NAME=@FILE_NAME");
            SQLiteParameter[] tmpParameter = {
                new SQLiteParameter("@FILE_NAME",DbType.String,100)
            };
            tmpParameter[0].Value = fileName;
            DataSet ds = null;
            try
            {
                ds = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, sb.ToString(), CommandType.Text, tmpParameter);
            }
            catch (Exception ex) {
                _log.Error(ex.Message.ToString());
                throw new Exception(ex.Message.ToString());
            }
            return ds.Tables[0];
        }

        /// <summary>
        /// 新增日志上传信息
        /// </summary>
        /// <param name=""></param>
        /// <returns></returns>
        /// <Remark></ Remark >
        public bool insertLogInfo(String fileName, Int64 fileLocation, String upAddr, String size,Int32 index) {
            StringBuilder sb = new StringBuilder();
            sb.Append("INSERT INTO SYS_LOG(LOG_INDEX,FILE_NAME,FILE_LOCATION,UP_ADDR,UP_SIZE,UP_DATE) ");
            sb.Append("values (@LOG_INDEX,@FILE_NAME,@FILE_LOCATION,@UP_ADDR,@UP_SIZE,@UP_DATE)");
            SQLiteParameter[] tmpParamter = { 
                new SQLiteParameter("@LOG_INDEX",DbType.String,50),
                new SQLiteParameter("@FILE_NAME",DbType.String,100),
                new SQLiteParameter("@FILE_LOCATION",DbType.Int64),
                new SQLiteParameter("@UP_ADDR",DbType.String,100),
                new SQLiteParameter("@UP_SIZE",DbType.String,50),
                new SQLiteParameter("@UP_DATE",DbType.DateTime)
            };
            String tmpApplyId = CreateTableUID("LOG", index);
            tmpParamter[0].Value = tmpApplyId;
            tmpParamter[1].Value = fileName;
            tmpParamter[2].Value = fileLocation;
            tmpParamter[3].Value = upAddr;
            tmpParamter[4].Value = size;
            tmpParamter[5].Value = DateTime.Now;
            int count = 0;
            try
            {
                count = SQLiteHelper.ExecuteNonQuery(SQLiteHelper.LocalDbConnectionString, sb.ToString(), CommandType.Text, tmpParamter);
            }
            catch (Exception ex) {
                _log.Error(ex.Message.ToString());
                throw new Exception(ex.Message.ToString());
            }
            if (count > 0)
            {
                return true;
            }
            else {
                return false;
            }
        }

        /// <summary>
        /// 更改日志上传信息
        /// </summary>
        /// <param name=""></param>
        /// <returns></returns>
        /// <Remark></ Remark >
        public bool updateLogInfo(String fileName, Int64 fileLocation, String upAddr, String size)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("UPDATE SYS_LOG SET FILE_LOCATION=@FILE_LOCATION,UP_ADDR=@UP_ADDR,UP_SIZE=@UP_SIZE,UP_DATE=@UP_DATE ");
            sb.Append("WHERE FILE_NAME=@FILE_NAME");
            SQLiteParameter[] tmpParamter = {  
                new SQLiteParameter("@FILE_LOCATION",DbType.Int64),
                new SQLiteParameter("@UP_ADDR",DbType.String,100),
                new SQLiteParameter("@UP_SIZE",DbType.String,50),
                new SQLiteParameter("@UP_DATE",DbType.DateTime),
                new SQLiteParameter("@FILE_NAME",DbType.String,100)
            };
            tmpParamter[0].Value = fileLocation;
            tmpParamter[1].Value = upAddr;
            tmpParamter[2].Value = size;
            tmpParamter[3].Value = DateTime.Now;
            tmpParamter[4].Value = fileName;
            int count = 0;
            try
            {
                count = SQLiteHelper.ExecuteNonQuery(SQLiteHelper.LocalDbConnectionString, sb.ToString(), CommandType.Text, tmpParamter);
            }
            catch (Exception ex)
            {
                _log.Error(ex.Message.ToString());
                throw new Exception(ex.Message.ToString());
            }
            if (count > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public static string CreateTableUID(string Rule, int indx)
        {
            string strUID = "";
            strUID = Rule + DateTime.Now.ToString("yyyyMMddHHmmss") + indx.ToString();
            return strUID;

        }
    }
}
