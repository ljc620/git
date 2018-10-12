package com.tbims.service;

import java.util.Date;

import com.tbims.bean.TicketConfirmBean;
import com.tbims.bean.TicketInfoBean;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;

public interface ITicketConfirmService {

	/**
	 * 
	* Title: 显示废票交回确认列表<br/>
	* Description: 
	* @param loginUserBean
	* @param startDate
	* @param endDate
	* @param outletId
	* @param stat
	* @return
	* @throws BaseException
	 */
	public PageBean<TicketConfirmBean> listTicketConfirm(UserBean loginUserBean,Date startDate,Date endDate,Long outletId,String stat) throws BaseException;
	
	/**
	 * 
	* Title: 获取合计<br/>
	* Description: 
	* @param startDate
	* @param endDate
	* @param outletId
	* @param stat
	* @return
	 */
	public Long  getToNum(Date startDate,Date endDate,Long outletId,String stat);

	/**
	 * 
	* Title: 确认废票<br/>
	* Description: 
	* @param loginUserBean
	* @param myJSONText
	 */
	public void confirm(UserBean loginUserBean,String myJSONText) throws BaseException;

	/**
	 * 
	* Title: 显示作废票务信息<br/>
	* Description: 
	* @param loginUserBean
	* @param startDate
	* @param endDate
	* @param uselessTime
	* @param outletId
	* @param ticketTypeId
	* @param stat
	* @return
	* @throws BaseException
	 */
	public PageBean<TicketInfoBean> showTicketInfo(UserBean loginUserBean, Date startDate,Date endDate,Date uselessTime,Long outletId,String ticketTypeId,String stat) throws BaseException;

}
