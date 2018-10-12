package com.tbims.service;

import com.tbims.bean.PeriodBean;
import com.tbims.entity.SlPeriod;
import com.tbims.entity.SysTicketType;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;

/**
* Title: 预售期管理  <br/>
* Description: 
* @ClassName: IPeriodService
* @author ydc
* @date 2017年7月8日 下午3:28:59
* 
*/
public interface IPeriodService {
	/**
	 * 
	* Title:查询预售期
	* Description: 
	* @param userBean
	* @param slPeriod
	* @return
	 */
	public PageBean<PeriodBean> listPeriod(UserBean userBean,SlPeriod slPeriod);
	/**
	 * 
	* Title: 添加预售期
	* Description: 
	* @param userBean
	* @param slPeriod
	* @throws ServiceException
	 */
	public void  addPeriod(UserBean userBean, SlPeriod slPeriod) throws ServiceException;
	/**
	 * 
	* Title: 根据预售期Id查询
	* Description: 
	* @param salePeriodId
	* @return
	 */
	public SlPeriod getPeriodById(String salePeriodId);
	/**
	 * 
	* Title: 更新预售期
	* Description: 
	* @param userBean
	* @param slPeriod
	 */
	public void updatePeriod(UserBean userBean,SlPeriod slPeriod) throws ServiceException ;
	/**
	 * 
	* Title: 删除预售期
	* Description: 
	* @param userBean
	* @param salePeriodId
	 */
	public void delPeriod(UserBean userBean,String salePeriodId);
	/**
	 * 
	* Title: 根据票种编号获取票种对象
	* Description: 
	* @param ticketTypeId
	* @return
	 */
	public SysTicketType getTicketType(String ticketTypeId);
	
	/**
	 * 
	* Title: 根据预售期编号查询场馆 
	* Description: 
	* @param salePeriodId
	* @return
	* @throws ServiceException
	 */
	public String getTicketTypeName(String salePeriodId)throws ServiceException;
}
