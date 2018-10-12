package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

/**
 * 
* Title: 网点销售统计表  <br/>
* Description: 
* @ClassName: RptOutletSaleBean
* @author ly
* @date 2017年7月10日 下午7:36:14
*
 */
public class RptOutletSale1Bean {
	
	private Date rptDt;

	/** 
	 * 网点编号
	 */
	private Long outletId;

	/** 
	 * 网点名称
	 */
	private String outletName;
	
	
	/**
	 * 行数量
	 */
	private int outletRowSpan;
	
	/**
	 * 小计数量
	 */
	private Long outletSum;
	
	/**
	 * 小计金额
	 */
	private Long outletAmt;
	
	/**
	 * 订单
	 * Bean
	 */
	/** 
	 * 支付方式(01-现金,02-POS付款).
	 */
	private String payType;
	
	/**
	 * 支付方式描述
	 */
	private String payTypeDesc;
	
	/** 
	 * 票种名称.
	 */
	private String ticketTypeName;
	/** 
	 * 票种名称.
	 */
	private String ticketClassName;
	
	/**
	 * 销售张数
	 */
	private Long saleSum;
	
	/**
	 *  销售金额
	 */
	private Long saleAmt;
	
	/**
	* 销售类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票)
	*/
	private String orderType;

	private String orderTypeDesc;

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



	public int getOutletRowSpan() {
		return outletRowSpan;
	}

	public void setOutletRowSpan(int outletRowSpan) {
		this.outletRowSpan = outletRowSpan;
	}

	public Long getOutletSum() {
		return outletSum;
	}

	public void setOutletSum(Long outletSum) {
		this.outletSum = outletSum;
	}


	public Long getOutletAmt() {
		return outletAmt;
	}

	public void setOutletAmt(Long outletAmt) {
		this.outletAmt = outletAmt;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayTypeDesc() {
		return payTypeDesc;
	}

	public void setPayTypeDesc(String payTypeDesc) {
		this.payTypeDesc = payTypeDesc;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
	
	public void setTicketClassName(String ticketClassName) {
		this.ticketClassName = ticketClassName;
	}
	
	public String getTicketClassName() {
		return ticketClassName;
	}

	public Long getSaleSum() {
		return saleSum;
	}

	public void setSaleSum(Long saleSum) {
		this.saleSum = saleSum;
	}

	public Long getSaleAmt() {
		return saleAmt;
	}

	public void setSaleAmt(Long saleAmt) {
		this.saleAmt = saleAmt;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderTypeDesc() {
		return orderTypeDesc;
	}

	public void setOrderTypeDesc(String orderTypeDesc) {
		this.orderTypeDesc = orderTypeDesc;
	}
	@JsonSerialize(using = DateSerializer.class)
	public Date getRptDt() {
		return rptDt;
	}

	public void setRptDt(Date rptDt) {
		this.rptDt = rptDt;
	}

	
}
