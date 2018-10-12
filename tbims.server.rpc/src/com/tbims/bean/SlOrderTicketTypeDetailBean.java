package com.tbims.bean;

/**
 * 
 * Title: 售票机补票查询（购票）<br/>
 * Description:
 * @ClassName: SaleSupplyTicketBean
 * @author syq
 * @date 2017年8月14日 下午2:27:24
 *
 */
public class SlOrderTicketTypeDetailBean {
	/**
	 * 销售单号
	 */
	public String orderId; // required
	/**
	 * 票种编号
	 */
	public String ticketTypeId; // required
	/**
	 * 销售张数
	 */
	public Long ticketCount; // required
	/**
	 * 出票张数
	 */
	public Long ejectTicketCount; // required
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public Long getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(Long ticketCount) {
		this.ticketCount = ticketCount;
	}
	public Long getEjectTicketCount() {
		return ejectTicketCount;
	}
	public void setEjectTicketCount(Long ejectTicketCount) {
		this.ejectTicketCount = ejectTicketCount;
	}
}
