package com.zhming.support.common;

/**
 * Title: 系统状态码 <br/>
 * Description:
 * @ClassName: MSG
 * @author ydc
 * @date 2016-1-7 下午5:08:57
 * 
 */
public class MSG {
	public static final int OK = 0;

	/** 数据库处理错误 */
	public static final int DB_ERROR = 2;

	/** 业务处理错误 */
	public static final int BF_ERROR = 1;

	/** 事务处理错误 */
	public static final int DB_TRANSACTION_ERROR = 4;

	/**
	 * 系统错误
	 */
	public static final int ERROR = 3;
	
	/**
	 * 授权码错误
	 */
	public static final int BF_ERROR_TOKEN = 4;

	/** 无此功能的执行权限 */
	public static final int BF_ERROR_FUNCTION = 10000;

	/** 用户名不存在 */
	public static final int BF_ERROR_USERCODE = 10001;

	/** 用户名不能为空 */
	public static final int BF_ERROR_USERCODE_BANK = 1000101;

	/** 密码输入错误 */
	public static final int BF_ERROR_PASS = 10002;

	/** 密码不能为空 */
	public static final int BF_ERROR_PASS_BANK = 1000201;

	/** 帐户已锁定 */
	public static final int BF_ERROR_USER_LOCK = 10003;

	/** 帐户已注销 */
	public static final int BF_ERROR_USER_CANCEL = 10004;

	/** 未分配机构 */
	public static final int BF_ERROR_UNALLOCATED_INST = 10005;

	/** 未分配职位 */
	public static final int BF_ERROR_UNALLOCATED_POSI = 10010;

	/** 未分配角色或角色已停用 */
	public static final int BF_ERROR_UNALLOCATED_ROLE = 10006;

	/** 机构已锁定 */
	public static final int BF_ERROR_INST_LOCK = 10007;

	/** 机构不存在 */
	public static final int BF_ERROR_INST_NO = 10008;

	/** 岗位不存在 */
	public static final int BF_ERROR_POSI_NO = 10009;

	/** 岗位已停用 */
	public static final int BF_ERROR_POSI_STOP = 10011;

	/** 文件保存目录不存在 */
	public static final int BF_ERROR_DIR_NO = 10012;

	/** 文件类型不支持 */
	public static final int BF_ERROR_FILETYPE_NO = 10013;

	/** 文件大小超出限制 */
	public static final int BF_ERROR_FILESIZE_NO = 10014;

	/** 客户版本号不正确，请下载最新的安装程序 */
	public static final int BF_ERROR_CVERSION_NO  = 10015;
	
	/** 无此操作的权限 */
	public static final int BF_ERROR_AUTH_NO  = 10016;

	/** 数据库连接失败,请联系系统管理员 */
	public static final int DB_ERROR_CONNECT = 20001;

	/** 数据库执行语句错误,请联系系统管理员 */
	public static final int DB_ERROR_EXECUTE = 20002;
	
	/**您的额度或预付款余额已不足，请及时补充*/
	public static final int BF_ERROR_AMT = 20003;
	/**人数不满足最低成团人数*/
	public static final int BF_TEAM_LOW_NUM = 20004;
	
}