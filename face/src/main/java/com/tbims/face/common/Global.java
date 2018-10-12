package com.tbims.face.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tbims.face.entity.FingerTemp;


@Component
public class Global {
	
	
	//用户指纹模板集合
	public final static List<FingerTemp> FINGER_TEMPS  = new ArrayList<FingerTemp>();
	
	//指纹匹配阈值
	public static Double FINGER_MATCH_SOCRE;
	
	//指纹匹配线程并发数
	public static int FINGER_MATCH_CONCURRENCY;
	
	//使用线程匹配最低达到数量
	public static int FINGER_MATCH_MINNUM;
	
	//失效时间
	public static int EXPIRATION_TIME;
	
	//重新加载指纹数据间隔时间
	public static int FINGER_LOAD_INTERVAL;
	
	//时间转换格式
	public final static String DEFT_TIME_FOMT = "HH:mm:ss";
	
	//重新加载指纹时间
	public static Date FINGER_LOAD_TIME;
	
	//是否可重复登记 0为true 1为false
	public static int ISEPEATREGISTER;
	
	//是否可重复登记 0为true 1为false
	public static int ISEPEATVERIFICATION;
	
	//验证登记开始时间
	public static Date START_TIME;
	  
}
