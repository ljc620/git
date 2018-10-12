package com.tbims.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

public class RptStrinfoDBean {

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
	 * 票种编号
	 */
	private String ticketTypeId;
	/** 
	 * 票种名称
	 */
	private String ticketTypeName;
	/** 
	 * 上日库存箱数
	 */
	private BigDecimal strLastChestNum;

	/** 
	 * 上日库存张数
	 */
	private Long strLastTicketNum;

	/** 
	 * 入库箱数
	 */
	private BigDecimal inChestNum;

	/** 
	 * 入库张数
	 */
	private Long inTicketNum;

	/** 
	 * 出库箱数
	 */
	private BigDecimal outChestNum;

	/** 
	 * 出库张数
	 */
	private Long outTicketNum;

	/** 
	 * 库存箱数
	 */
	private BigDecimal strChestNum;

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

	public Long getOutTicketNum() {
		return outTicketNum;
	}

	public void setOutTicketNum(Long outTicketNum) {
		this.outTicketNum = outTicketNum;
	}
	public Long getStrTicketNum() {
		return strTicketNum;
	}

	public void setStrTicketNum(Long strTicketNum) {
		this.strTicketNum = strTicketNum;
	}

	public BigDecimal getStrLastChestNum() {
		return strLastChestNum;
	}

	public void setStrLastChestNum(BigDecimal strLastChestNum) {
		this.strLastChestNum = strLastChestNum;
	}

	public BigDecimal getInChestNum() {
		return inChestNum;
	}

	public void setInChestNum(BigDecimal inChestNum) {
		this.inChestNum = inChestNum;
	}

	public BigDecimal getOutChestNum() {
		return outChestNum;
	}

	public void setOutChestNum(BigDecimal outChestNum) {
		this.outChestNum = outChestNum;
	}

	public BigDecimal getStrChestNum() {
		return strChestNum;
	}

	public void setStrChestNum(BigDecimal strChestNum) {
		this.strChestNum = strChestNum;
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
	
	

	

}
