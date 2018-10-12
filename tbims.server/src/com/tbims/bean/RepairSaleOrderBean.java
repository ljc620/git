package com.tbims.bean;

import java.util.Date;

/**
 *人工补录订单
 */
public class RepairSaleOrderBean {
	
	private Date saleDate;
	
	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 销售人
	 */
	private String saleUserId;

	/**
	 * 票种
	 */
	private String ticketTypeId;

	/**
	 * 机构号
	 */
	private String orgId;
	
	/**
	 * 支付方式
	 */
	private String payType;
	
	/**
	 * 第三单号
	 */
	private String thirdOrderNum;
	
	/**
	 * 门票数量
	 */
	private long ticketCount;
	
	/**
	 * 网点编号
	 */
	private long outletId;
	
	/**
	 * 售票终端
	 */
	private long clientId;
	
	/**
	 * 门票号 以，分隔
	 */
	private String ticketIds;
	
	/**
	 * 单价
	 */
	private long price;
	
	/**
	 *门票开始日期  
	 */
	private Date validStartDate;
	
	/**
	 * 门票结束日期 
	 */
	private Date validEndDate;
	
	public String getSaleUserId() {
		return saleUserId;
	}

	public String getTicketTypeId() {
		return ticketTypeId;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getThirdOrderNum() {
		return thirdOrderNum;
	}

	public void setSaleUserId(String saleUserId) {
		this.saleUserId = saleUserId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setThirdOrderNum(String thirdOrderNum) {
		this.thirdOrderNum = thirdOrderNum;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public long getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(long ticketCount) {
		this.ticketCount = ticketCount;
	}

	public long getOutletId() {
		return outletId;
	}

	public void setOutletId(long outletId) {
		this.outletId = outletId;
	}

	public String getTicketIds() {
		return ticketIds;
	}

	public void setTicketIds(String ticketIds) {
		this.ticketIds = ticketIds;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Date getValidStartDate() {
		return validStartDate;
	}

	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

}
