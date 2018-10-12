package com.tbims.face.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbims.face.common.Global;
import com.tbims.face.common.JsonResult;
import com.tbims.face.entity.Client;
import com.tbims.face.entity.SecondinRegInfo;
import com.tbims.face.service.EpeatVerificationException;
import com.tbims.face.service.StartTiemException;
import com.tbims.face.service.TbimsService;
import com.tbims.face.service.TbimsServiceImpl;

import net.sf.json.JSONObject;
/**
 * TbimsController
 * @author develop3
 *
 */
@Controller
public class TbimsController {
	
	enum method{PING, SECONDINREGINFO, VERINFO,FINDREGBYFACEID}  
	
	@Resource(name="tbimsService")
	TbimsService tbimsService;
	
	@RequestMapping(value={"all.do"},method= {RequestMethod.POST})
	@ResponseBody
	public JsonResult<SecondinRegInfo> all(method METHOD,String CLIENTAUTH,String INPUTJSON,String MATCHTYPE) {
		method opd= METHOD;
		SecondinRegInfo newSri = null;
		
		if(opd == null) {
			//System.out.println("参数为空.....");
			return new JsonResult<SecondinRegInfo>(1,"参数为空.....");
		}
		
		switch(opd){
			//ping
			case PING:
				//转成实体类
				JSONObject jsStr = JSONObject.fromObject(CLIENTAUTH);
				Client c = (Client)JSONObject.toBean(jsStr,Client.class);
				//判断是否成功
				if(tbimsService.ping(c) == false) {
					return new JsonResult<SecondinRegInfo>(2);
				}
				break; 
			case SECONDINREGINFO:
				JSONObject jsObj = JSONObject.fromObject(INPUTJSON);
				SecondinRegInfo sri = (SecondinRegInfo)JSONObject.toBean(jsObj,SecondinRegInfo.class);
				if(tbimsService.secondinRegInfo(sri) == false) {
					return new JsonResult<SecondinRegInfo>(4,"已经登记过不可重复登记");
				}
				break; 
				
			case VERINFO:
				JSONObject jo = JSONObject.fromObject(INPUTJSON);
				SecondinRegInfo second = (SecondinRegInfo)JSONObject.toBean(jo,SecondinRegInfo.class);
				//System.out.println(second.toString());
				Map<String,Object> resuleMap = tbimsService.verInfo(second,MATCHTYPE);

				if(resuleMap.get("data") == null) {
					return new JsonResult<SecondinRegInfo>(2);
				}
				Integer stateObj =(Integer) resuleMap.get("state");
				return new JsonResult<SecondinRegInfo>(stateObj,(SecondinRegInfo)resuleMap.get("data"));
				
				
			case FINDREGBYFACEID:
				JSONObject jsonObj = JSONObject.fromObject(INPUTJSON);
				SecondinRegInfo reg = (SecondinRegInfo)JSONObject.toBean(jsonObj,SecondinRegInfo.class);
				SecondinRegInfo reginfo =  tbimsService.findRebByFaceid(reg);
				if(reginfo == null){
					return new JsonResult<SecondinRegInfo>(2,"未查询到登记记录");
				}
				return new JsonResult<SecondinRegInfo>(reginfo);
				
		}
		
		return new JsonResult<SecondinRegInfo>(newSri);
	}
	

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(StartTiemException.class)
	@ResponseBody
	public JsonResult exp(StartTiemException e){
		e.printStackTrace();
		return new JsonResult(-1,e.getMessage());
	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public JsonResult exp(Exception e){
		e.printStackTrace();
		return new JsonResult(e);
	}
	
}
