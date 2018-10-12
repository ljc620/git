package com.tbims.bean;

import java.util.List;

import com.tbims.entity.SlTeamOrderDetail;

/**
 * 
* Title: 团队换票对应详情表 <br/>
* Description: 
* @ClassName: TicketTeamOrderBean
* @author syq
* @date 2017年7月12日 上午11:14:29
*
 */
public class RptTeamOrderDetailBean {
	
	private RptTeamTdBean teamBean;//主表信息
	private List<SlTeamOrderDetail> orderInfo;//主表对应不同票种信息 
	public RptTeamTdBean getTeamBean() {
		return teamBean;
	}
	public void setTeamBean(RptTeamTdBean teamBean) {
		this.teamBean = teamBean;
	}
	public List<SlTeamOrderDetail> getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(List<SlTeamOrderDetail> orderInfo) {
		this.orderInfo = orderInfo;
	}
}
