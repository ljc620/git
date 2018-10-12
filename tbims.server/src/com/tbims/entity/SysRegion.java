package com.tbims.entity;
// Generated 2017-6-17 15:51:21 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 区域表
 */
@Entity
@Table(name = "SYS_REGION", schema = "TBIMS")
public class SysRegion implements java.io.Serializable {

	/**
	 * 区域编号.
	 */
	private Long regionId;

	/**
	 * 场馆编号.
	 */
	private Long venueId;

	/**
	 * 区域名称.
	 */
	private String regionName;

	/**
	 * 地点.
	 */
	private String location;

	/**
	 * 联系电话.
	 */
	private String tel;

	/**
	 * 负责人.
	 */
	private String leader;

	/**
	 * 操作人.
	 */
	private String opeUserId;

	/**
	 * 操作时间.
	 */
	private Date opeTime;

	/**
	 * 状态(Y正常N停用)
	 */
	private String stat;

	public SysRegion() {
	}

	public SysRegion(Long regionId) {
		this.regionId = regionId;
	}
	public SysRegion(Long regionId, Long venueId, String regionName, String location, String tel, String leader, String opeUserId, Date opeTime, String stat) {
		this.regionId = regionId;
		this.venueId = venueId;
		this.regionName = regionName;
		this.location = location;
		this.tel = tel;
		this.leader = leader;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.stat = stat;
	}

	/**
	 * 区域编号.
	 */
	@Id
	@Column(name = "REGION_ID", unique = true, nullable = false, precision = 6, scale = 0)
	public Long getRegionId() {
		return this.regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	/**
	 * 场馆编号.
	 */
	@Column(name = "VENUE_ID", precision = 6, scale = 0)
	public Long getVenueId() {
		return this.venueId;
	}

	public void setVenueId(Long venueId) {
		this.venueId = venueId;
	}

	/**
	 * 区域名称.
	 */
	@Column(name = "REGION_NAME", length = 200)
	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * 地点.
	 */
	@Column(name = "LOCATION", length = 300)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * 联系电话.
	 */
	@Column(name = "TEL", length = 30)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 负责人.
	 */
	@Column(name = "LEADER", length = 100)
	public String getLeader() {
		return this.leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
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
	 * 状态(Y正常N停用)
	 */
	public String getStat() {
		return stat;
	}
	/**
	 * 状态(Y正常N停用)
	 */
	public void setStat(String stat) {
		this.stat = stat;
	}

}
