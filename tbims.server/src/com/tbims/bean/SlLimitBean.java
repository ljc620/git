package com.tbims.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

public class SlLimitBean {
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
	 * 操作人.
	 */
	private String opeUserName;
	/** 
	 * 操作时间.
	 */
	private Date opeTime;
	
	

	/** 
	 * 票种编号.
	 */
	private String ticketTypeId;

	/** 
	 * 备注.
	 */
	private String remark;

	public String getLimitId() {
		return limitId;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
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
	@JsonSerialize(using = DateSerializer.class)
	public Date getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

	public String getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
