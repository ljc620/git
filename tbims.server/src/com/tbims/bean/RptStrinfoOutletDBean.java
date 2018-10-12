package com.tbims.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

public class RptStrinfoOutletDBean {
	/** 
	 * 统计日期
	 */
	private Date rptDate;
	/** 
	 * 开始日期
	 */
	private Date startDate;
	/** 
	 * 结束日期
	 */
	private Date endDate;
	/** 
	 * 网点编号
	 */
	private Long outletId;
	/** 
	 * 网点名称
	 */
	private String outletName;

	/** 
	 * 票种
	 */
	private String ticketTypeId;

	/** 
	 * 票种名称
	 */
	private String ticketTypeName;
	/** 
	 * 上日库存张数
	 */
	private Long strLastTicketNum;

	/** 
	 * 入库张数
	 */
	private Long inTicketNum;

	/** 
	 * 售票张数（包括换票）
	 */
	private Long saleTicketNum;

	/** 
	 * 坏票废票张数
	 */
	private Long uselessTicketNum;
	
	/** 
	 * 补票废票张数
	 */
	private Long supplyTicketNum;

	/** 
	 * 库存张数
	 */
	private Long strTicketNum;

	
	@JsonSerialize(using = DateSerializer.class)
	@Temporal(TemporalType.DATE)
	@Column(name = "RPT_DATE", length = 7)
	public Date getRptDate() {
		return rptDate;
	}

	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
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

	public Long getStrLastTicketNum() {
		return strLastTicketNum;
	}

	public void setStrLastTicketNum(Long strLastTicketNum) {
		this.strLastTicketNum = strLastTicketNum;
	}

	public Long getInTicketNum() {
		return inTicketNum;
	}

	public void setInTicketNum(Long inTicketNum) {
		this.inTicketNum = inTicketNum;
	}

	public Long getSaleTicketNum() {
		return saleTicketNum;
	}

	public void setSaleTicketNum(Long saleTicketNum) {
		this.saleTicketNum = saleTicketNum;
	}

	public Long getUselessTicketNum() {
		return uselessTicketNum;
	}

	public void setUselessTicketNum(Long uselessTicketNum) {
		this.uselessTicketNum = uselessTicketNum;
	}

	public Long getStrTicketNum() {
		return strTicketNum;
	}

	public void setStrTicketNum(Long strTicketNum) {
		this.strTicketNum = strTicketNum;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
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

	public Long getSupplyTicketNum() {
		return supplyTicketNum;
	}

	public void setSupplyTicketNum(Long supplyTicketNum) {
		this.supplyTicketNum = supplyTicketNum;
	}
}
