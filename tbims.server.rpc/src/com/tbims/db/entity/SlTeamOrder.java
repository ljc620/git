package com.tbims.db.entity;
// Generated 2017-7-4 10:27:57 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity: 团队票预订表
 */
@Entity
@Table(name = "SL_TEAM_ORDER", schema = "TBIMS")
public class SlTeamOrder implements java.io.Serializable {

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

	private List<SlTeamOrderDetail> slTeamOrderDetails = new ArrayList<SlTeamOrderDetail>(0);

	public SlTeamOrder() {
	}

	public SlTeamOrder(String applyId, String orgId) {
		this.applyId = applyId;
		this.orgId = orgId;
	}
	public SlTeamOrder(String applyId, Date applyTime, String applyUserId, String orgId, String orgName, Date inDt, String changeUserId, String changeUserName, String changeOpeUser, Date changeTime, Long changeOutletId, String examUserId, Date examTime, String stat, Long examFrozenAdvanceAmt, Long applyFrozenAdvanceAmt, String examType, Long minusAdvanceAmt, String remark, Long flowAdvanceAmt, List<SlTeamOrderDetail> slTeamOrderDetails) {
		this.applyId = applyId;
		this.applyTime = applyTime;
		this.applyUserId = applyUserId;
		this.orgId = orgId;
		this.orgName = orgName;
		this.inDt = inDt;
		this.changeUserId = changeUserId;
		this.changeUserName = changeUserName;
		this.changeOpeUser = changeOpeUser;
		this.changeTime = changeTime;
		this.changeOutletId = changeOutletId;
		this.examUserId = examUserId;
		this.examTime = examTime;
		this.stat = stat;
		this.examFrozenAdvanceAmt = examFrozenAdvanceAmt;
		this.applyFrozenAdvanceAmt = applyFrozenAdvanceAmt;
		this.examType = examType;
		this.minusAdvanceAmt = minusAdvanceAmt;
		this.remark = remark;
		this.flowAdvanceAmt = flowAdvanceAmt;
		this.slTeamOrderDetails = slTeamOrderDetails;
	}

	/**
	 * 团队申请编号.
	 */
	@Id
	@Column(name = "APPLY_ID", unique = true, nullable = false, length = 60)
	public String getApplyId() {
		return this.applyId;
	}
	/**
	 * 团队申请编号.
	 */
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	/**
	 * 申请时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLY_TIME", length = 7)
	public Date getApplyTime() {
		return this.applyTime;
	}
	/**
	 * 申请时间.
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * 申请人.
	 */
	@Column(name = "APPLY_USER_ID", length = 50)
	public String getApplyUserId() {
		return this.applyUserId;
	}
	/**
	 * 申请人.
	 */
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	/**
	 * 组织机构代码.
	 */
	@Column(name = "ORG_ID", nullable = false, length = 50)
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
	 * 机构名称.
	 */
	@Column(name = "ORG_NAME", length = 100)
	public String getOrgName() {
		return this.orgName;
	}
	/**
	 * 机构名称.
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 入园日期.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "IN_DT", length = 7)
	public Date getInDt() {
		return this.inDt;
	}
	/**
	 * 入园日期.
	 */
	public void setInDt(Date inDt) {
		this.inDt = inDt;
	}

	/**
	 * 换票人编号.
	 */
	@Column(name = "CHANGE_USER_ID", length = 50)
	public String getChangeUserId() {
		return this.changeUserId;
	}
	/**
	 * 换票人编号.
	 */
	public void setChangeUserId(String changeUserId) {
		this.changeUserId = changeUserId;
	}

	/**
	 * 换票人姓名.
	 */
	@Column(name = "CHANGE_USER_NAME")
	public String getChangeUserName() {
		return this.changeUserName;
	}
	/**
	 * 换票人姓名.
	 */
	public void setChangeUserName(String changeUserName) {
		this.changeUserName = changeUserName;
	}

	/**
	 * 换票操作人.
	 */
	@Column(name = "CHANGE_OPE_USER", length = 50)
	public String getChangeOpeUser() {
		return this.changeOpeUser;
	}
	/**
	 * 换票操作人.
	 */
	public void setChangeOpeUser(String changeOpeUser) {
		this.changeOpeUser = changeOpeUser;
	}

