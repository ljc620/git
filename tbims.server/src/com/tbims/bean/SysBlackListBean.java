package com.tbims.bean;

public class SysBlackListBean {
	//beginNO
	private String lossReason;
	private String cardType;
	private String beginNo;
	private String endNo;
	private String ticketId;
	
	public String getLossReason() {
		return lossReason;
	}
	public void setLossReason(String lossReason) {
		this.lossReason = lossReason;
	}
	public String getCardType() {
		return cardType;
	}
	
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getBeginNo() {
		return beginNo;
	}
	public void setBeginNo(String beginNo) {
		this.beginNo = beginNo;
	}
	public String getEndNo() {
		return endNo;
	}
	public void setEndNo(String endNo) {
		this.endNo = endNo;
	}
 }

