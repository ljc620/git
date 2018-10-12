package com.tbims.bean;


/**
 * 
* Title: 门票未入园统计<br/>
* Description: 
* @ClassName: TicketNoCheckBean
* @author syq
* @date 2017年9月14日 下午2:39:28
*
 */
public class TicketNoCheckBean {
	
	/**
	 * 网点编号
	 */
	private Long outletId;
	
	/**
	 * 网点名称
	 */
	private String outletName;
	
	/**
	* 交易类型(XC-现场售票、ZY-自营售票、TD-团队换票、WL-网络人工换票、ST-实体代理、BP-补票、ZG-自助购票、ZQ-自助取票)
	*/
	private String itemCd;
	/**
	 * 交易类型（名称）
	 */
	private String itemName;
	
	/**
	 * 票种名称
	 */
	private String ticketTypeName;
	
	/**
	 * 门票未入园数量
	 */
	private Long ticketNum;
	public Long getOutletId() {
		return outletId;
	}
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	
	
}
