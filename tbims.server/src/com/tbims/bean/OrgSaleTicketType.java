package com.tbims.bean;

public class OrgSaleTicketType {
	/** 
	 * 票种代码
	 */
	private String ticketTypeId;

	/** 
	 * 票种名称
	 */
	private String ticketTypeName;
	
	/**
	 * 是否可售
	 * */
	private String checked;



	public boolean getChecked() {
		if("true".equals(checked)){
		return true;
		}
		else{
			return false;	
		}
	}

	public void setChecked(String checked) {
		this.checked = checked;
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
}
