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
    /// 字典表数据库访问类
    /// </summary>
    class D_Dictionary
    {

        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        /// <summary>
        /// 根据字典ID号判断是否有记录
        /// </summary>
        /// <param name="varEmpId">字典编号</param>
        /// <returns>1 存在 2 不存在 3 查询失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public int IsExist(String varDictionaryId)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("SELECT DICTIONARY_ID,KEY_CD,KEY_NAME,ITEM_CD,ITEM_NAME,ITEM_VAL,STAT,ORDER_NUM,VERSION_NO ");
            tmpStrSql.Append("FROM SYS_DICTIONARY ");
            tmpStrSql.Append("WHERE DICTIONARY_ID=@DICTIONARY_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@DICTIONARY_ID", DbType.String,60)
					};
            tmpParameters[0].Value = varDictionaryId;
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
                _log.Error(typeof(D_Dictionary), ex);
                return 3;
            }

            if (tmpDtable.Rows.Count > 0)
            {
                return 1;
            }
            return 2;
        }

        /// <summary>
        /// 获取字典表最新版本号
        /// </summary>
        /// <returns>最新版本号</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public DateTime GetLasterVersion()
        {
            SQLiteCommand tmpCmd = new SQLiteCommand("SELECT MAX(VERSION_NO) FROM SYS_DICTIONARY");
            DataSet tmpDtable = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, tmpCmd);
            String tmpVersion = (String)tmpDtable.Tables[0].Rows[0].ItemArray[0];
            return Convert.ToDateTime(tmpVersion, T_Date.DateFormatSet());
        }

        /// <summary>
        /// 更新字典表信息(同步下载处理)
        /// </summary>
        /// <param name="varEmpRegisterModel">字典表信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool UpdateDictionary(SYS_DICTIONARY varDictionaryModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("UPDATE SYS_DICTIONARY ");
            tmpStrSql.Append("SET KEY_CD=@KEY_CD,KEY_NAME=@KEY_NAME,ITEM_CD=@ITEM_CD,ITEM_NAME=@ITEM_NAME,ITEM_VAL=@ITEM_VAL,STAT=@STAT,");
            tmpStrSql.Append("ORDER_NUM=@ORDER_NUM,VERSION_NO=@VERSION_NO ");
            tmpStrSql.Append("WHERE DICTIONARY_ID=@DICTIONARY_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@DICTIONARY_ID", DbType.String,60),
					new SQLiteParameter("@KEY_CD", DbType.String,100),
					new SQLiteParameter("@KEY_NAME", DbType.String,100),
                    new SQLiteParameter("@ITEM_CD", DbType.String,100),
					new SQLiteParameter("@ITEM_NAME", DbType.String,100),
                    new SQLiteParameter("@ITEM_VAL", DbType.String,500),
					new SQLiteParameter("@STAT", DbType.String,1),
                    new SQLiteParameter("@ORDER_NUM", DbType.Int32),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varDictionaryModel.DictionaryId;
            tmpParameters[1].Value = varDictionaryModel.KeyCd;
            tmpParameters[2].Value = varDictionaryModel.KeyName;
            tmpParameters[3].Value = varDictionaryModel.ItemCd;
            tmpParameters[4].Value = varDictionaryModel.ItemName;
            tmpParameters[5].Value = varDictionaryModel.ItemVal;
            tmpParameters[6].Value = varDictionaryModel.Stat;
            tmpParameters[7].Value = varDictionaryModel.OrderNum;
            tmpParameters[8].Value = T_Date.ConvertLongToDateTime(varDictionaryModel.VersionNo);
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
                _log.Error(typeof(D_Dictionary), ex);
                return false;
            }
            if (tmpRows > 0)
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// 新增字典信息
        /// </summary>
        /// <param name="varEmpRegisterModel">字典信息信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool InsertDictionary(SYS_DICTIONARY varDictionaryModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("INSERT INTO SYS_DICTIONARY(");
            tmpStrSql.Append("DICTIONARY_ID,KEY_CD,KEY_NAME,ITEM_CD,ITEM_NAME,ITEM_VAL,STAT,ORDER_NUM,VERSION_NO)");
            tmpStrSql.Append(" values (");
            tmpStrSql.Append("@DICTIONARY_ID,@KEY_CD,@KEY_NAME,@ITEM_CD,@ITEM_NAME,@ITEM_VAL,@STAT,@ORDER_NUM,@VERSION_NO)");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@DICTIONARY_ID", DbType.String,60),
					new SQLiteParameter("@KEY_CD", DbType.String,100),
					new SQLiteParameter("@KEY_NAME", DbType.String,100),
                    new SQLiteParameter("@ITEM_CD", DbType.String,100),
					new SQLiteParameter("@ITEM_NAME", DbType.String,100),
                    new SQLiteParameter("@ITEM_VAL", DbType.String,500),
					new SQLiteParameter("@STAT", DbType.String,1),
                    new SQLiteParameter("@ORDER_NUM", DbType.Int32),
					new SQLiteParameter("@VERSION_NO", DbType.DateTime)};
            tmpParameters[0].Value = varDictionaryModel.DictionaryId;
            tmpParameters[1].Value = varDictionaryModel.KeyCd;
            tmpParameters[2].Value = varDictionaryModel.KeyName;
            tmpParameters[3].Value = varDictionaryModel.ItemCd;
            tmpParameters[4].Value = varDictionaryModel.ItemName;
            tmpParameters[5].Value = varDictionaryModel.ItemVal;
            tmpParameters[6].Value = varDictionaryModel.Stat;
            tmpParameters[7].Value = varDictionaryModel.OrderNum;
            tmpParameters[8].Value = T_Date.ConvertLongToDateTime(varDictionaryModel.VersionNo);
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
                _log.Error(typeof(D_Dictionary), ex);
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
        /// 清空字典信息
        /// </summary>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/24</ Remark >
        public bool ClearDictionary(SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("DELETE FROM SYS_DICTIONARY");
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
