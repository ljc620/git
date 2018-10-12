package com.zhming.support.util;

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
	static {
		u = new PropertiesUtil();
		u.setProperties("msg.properties");
	}

	public static String getMsg(int code) {
		String msg = u.getValue(String.valueOf(code));
		if (msg == null) {
			return "";
		}
		return msg;
	}

	public static void main(String[] args) {
		System.out.println(MsgUtil.getMsg(4));
	}
}
