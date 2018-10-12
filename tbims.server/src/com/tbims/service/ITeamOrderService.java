package com.tbims.service;

import java.util.List;

import com.tbims.bean.ChangeUserBean;
import com.tbims.bean.TeamOrderBean;
import com.tbims.entity.SlChangeUser;
import com.tbims.entity.SlTeamOrder;
import com.tbims.entity.SlTeamOrderDetail;
import com.tbims.entity.SysTicketType;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;

public interface ITeamOrderService {
	/**
	 * 
	* Title:查询申请列表 <br/>
	* Description: 
	* @param userBean
	* @param slTeamOrder
	* @return
	 * @throws Exception 
	 */
	public PageBean<TeamOrderBean> listApply(UserBean userBean,TeamOrderBean teamOrderBean) throws ServiceException;
	/**
	 * 
	* Title: 查询票种<br/>
	* Description: 
	* @return
	 */
	public List<SysTicketType> listTicketType();
	
	/**
	 * 
	* Title: 添加订单<br/>
	* Description: 
	* @param userBean
	* @param slTeamOrder
	* @param detailList
	* @throws Exception
	 */
	public void addOrder(UserBean userBean,SlTeamOrder slTeamOrder,List<SlTeamOrderDetail> detailList) throws  Exception;
	

	
	/**
	 * 
	* Title: 根据申请编号获取团队票订单<br/>
	* Description: 
	* @param applyId
	* @return
	 */
	public SlTeamOrder getSlTeamOrder(String applyId);
	
	/**
	 * 
	* Title: 根据换票人编号获取换票人<br/>
	* Description: 
	* @param changeUserId
	* @return
	 */
	public ChangeUserBean getChangeUser(String changeUserId);
	
	/**
	 * 
	* Title: 删除订单<br/>
	* Description: 
	* @param applyId
	 */
	public void delOrder(String applyId);
	

	
	/**
	 * 
	* Title: 更新换票人<br/>
	* Description: 
	* @param teamOrderBean
	* @throws ServiceException
	 */
	public void updChangeUser(TeamOrderBean teamOrderBean)throws ServiceException;
	
	/**
	 * 
	* Title: 申请历史<br/>
	* Description: 
	* @param userBean
	* @param teamOrderBean
	* @return
	* @throws Exception
	 */
	public PageBean<TeamOrderBean> listApplyHis(UserBean userBean,TeamOrderBean teamOrderBean) throws Exception;
	
	/**
	 * 
	* Title: 获取换票人列表<br/>
	* Description: 
	* @param userBean
	* @param orgId
	* @return
	 */
	List<SlChangeUser> listChangeUser(String orgId);
	
}
