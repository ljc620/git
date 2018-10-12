/**
 * @FILE:PropertiesUtil.java
 * @AUTHOR:ydc
 * @DATE:2013-5-3  
 **/
package com.zhming.support.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

	public PropertiesUtil() {
		super();
	}

	/**
	 * 
	 * Description:根据文件名称创建Properties对象
	 * 
	 * @param propertiesName 资源文件名
	 */
	public void setProperties(String propertiesName) {
		try {
			String propertiesPath = getConfigPath(propertiesName);
			logger.log(Level.INFO, "配值文件路径：" + propertiesPath);
			p = createProperties(propertiesPath);
		} catch (Exception e) {
			logger.log(Level.WARNING, "配值文件读取错误", e);
		}
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

	public void setValue(String itemName, String value) {
		p.setProperty(itemName, value);
	}

	/**
	 * 
	 * Description: 内部方法，根据Properties文件路径创建Properties对象
	 * 
	 */
	private Properties createProperties(String fileFullName) {
		try {
			File file = new File(fileFullName);
			FileInputStream fis = null;
			Properties properties = new Properties();
			fis = new FileInputStream(file);
			properties.load(fis);
			return properties;
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "系统读取Properties文件发生异常", e);
			return null;
		} catch (IOException e) {
			logger.log(Level.WARNING, "系统读取配置文件加载Properties对象时发生异常", e);
			return null;
		}
	}

	/**
	 * Description: 获取Properties文件路径
	 * 
	 * @param propertiesName 资源文件名，推荐使用PropertiesUtil类的静态常量
	 * @return 文件路径
	 */
	private String getConfigPath(String propertiesName) {
		String resultPath = null;
		String classPath = PropertiesUtil.class.getClassLoader().getResource(PropertiesUtil.class.getName().replace('.', '/') + ".class").getFile();
		String classRoot = "";
		classRoot = classPath.substring(0, classPath.indexOf("com/zhming/support/util"));
		if (!classRoot.equals("") && classRoot.startsWith("/") && classRoot.indexOf(":") == 2) {
			classRoot = classRoot.substring(1);
		}
		if (!classRoot.equals("")) {
			resultPath = classRoot + propertiesName;
		}
		resultPath = resultPath.replace("%20", " ");
		return resultPath;
	}

	public static void main(String[] args) {
		PropertiesUtil u = new PropertiesUtil("crmConfig.properties");
		System.out.println(u.getValue("newPath"));
	}

}
