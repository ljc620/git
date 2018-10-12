package com.tbims.bean;

public class RptPayDetail {

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
	 * 销售张数
	 */
	private Long saleSum;
	
	/**
	 *  销售金额
	 */
	private Long saleAmt;
	

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}

	public String getPayTypeDesc() {
		return payTypeDesc;
	}

	public void setPayTypeDesc(String payTypeDesc) {
		this.payTypeDesc = payTypeDesc;
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

}
