package com.tbims.bean;

import java.util.Date;

/**
 * 
* Title: 废票明细查询 <br/>
* Description: 
* @ClassName: UselessTicketDetailBean
* @author syq
* @date 2017年8月11日 下午2:43:33
*
 */
public class UselessTicketDetailBean {
	private Date uselessTime;//作废时间
	private Long ticketId;//票号
	private String ticketTypeId;//票种编号
	private String ticketTypeName;//票种名称
	private Long outletId;//网点编号
	private String outletName;//网点名称
	private String uselessUserId;//作废人号 
	private String uselessUserName;//作废人名称
	private String uselessReason;//作废原因
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
	public Date getUselessTime() {
		return uselessTime;
	}
	public void setUselessTime(Date uselessTime) {
		this.uselessTime = uselessTime;
	}
	public String getUselessUserId() {
		return uselessUserId;
	}
	public void setUselessUserId(String uselessUserId) {
		this.uselessUserId = uselessUserId;
	}
	public String getUselessReason() {
		return uselessReason;
	}
	public void setUselessReason(String uselessReason) {
		this.uselessReason = uselessReason;
	}
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public String getUselessUserName() {
		return uselessUserName;
	}
	public void setUselessUserName(String uselessUserName) {
		this.uselessUserName = uselessUserName;
	}
}
