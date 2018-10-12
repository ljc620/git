package com.tbims.cache;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.tbims.util.PropertiesUtil;

/**
 * Title: 框架属性文件读取（msg.properties） <br/>
 * Description:
 * @ClassName: MsgUtil
 * @author ydc
 * @date 2016-1-7 下午5:18:29
 * 
 */
public class MsgUtil {
	private static PropertiesUtil u;
	public static Map<Integer, String> msgs = new HashMap<Integer, String>();

	static {
		//初始化提示信息
		u = new PropertiesUtil("/msg.properties");
		@SuppressWarnings("rawtypes")
		Enumeration er = u.getPropertiesName();
		while (er.hasMoreElements()) {
			Integer key = Integer.parseInt(String.valueOf(er.nextElement()));
			String value = u.getValue(String.valueOf(key));
			msgs.put(key, value);
		}
	}

	public static String getMsg(Integer code) {
		String value = msgs.get(code);
		if (value == null) {
			return "";
		}
		return value;
	}

	public static void main(String[] args) {
		System.out.println(MsgUtil.getMsg(1));
	}
}
