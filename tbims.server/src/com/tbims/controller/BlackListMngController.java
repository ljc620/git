package com.tbims.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbims.bean.SysBlackListBean;
import com.tbims.entity.SysBlackList;
import com.tbims.service.IBlackListMngService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
 * 黑名单管理
 * @author a
 *
 */
@RestController
@RequestMapping("/blackListMng/")
public class BlackListMngController  extends BaseController{
	@Autowired
	private IBlackListMngService blackListMngService;
	
	
	/**
	 * 查询黑名单
	 * @param sysBlackList
	 * @return
	 */
	@RequestMapping(value = "listBlackList")
	@ControlAspect(funtionCd = "i2_sys_black_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<SysBlackList> listBlackList(SysBlackList sysBlackList){
		return blackListMngService.listBlackList(getLoginUserBean(), sysBlackList);
	}
	
	/**
	 * 添加黑名单
	 * @param sysBlackList
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addBlackList")
	@ControlAspect(funtionCd = "i2_sys_black_list", operType = OperType.ADD, havPrivs = true)
	@ControllerException
	public void addBlackList(SysBlackListBean sysBlackListBean) throws ServiceException {
		blackListMngService.addBlackList(getLoginUserBean(), sysBlackListBean);
	}
	/**
	 * 
	* Title: 更新状态
	* Description: 
	* @param blackListId
	* @param stat
	* @throws BaseException
	 */
	@RequestMapping(value = "updateStat")
	@ControlAspect(funtionCd = "i2_sys_black_list", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updateStat(String blackListId, String stat) throws BaseException {
		blackListMngService.updateStat(getLoginUserBean(), blackListId, stat);
	}
	
}
