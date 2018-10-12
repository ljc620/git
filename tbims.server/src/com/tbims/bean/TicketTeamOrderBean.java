package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

/**
 * 
 * Title: 团队换票（团队预订表详情） <br/>
 * Description:
 * 
 * @ClassName: TicketTeamOrderBean
 * @author syq
 * @date 2017年7月12日 上午11:14:29
 *
 */
public class TicketTeamOrderBean {
	private String orgId;
	private String orgName;// 机构名称
	private Date changeTime;// 换票时间--销售日期
	private Long minusAdvanceAmt;// 扣减预付款
	private String ticketTypeId;// 票种编号
	private String ticketTypeName;// 票种名称
	private Long changeNum;// 销售数量
	private Long minusLimit;// 扣减额度

	/**
	 * 开始日期
	 */
	private Date changeTimeStart;
	/**
	 * 结束日期 
	 */
	private Date changeTimeEnd;
	
	public String getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}

	public Long getMinusLimit() {
		return minusLimit;
	}

	public void setMinusLimit(Long minusLimit) {
		this.minusLimit = minusLimit;
	}

	public Long getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(Long changeNum) {
		this.changeNum = changeNum;
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

	public Date getChangeTimeStart() {
		return changeTimeStart;
	}

	public Date getChangeTimeEnd() {
		return changeTimeEnd;
	}

	public void setChangeTimeStart(Date changeTimeStart) {
		this.changeTimeStart = changeTimeStart;
	}

	public void setChangeTimeEnd(Date changeTimeEnd) {
		this.changeTimeEnd = changeTimeEnd;
	}
}
