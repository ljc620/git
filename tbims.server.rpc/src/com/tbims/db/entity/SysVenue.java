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
 * Entity: 场馆表
 */
@Entity
@Table(name = "SYS_VENUE", schema = "TBIMS")
public class SysVenue implements java.io.Serializable {

	/** 
	 * 场馆编号.
	 */
	private Long venueId;

	/** 
	 * 场馆名称.
	 */
	private String venueName;

	/** 
	 * 状态(Y启用N停用).
	 */
	private String stat;

	/** 
	 * 操作人.
	 */
	private String opeUserId;

	/** 
	 * 操作时间.
	 */
	private Date opeTime;

	public SysVenue() {
	}

	public SysVenue(Long venueId) {
		this.venueId = venueId;
	}
	public SysVenue(Long venueId, String venueName, String stat, String opeUserId, Date opeTime) {
		this.venueId = venueId;
		this.venueName = venueName;
		this.stat = stat;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
	}

	/** 
	 * 场馆编号.
	 */
	@Id
	@Column(name = "VENUE_ID", unique = true, nullable = false, precision = 6, scale = 0)
	public Long getVenueId() {
		return this.venueId;
	}

	public void setVenueId(Long venueId) {
		this.venueId = venueId;
	}

	/** 
	 * 场馆名称.
	 */
	@Column(name = "VENUE_NAME", length = 100)
	public String getVenueName() {
		return this.venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	/** 
	 * 状态(Y启用N停用).
	 */
	@Column(name = "STAT", length = 1)
	public String getStat() {
		return this.stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
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

}
