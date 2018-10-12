package com.tbims.db.entity;
// Generated 2017-7-15 14:20:42 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tbims.util.StringUtil;


/**
 * Entity: 销售单记录表
 */
@Entity
@Table(name = "SL_ORDER", schema = "TBIMS")
public class SlOrder implements java.io.Serializable {

	/**
	 * 销售单号,按销售类型等规则
	 */
	private String orderId;

	/**
	 * 销售类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票)
	 */
	private String orderType;

	/**
	 * 销售张数
	 */
	private Long ticketCount;

	/**
	 * 应收合计
	 */
	private Long dueSum;

	/**
	 * 实收合计
	 */
	private Long realSum;

	/**
	 * 订单说明
	 */
	private String remark;

	/**
	 * 支付状态(1-待支付 2-已支付 3-支付失败)
	 */
	private String payStat;

	/**
	 * 订单状态(0-正常 1-全部退款 2-部分退款)
	 */
	private String orderStat;

	/**
	 * 售票人
	 */
	private String saleUserId;

	/**
	 * 售票时间
	 */
	private Date saleTime;

	/**
	 * 版本号
	 */
	private Date versionNo;

	public SlOrder() {
	}

	public SlOrder(String orderId) {
		this.orderId = orderId;
	}

	public SlOrder(String orderId, String orderType, Long ticketCount, Long dueSum, Long realSum, String remark, String payStat, String orderStat, String saleUserId, Date saleTime, Date versionNo) {
		this.orderId = orderId;
		this.orderType = orderType;
		this.ticketCount = ticketCount;
		this.dueSum = dueSum;
		this.realSum = realSum;
		this.remark = remark;
		this.payStat = payStat;
		this.orderStat = orderStat;
		this.saleUserId = saleUserId;
		this.saleTime = saleTime;
		this.versionNo = versionNo;
	}

	/**
	 * 销售单号,按销售类型等规则.
	 */
	@Id
	@Column(name = "ORDER_ID", unique = true, nullable = false, length = 50)
	public String getOrderId() {
		return this.orderId;
	}

	/**
	 * 销售单号,按销售类型等规则.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 销售类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票).
	 */
	@Column(name = "ORDER_TYPE", length = 2)
	public String getOrderType() {
		return this.orderType;
	}

	/**
	 * 销售类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票).
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * 销售张数.
	 */
	@Column(name = "TICKET_COUNT", precision = 6, scale = 0)
	public Long getTicketCount() {
		return this.ticketCount;
	}

	/**
	 * 销售张数.
	 */
	public void setTicketCount(Long ticketCount) {
		this.ticketCount = ticketCount;
	}

	/**
	 * 应收合计.
	 */
	@Column(name = "DUE_SUM", precision = 12, scale = 0)
	public Long getDueSum() {
		return this.dueSum;
	}

	/**
	 * 应收合计.
	 */
	public void setDueSum(Long dueSum) {
		this.dueSum = dueSum;
	}

	/**
	 * 实收合计.
	 */
	@Column(name = "REAL_SUM", precision = 12, scale = 0)
	public Long getRealSum() {
		return this.realSum;
	}

	/**
	 * 实收合计.
	 */
	public void setRealSum(Long realSum) {
		this.realSum = realSum;
	}

	/**
	 * 订单说明.
	 */
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 订单说明.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 支付状态(1-待支付 2-已支付 3-支付失败).
	 */
	@Column(name = "PAY_STAT", length = 1)
	public String getPayStat() {
		return this.payStat;
	}

	/**
	 * 支付状态(1-待支付 2-已支付 3-支付失败).
	 */
	public void setPayStat(String payStat) {
		this.payStat = payStat;
	}

	/**
	 * 订单状态(0-正常 1-全部退款 2-部分退款)
	 */
	@Column(name = "ORDER_STAT")
	public String getOrderStat() {
		if(StringUtil.isNull(orderStat)){
			return "0";
		}
		return orderStat;
	}

	/**
	 * 订单状态(0-正常 1-全部退款 2-部分退款)
	 */
	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	/**
	 * 售票人.
	 */
	@Column(name = "SALE_USER_ID", length = 50)
	public String getSaleUserId() {
		return this.saleUserId;
	}

	/**
	 * 售票人.
	 */
	public void setSaleUserId(String saleUserId) {
		this.saleUserId = saleUserId;
	}

	/**
	 * 售票时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SALE_TIME", length = 7)
	public Date getSaleTime() {
		return this.saleTime;
	}

	/**
	 * 售票时间.
	 */
	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	/**
	 * 版本号.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VERSION_NO", length = 7)
	public Date getVersionNo() {
		return this.versionNo;
	}

	/**
	 * 版本号.
	 */
	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

}
