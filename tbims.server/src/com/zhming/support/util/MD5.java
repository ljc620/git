package com.zhming.support.util;

import java.security.MessageDigest;

/**
 * Title: MD5加密 <br/>
 * Description:
 * @ClassName: MD5
 * @author ydc
 * @date 2016-1-7 下午5:18:21
 * 
 */
public class MD5 {
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

	/**
	 * 将字节数组加密成MD5字符串
	 * @param b
	 * @return
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 原始字符串加密成MD5字符串
	 * @param origin
	 * @return
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString.toUpperCase();
	}

	/**
	 * 对原始字符串加密两次
	 * @param origin
	 * @return
	 */
	public static String MD5Encode2(String origin) {
		return MD5Encode(MD5Encode(origin)).toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(MD5.MD5Encode2("0"));
		System.out.println(MD5Encode("0"));
	}
}
