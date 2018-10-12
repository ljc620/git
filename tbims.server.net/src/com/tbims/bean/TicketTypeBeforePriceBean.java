package com.tbims.bean;

/**
 * 按票种缓存计算后队梯票价
 */
public class TicketTypeBeforePriceBean {
	/**
	 * 票种
	 */
	private String ticketTypeId;

	/**
	 * 销售总金额
	 */
	private long minusAmt;

	/**
	 * 票价1
	 */
	private long lv1Price;

	/**
	 * 票价1销售张数
	 */
	private long lv1Num;

	/**
	 * 票价2
	 */
	private long lv2Price;

	/**
	 * 票价2销售张数
	 */
	private long lv2Num;
	
	/**
	 * 原票价
	 */
	private long price;
	
	public String getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public long getMinusAmt() {
		return minusAmt;
	}

	public void setMinusAmt(long minusAmt) {
		this.minusAmt = minusAmt;
	}

	public long getLv1Price() {
		return lv1Price;
	}

	public void setLv1Price(long lv1Price) {
		this.lv1Price = lv1Price;
	}

	public long getLv1Num() {
		return lv1Num;
	}

	public void setLv1Num(long lv1Num) {
		this.lv1Num = lv1Num;
	}

	public long getLv2Price() {
		return lv2Price;
	}

	public void setLv2Price(long lv2Price) {
		this.lv2Price = lv2Price;
	}

	public long getLv2Num() {
		return lv2Num;
	}

	public void setLv2Num(long lv2Num) {
		this.lv2Num = lv2Num;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

}
