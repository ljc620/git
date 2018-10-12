package com.tbims.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbims.bean.TicketConfirmBean;
import com.tbims.bean.TicketInfoBean;
import com.tbims.service.ITicketConfirmService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;

/**
 * 
* Title:   废票交回确认<br/>
* Description: 
* @ClassName: TicketConfirmController
* @author syq
* @date 2017年7月5日 下午7:23:55
*
 */
@RestController
@RequestMapping("/ticketConfirm/")
public class TicketConfirmController extends BaseController {

	@Autowired
	private ITicketConfirmService ticketService;
	
	/**
	 * 
	* Title:废票交回确认列表 <br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param outletId
	* @param stat
	* @return
	* @throws BaseException
	 */
	@RequestMapping(value = "listTicketConfirm")
	@ControlAspect(funtionCd = "i2_delivery_rerurn", operType = OperType.QUERY,havPrivs=true)
	@ControllerException
	public PageBean<TicketConfirmBean> listTicketConfirm(Date startDate,Date endDate,Long outletId,String stat) throws BaseException{
		PageBean<TicketConfirmBean> ticketTypeList =ticketService.listTicketConfirm(getLoginUserBean(),startDate,endDate,outletId,stat);
		return ticketTypeList;
	}
	
	/**
	 * 
	* Title:废票交回历史列表 <br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param outletId
	* @param stat
	* @return
	* @throws BaseException
	 */
	@RequestMapping(value = "listTicketConfirmHis")
	@ControlAspect(funtionCd = "i2_delivery_return_his", operType = OperType.QUERY,havPrivs=true)
	@ControllerException
	public PageBean<TicketConfirmBean> listTicketConfirmHis(Date startDate,Date endDate,Long outletId,String stat) throws BaseException{
		PageBean<TicketConfirmBean> ticketTypeList =ticketService.listTicketConfirm(getLoginUserBean(),startDate,endDate,outletId,stat);
		return ticketTypeList;
	}
	
	/**
	 * 
	* Title: 查询废票数量<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param outletId
	* @param stat
	* @return
	 */
	@RequestMapping(value = "getToNum")
	@ControlAspect(funtionCd = "查询总数", operType = OperType.QUERY)
	@ControllerException
	public Long  getToNum(Date startDate,Date endDate,Long outletId,String stat){
		return ticketService.getToNum(startDate, endDate, outletId, stat);
	}
	/**
	 * 
	* Title:确认废票交回 <br/>
	* Description: 
	* @param uselessTicket
	* @throws BaseException
	 */
	@RequestMapping(value = "confirm")
	@ControlAspect(funtionCd = "i2_delivery_rerurn", operType = OperType.UPDATE,havPrivs=true)
	@ControllerException
	public void confirm(String myJSONText) throws BaseException{
		ticketService.confirm(getLoginUserBean(), myJSONText);
	}
	/**
	 * 
	* Title: 显示票务详细信息<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param uselessTime
	* @param outletId
	* @param ticketTypeId
	* @param stat
	* @return
	* @throws BaseException
	 */
	@ControlAspect(funtionCd = "显示票务详细信息", operType = OperType.QUERY)
	@RequestMapping(value = "showTicketInfo")
	@ControllerException
	public PageBean<TicketInfoBean> showTicketInfo(Date startDate,Date endDate,Date uselessTime,Long outletId,String ticketTypeId,String stat) throws BaseException {
		PageBean<TicketInfoBean>  ticketInfoList =ticketService.showTicketInfo(getLoginUserBean(),startDate,endDate,uselessTime,outletId,ticketTypeId,stat);
		return ticketInfoList;
	}
}
