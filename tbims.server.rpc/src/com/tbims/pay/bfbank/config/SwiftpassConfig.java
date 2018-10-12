package com.tbims.pay.bfbank.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.tbims.util.CommonUtil;
import com.tbims.util.StringUtil;

/**
 * <一句话功能简述> <功能详细描述>配置信息
 * 
 * @author Administrator
 * @version [版本号, 2014-8-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SwiftpassConfig {

	/**
	 * 威富通交易密钥
	 */
	public static String key;

	/**
	 * 威富通商户号
	 */
	public static String mch_id;

	/**
	 * 威富通请求url
	 */
	public static String req_url;

	/**
	 * 威富通支付单位时 1=元 100=分
	 */
	public static int unit_type;

	/**
	 * 下载对账单地址
	 */
	public static String bill_req_url;

	/**
	 * 银行接口超时时间秒
	 */
	public static int timeout;

	static {
		Properties prop = new Properties();
		InputStream in = SwiftpassConfig.class.getResourceAsStream("/bfbank.properties");
		try {
			prop.load(in);
			key = prop.getProperty("key").trim();
			mch_id = prop.getProperty("mch_id").trim();
			req_url = prop.getProperty("req_url").trim();
			unit_type = CommonUtil.covertInt(prop.getProperty("unit_type").trim());
			bill_req_url = prop.getProperty("bill_req_url").trim();
			if (StringUtil.isNotNull(prop.getProperty("timeout"))) {
				timeout = CommonUtil.covertInt(prop.getProperty("timeout").trim());
			} else {
				timeout = 25;//默认25秒
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}