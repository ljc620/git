package com.tbims.db.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 票种场馆表
 */
@Entity
@Table(name = "SYS_TICKET_TYPE_VENUE", schema = "TBIMS")
public class SysTicketTypeVenue implements java.io.Serializable {

	/** 
	 * 票种编号.
	 */
	/** 
	* 场馆编号.
	*/
	private SysTicketTypeVenueId id;

	/** 
	 * 版本号.
	 */
	private Date versionNo;

	/** 
	 * 操作人.
	 */
	private String opeUserId;

	/** 
	 * 操作时间.
	 */
	private Date opeTime;

	public SysTicketTypeVenue() {
	}

	public SysTicketTypeVenue(SysTicketTypeVenueId id) {
		this.id = id;
	}
	public SysTicketTypeVenue(SysTicketTypeVenueId id, Date versionNo, String opeUserId, Date opeTime) {
		this.id = id;
		this.versionNo = versionNo;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
	}

	/** 
	 * 票种编号.
	 */
	/** 
	* 场馆编号.
	*/
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "ticketTypeId", column = @Column(name = "TICKET_TYPE_ID", nullable = false, length = 3)),
			@AttributeOverride(name = "venueId", column = @Column(name = "VENUE_ID", nullable = false, precision = 6, scale = 0))})
	public SysTicketTypeVenueId getId() {
		return this.id;
	}

	public void setId(SysTicketTypeVenueId id) {
		this.id = id;
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
