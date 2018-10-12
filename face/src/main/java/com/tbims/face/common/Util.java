package com.tbims.face.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.tbims.face.dao.SecondinRegInfoDao;
import com.tbims.face.entity.FingerTemp;

/**
 * 工具类
 * @author develop3
 *
 */
public class Util {
	
	//字符串非空判断
	public static boolean isNotEmpty(String str) {
		if(str == null) {
			return false;
		}
		if(str.trim().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @Title: isStart   
	 * @Description: 判断是否到达开始验证和匹配时间  
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean isStart() {
		SimpleDateFormat sdf = new SimpleDateFormat(Global.DEFT_TIME_FOMT);
		try {
			Date now = sdf.parse(sdf.format(new Date()));
			if (now.getTime() >= Global.START_TIME.getTime()) {
				return true;
			}

		} catch (ParseException e) {
			System.out.println("Util.isStart 转化错误");

			e.printStackTrace();
		}

		return false;

	}
	
	
	 //base64字符串转byte[]  
    public static byte[] base64String2ByteFun(String base64Str){  
        return Base64.decodeBase64(base64Str);  
    }  
    //byte[]转base64  
    public static String byte2Base64StringFun(byte[] b){  
        return Base64.encodeBase64String(b);  
    }  
    
    /**
     * base64字符串转 FingerprintTemplate
     * @param fingerStr 指纹图片转的base64 字符串
     * @return
     */
    public static FingerprintTemplate base64ForFinger(String fingerStr) {
    	byte[] fingerBytes = Util.base64String2ByteFun(fingerStr);
    	return new FingerprintTemplate(fingerBytes);
    }
    
    /**
     * 指纹匹配
     * @param fingerStr1,指纹图片转的base64 字符串
     * @param base64 fingerStr2 指纹图片转的base64 字符串
     * @return 匹配得分
     */
    public static double fingerMatch(String fingerStr1,String fingerStr2) {
    	byte[] fingerBytes1 = Util.base64String2ByteFun(fingerStr1);
		byte[] fingerBytes2 = Util.base64String2ByteFun(fingerStr2);
		FingerprintTemplate template1 = new FingerprintTemplate(fingerBytes1);
		FingerprintTemplate template2 = new FingerprintTemplate(fingerBytes2);
		FingerprintMatcher matcher = new FingerprintMatcher(template1);
		double score = matcher.match(template2);
		return score;
    	
    }
    
    /**
     * 指纹匹配
     * @param probe 
     * @param candidate
     * @return
     */
    public static double fingerMatch(FingerprintTemplate  probe ,FingerprintTemplate  candidate ) {
    	FingerprintMatcher matcher = new FingerprintMatcher(probe);
    	return matcher.match(candidate);
    	
    }
    /**
     * 指纹匹配
     * @param probe 
     * @param candidate
     * @return
     */
    public static double fingerMatch(FingerprintMatcher  matcher ,FingerprintTemplate  candidate ) {
    	return matcher.match(candidate);
    	
    }
    
    /**
     * 加载指纹
     * @param sriDao
     * @throws IOException 
     * @throws SQLException 
     */
    public static void fingersLoad(SecondinRegInfoDao sriDao) throws SQLException, IOException {
    	Global.FINGER_TEMPS.clear();
    	List<Map<String,Object>> fingers = sriDao.findAllFinger(Global.EXPIRATION_TIME);
		String fingerId1 = null;
		String fingerId2 = null;
		//加载指纹到内存
		for(Map<String,Object> map : fingers) {
			fingerId1 =  ClobToString ((Clob)map.get("fingerId1"));;
			fingerId2 = ClobToString ((Clob)map.get("fingerId2"));
			if(Util.isNotEmpty(fingerId1) && Util.isNotEmpty(fingerId2) ) {
				Global.FINGER_TEMPS.add(
						new FingerTemp((String)map.get("regId"),new FingerprintTemplate(fingerId1),
								new FingerprintTemplate(fingerId2)));
			}
		}
		//释放资源
		fingers = null;
    }
	
    
    public static String ClobToString(Clob clob) throws SQLException, IOException {   
        
        String reString = "";   
        java.io.Reader is = clob.getCharacterStream();// 得到流   
        BufferedReader br = new BufferedReader(is);   
        String s = br.readLine();   
        StringBuffer sb = new StringBuffer();   
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING   
            sb.append(s);   
            s = br.readLine();   
        }   
        reString = sb.toString();   
        return reString;   
    }   
    
}
