package com.tbims.bean;

import java.util.Date;

/**
 * Title: 在线检票 <br/>
 * Description:
 * 
 * @ClassName: CheckOnlineBean
 * @author ydc
 * @date 2017年8月1日 上午11:02:38
 * 
 */
public class CheckOnlineBean {

	/**
	 * 销售明细表ID
	 */
	private String orderDetailId;

	/**
	 * 场馆编号
	 */
	private Long venueId;

	/**
	 * 票种
	 */
	private String ticketTypeId;

	/**
	 * 售票时间
	 */
	private Date saleTime;

	/**
	*销售价
	*/
	private Long salePrice;
	
	/**
	 * 有效开始日期
	 */
	private Date validStartDate;

	/**
	 * 有效结束日期
	 */
	private Date validEndDate;

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public Long getVenueId() {
		return venueId;
	}

	public void setVenueId(Long venueId) {
		this.venueId = venueId;
	}

	public Date getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	public Long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}

}
