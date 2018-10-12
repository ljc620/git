package com.tbims.entity;
// Generated 2017-9-11 16:47:31 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

/**
 * Entity: 实体代理商销售登记表
 */
@Entity
@Table(name = "SL_SALE_REG", schema = "TBIMS")
public class SlSaleReg implements java.io.Serializable {

	/** 
	 * 销售登记id
	 */
	private String saleRegId;

	/** 
	 * 销售日期
	 */
	private Date saleDate;

	/** 
	 * 票种编号
	 */
	private String ticketTypeId;

	/** 
	 * 机构号
	 */
	private String orgId;
	
	/** 
	 * 网点编号
	 */
	private Long outletId;

	/** 
	 * 销售数量
	 */
	private Long saleNum;

	/** 
	 * 销售金额
	 */
	private Long saleAmt;

	public SlSaleReg() {
	}

	public SlSaleReg(String saleRegId) {
		this.saleRegId = saleRegId;
	}
	
	public SlSaleReg(String saleRegId, Date saleDate, String ticketTypeId, String orgId, Long outletId, Long saleNum, Long saleAmt) {
		super();
		this.saleRegId = saleRegId;
		this.saleDate = saleDate;
		this.ticketTypeId = ticketTypeId;
		this.orgId = orgId;
		this.outletId = outletId;
		this.saleNum = saleNum;
		this.saleAmt = saleAmt;
	}

	/** 
	 * 销售登记id.
	 */
	@Id
	@Column(name = "SALE_REG_ID", unique = true, nullable = false, length = 60)
	public String getSaleRegId() {
		return this.saleRegId;
	}

	/** 
	 * 销售登记id.
	 */
	public void setSaleRegId(String saleRegId) {
		this.saleRegId = saleRegId;
	}

	/** 
	 * 销售日期.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SALE_DATE", length = 7)
	@JsonSerialize(using = DateSerializer.class)
	public Date getSaleDate() {
		return this.saleDate;
	}

	/** 
	 * 销售日期.
	 */
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	/** 
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}

	/** 
	 * 票种编号.
	 */
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 机构号.
	 */
	@Column(name = "ORG_ID", length = 20)
	public String getOrgId() {
		return this.orgId;
	}

	/** 
	 * 机构号.
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	/** 
	 * 网点编号
	 */
	@Column(name = "OUTLET_ID",precision = 6, scale = 0)
	public Long getOutletId() {
		return outletId;
	}
	
	/** 
	 * 网点编号
	 */
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	/** 
	 * 销售数量.
	 */
	@Column(name = "SALE_NUM", precision = 12, scale = 0)
	public Long getSaleNum() {
		return this.saleNum;
	}

	/** 
	 * 销售数量.
	 */
	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}

	/** 
	 * 销售金额.
	 */
	@Column(name = "SALE_AMT", precision = 12, scale = 0)
	public Long getSaleAmt() {
		return this.saleAmt;
	}

	/** 
	 * 销售金额.
	 */
	public void setSaleAmt(Long saleAmt) {
		this.saleAmt = saleAmt;
	}

}
