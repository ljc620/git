package com.tbims.db.entity;
// Generated 2017-7-29 18:18:18 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 退票表
 */
@Entity
@Table(name = "SL_REFUND_TICKET", schema = "TBIMS")
public class SlRefundTicket implements java.io.Serializable {

	/** 
	 * 退款单号(确保唯一)
	 */
	private String refundId;

	/** 
	 * 销售类型(XC-现场售票、ST-实体代理、ZG-自助购票、ZQ-自助取票)
	 */
	private String orderType;

	/** 
	 * 机构ID（退票发起机构或发起人或发起终端）
	 */
	private String orgId;

	/** 
	 * 原购票订单号
	 */
	private String orderId;

	/** 
	 * 退款门票张数
	 */
	private Long ticketCount;

	/** 
	 * 退款金额（整个退款金额合计）
	 */
	private Long refundAmtSum;

	/** 
	 * 退款日期时间
	 */
	private Date refundTime;

	/** 
	 * 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复
	 */
	private String transq;

	/**
	 * 备注
	 */
	private String remark;
	
	public SlRefundTicket() {
	}

	public SlRefundTicket(String refundId) {
		this.refundId = refundId;
	}

	public SlRefundTicket(String refundId, String orderType, String orgId, String orderId, Long ticketCount, Long refundAmtSum, Date refundTime, String transq) {
		this.refundId = refundId;
		this.orderType = orderType;
		this.orgId = orgId;
		this.orderId = orderId;
		this.ticketCount = ticketCount;
		this.refundAmtSum = refundAmtSum;
		this.refundTime = refundTime;
		this.transq = transq;
	}

	/** 
	 * 退款单号(确保唯一).
	 */
	@Id
	@Column(name = "REFUND_ID", unique = true, nullable = false, length = 50)
	public String getRefundId() {
		return this.refundId;
	}

	/** 
	 * 退款单号(确保唯一).
	 */
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	/** 
	 * 销售类型(XC-现场售票、ST-实体代理、ZG-自助购票、ZQ-自助取票).
	 */
	@Column(name = "ORDER_TYPE", length = 2)
	public String getOrderType() {
		return this.orderType;
	}

	/** 
	 * 销售类型(XC-现场售票、ST-实体代理、ZG-自助购票、ZQ-自助取票).
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/** 
	 * 机构ID（退票发起机构或发起人或发起终端）.
	 */
	@Column(name = "ORG_ID", length = 60)
	public String getOrgId() {
		return this.orgId;
	}

	/** 
	 * 机构ID（退票发起机构或发起人或发起终端）.
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/** 
	 * 原购票订单号.
	 */
	@Column(name = "ORDER_ID", length = 50)
	public String getOrderId() {
		return this.orderId;
	}

	/** 
	 * 原购票订单号.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/** 
	 * 退款门票张数.
	 */
	@Column(name = "TICKET_COUNT", precision = 3, scale = 0)
	public Long getTicketCount() {
		return this.ticketCount;
	}

	/** 
	 * 退款门票张数.
	 */
	public void setTicketCount(Long ticketCount) {
		this.ticketCount = ticketCount;
	}

	/** 
	 * 退款金额（整个退款金额合计）.
	 */
	@Column(name = "REFUND_AMT_SUM", precision = 12, scale = 0)
	public Long getRefundAmtSum() {
		return this.refundAmtSum;
	}

	/** 
	 * 退款金额（整个退款金额合计）.
	 */
	public void setRefundAmtSum(Long refundAmtSum) {
		this.refundAmtSum = refundAmtSum;
	}

	/** 
	 * 退款日期时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REFUND_TIME", length = 7)
	public Date getRefundTime() {
		return this.refundTime;
	}

	/** 
	 * 退款日期时间.
	 */
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	/** 
	 * 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复.
	 */
	@Column(name = "TRANSQ", length = 60)
	public String getTransq() {
		return this.transq;
	}

	/** 
	 * 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复.
	 */
	public void setTransq(String transq) {
		this.transq = transq;
	}

	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
