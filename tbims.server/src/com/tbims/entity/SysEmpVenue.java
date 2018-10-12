package com.tbims.entity;
// Generated 2017-6-28 13:30:25 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 员工通行场馆表
 */
@Entity
@Table(name = "SYS_EMP_VENUE", schema = "TBIMS")
public class SysEmpVenue implements java.io.Serializable {

	/** 
	 * 员工通行场馆表ID
	 */
	private String empVenueId;

	/** 
	 * 员工编号
	 */
	private SysEmpRegister sysEmpRegister;

	/** 
	 * 场馆编号
	 */
	private Long venueId;

	/** 
	 * 版本号
	 */
	private Date versionNo;

	public SysEmpVenue() {
	}

	public SysEmpVenue(String empVenueId) {
		this.empVenueId = empVenueId;
	}
	public SysEmpVenue(String empVenueId, SysEmpRegister sysEmpRegister, Long venueId, Date versionNo) {
		this.empVenueId = empVenueId;
		this.sysEmpRegister = sysEmpRegister;
		this.venueId = venueId;
		this.versionNo = versionNo;
	}

	/** 
	 * 员工通行场馆表ID.
	 */
	@Id
	@Column(name = "EMP_VENUE_ID", unique = true, nullable = false, length = 50)
	public String getEmpVenueId() {
		return this.empVenueId;
	}
	/** 
	 * 员工通行场馆表ID.
	 */
	public void setEmpVenueId(String empVenueId) {
		this.empVenueId = empVenueId;
	}

	/** 
	 * 员工编号.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMP_ID")
	public SysEmpRegister getSysEmpRegister() {
		return this.sysEmpRegister;
	}
	/** 
	 * 员工编号.
	 */
	public void setSysEmpRegister(SysEmpRegister sysEmpRegister) {
		this.sysEmpRegister = sysEmpRegister;
	}

	/** 
	 * 场馆编号.
	 */
	@Column(name = "VENUE_ID", precision = 6, scale = 0)
	public Long getVenueId() {
		return this.venueId;
	}
	/** 
	 * 场馆编号.
	 */
	public void setVenueId(Long venueId) {
		this.venueId = venueId;
	}

	/** 
	 * 版本号.
	 */
	@Temporal(TemporalType.DATE)
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
