package com.zhming.support.util;


import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 资源文件读取器
 * 
 * @author wanggb
 *
 */
public class ResourcerUtil {
	private ResourceBundle props ;
	//private static Map<String, Properties> loaderMap = new HashMap<String, Properties>();
	public ResourcerUtil(String fileName) {
		props = ResourceBundle.getBundle(fileName);
	}

	/**
	 * 得到配置文件中指定名称对应的值
	 * @param key 
	 * @return
	 */
	public String get(String key) {
		String v = props.getString(key);
		if (v == null) {
			throw new MissingResourceException("\"" + key + "\" is not specified!", ResourcerUtil.class.getName(), key);
		}
		return v;
	}

	/**
	 * 得到配置文件中指定名称对应的值，如果对应值不存在，返回缺省值
	 * @param key
	 * @param defaultValue 缺省值
	 * @return
	 */
	public String get(String key, String defaultValue) {
		String v = props.getString(key);
		return v == null ? defaultValue : v;
	}
}
