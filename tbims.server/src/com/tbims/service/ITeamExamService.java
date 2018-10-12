package com.tbims.service;

import java.util.List;

import com.tbims.bean.ChangeUserBean;
import com.tbims.bean.TeamOrderBean;
import com.tbims.bean.TeamOrderDetailBean;
import com.tbims.entity.SlTeamOrder;
import com.tbims.entity.SysTicketType;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;

public interface ITeamExamService {
	/**
	 * 
	* Title: <br/>
	* Description: 
	* @param userBean
	* @param slTeamOrder
	* @return
	 * @throws Exception 
	 */
	public PageBean<TeamOrderBean> listApply(UserBean userBean,TeamOrderBean teamOrderBean,String flag) throws Exception;
	

	/**
	 * 
	* Title: 获取总数量<br/>
	* Description: 
	* @param teamOrderBean
	* @return
	* @throws Exception
	 */
	public TeamOrderBean getTotalNum(TeamOrderBean teamOrderBean,String flag) throws Exception;
	/**
	 * 查询票种列表
	* Title: <br/>
	* Description: 
	* @return
	 */
	public List<SysTicketType> listTicketType();
	
	/**
	 * 
	* Title: 根据申请编号获取团队订单<br/>
	* Description: 
	* @param applyId
	* @return
	 */
	public SlTeamOrder getSlTeamOrder(String applyId);
	/**
	 * 
	* Title: 获取换票人<br/>
	* Description: 
	* @param changeUserId
	* @return
	 */
	public ChangeUserBean getChangeUser(String changeUserId);
	
	/**
	 * 
	* Title: 获取申请明细<br/>
	* Description: 
	* @param applyId
	* @return
	 */
	public List<TeamOrderDetailBean> applyDetail(String applyId);
	
	/**
	 * 
	* Title: 审核订单<br/>
	* Description: 
	* @param userBean
	* @param slTeamOrder
	* @param applyDetailList
	* @throws Exception
	 */
	public void examOrder(UserBean userBean,SlTeamOrder slTeamOrder,List<TeamOrderDetailBean> applyDetailList) throws Exception;
	
	/**
	 * 
	* Title: 审核订单<br/>
	* Description: 
	* @param userBean
	* @param slTeamOrder
	* @throws Exception
	 */
	public void cancleOrder(UserBean userBean, SlTeamOrder slTeamOrder) throws Exception ;
}
