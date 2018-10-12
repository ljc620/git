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
 * Entity: 额度表
 */
@Entity
@Table(name = "SL_LIMIT", schema = "TBIMS")
public class SlLimit implements java.io.Serializable {

	/** 
	 * 额度编号.
	 */
	private String limitId;

	/** 
	 * 机构编号.
	 */
	private String orgId;

	/** 
	 * 额度.
	 */
	private Long limit;

	/** 
	 * 操作人.
	 */
	private String opeUserId;

	/** 
	 * 操作时间.
	 */
	private Date opeTime;

	/** 
	 * 票种编号.
	 */
	private String ticketTypeId;

	public SlLimit() {
	}

	public SlLimit(String limitId) {
		this.limitId = limitId;
	}
	public SlLimit(String limitId, String orgId, Long limit, String opeUserId, Date opeTime, String ticketTypeId) {
		this.limitId = limitId;
		this.orgId = orgId;
		this.limit = limit;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 额度编号.
	 */
	@Id
	@Column(name = "LIMIT_ID", unique = true, nullable = false, length = 50)
	public String getLimitId() {
		return this.limitId;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	/** 
	 * 机构编号.
	 */
	@Column(name = "ORG_ID", length = 50)
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/** 
	 * 额度.
	 */
	@Column(name = "LIMIT", precision = 12, scale = 0)
	public Long getLimit() {
		return this.limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "OPE_USER_ID", length = 50)
	public String getOpeUserId() {
		return this.opeUserId;
	}

	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
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
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

}
