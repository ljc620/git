package com.tbims.bean;

/**
 * 
* Title:   门票申请<br/>
* Description: 
* @ClassName: ApplyDetailBean
* @author syq
* @date 2017年9月12日 下午2:54:17
*
 */
public class ApplyDetailBean {

	/** 
	 * 票种编号
	 */
	private String ticketTypeId;
	
	/**
	 * 票种名称
	 */
	private String ticketTypeName;

	/** 
	 * 门票数
	 */
	private Long applyNum;

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
}
