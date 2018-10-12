package com.tbims.db.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 废票信息表
 */
@Entity
@Table(name = "SL_USELESS_TICKET_INFO", schema = "TBIMS")
public class SlUselessTicketInfo implements java.io.Serializable {

	/** 
	 * 票号.
	 */
	private Long ticketId;

	/** 
	 * 票种编号.
	 */
	private String ticketTypeId;

	/** 
	 * 作废时间.
	 */
	private Date uselessTime;

	/** 
	 * 作废人.
	 */
	private String uselessUserId;

	/** 
	 * 作废原因.
	 */
	private String uselessReason;

	/** 
	 * 确认人.
	 */
	private String confirmUserId;

	/** 
	 * 确认时间.
	 */
	private Date confirmTime;
	
	/** 
	 * 网点编号
	 */
	private Long outletId;

	public SlUselessTicketInfo() {
	}

	public SlUselessTicketInfo(Long ticketId) {
		this.ticketId = ticketId;
	}
	public SlUselessTicketInfo(Long ticketId, String ticketTypeId, Date uselessTime, String uselessUserId, String uselessReason, String confirmUserId, Date confirmTime) {
		this.ticketId = ticketId;
		this.ticketTypeId = ticketTypeId;
		this.uselessTime = uselessTime;
		this.uselessUserId = uselessUserId;
		this.uselessReason = uselessReason;
		this.confirmUserId = confirmUserId;
		this.confirmTime = confirmTime;
	}

	/** 
	 * 票号.
	 */
	@Id
	@Column(name = "TICKET_ID", unique = true, nullable = false, precision = 15, scale = 0)
	public Long getTicketId() {
		return this.ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	/** 
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 作废时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "USELESS_TIME", length = 7)
	public Date getUselessTime() {
		return this.uselessTime;
	}

	public void setUselessTime(Date uselessTime) {
		this.uselessTime = uselessTime;
	}

	/** 
	 * 作废人.
	 */
	@Column(name = "USELESS_USER_ID", length = 100)
	public String getUselessUserId() {
		return this.uselessUserId;
	}

	public void setUselessUserId(String uselessUserId) {
		this.uselessUserId = uselessUserId;
	}

	/** 
	 * 作废原因.
	 */
	@Column(name = "USELESS_REASON")
	public String getUselessReason() {
		return this.uselessReason;
	}

	public void setUselessReason(String uselessReason) {
		this.uselessReason = uselessReason;
	}

	/** 
	 * 确认人.
	 */
	@Column(name = "CONFIRM_USER_ID", length = 100)
	public String getConfirmUserId() {
		return this.confirmUserId;
	}

	public void setConfirmUserId(String confirmUserId) {
		this.confirmUserId = confirmUserId;
	}

	/** 
	 * 确认时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CONFIRM_TIME", length = 7)
	public Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	/** 
	 * 网点编号.
	 */
	@Column(name = "OUTLET_ID", precision = 6, scale = 0)
	public Long getOutletId() {
		return this.outletId;
	}
	/** 
	 * 网点编号.
	 */
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
}
