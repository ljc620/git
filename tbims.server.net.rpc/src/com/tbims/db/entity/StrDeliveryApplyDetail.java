package com.tbims.db.entity;
// Generated 2017-6-19 19:09:15 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity: 配送申请明细表
 */
@Entity
@Table(name = "STR_DELIVERY_APPLY_DETAIL", schema = "TBIMS")
public class StrDeliveryApplyDetail implements java.io.Serializable {

	/** 
	 * 申请明细主键
	 */
	private String applyExamId;

	/** 
	 * 申请编号
	 */
	private StrDeliveryApply strDeliveryApply;

	/** 
	 * 票种编号
	 */
	private String ticketTypeId;

	/** 
	 * 申请数量
	 */
	private Long applyNum;

	/** 
	 * 审核数量
	 */
	private Long examNum;

	public StrDeliveryApplyDetail() {
	}

	public StrDeliveryApplyDetail(String applyExamId) {
		this.applyExamId = applyExamId;
	}
	public StrDeliveryApplyDetail(String applyExamId, StrDeliveryApply strDeliveryApply, String ticketTypeId, Long applyNum, Long examNum) {
		this.applyExamId = applyExamId;
		this.strDeliveryApply = strDeliveryApply;
		this.ticketTypeId = ticketTypeId;
		this.applyNum = applyNum;
		this.examNum = examNum;
	}

	/** 
	 * 申请明细主键.
	 */
	@Id
	@Column(name = "APPLY_EXAM_ID", unique = true, nullable = false, length = 50)
	public String getApplyExamId() {
		return this.applyExamId;
	}

	public void setApplyExamId(String applyExamId) {
		this.applyExamId = applyExamId;
	}

	/** 
	 * 申请编号.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPLY_ID")
	public StrDeliveryApply getStrDeliveryApply() {
		return this.strDeliveryApply;
	}

	public void setStrDeliveryApply(StrDeliveryApply strDeliveryApply) {
		this.strDeliveryApply = strDeliveryApply;
	}

	/** 
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 申请数量.
	 */
	@Column(name = "APPLY_NUM", precision = 12, scale = 0)
	public Long getApplyNum() {
		return this.applyNum;
	}

	public void setApplyNum(Long applyNum) {
		this.applyNum = applyNum;
	}

	/** 
	 * 审核数量.
	 */
	@Column(name = "EXAM_NUM", precision = 12, scale = 0)
	public Long getExamNum() {
		return this.examNum;
	}

	public void setExamNum(Long examNum) {
		this.examNum = examNum;
	}

}
