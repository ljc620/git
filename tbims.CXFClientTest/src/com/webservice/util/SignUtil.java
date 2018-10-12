package com.webservice.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 请求签名算法（HmacSHA1）
 * */
public class SignUtil {
	/**
     * 对参数签名 hmac 算法 , 采用 HmacSHA1
     * @param params
     * @param appsecret
     * @param devicesecret
     * @param hmac 
     * @return
     */
    public static String sign(Map<String, String> params, String SecretKey) {
        //将参数Key按字典顺序排序
        String[] sortedKeys = params.keySet().toArray(new String[] {});
        Arrays.sort(sortedKeys);

        //生成规范化请求字符串
        StringBuilder canonicalizedQueryString = new StringBuilder();
        for (String key : sortedKeys) {
            if ("sign".equalsIgnoreCase(key)) {
                continue;
            }
            canonicalizedQueryString.append(key).append(params.get(key));
        }

        String key = SecretKey;
        return sign(canonicalizedQueryString.toString(), key);
    }

    /** 
     * HMAC加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */
    public static String sign(String content, String key) {
    	try {
	        SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"), "hmacsha1");
	        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
	        mac.init(secretKey);
	        byte[] data = mac.doFinal(content.getBytes("utf-8"));
	        return bytesToHexString(data);
    	} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static byte toByte(char c) {

        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    public static byte[] hexStringToByte(String hex) {

        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }
    public static final String bytesToHexString(byte[] bArray) {

        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
    	Map<String, String> params = new HashMap<String,String>();
    	params.put("key1", "abcdefghijklmnopqrstuvwxyz");
    	params.put("key2", "0123456789");
    	
    	String sign = SignUtil.sign(params, "abcdefghi1jklmn");
    	System.out.println(sign);
    	sign = SignUtil.sign("ghhhhhh", "abcdefghi1jklmn");
    	System.out.println(sign);
    }
}
