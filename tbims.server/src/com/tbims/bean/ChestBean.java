package com.tbims.bean;


public class ChestBean {

	/** 
	 * 箱号
	 */
	private String chestId;

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
	private Long ticketNum;

	/** 
	 * 票起号
	 */
	private Long beginNo;

	/** 
	 * 票止号
	 */
	private Long endNo;


	

	public String getChestId() {
		return chestId;
	}

	public void setChestId(String chestId) {
		this.chestId = chestId;
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

	public Long getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}

	public Long getBeginNo() {
		return beginNo;
	}

	public void setBeginNo(Long beginNo) {
		this.beginNo = beginNo;
	}

	public Long getEndNo() {
		return endNo;
	}

	public void setEndNo(Long endNo) {
		this.endNo = endNo;
	}
}
