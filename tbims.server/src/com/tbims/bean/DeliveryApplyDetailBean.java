package com.tbims.bean;

import com.tbims.entity.StrDeliveryApply;

public class DeliveryApplyDetailBean {
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

	public String getApplyExamId() {
		return applyExamId;
	}

	public void setApplyExamId(String applyExamId) {
		this.applyExamId = applyExamId;
	}

	public StrDeliveryApply getStrDeliveryApply() {
		return strDeliveryApply;
	}

	public void setStrDeliveryApply(StrDeliveryApply strDeliveryApply) {
		this.strDeliveryApply = strDeliveryApply;
	}

	public String getTicketTypeId() {
		return ticketTypeId;
	}

	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public Long getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(Long applyNum) {
		this.applyNum = applyNum;
	}

	public Long getExamNum() {
		return examNum;
	}

	public void setExamNum(Long examNum) {
		this.examNum = examNum;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
	
	
}
