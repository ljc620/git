package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

public class TeamOrderBean {
	/**
	 * 团队申请编号
	 */
	private String applyId;

	/**
	 * 申请时间
	 */
	private Date applyTime;

	/**
	 * 申请人
	 */
	private String applyUserId;

	/**
	 * 组织机构代码
	 */
	private String orgId;

	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 入园日期
	 */
	private Date inDt;

	/**
	 * 换票人编号
	 */
	private String changeUserId;

	/**
	 * 换票人姓名
	 */
	private String changeUserName;

	/**
	 * 换票操作人
	 */
	private String changeOpeUser;

	/**
	 * 换票时间
	 */
	private Date changeTime;

	/**
	 * 换票网点编号
	 */
	private Long changeOutletId;

	/**
	 * 审核人
	 */
	private String examUserId;

	/**
	 * 审核时间
	 */
	private Date examTime;

	/**
	 * 状态(01-已保存,02-未审核,03-已审核,04-已换票05-已拒绝06-已流单)
	 */
	private String stat;

	/**
	 * 审核后冻结预付款
	 */
	private Long examFrozenAdvanceAmt;

	/**
	 * 申请后冻结预付款
	 */
	private Long applyFrozenAdvanceAmt;

	/**
	 * 审核类型(01-自动审核,02-人工审核)
	 */
	private String examType;

	/**
	 * 扣减预付款
	 */
	private Long minusAdvanceAmt;

	/**
	 * 订单说明
	 */
	private String remark;

	/**
	 * 预付款流单罚金
	 */
	private Long flowAdvanceAmt;
	
	/**
	 * 申请开始时间
	 */
	private Date applyBTime;
	/**
	 * 申请结束时间
	 */
	private Date applyETime;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	@JsonSerialize(using = DateSerializer.class)
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
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

	public Date getInDt() {
		return inDt;
	}

	public void setInDt(Date inDt) {
		this.inDt = inDt;
	}

	public String getChangeUserId() {
		return changeUserId;
	}

	public void setChangeUserId(String changeUserId) {
		this.changeUserId = changeUserId;
	}

	public String getChangeUserName() {
		return changeUserName;
	}

	public void setChangeUserName(String changeUserName) {
		this.changeUserName = changeUserName;
	}

	public String getChangeOpeUser() {
		return changeOpeUser;
	}

	public void setChangeOpeUser(String changeOpeUser) {
		this.changeOpeUser = changeOpeUser;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Long getChangeOutletId() {
		return changeOutletId;
	}

	public void setChangeOutletId(Long changeOutletId) {
		this.changeOutletId = changeOutletId;
	}

	public String getExamUserId() {
		return examUserId;
	}

	public void setExamUserId(String examUserId) {
		this.examUserId = examUserId;
	}

	public Date getExamTime() {
		return examTime;
	}

	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public Long getApplyFrozenAdvanceAmt() {
		return applyFrozenAdvanceAmt;
	}

	public void setApplyFrozenAdvanceAmt(Long applyFrozenAdvanceAmt) {
		this.applyFrozenAdvanceAmt = applyFrozenAdvanceAmt;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public Long getMinusAdvanceAmt() {
		return minusAdvanceAmt;
	}

	public void setMinusAdvanceAmt(Long minusAdvanceAmt) {
		this.minusAdvanceAmt = minusAdvanceAmt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getExamFrozenAdvanceAmt() {
		return examFrozenAdvanceAmt;
	}

	public void setExamFrozenAdvanceAmt(Long examFrozenAdvanceAmt) {
		this.examFrozenAdvanceAmt = examFrozenAdvanceAmt;
	}

	public Long getFlowAdvanceAmt() {
		return flowAdvanceAmt;
	}

	public void setFlowAdvanceAmt(Long flowAdvanceAmt) {
		this.flowAdvanceAmt = flowAdvanceAmt;
	}

	public Date getApplyBTime() {
		return applyBTime;
	}

	public void setApplyBTime(Date applyBTime) {
		this.applyBTime = applyBTime;
	}

	public Date getApplyETime() {
		return applyETime;
	}

	public void setApplyETime(Date applyETime) {
		this.applyETime = applyETime;
	}
	
}
