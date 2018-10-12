package com.tbims.bean;


public class TeamOrderDetailBean {

	/** 
	 * 团队票预订明细ID
	 */
	private String detailId;

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

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

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

	public Long getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(Long changeNum) {
		this.changeNum = changeNum;
	}

	public Long getMinusLimit() {
		return minusLimit;
	}

	public void setMinusLimit(Long minusLimit) {
		this.minusLimit = minusLimit;
	}

	public Long getApplyFrozenLimit() {
		return applyFrozenLimit;
	}

	public void setApplyFrozenLimit(Long applyFrozenLimit) {
		this.applyFrozenLimit = applyFrozenLimit;
	}

	public Long getExamFrozenLimit() {
		return examFrozenLimit;
	}

	public void setExamFrozenLimit(Long examFrozenLimit) {
		this.examFrozenLimit = examFrozenLimit;
	}

	public Long getFlowLimit() {
		return flowLimit;
	}

	public void setFlowLimit(Long flowLimit) {
		this.flowLimit = flowLimit;
	}
	
	

}
