package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateTimeSerializer;

/**
 * 
* Title:票务详情信息（废票交回确认）   <br/>
* Description: 
* @ClassName: TicketInfoBean
* @author syq
* @date 2017年8月1日 下午5:19:12
*
 */
public class TicketInfoBean {
	private Long ticketId;//票号
	private Long outletId;//网点编号
	private String outletName;//网点名称
	private String ticketTypeId;//票种编号
	private String ticketTypeName;//票种名称
	private Date uselessTime;//作废时间
	private String uselessUserId;//作废人号 
	private String userName;//作废人名称
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
	@JsonSerialize(using=DateTimeSerializer.class)
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
