package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

/**
 * 
* Title:   废票交回确认bean<br/>
* Description: 
* @ClassName: TicketConfirmBean
* @author syq
* @date 2017年7月5日 下午6:35:05
*
 */
public class TicketConfirmBean {

	private Long outletId;//网点编号
	private String outletName;//网点名称
	private String ticketTypeId;//票种编号
	private String ticketTypeName;//票种名称
	private Long ticketNum; //废票数量
	private Date uselessTime;//作废时间
	private Date confirmTime;//确认时间
	public String getTicketTypeName() {
		return ticketTypeName;
	}
	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public Long getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}
	@JsonSerialize(using = DateSerializer.class)
	public Date getUselessTime() {
		return uselessTime;
	}
	public void setUselessTime(Date uselessTime) {
		this.uselessTime = uselessTime;
	}
	public Long getOutletId() {
		return outletId;
	}
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	public String getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	@JsonSerialize(using = DateSerializer.class)
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
}
