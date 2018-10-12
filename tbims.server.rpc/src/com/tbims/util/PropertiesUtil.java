/**
 * @FILE:PropertiesUtil.java
 * @AUTHOR:ydc
 * @DATE:2013-5-3  
 **/
package com.tbims.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Title: 属性文件操作工具类 <br/>
 * Description:
 * @ClassName: PropertiesUtil
 * @author ydc
 * @date 2016-1-7 下午5:19:09
 * 
 */
public class PropertiesUtil {
	private static Logger logger = Logger.getLogger(PropertiesUtil.class.getName());

	private Properties p;

	/**
	 * PropertiesUtil构造方法
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * @param path
	 */
	public PropertiesUtil(String path) {
		setProperties(path);
	}

	public Enumeration<?> getPropertiesName() {
		return p.propertyNames();
	}

	/**
	 * 
	 * Description:根据文件名称创建Properties对象
	 * 
	 * @param propertiesName 资源文件名
	 */
	public void setProperties(String propertiesName) {
		try {
			logger.log(Level.INFO, "配值文件路径：" + propertiesName);
			@SuppressWarnings("rawtypes")
			Class clazz = PropertiesUtil.class;
			InputStream is = clazz.getResourceAsStream(propertiesName);
			p = new Properties();
			p.load(is);
		} catch (Exception e) {
			logger.log(Level.WARNING, "配值文件读取错误", e);
		}
	}

	public void setValue(String itemName, String value) {
		p.setProperty(itemName, value);
	}

	public String getValue(String itemName) {
		return p.getProperty(itemName);
	}

	public String getValue(String itemName, String defaultValue) {
		String value = "";
		try {
			value = p.getProperty(itemName, defaultValue);
		} catch (Exception e) {
			logger.log(Level.WARNING, "配值文件读取错误，使用默认值", e);
			value = defaultValue;
		}
		return value;
	}

	public static void main(String[] args) {
		PropertiesUtil u = new PropertiesUtil("/config.properties");
		System.out.println(u.getValue("sys.online.type"));
	}
}