	/**
	 * 换票时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHANGE_TIME", length = 7)
	public Date getChangeTime() {
		return this.changeTime;
	}
	/**
	 * 换票时间.
	 */
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	/**
	 * 换票网点编号.
	 */
	@Column(name = "CHANGE_OUTLET_ID", precision = 6, scale = 0)
	public Long getChangeOutletId() {
		return this.changeOutletId;
	}
	/**
	 * 换票网点编号.
	 */
	public void setChangeOutletId(Long changeOutletId) {
		this.changeOutletId = changeOutletId;
	}

	/**
	 * 审核人.
	 */
	@Column(name = "EXAM_USER_ID", length = 50)
	public String getExamUserId() {
		return this.examUserId;
	}
	/**
	 * 审核人.
	 */
	public void setExamUserId(String examUserId) {
		this.examUserId = examUserId;
	}

	/**
	 * 审核时间.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXAM_TIME", length = 7)
	public Date getExamTime() {
		return this.examTime;
	}
	/**
	 * 审核时间.
	 */
	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}

	/**
	 * 状态(01-已保存,02-未审核,03-已审核,04-已换票05-已拒绝06-已流单).
	 */
	@Column(name = "STAT", length = 2)
	public String getStat() {
		return this.stat;
	}
	/**
	 * 状态(01-已保存,02-未审核,03-已审核,04-已换票05-已拒绝06-已流单).
	 */
	public void setStat(String stat) {
		this.stat = stat;
	}

	/**
	 * 审核后冻结预付款.
	 */
	@Column(name = "EXAM_FROZEN_ADVANCE_AMT", precision = 12, scale = 0)
	public Long getExamFrozenAdvanceAmt() {
		return this.examFrozenAdvanceAmt;
	}
	/**
	 * 审核后冻结预付款.
	 */
	public void setExamFrozenAdvanceAmt(Long examFrozenAdvanceAmt) {
		this.examFrozenAdvanceAmt = examFrozenAdvanceAmt;
	}

	/**
	 * 申请后冻结预付款.
	 */
	@Column(name = "APPLY_FROZEN_ADVANCE_AMT", precision = 12, scale = 0)
	public Long getApplyFrozenAdvanceAmt() {
		return this.applyFrozenAdvanceAmt;
	}
	/**
	 * 申请后冻结预付款.
	 */
	public void setApplyFrozenAdvanceAmt(Long applyFrozenAdvanceAmt) {
		this.applyFrozenAdvanceAmt = applyFrozenAdvanceAmt;
	}

	/**
	 * 审核类型(01-自动审核,02-人工审核).
	 */
	@Column(name = "EXAM_TYPE", length = 2)
	public String getExamType() {
		return this.examType;
	}
	/**
	 * 审核类型(01-自动审核,02-人工审核).
	 */
	public void setExamType(String examType) {
		this.examType = examType;
	}

	/**
	 * 扣减预付款.
	 */
	@Column(name = "MINUS_ADVANCE_AMT", precision = 12, scale = 0)
	public Long getMinusAdvanceAmt() {
		return this.minusAdvanceAmt;
	}
	/**
	 * 扣减预付款.
	 */
	public void setMinusAdvanceAmt(Long minusAdvanceAmt) {
		this.minusAdvanceAmt = minusAdvanceAmt;
	}

	/**
	 * 订单说明.
	 */
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}
	/**
	 * 订单说明.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 预付款流单罚金.
	 */
	@Column(name = "FLOW_ADVANCE_AMT", precision = 12, scale = 0)
	public Long getFlowAdvanceAmt() {
		return this.flowAdvanceAmt;
	}
	/**
	 * 预付款流单罚金.
	 */
	public void setFlowAdvanceAmt(Long flowAdvanceAmt) {
		this.flowAdvanceAmt = flowAdvanceAmt;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "slTeamOrder")
	public List<SlTeamOrderDetail> getSlTeamOrderDetails() {
		return this.slTeamOrderDetails;
	}

	public void setSlTeamOrderDetails(List<SlTeamOrderDetail> slTeamOrderDetails) {
		this.slTeamOrderDetails = slTeamOrderDetails;
	}

}
