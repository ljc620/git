package com.tbims.entity;
// Generated 2017-8-22 9:08:41 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: 对账单
 */
@Entity
@Table(name = "SL_BILL", schema = "TBIMS")
public class SlBill implements java.io.Serializable {

	/** 
	 * BILL_ID
	 */
	private String billId;

	/** 
	 * 账单日期
	 */
	private String billDate;

	/** 
	 * 银行支付总金额
	 */
	private double payFeeTotal;

	/** 
	 * 银行支付总笔数
	 */
	private long payNumTotal;

	/** 
	 * 银行退款总金额
	 */
	private double refundFeeTotal;

	/** 
	 * 银行退款总笔数
	 */
	private long refundNumTotal;

	/** 
	 * 支付对账失败总笔数
	 */
	private long payNumFail;

	/** 
	 * 退款对账失败总笔数
	 */
	private long refundNumFail;
	
	/**
	 * 0-对账完成 1-下载对账单成功 3-下载对账单错误 0-对账成功 5-对账失败 4-对账错误 
	 */
	private String stat;
	
	/**
	 * 状态信息
	 */
	private String statInfo;
	
	public SlBill() {
	}

	public SlBill(String billId) {
		this.billId = billId;
	}

	public SlBill(String billId, String billDate, double payFeeTotal, long payNumTotal, double refundFeeTotal, long refundNumTotal, long payNumFail, long refundNumFail,String stat,String statInfo) {
		this.billId = billId;
		this.billDate = billDate;
		this.payFeeTotal = payFeeTotal;
		this.payNumTotal = payNumTotal;
		this.refundFeeTotal = refundFeeTotal;
		this.refundNumTotal = refundNumTotal;
		this.payNumFail = payNumFail;
		this.refundNumFail = refundNumFail;
		this.stat=stat;
		this.statInfo=statInfo;
	}

	/** 
	 * BILL_ID.
	 */
	@Id
	@Column(name = "BILL_ID", unique = true, nullable = false, length = 60)
	public String getBillId() {
		return this.billId;
	}

	/** 
	 * BILL_ID.
	 */
	public void setBillId(String billId) {
		this.billId = billId;
	}

	/** 
	 * 账单日期.
	 */
	@Column(name = "BILL_DATE", length = 8)
	public String getBillDate() {
		return this.billDate;
	}

	/** 
	 * 账单日期.
	 */
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	/** 
	 * 银行支付总金额.
	 */
	@Column(name = "PAY_FEE_TOTAL", precision = 12, scale = 2)
	public double getPayFeeTotal() {
		return this.payFeeTotal;
	}

	/** 
	 * 银行支付总金额.
	 */
	public void setPayFeeTotal(double payFeeTotal) {
		this.payFeeTotal = payFeeTotal;
	}

	/** 
	 * 银行支付总笔数.
	 */
	@Column(name = "PAY_NUM_TOTAL", precision = 12, scale = 2)
	public long getPayNumTotal() {
		return this.payNumTotal;
	}

	/** 
	 * 银行支付总笔数.
	 */
	public void setPayNumTotal(long payNumTotal) {
		this.payNumTotal = payNumTotal;
	}

	/** 
	 * 银行退款总金额.
	 */
	@Column(name = "REFUND_FEE_TOTAL", precision = 12, scale = 2)
	public double getRefundFeeTotal() {
		return this.refundFeeTotal;
	}

	/** 
	 * 银行退款总金额.
	 */
	public void setRefundFeeTotal(double refundFeeTotal) {
		this.refundFeeTotal = refundFeeTotal;
	}

	/** 
	 * 银行退款总笔数.
	 */
	@Column(name = "REFUND_NUM_TOTAL", precision = 12, scale = 0)
	public long getRefundNumTotal() {
		return this.refundNumTotal;
	}

	/** 
	 * 银行退款总笔数.
	 */
	public void setRefundNumTotal(long refundNumTotal) {
		this.refundNumTotal = refundNumTotal;
	}

	/** 
	 * 支付对账失败总笔数.
	 */
	@Column(name = "PAY_NUM_FAIL", precision = 12, scale = 0)
	public long getPayNumFail() {
		return this.payNumFail;
	}

	/** 
	 * 支付对账失败总笔数.
	 */
	public void setPayNumFail(long payNumFail) {
		this.payNumFail = payNumFail;
	}

	/** 
	 * 退款对账失败总笔数.
	 */
	@Column(name = "REFUND_NUM_FAIL", precision = 12, scale = 0)
	public long getRefundNumFail() {
		return this.refundNumFail;
	}

	/** 
	 * 退款对账失败总笔数.
	 */
	public void setRefundNumFail(long refundNumFail) {
		this.refundNumFail = refundNumFail;
	}

	/**
	 * 0-对账完成 1-下载对账单成功 3-下载对账单错误 0-对账成功 5-对账失败 4-对账错误 
	 */
	@Column(name = "STAT")
	public String getStat() {
		return stat;
	}

	/**
	 *0-对账完成 1-下载对账单成功 3-下载对账单错误 0-对账成功 5-对账失败 4-对账错误 
	 */
	public void setStat(String stat) {
		this.stat = stat;
	}

	/**
	 * 状态信息
	 */
	@Column(name = "STAT_INFO")
	public String getStatInfo() {
		return statInfo;
	}

	/**
	 * 状态信息
	 */
	public void setStatInfo(String statInfo) {
		this.statInfo = statInfo;
	}

}
