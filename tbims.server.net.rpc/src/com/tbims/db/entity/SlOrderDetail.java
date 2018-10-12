package com.tbims.db.entity;
// Generated 2017-7-15 14:20:42 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 销售明细表
 */
@Entity
@Table(name = "SL_ORDER_DETAIL", schema = "TBIMS")
public class SlOrderDetail implements java.io.Serializable {

	/**
	 * 销售明细表ID
	 */
	private String orderDetailId;

	/**
	 * 按销售类型等规则
	 */
	private String orderId;

	/**
	 * 门票类型(1-FRID、2-身份证、3-二维码)
	 */
	private String ticketClass;

	/**
	 * 票号
	 */
	private Long ticketId;

	/**
	 * 票据唯一号或字符串型票号
	 */
	private String ticketUid;

	/**
	 * 身份证号码
	 */
	private String identtyId;

	/**
	 * 票种编号
	 */
	private String ticketTypeId;

	/**
	 * 可用次数
	 */
	private Long validateTimes;

	/**
	 * 原单价
	 */
	private Long originalPrice;

	/**
	 * 销售价
	 */
	private Long salePrice;

	/**
	 * 是否检票(Y是N否)
	 */
	private String checkFlag;

	/**
	 * 是否作废(Y是N否)
	 */
	private String uselessFlag;

	/**
	 * 版本号
	 */
	private Date versionNo;

	/**
	 * 出票网点编号
	 */
	private Long outletId;

	/**
	 * 出票终端编号
	 */
	private Long clientId;

	/**
	 * 出票人
	 */
	private String ejectUserId;

	/**
	 * 出票状态(1-待出票 2-已出票)
	 */
	private String ejectTicketStat;

	/**
	 * 出票时间
	 */
	private Date ejectTicketTime;

	/**
	 * 作废时间
	 */
	private Date uselessTime;

	public SlOrderDetail() {
	}

	public SlOrderDetail(String orderDetailId, String orderId) {
		this.orderDetailId = orderDetailId;
		this.orderId = orderId;
	}

	public SlOrderDetail(String orderDetailId, String orderId, String ticketClass, Long ticketId, String ticketUid, String identtyId, String ticketTypeId, Long validateTimes, Long originalPrice, Long salePrice, String checkFlag, String uselessFlag, Date versionNo, Long outletId, Long clientId, String ejectUserId, String ejectTicketStat, Date ejectTicketTime, Date uselessTime) {
		this.orderDetailId = orderDetailId;
		this.orderId = orderId;
		this.ticketClass = ticketClass;
		this.ticketId = ticketId;
		this.ticketUid = ticketUid;
		this.identtyId = identtyId;
		this.ticketTypeId = ticketTypeId;
		this.validateTimes = validateTimes;
		this.originalPrice = originalPrice;
		this.salePrice = salePrice;
		this.checkFlag = checkFlag;
		this.uselessFlag = uselessFlag;
		this.versionNo = versionNo;
		this.outletId = outletId;
		this.clientId = clientId;
		this.ejectUserId = ejectUserId;
		this.ejectTicketStat = ejectTicketStat;
		this.ejectTicketTime = ejectTicketTime;
		this.uselessTime = uselessTime;
	}

	/**
	 * 销售明细表ID.
	 */
	@Id
	@Column(name = "ORDER_DETAIL_ID", unique = true, nullable = false, length = 60)
	public String getOrderDetailId() {
		return this.orderDetailId;
	}

	/**
	 * 销售明细表ID.
	 */
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	/**
	 * 按销售类型等规则.
	 */
	@Column(name = "ORDER_ID", nullable = false, length = 50)
	public String getOrderId() {
		return this.orderId;
	}

	/**
	 * 按销售类型等规则.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 门票类型(1-FRID、2-身份证、3-二维码).
	 */
	@Column(name = "TICKET_CLASS", length = 1)
	public String getTicketClass() {
		return this.ticketClass;
	}

	/**
	 * 门票类型(1-FRID、2-身份证、3-二维码).
	 */
	public void setTicketClass(String ticketClass) {
		this.ticketClass = ticketClass;
	}

