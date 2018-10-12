package com.tbims.bean;

import java.math.BigDecimal;
import java.util.Date;

public class RptStrinfoBean {
	/** 
	 * 统计日期
	 */
	private Date rptDate;
	/** 
	 * 网点名称
	 */
	private String  outletName;
	
	/** 
	 * 网点编号
	 */
	private Long outletId;

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

	public BigDecimal getStrLastChestNum() {
		return strLastChestNum;
	}

	public void setStrLastChestNum(BigDecimal strLastChestNum) {
		this.strLastChestNum = strLastChestNum;
	}

	public Long getStrLastTicketNum() {
		return strLastTicketNum;
	}

	public void setStrLastTicketNum(Long strLastTicketNum) {
		this.strLastTicketNum = strLastTicketNum;
	}

	public BigDecimal getInChestNum() {
		return inChestNum;
	}

	public void setInChestNum(BigDecimal inChestNum) {
		this.inChestNum = inChestNum;
	}

	public Long getInTicketNum() {
		return inTicketNum;
	}

	public void setInTicketNum(Long inTicketNum) {
		this.inTicketNum = inTicketNum;
	}

	public BigDecimal getOutChestNum() {
		return outChestNum;
	}

	public void setOutChestNum(BigDecimal outChestNum) {
		this.outChestNum = outChestNum;
	}

	public Long getOutTicketNum() {
		return outTicketNum;
	}

	public void setOutTicketNum(Long outTicketNum) {
		this.outTicketNum = outTicketNum;
	}

	public BigDecimal getStrChestNum() {
		return strChestNum;
	}

	public void setStrChestNum(BigDecimal strChestNum) {
		this.strChestNum = strChestNum;
	}

	public Long getStrTicketNum() {
		return strTicketNum;
	}

	public void setStrTicketNum(Long strTicketNum) {
		this.strTicketNum = strTicketNum;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
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

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public Long getSupplyTicketNum() {
		return supplyTicketNum;
	}

	public void setSupplyTicketNum(Long supplyTicketNum) {
		this.supplyTicketNum = supplyTicketNum;
	}
}
