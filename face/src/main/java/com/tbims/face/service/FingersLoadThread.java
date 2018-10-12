package com.tbims.face.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tbims.face.common.Global;
import com.tbims.face.common.Util;
import com.tbims.face.dao.SecondinRegInfoDao;

public class FingersLoadThread  extends Thread{

	boolean thriadSwitch = true;
	SimpleDateFormat sdf = new SimpleDateFormat(Global.DEFT_TIME_FOMT);
	public SecondinRegInfoDao sriDao;
	
	public FingersLoadThread() {
		
	}
	public FingersLoadThread(SecondinRegInfoDao sriDao) {
		this.sriDao = sriDao;
	}
	
	
	@Override
	public void run() {
		System.out.println("加载指纹库线程启动。。。");
		while(thriadSwitch) {
			try {
				Thread.sleep(1000 * 60 * Global.FINGER_LOAD_INTERVAL);
				System.out.println("指纹库线程运行。。。");
				Date now  = sdf.parse(sdf.format(new Date()));
				if(now.getTime() >= Global.FINGER_LOAD_TIME.getTime() && now.getTime() < Global.FINGER_LOAD_TIME.getTime() + (1000 * 60 * Global.FINGER_LOAD_INTERVAL)) {
					System.out.println("重新加载指纹库。。。");
					Util.fingersLoad(sriDao);
				}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void threadStop() {
		thriadSwitch = false;
	}
	
}
