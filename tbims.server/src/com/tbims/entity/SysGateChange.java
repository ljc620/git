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
 * Entity: 终端模式切换登记表
 */
@Entity
@Table(name = "SYS_GATE_CHANGE", schema = "TBIMS")
public class SysGateChange implements java.io.Serializable {

	/** 
	 * 模式切换ID.
	 */
	private String changeId;

	/** 
	 * 操作人.
	 */
	private String opeUserId;

	/** 
	 * 操作时间.
	 */
	private Date opeTime;

	/** 
	 * 终端编号.
	 */
	private Long clientId;

	/** 
	 * 切换后状态.
	 */
	private String changeAfterStat;

	/** 
	 * 切换原因.
	 */
	private String changeReason;

	/** 
	 * 切换前状态.
	 */
	private String changeBeforeStat;

	public SysGateChange() {
	}

	public SysGateChange(String changeId) {
		this.changeId = changeId;
	}
	public SysGateChange(String changeId, String opeUserId, Date opeTime, Long clientId, String changeAfterStat, String changeReason, String changeBeforeStat) {
		this.changeId = changeId;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
		this.clientId = clientId;
		this.changeAfterStat = changeAfterStat;
		this.changeReason = changeReason;
		this.changeBeforeStat = changeBeforeStat;
	}

	/** 
	 * 模式切换ID.
	 */
	@Id
	@Column(name = "CHANGE_ID", unique = true, nullable = false, length = 60)
	public String getChangeId() {
		return this.changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
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
	 * 终端编号.
	 */
	@Column(name = "CLIENT_ID", length = 50)
	public Long getClientId() {
		return this.clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/** 
	 * 切换后状态.
	 */
	@Column(name = "CHANGE_AFTER_STAT", length = 2)
	public String getChangeAfterStat() {
		return this.changeAfterStat;
	}

	public void setChangeAfterStat(String changeAfterStat) {
		this.changeAfterStat = changeAfterStat;
	}

	/** 
	 * 切换原因.
	 */
	@Column(name = "CHANGE_REASON")
	public String getChangeReason() {
		return this.changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	/** 
	 * 切换前状态.
	 */
	@Column(name = "CHANGE_BEFORE_STAT", length = 2)
	public String getChangeBeforeStat() {
		return this.changeBeforeStat;
	}

	public void setChangeBeforeStat(String changeBeforeStat) {
		this.changeBeforeStat = changeBeforeStat;
	}

}
