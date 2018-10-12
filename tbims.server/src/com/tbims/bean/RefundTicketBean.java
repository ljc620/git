package com.tbims.bean;

/**
 * 
 * Title:网点退票统计报表 <br/>
 * Description:
 * 
 * @ClassName: RefundTicketBean
 * @author syq
 * @date 2017年7月11日 上午9:38:56
 *
 */
public class RefundTicketBean {
	/** 退款时间 */
	private String refundTime;
	/** 销售时间 */
	private String saleTime;
	/** 交易类型 */
	private String orderType;
	/** 订单状态 */
	private String orderStat;
	/** 支付方式 */
	private String payType;
	/** 销售张数 */
	private Long ticketCount;
	/** 销售金额 */
	private Long realSum;
	/** 退款张数 */
	private Long tradeNum;
	/** 退款金额 */
	private Long refundAmtSum;

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Long getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(Long ticketCount) {
		this.ticketCount = ticketCount;
	}

	public Long getRealSum() {
		return realSum;
	}

	public void setRealSum(Long realSum) {
		this.realSum = realSum;
	}

	public Long getTradeNum() {
		return tradeNum;
	}

	public void setTradeNum(Long tradeNum) {
		this.tradeNum = tradeNum;
	}

	public Long getRefundAmtSum() {
		return refundAmtSum;
	}

	public void setRefundAmtSum(Long refundAmtSum) {
		this.refundAmtSum = refundAmtSum;
	}
}
