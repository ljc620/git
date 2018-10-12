package com.tbims.face.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.machinezoo.sourceafis.FingerprintTemplate;
import com.tbims.face.common.Global;
import com.tbims.face.common.PropertyManagerjson;
import com.tbims.face.common.Util;
import com.tbims.face.dao.SecondinRegInfoDao;
import com.tbims.face.entity.FingerTemp;


@Component("beanDefineConfigue")
public class BeanDefineConfigue implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private SecondinRegInfoDao sriDao;
	
	//spring 启动完成执行方法
	public void onApplicationEvent(ContextRefreshedEvent event){
		if(event.getApplicationContext().getParent() == null){
			Global.FINGER_MATCH_SOCRE = Double.parseDouble(PropertyManagerjson.getProperty("fingerMatchScore"));  
			Global.FINGER_MATCH_CONCURRENCY = Integer.parseInt(PropertyManagerjson.getProperty("fingerMatchConcurrency"));
			Global.FINGER_MATCH_MINNUM = Integer.parseInt(PropertyManagerjson.getProperty("fingerMatchMinnum"));
			Global.EXPIRATION_TIME = Integer.parseInt(PropertyManagerjson.getProperty("expirationTime"));
			Global.FINGER_LOAD_INTERVAL = Integer.parseInt(PropertyManagerjson.getProperty("fingerLoadInterval"));
			Global.ISEPEATREGISTER = Integer.parseInt(PropertyManagerjson.getProperty("isEpeatRegister"));
			Global.ISEPEATVERIFICATION = Integer.parseInt(PropertyManagerjson.getProperty("isEpeatVerification"));
			
			try {
				Global.FINGER_LOAD_TIME = new SimpleDateFormat(Global.DEFT_TIME_FOMT).parse(PropertyManagerjson.getProperty("fingerLoadTime"));
				Global.START_TIME = new SimpleDateFormat(Global.DEFT_TIME_FOMT).parse(PropertyManagerjson.getProperty("startTime"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				Util.fingersLoad(sriDao);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new FingersLoadThread(sriDao).start();
		}
		
	}

}
