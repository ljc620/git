package com.tbims.db.entity;
// Generated 2017-6-20 16:28:44 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 检票表
 */
@Entity
@Table(name = "SL_CHECK", schema = "TBIMS")
public class SlCheck implements java.io.Serializable {

	/**
	 * 检票表id
	 */
	private String checkId;

	/**
	 * 门票类型(1-FRID、2-身份证、3-二维码)
	 */
	private String ticketClass;

	/**
	 * 票种编号
	 */
	private String ticketTypeId;

	/**
	 * 票号
	 */
	private Long ticketId;

	/**
	 * 票据唯一号
	 */
	private String ticketUid;

	/**
	 * 操作时间
	 */
	private Date opeTime;

	/**
	 * 场馆编号
	 */
	private Long venueId;

	/**
	 * 终端编号
	 */
	private Long clientId;

	/**
	 * 是否通过(Y是N否)
	 */
	private String passFlag;

	/**
	 * 错误码
	 */
	private String errorCode;

	/**
	 * 未通过原因
	 */
	private String nopassReason;

	/**
	 * 剩余次数
	 */
	private Long remainTimes;

	/**
	 * 版本号
	 */
	private Date versionNo;

	/**
	 * 销售明细表ID
	 */
	private String orderDetailId;

	public SlCheck() {
	}

	public SlCheck(String checkId) {
		this.checkId = checkId;
	}

	public SlCheck(String checkId, String ticketClass, String ticketTypeId, Long ticketId, String ticketUid, Date opeTime, Long venueId, Long clientId, String passFlag, String errorCode,
			String nopassReason, Long remainTimes, Date versionNo, String orderDetailId) {
		this.checkId = checkId;
		this.ticketClass = ticketClass;
		this.ticketTypeId = ticketTypeId;
		this.ticketId = ticketId;
		this.ticketUid = ticketUid;
		this.opeTime = opeTime;
		this.venueId = venueId;
		this.clientId = clientId;
		this.passFlag = passFlag;
		this.errorCode = errorCode;
		this.nopassReason = nopassReason;
		this.remainTimes = remainTimes;
		this.versionNo = versionNo;
		this.orderDetailId = orderDetailId;
	}

	/**
	 * 检票表id.
	 */
	@Id
	@Column(name = "CHECK_ID", unique = true, nullable = false, length = 60)
	public String getCheckId() {
		return this.checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	/**
	 * 门票类型(1-FRID、2-身份证、3-二维码).
	 */
	@Column(name = "TICKET_CLASS", length = 1)
	public String getTicketClass() {
		return this.ticketClass;
	}

	public void setTicketClass(String ticketClass) {
		this.ticketClass = ticketClass;
	}

	/**
	 * 票种编号
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return ticketTypeId;
	}

	/**
	 * 票种编号
	 */
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/**
	 * 票号.
	 */
	@Column(name = "TICKET_ID", precision = 15, scale = 0)
	public Long getTicketId() {
		return this.ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * 票据唯一号.
	 */
	@Column(name = "TICKET_UID", length = 50)
	public String getTicketUid() {
		return this.ticketUid;
	}

	public void setTicketUid(String ticketUid) {
		this.ticketUid = ticketUid;
	}

	/**
	 * 操作时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPE_TIME", length = 7)
	public Date getOpeTime() {
		return this.opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

	/**
	 * 场馆编号
	 */
	@Column(name = "VENUE_ID", precision = 6, scale = 0)
	public Long getVenueId() {
		return venueId;
	}

	/**
	 * 场馆编号
	 */
	public void setVenueId(Long venueId) {
		this.venueId = venueId;
	}

	/**
	 * 终端编号.
	 */
	@Column(name = "CLIENT_ID", precision = 10, scale = 0)
	public Long getClientId() {
		return this.clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/**
	 * 是否通过(Y是N否).
	 */
	@Column(name = "PASS_FLAG", length = 1)
	public String getPassFlag() {
		return this.passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	/**
	 * 错误码.
	 */
	@Column(name = "ERROR_CODE", length = 6)
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 未通过原因.
	 */
	@Column(name = "NOPASS_REASON", length = 200)
	public String getNopassReason() {
		return this.nopassReason;
	}

	public void setNopassReason(String nopassReason) {
		this.nopassReason = nopassReason;
	}

	/**
	 * 剩余次数.
	 */
	@Column(name = "REMAIN_TIMES", precision = 3, scale = 0)
	public Long getRemainTimes() {
		return this.remainTimes;
	}

	public void setRemainTimes(Long remainTimes) {
		this.remainTimes = remainTimes;
	}

	/**
	 * 版本号.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VERSION_NO", length = 7)
	public Date getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * 销售明细表ID.
	 */
	@Column(name = "ORDER_DETAIL_ID")
	public String getOrderDetailId() {
		return this.orderDetailId;
	}

	/**
	 * 销售明细表ID.
	 */
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

}
