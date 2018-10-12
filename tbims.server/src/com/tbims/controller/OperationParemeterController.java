package com.tbims.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SysParemeter;
import com.tbims.service.IOperationParemeterService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.ServiceException;
/**
 * 
* Title:   运营参数管理
* Description: 
* @ClassName: OperationParemeterController
* @author ly
* @date 2017年7月3日 上午11:21:44
*
 */
@RestController
@RequestMapping("/operationParemeter/")
public class OperationParemeterController extends BaseController{
	@Autowired
	private IOperationParemeterService iOperationParemeterService;
	
	
	/**
	 * 
	* Title: 查询运营参数
	* Description: 
	* @param sysParemeter
	* @return
	 */
	@RequestMapping(value = "listOperationParemeter")
	@ControlAspect(funtionCd = "i2_sys_paremeter", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<Map<String, Object>> listOperationParemeter(SysParemeter sysParemeter){
		PageBean<Map<String, Object>> listOperationParemeter=iOperationParemeterService.listOperationParemeter(getLoginUserBean(), sysParemeter);
		return listOperationParemeter;
	}
	
	/**
	 * 
	* Title: 添加运营参数
	* Description: 
	* @param sysParemeter
	* @throws ServiceException
	 */
	
	@RequestMapping(value = "addOperationParemeter")
	@ControlAspect(funtionCd = "i2_sys_paremeter", operType = OperType.ADD, havPrivs = true)
	@ControllerException
	public void addOperationParemeter(SysParemeter sysParemeter) throws ServiceException {
		iOperationParemeterService.addOperationParemeter(getLoginUserBean(), sysParemeter);
	}
	
	
	/**
	 * 
	* Title: 更新运营参数
	* Description: 
	* @param sysParemeter
	 */
	@RequestMapping(value = "updateOperationParemeter")
	@ControlAspect(funtionCd = "i2_sys_paremeter", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void  updateOperationParemeter(SysParemeter sysParemeter){
		iOperationParemeterService.updateOperationParemeter(getLoginUserBean(), sysParemeter);
	}
	
	/**
	 * 
	* Title: 删除运营参数
	* Description: 
	* @param paremeterId
	 */
	@RequestMapping(value = "delOperationParemeter")
	@ControlAspect(funtionCd = "i2_sys_paremeter", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void  delOperationParemeter(String paremeterId){
		iOperationParemeterService.delChangeUser(getLoginUserBean(), paremeterId);;
	}
	/**
	 * 
	 * 
	* Title: 修改前查询
	* Description: 
	* @param paremeterId
	* @return
	 */
	@RequestMapping(value = "beforeUpdteParemeter")
	@ControlAspect(funtionCd = "i2_sys_paremeter", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeUpdteParemeter(String paremeterId){
		ModelAndView   mv=	new ModelAndView("pages/operationParemeter/updateOperationParemeter");
		SysParemeter sysParemeter = iOperationParemeterService.getById(paremeterId);
		mv.addObject("sysParemeter", sysParemeter);
		return mv;
	}
	
}
