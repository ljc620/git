package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

public class SlDepositBean {
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
	 * 操作人
	 */
	private String opeUserName;

	/** 
	 * 操作时间
	 */
	private Date opeTime;

	public String getDepositId() {
		return depositId;
	}

	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public long getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(long depositAmt) {
		this.depositAmt = depositAmt;
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
	
	@JsonSerialize(using = DateSerializer.class)
	public Date getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}
	
	
}
