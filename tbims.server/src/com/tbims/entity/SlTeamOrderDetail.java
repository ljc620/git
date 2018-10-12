package com.tbims.entity;
// Generated 2017-7-4 10:58:38 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity: 团队票预订明细表
 */
@Entity
@Table(name = "SL_TEAM_ORDER_DETAIL", schema = "TBIMS")
public class SlTeamOrderDetail implements java.io.Serializable {

	/** 
	 * 团队票预订明细ID
	 */
	private String detailId;

	/** 
	 * 团队申请编号
	 */
	private SlTeamOrder slTeamOrder;

	/** 
	 * 票种编号
	 */
	private String ticketTypeId;

	/** 
	 * 票种名称
	 */
	private String ticketTypeName;

	/** 
	 * 申请数量
	 */
	private Long applyNum;

	/** 
	 * 审核数量
	 */
	private Long examNum;

	/** 
	 * 出票数量
	 */
	private Long changeNum;

	/** 
	 * 扣减额度
	 */
	private Long minusLimit;

	/** 
	 * 审核冻结额度
	 */
	private Long examFrozenLimit;

	/** 
	 * 申请冻结额度
	 */
	private Long applyFrozenLimit;

	/** 
	 * 额度流单罚金
	 */
	private Long flowLimit;
	
	/**
	*备注
	*/
	private String remark;

	public SlTeamOrderDetail() {
	}

	public SlTeamOrderDetail(String detailId) {
		this.detailId = detailId;
	}
	public SlTeamOrderDetail(String detailId, SlTeamOrder slTeamOrder, String ticketTypeId, String ticketTypeName, Long applyNum, Long examNum, Long changeNum, Long minusLimit, Long examFrozenLimit, Long applyFrozenLimit, Long flowLimit,String remark) {
		this.detailId = detailId;
		this.slTeamOrder = slTeamOrder;
		this.ticketTypeId = ticketTypeId;
		this.ticketTypeName = ticketTypeName;
		this.applyNum = applyNum;
		this.examNum = examNum;
		this.changeNum = changeNum;
		this.minusLimit = minusLimit;
		this.examFrozenLimit = examFrozenLimit;
		this.applyFrozenLimit = applyFrozenLimit;
		this.flowLimit = flowLimit;
		this.remark=remark;
	}

	/** 
	 * 团队票预订明细ID.
	 */
	@Id
	@Column(name = "DETAIL_ID", unique = true, nullable = false, length = 60)
	public String getDetailId() {
		return this.detailId;
	}
	/** 
	 * 团队票预订明细ID.
	 */
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	/** 
	 * 团队申请编号.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPLY_ID")
	public SlTeamOrder getSlTeamOrder() {
		return this.slTeamOrder;
	}
	/** 
	 * 团队申请编号.
	 */
	public void setSlTeamOrder(SlTeamOrder slTeamOrder) {
		this.slTeamOrder = slTeamOrder;
	}

	/** 
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}
	/** 
	 * 票种编号.
	 */
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 票种名称.
	 */
	@Column(name = "TICKET_TYPE_NAME", length = 200)
	public String getTicketTypeName() {
		return this.ticketTypeName;
	}
	/** 
	 * 票种名称.
	 */
	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}

	/** 
	 * 申请数量.
	 */
	@Column(name = "APPLY_NUM", precision = 5, scale = 0)
	public Long getApplyNum() {
		return this.applyNum;
	}
	/** 
	 * 申请数量.
	 */
	public void setApplyNum(Long applyNum) {
		this.applyNum = applyNum;
	}

	/** 
	 * 审核数量.
	 */
	@Column(name = "EXAM_NUM", precision = 5, scale = 0)
	public Long getExamNum() {
		return this.examNum;
	}
	/** 
	 * 审核数量.
	 */
	public void setExamNum(Long examNum) {
		this.examNum = examNum;
	}

	/** 
	 * 出票数量.
	 */
	@Column(name = "CHANGE_NUM", precision = 5, scale = 0)
	public Long getChangeNum() {
		return this.changeNum;
	}
	/** 
	 * 出票数量.
	 */
	public void setChangeNum(Long changeNum) {
		this.changeNum = changeNum;
	}

	/** 
	 * 扣减额度.
	 */
	@Column(name = "MINUS_LIMIT", precision = 5, scale = 0)
	public Long getMinusLimit() {
		return this.minusLimit;
	}
	/** 
	 * 扣减额度.
	 */
	public void setMinusLimit(Long minusLimit) {
		this.minusLimit = minusLimit;
	}

	/** 
	 * 审核冻结额度.
	 */
	@Column(name = "EXAM_FROZEN_LIMIT", precision = 5, scale = 0)
	public Long getExamFrozenLimit() {
		return this.examFrozenLimit;
	}
	/** 
	 * 审核冻结额度.
	 */
	public void setExamFrozenLimit(Long examFrozenLimit) {
		this.examFrozenLimit = examFrozenLimit;
	}

	/** 
	 * 申请冻结额度.
	 */
	@Column(name = "APPLY_FROZEN_LIMIT", precision = 5, scale = 0)
	public Long getApplyFrozenLimit() {
		return this.applyFrozenLimit;
	}
	/** 
	 * 申请冻结额度.
	 */
	public void setApplyFrozenLimit(Long applyFrozenLimit) {
		this.applyFrozenLimit = applyFrozenLimit;
	}

	/** 
	 * 额度流单罚金.
	 */
	@Column(name = "FLOW_LIMIT", precision = 5, scale = 0)
	public Long getFlowLimit() {
		return this.flowLimit;
	}
	/** 
	 * 额度流单罚金.
	 */
	public void setFlowLimit(Long flowLimit) {
		this.flowLimit = flowLimit;
	}

	/**
	*备注
	*/
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}
	/**
	*备注
	*/
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
