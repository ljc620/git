package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateTimeSerializer;


public class RptDeliveryBean {

	/** 
	 * 网点编号
	 */
	private Long outletId;
	
	/** 
	 * 网点名称
	 */
	private String outletName;
	
	/** 
	 * 票种编号.
	 */
	private String ticketTypeId;
	
	/** 
	 * 票种名称.
	 */
	private String ticketTypeName;
	
	/**
	 * 配送日期
	 */
	private Date deliveryDt;
	
	/**
	 * 配送日期
	 */
	private Date signTime;
	
	/** 
	 * 出库人编号
	 */
	private String deliveryUserId;
	
	/** 
	 * 出库人姓名
	 */
	private String deliveryUserName;
	
	/** 
	 * 签收人编号
	 */
	private String signUserId;
	
	/** 
	 * 签收人姓名
	 */
	private String signUserName;
	
	/**
	 * 箱数
	 */
	private Long chestNum;
	
	/**
	 * 张数
	 */
	private Long ticketNum;

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getDeliveryDt() {
		return deliveryDt;
	}

	public void setDeliveryDt(Date deliveryDt) {
		this.deliveryDt = deliveryDt;
	}

	public String getDeliveryUserId() {
		return deliveryUserId;
	}

	public void setDeliveryUserId(String deliveryUserId) {
		this.deliveryUserId = deliveryUserId;
	}

	public String getDeliveryUserName() {
		return deliveryUserName;
	}

	public void setDeliveryUserName(String deliveryUserName) {
		this.deliveryUserName = deliveryUserName;
	}

	public String getSignUserId() {
		return signUserId;
	}

	public void setSignUserId(String signUserId) {
		this.signUserId = signUserId;
	}

	public String getSignUserName() {
		return signUserName;
	}

	public void setSignUserName(String signUserName) {
		this.signUserName = signUserName;
	}

	public Long getChestNum() {
		return chestNum;
	}

	public void setChestNum(Long chestNum) {
		this.chestNum = chestNum;
	}

	public Long getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

}
