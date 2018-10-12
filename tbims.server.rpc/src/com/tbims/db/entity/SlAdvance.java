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
 * Entity: 预付款表
 */
@Entity
@Table(name = "SL_ADVANCE", schema = "TBIMS")
public class SlAdvance implements java.io.Serializable {

	/** 
	 * 预付款编号.
	 */
	private String advanceId;

	/** 
	 * 组织机构代码.
	 */
	private String orgId;

	/** 
	 * 预付款金额.
	 */
	private Long advanceAmt;

	/** 
	 * 备注.
	 */
	private String remark;

	/** 
	 * 操作人.
	 */
	private String opeUserId;

	/** 
	 * 操作时间.
	 */
	private Date opeTime;

	public SlAdvance() {
	}

	public SlAdvance(String advanceId) {
		this.advanceId = advanceId;
	}
	public SlAdvance(String advanceId, String orgId, Long advanceAmt, String remark, String opeUserId, Date opeTime) {
		this.advanceId = advanceId;
		this.orgId = orgId;
		this.advanceAmt = advanceAmt;
		this.remark = remark;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
	}

	/** 
	 * 预付款编号.
	 */
	@Id
	@Column(name = "ADVANCE_ID", unique = true, nullable = false, length = 50)
	public String getAdvanceId() {
		return this.advanceId;
	}

	public void setAdvanceId(String advanceId) {
		this.advanceId = advanceId;
	}

	/** 
	 * 组织机构代码.
	 */
	@Column(name = "ORG_ID", length = 50)
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/** 
	 * 预付款金额.
	 */
	@Column(name = "ADVANCE_AMT", precision = 12, scale = 0)
	public Long getAdvanceAmt() {
		return this.advanceAmt;
	}

	public void setAdvanceAmt(Long advanceAmt) {
		this.advanceAmt = advanceAmt;
	}

	/** 
	 * 备注.
	 */
	@Column(name = "REMARK", length = 512)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "OPE_USER_ID", length = 100)
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
