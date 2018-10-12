package com.tbims.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbims.bean.StorageTicketBean;
import com.tbims.entity.StrTicketInfo;
import com.tbims.service.IStorageTicketService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
/**
 * 
* Title:    中心仓储库存明细查询
* Description: 
* @ClassName: StorageTicketController
* @author ly
* @date 2017年7月23日 下午5:34:15
*
 */
@RestController
@RequestMapping("/storageTicket/")
public class StorageTicketController  extends BaseController {
	@Autowired
	private IStorageTicketService storageTicketService;
	/**
	 * 
	* Title: 查询中心仓储库存明细
	* Description: 
	* @param strChest
	* @return
	 */
	@RequestMapping(value = "listStorageTicket")
	@ControlAspect(funtionCd = "查询中心仓储库存明细", operType = OperType.QUERY)
	@ControllerException
	public PageBean<StorageTicketBean> listStorageTicket(StrTicketInfo strTicketInfo) {
		PageBean<StorageTicketBean> listStorageTicket = storageTicketService.listStorageTicket(getLoginUserBean(), strTicketInfo);
		return listStorageTicket;
	}
	
}
