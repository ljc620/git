package com.tbims.face.service;

import java.util.Map;

import com.tbims.face.entity.Client;
import com.tbims.face.entity.SecondinRegInfo;

public interface TbimsService {

	
	/**
	 * ping
	 * @param c
	 * @return
	 */
	public boolean ping(Client c);
	
	/**
	 * 游客登记
	 * @param sri
	 * @return
	 */
	public boolean secondinRegInfo(SecondinRegInfo sri);
	
	/**
	 * 验证
	 * @param iNPUTJSON
	 * @param matchType
	 * @throws InterruptedException 
	 */
	public Map<String,Object> verInfo(SecondinRegInfo second, String matchType);
	
	
	/**
	 * 根据faceid查询reginfo
	 * @param reg
	 * @return
	 */
	public SecondinRegInfo findRebByFaceid(SecondinRegInfo reg);
	
}
