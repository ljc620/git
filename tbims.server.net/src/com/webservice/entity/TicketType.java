package com.webservice.entity;

/**
 * Title: 票种 <br/>
 * Description:
 * 
 * @ClassName: TicketType
 * @author ydc
 * @date 2017年7月25日 上午10:47:16
 * 
 */
public class TicketType {

	public TicketType() {
		super();
	}

	public TicketType(String ticketTypeId, String ticketTypeName, long price) {
		super();
		this.ticketTypeId = ticketTypeId;
		this.ticketTypeName = ticketTypeName;
		this.price = price;
	}

	/**
	 * 票种编号
	 */
	private String ticketTypeId;
	/**
	 * 票种名称
	 */
	private String ticketTypeName;
	/**
	 * 销售单价
	 */
	private long price;

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

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

}
