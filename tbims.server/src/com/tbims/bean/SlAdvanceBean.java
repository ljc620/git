package com.tbims.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateTimeSerializer;

public class SlAdvanceBean {
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
	 * 操作人.
	 */
	private String opeUserName;

	/** 
	 * 操作时间.
	 */
	private Date opeTime;

	public String getAdvanceId() {
		return advanceId;
	}

	public void setAdvanceId(String advanceId) {
		this.advanceId = advanceId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Long getAdvanceAmt() {
		return advanceAmt;
	}

	public void setAdvanceAmt(Long advanceAmt) {
		this.advanceAmt = advanceAmt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOpeUserId() {
		return opeUserId;
	}

	public void setOpeUserId(String opeUserId) {
		this.opeUserId = opeUserId;
	}

	public String getOpeUserName() {
		return opeUserName;
	}

	public void setOpeUserName(String opeUserName) {
		this.opeUserName = opeUserName;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "OPE_TIME", length = 7)
	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}
	
}
