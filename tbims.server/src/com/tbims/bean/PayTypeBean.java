package com.tbims.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

public class PayTypeBean {
	private Date  rptDate;
	private Date startDate; //开始时间 RPT_DATE
	private Date endDate; //结束时间
	private Long  money;//现金
	private Long  posMachine;//POS付款
	private Long wechat;//微信
	private Long alipay;//支付宝
	private Long agents;//代理
	private String ticketTypeId;//票种
	private String ticketTypeName;//票种名称
	private String userId;// 销售用户名称
	private String userName;// 销售用户名称
	private Long outletId;//网点名称
	private String outletName;//网点名称
	private Long total;//合计
	
	
	@JsonSerialize(using = DateSerializer.class)
	@Temporal(TemporalType.DATE)
	@Column(name = "RPT_DATE", length = 7)
	public Date getRptDate() {
		return rptDate;
	}
	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
	}
	public Long getWechat() {
		return wechat;
	}
	public void setWechat(Long wechat) {
		this.wechat = wechat;
	}
	public Long getAlipay() {
		return alipay;
	}
	public void setAlipay(Long alipay) {
		this.alipay = alipay;
	}
	public Long getAgents() {
		return agents;
	}
	public void setAgents(Long agents) {
		this.agents = agents;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getMoney() {
		return money;
	}
	public void setMoney(Long money) {
		this.money = money;
	}
	public Long getPosMachine() {
		return posMachine;
	}
	public void setPosMachine(Long posMachine) {
		this.posMachine = posMachine;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
}
