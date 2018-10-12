package com.zhming.support.util;

/**
 * Title: 框架属性文件读取(bframe.properties) <br/>
 * Description:
 * 
 * @ClassName: BframeProp
 * @author ydc
 * @date 2016-1-7 下午5:15:16
 * 
 */
public class FrameProp {

	private static PropertiesUtil u = new PropertiesUtil();;

	private static boolean isOnlineTestEnv = false;

	private static String initPassword = "";

	private static String sys_log_type = "";

	private static String ukey_enckey_code = "";

	private static String webservice_query_token = "";

	static {
		u.setProperties("frame.properties");

		// ## 1:The production environment 2:The test environment
		if ("2".equals(FrameProp.getValue("sys.online.type"))) {
			isOnlineTestEnv = true;
		}

		// 初始密码
		String password = FrameProp.getValue("sys.init.password");
		initPassword = password;//MD5.MD5Encode2(password);

		// ##1:save 2:delete 3:update 4:select
		sys_log_type = FrameProp.getValue("sys.log.type");

		// Ukey使用,增强算法一，加密时使用的密钥
		ukey_enckey_code = FrameProp.getValue("ukey.enckey.code");

		// web service 售票、检票信息查询服务接口的授权码
		webservice_query_token = FrameProp.getValue("webservice.query.token");
	}

	/**
	 * Title: 获取 bframe.properties 文件的值 <br/>
	 * Description:
	 * 
	 * @param name
	 * @return
	 */
	public static String getValue(String name) {
		return u.getValue(name);
	}

	/**
	 * Title: 获取 bframe.properties 文件的值 <br/>
	 * Description:
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(String name, String defaultValue) {
		return u.getValue(name, defaultValue);
	}

	/**
	 * Title: 判断是否为测试环境<br/>
	 * Description: 1:The production environment 2:The test environment
	 * 
	 * @return TRUE：测试环境 FALSE:生产环境
	 */
	public static boolean isOnlineTestEnv() {
		return isOnlineTestEnv;
	}

	/**
	 * Title: 获取用户初始密码 <br/>
	 * Description:
	 * 
	 * @return
	 */
	public static String getInitPassword() {
		return initPassword;
	}

	/**
	 * Title: 获取系统操作日志保存级别<br/>
	 * Description:
	 * 
	 * @return
	 */
	public static String getSys_log_type() {
		return sys_log_type;
	}

	/**
	 * Title:Ukey使用,增强算法一，加密时使用的密钥 <br/>
	 * Description:
	 * 
	 * @return
	 */
	public static String getUkey_enckey_code() {
		return ukey_enckey_code;
	}

	/**
	 * Title:web service 售票、检票信息查询服务接口的授权码 <br/>
	 * Description:
	 * 
	 * @return
	 */
	public static String getWebservice_query_token() {
		return webservice_query_token;
	}

}