	/**
	 * 票号.
	 */
	@Column(name = "TICKET_ID", precision = 15, scale = 0)
	public Long getTicketId() {
		return this.ticketId;
	}

	/**
	 * 票号.
	 */
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * 票据唯一号或字符串型票号.
	 */
	@Column(name = "TICKET_UID", length = 50)
	public String getTicketUid() {
		return this.ticketUid;
	}

	/**
	 * 票据唯一号或字符串型票号.
	 */
	public void setTicketUid(String ticketUid) {
		this.ticketUid = ticketUid;
	}

	/**
	 * 身份证号码.
	 */
	@Column(name = "IDENTTY_ID", length = 18)
	public String getIdenttyId() {
		return this.identtyId;
	}

	/**
	 * 身份证号码.
	 */
	public void setIdenttyId(String identtyId) {
		this.identtyId = identtyId;
	}

	/**
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	/**
	 * 票种编号.
	 */
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/**
	 * 可用次数.
	 */
	@Column(name = "VALIDATE_TIMES", precision = 3, scale = 0)
	public Long getValidateTimes() {
		return this.validateTimes;
	}

	/**
	 * 可用次数.
	 */
	public void setValidateTimes(Long validateTimes) {
		this.validateTimes = validateTimes;
	}

	/**
	 * 原单价.
	 */
	@Column(name = "ORIGINAL_PRICE", precision = 5, scale = 0)
	public Long getOriginalPrice() {
		return this.originalPrice;
	}

	/**
	 * 原单价.
	 */
	public void setOriginalPrice(Long originalPrice) {
		this.originalPrice = originalPrice;
	}

	/**
	 * 销售价.
	 */
	@Column(name = "SALE_PRICE", precision = 5, scale = 0)
	public Long getSalePrice() {
		return this.salePrice;
	}

	/**
	 * 销售价.
	 */
	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}

	/**
	 * 是否检票(Y是N否).
	 */
	@Column(name = "CHECK_FLAG", length = 1)
	public String getCheckFlag() {
		return this.checkFlag;
	}

	/**
	 * 是否检票(Y是N否).
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	/**
	 * 是否作废(Y是N否).
	 */
	@Column(name = "USELESS_FLAG", length = 1)
	public String getUselessFlag() {
		return this.uselessFlag;
	}

	/**
	 * 是否作废(Y是N否).
	 */
	public void setUselessFlag(String uselessFlag) {
		this.uselessFlag = uselessFlag;
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

	/**
	 * 出票网点编号.
	 */
	@Column(name = "OUTLET_ID", precision = 6, scale = 0)
	public Long getOutletId() {
		return this.outletId;
	}

	/**
	 * 出票网点编号.
	 */
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	/**
	 * 出票终端编号.
	 */
	@Column(name = "CLIENT_ID", precision = 10, scale = 0)
	public Long getClientId() {
		return this.clientId;
	}

	/**
	 * 出票终端编号.
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/**
	 * 出票人.
	 */
	@Column(name = "EJECT_USER_ID", length = 50)
	public String getEjectUserId() {
		return this.ejectUserId;
	}

	/**
	 * 出票人.
	 */
	public void setEjectUserId(String ejectUserId) {
		this.ejectUserId = ejectUserId;
	}

	/**
	 * 出票状态(1-待出票 2-已出票).
	 */
	@Column(name = "EJECT_TICKET_STAT", length = 1)
	public String getEjectTicketStat() {
		return this.ejectTicketStat;
	}

	/**
	 * 出票状态(1-待出票 2-已出票).
	 */
	public void setEjectTicketStat(String ejectTicketStat) {
		this.ejectTicketStat = ejectTicketStat;
	}

	/**
	 * 出票时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EJECT_TICKET_TIME", length = 7)
	public Date getEjectTicketTime() {
		return this.ejectTicketTime;
	}

	/**
	 * 出票时间.
	 */
	public void setEjectTicketTime(Date ejectTicketTime) {
		this.ejectTicketTime = ejectTicketTime;
	}

	/**
	 * 作废时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "USELESS_TIME", length = 7)
	public Date getUselessTime() {
		return uselessTime;
	}
	/**
	 * 作废时间
	 */
	public void setUselessTime(Date uselessTime) {
		this.uselessTime = uselessTime;
	}

}
