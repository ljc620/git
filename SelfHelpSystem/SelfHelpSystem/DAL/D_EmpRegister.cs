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
    /// 员工登记表数据库访问类
    /// </summary>
    class D_EmpRegister
    {
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.DataSync");
        /// <summary>
        /// 根据员工ID号判断该员工是否有记录
        /// </summary>
        /// <param name="varEmpId">员工编号</param>
        /// <returns>1 存在 2 不存在 3 查询失败</returns>
        /// <Remark>郝毅志，2017/06/22</ Remark >
        public int IsExist(Int64 varEmpId) {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("SELECT EMP_ID,EMP_NAME,CHIP_ID,DEPARTMENT,PHOTO,STAT,CARD_TYPE,CARD_ID,MAIL,GENDER,TEL,VERSION_NO,OPE_USER_ID,OPE_TIME ");
            tmpStrSql.Append("FROM SYS_EMP_REGISTER ");
            tmpStrSql.Append("WHERE EMP_ID=@EMP_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@EMP_ID", DbType.Int64)
					};
            tmpParameters[0].Value = varEmpId;
            DataTable tmpDtable= null;
            try
            {
                tmpDtable = SQLiteHelper.ExecuteDataSet(
                    SQLiteHelper.LocalDbConnectionString,
                    tmpStrSql.ToString(),
                    CommandType.Text,
                    tmpParameters).Tables[0];
            }
            catch (Exception ex) {
                _log.Error(typeof(D_EmpRegister), ex);
                return 3;
            }
             
            if (tmpDtable.Rows.Count > 0) {
                return 1;
            }
            return 2;
        }

        /// <summary>
        /// 获取根据员工登记表最新版本号
        /// </summary>
        /// <returns>最新版本号</returns>
        /// <Remark>郝毅志，2017/06/22</ Remark >
        public DateTime GetLasterVersion() {
            SQLiteCommand tmpCmd = new SQLiteCommand("SELECT MAX(VERSION_NO) FROM SYS_EMP_REGISTER");
            DataSet tmpDtable = SQLiteHelper.ExecuteDataSet(SQLiteHelper.LocalDbConnectionString, tmpCmd);
            String tmpVersion = (String)tmpDtable.Tables[0].Rows[0].ItemArray[0];
            return Convert.ToDateTime(tmpVersion, T_Date.DateFormatSet());
        }


        /// <summary>
        /// 更新员工登记信息(同步下载处理)
        /// </summary>
        /// <param name="varEmpRegisterModel">员工登记信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/22</ Remark >
        public bool UpdateEmpRegister(SYS_EMP_REGISTER varEmpRegisterModel,SQLiteCommand varCommand){
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("UPDATE SYS_EMP_REGISTER ");
            tmpStrSql.Append("SET EMP_NAME=@EMP_NAME,CHIP_ID=@CHIP_ID,DEPARTMENT=@DEPARTMENT,PHOTO=@PHOTO,STAT=@STAT,CARD_TYPE=@CARD_TYPE,");
            tmpStrSql.Append("CARD_ID=@CARD_ID,MAIL=@MAIL,GENDER=@GENDER,TEL=@TEL,VERSION_NO=@VERSION_NO,OPE_USER_ID=@OPE_USER_ID,OPE_TIME=@OPE_TIME ");
            tmpStrSql.Append("WHERE EMP_ID=@EMP_ID");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@EMP_ID", DbType.Int64),
					new SQLiteParameter("@EMP_NAME", DbType.String,100),
					new SQLiteParameter("@CHIP_ID", DbType.String,20),
                    new SQLiteParameter("@DEPARTMENT", DbType.String,100),
					new SQLiteParameter("@PHOTO", DbType.Binary),
                    new SQLiteParameter("@STAT", DbType.String,1),
					new SQLiteParameter("@CARD_TYPE", DbType.String,2),
                    new SQLiteParameter("@CARD_ID", DbType.String,100),
					new SQLiteParameter("@MAIL", DbType.String,200),
                    new SQLiteParameter("@GENDER", DbType.String,1),
					new SQLiteParameter("@TEL", DbType.String,15),
                    new SQLiteParameter("@VERSION_NO", DbType.DateTime),
                    new SQLiteParameter("@OPE_USER_ID", DbType.String,60),
                    new SQLiteParameter("@OPE_TIME", DbType.DateTime)};
            tmpParameters[0].Value = varEmpRegisterModel.EmpId;
            tmpParameters[1].Value = varEmpRegisterModel.EmpName;
            tmpParameters[2].Value = varEmpRegisterModel.ChipId;
            tmpParameters[3].Value = varEmpRegisterModel.Department;
            tmpParameters[4].Value = varEmpRegisterModel.Photo;
            tmpParameters[5].Value = varEmpRegisterModel.Stat;
            tmpParameters[6].Value = varEmpRegisterModel.CardType;
            tmpParameters[7].Value = varEmpRegisterModel.CardId;
            tmpParameters[8].Value = varEmpRegisterModel.Mail;
            tmpParameters[9].Value = varEmpRegisterModel.Gender;
            tmpParameters[10].Value = varEmpRegisterModel.Tel;
            tmpParameters[11].Value = T_Date.ConvertLongToDateTime(varEmpRegisterModel.VersionNo);
            tmpParameters[12].Value = varEmpRegisterModel.OpeUserId;
            tmpParameters[13].Value = T_Date.ConvertLongToDateTime(varEmpRegisterModel.OpeTime);
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
                _log.Error(typeof(D_EmpRegister), ex);
                return false;
            }
            if (tmpRows > 0)
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// 新增员工登记信息
        /// </summary>
        /// <param name="varEmpRegisterModel">员工登记信息实体</param>
        /// <returns>true成功 false失败</returns>
        /// <Remark>郝毅志，2017/06/22</ Remark >
        public bool InsertEmpRegister(SYS_EMP_REGISTER varEmpRegisterModel, SQLiteCommand varCommand)
        {
            StringBuilder tmpStrSql = new StringBuilder();
            tmpStrSql.Append("INSERT INTO SYS_EMP_REGISTER(");
            tmpStrSql.Append("EMP_ID,EMP_NAME,CHIP_ID,DEPARTMENT,PHOTO,STAT,CARD_TYPE,CARD_ID,MAIL,GENDER,TEL,VERSION_NO,OPE_USER_ID,OPE_TIME)");
            tmpStrSql.Append(" values (");
            tmpStrSql.Append("@EMP_ID,@EMP_NAME,@CHIP_ID,@DEPARTMENT,@PHOTO,@STAT,@CARD_TYPE,@CARD_ID,@MAIL,@GENDER,@TEL,@VERSION_NO,@OPE_USER_ID,@OPE_TIME)");
            SQLiteParameter[] tmpParameters = {
                    new SQLiteParameter("@EMP_ID", DbType.Int64),
					new SQLiteParameter("@EMP_NAME", DbType.String,100),
					new SQLiteParameter("@CHIP_ID", DbType.String,20),
                    new SQLiteParameter("@DEPARTMENT", DbType.String,100),
					new SQLiteParameter("@PHOTO", DbType.Binary),
                    new SQLiteParameter("@STAT", DbType.String,1),
					new SQLiteParameter("@CARD_TYPE", DbType.String,2),
                    new SQLiteParameter("@CARD_ID", DbType.String,100),
					new SQLiteParameter("@MAIL", DbType.String,200),
                    new SQLiteParameter("@GENDER", DbType.String,1),
					new SQLiteParameter("@TEL", DbType.String,15),
                    new SQLiteParameter("@VERSION_NO", DbType.DateTime),
                    new SQLiteParameter("@OPE_USER_ID", DbType.String,60),
                    new SQLiteParameter("@OPE_TIME", DbType.DateTime)};
            tmpParameters[0].Value = varEmpRegisterModel.EmpId;
            tmpParameters[1].Value = varEmpRegisterModel.EmpName;
            tmpParameters[2].Value = varEmpRegisterModel.ChipId;
            tmpParameters[3].Value = varEmpRegisterModel.Department;
            tmpParameters[4].Value = varEmpRegisterModel.Photo;
            tmpParameters[5].Value = varEmpRegisterModel.Stat;
            tmpParameters[6].Value = varEmpRegisterModel.CardType;
            tmpParameters[7].Value = varEmpRegisterModel.CardId;
            tmpParameters[8].Value = varEmpRegisterModel.Mail;
            tmpParameters[9].Value = varEmpRegisterModel.Gender;
            tmpParameters[10].Value = varEmpRegisterModel.Tel;
            tmpParameters[11].Value = T_Date.ConvertLongToDateTime(varEmpRegisterModel.VersionNo);
            tmpParameters[12].Value = varEmpRegisterModel.OpeUserId;
            tmpParameters[13].Value = T_Date.ConvertLongToDateTime(varEmpRegisterModel.OpeTime);
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
                _log.Error(typeof(D_EmpRegister), ex);
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
