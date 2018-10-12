package com.tbims.db.entity;
// Generated 2017-6-28 10:54:14 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 保证金表
 */
@Entity
@Table(name = "SL_DEPOSIT", schema = "TBIMS")
public class SlDeposit implements java.io.Serializable {

	/** 
	 * 保证金编号
	 */
	private String depositId;

	/** 
	 * 组织机构代码
	 */
	private String orgId;

	/** 
	 * 保证金金额
	 */
	private long depositAmt;

	/** 
	 * 备注
	 */
	private String remark;

	/** 
	 * 操作人
	 */
	private String opeUserId;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	public SlDeposit() {
	}

	public SlDeposit(String depositId) {
		this.depositId = depositId;
	}
	public SlDeposit(String depositId, String orgId, long depositAmt, String remark, String opeUserId, Date opeTime) {
		this.depositId = depositId;
		this.orgId = orgId;
		this.depositAmt = depositAmt;
		this.remark = remark;
		this.opeUserId = opeUserId;
		this.opeTime = opeTime;
	}

	/** 
	 * 保证金编号.
	 */
	@Id
	@Column(name = "DEPOSIT_ID", unique = true, nullable = false, length = 60)
	public String getDepositId() {
		return this.depositId;
	}
	/** 
	 * 保证金编号.
	 */
	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	/** 
	 * 组织机构代码.
	 */
	@Column(name = "ORG_ID", length = 50)
	public String getOrgId() {
		return this.orgId;
	}
	/** 
	 * 组织机构代码.
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/** 
	 * 保证金金额.
	 */
	@Column(name = "DEPOSIT_AMT", precision = 10, scale = 0)
	public long getDepositAmt() {
		return this.depositAmt;
	}
	/** 
	 * 保证金金额.
	 */
	public void setDepositAmt(long depositAmt) {
		this.depositAmt = depositAmt;
	}

	/** 
	 * 备注.
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}
	/** 
	 * 备注.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 
	 * 操作人.
	 */
	@Column(name = "OPE_USER_ID", length = 60)
	public String getOpeUserId() {
		return this.opeUserId;
	}
	/** 
	 * 操作人.
	 */
	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
	}

	/** 
	 * 操作时间.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPE_TIME", length = 7)
	public Date getOpeTime() {
		return this.opeTime;
	}
	/** 
	 * 操作时间.
	 */
	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

}
