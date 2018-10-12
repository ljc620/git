package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

/**
 * 
* Title: 团队换票统计  <br/>
* Description: 
* @ClassName: RptTeamTdBean
* @author syq
* @date 2017年7月12日 上午9:44:09
*
 */
public class RptTeamTdBean {
	private String orgId;
	private String orgName;//机构名称 
	private Date changeTime;//换票时间--销售日期
	private Long minusAdvanceAmt;//扣减预付款
	
	@JsonSerialize(using = DateSerializer.class)
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public Long getMinusAdvanceAmt() {
		return minusAdvanceAmt;
	}
	public void setMinusAdvanceAmt(Long minusAdvanceAmt) {
		this.minusAdvanceAmt = minusAdvanceAmt;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
