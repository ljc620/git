using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SelfHelpSystem.Model;
using System.Data;
using System.Data.SQLite;


namespace SelfHelpSystem.DAL
{
    /// <summary>
    /// 同步数据版本类
    /// </summary>
    class D_SYNCRecord
    {
        /// <summary>
        /// 根据表名查询
        /// </summary>
        /// <param name="varTableName">表名</param>
        /// <returns></returns>
        public M_SYNCRecord QueryVersion(string varTableName)
        {
            M_SYNCRecord tmpSyncRecord = new M_SYNCRecord();
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("SELECT SNYC_NAME,VERSION_NO FROM SYNC_Record  ");
            tmpStrSql.Append("WHERE SNYC_NAME=@SNYC_NAME");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@SNYC_NAME", DbType.String,3)
					};
            tmpParameters[0].Value = varTableName;
            DataTable tmpDtable = SQLiteHelper.ExecuteDataSet(
                SQLiteHelper.LocalDbConnectionString, 
                tmpStrSql.ToString(), 
                CommandType.Text, 
                tmpParameters).Tables[0];
            if (tmpDtable.Rows.Count > 0)
            {
                tmpSyncRecord.Snyc_name = (String)tmpDtable.Rows[0].ItemArray[0];
                tmpSyncRecord.Version_no = (DateTime)tmpDtable.Rows[0].ItemArray[1];
            }
            return tmpSyncRecord;
        }

        public bool UpdateVersion(string tabname,DateTime version)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append(" update SYNC_Record set version_no=@version_no where  snyc_name=@snyc_name ");

            SQLiteParameter[] tmpParameters = {
					new SQLiteParameter("@version_no", DbType.DateTime),
					new SQLiteParameter("@snyc_name", DbType.String,100)
                                              };
            tmpParameters[0].Value = version;
            tmpParameters[1].Value = tabname; 

            int tmpRows = SQLiteHelper.ExecuteNonQuery(SQLiteHelper.LocalDbConnectionString, tmpStrSql.ToString(), CommandType.Text, tmpParameters);
            if (tmpRows > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
