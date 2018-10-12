package com.tbims.cache;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.tbims.util.PropertiesUtil;

/**
 * Title: 框架属性文件读取（config.properties） <br/>
 * Description:
 * @ClassName: MsgUtil
 * @author ydc
 * @date 2016-1-7 下午5:18:29
 * 
 */
public class ConfigUtil {
	private static PropertiesUtil u;
	public static Map<String, String> configs = new HashMap<String, String>();

	static {
		//初始化配值文件的参数信息
		u = new PropertiesUtil("/config.properties");
		@SuppressWarnings("rawtypes")
		Enumeration er = u.getPropertiesName();
		while (er.hasMoreElements()) {
			String key = String.valueOf(er.nextElement());
			String value = u.getValue(String.valueOf(key));
			configs.put(key, value);
		}
	}

	public static String getValue(String key) {
		String value = configs.get(key);
		if (value == null) {
			return "";
		}
		return value;
	}

	public static void main(String[] args) {
		System.out.println(ConfigUtil.getValue("sys.online.type"));
	}
}
