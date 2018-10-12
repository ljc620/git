package com.tbims.entity;
// Generated 2017-6-26 15:59:15 by Hibernate Tools 4.0.0

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
 * Entity: 预售期表
 */
@Entity
@Table(name = "SL_PERIOD", schema = "TBIMS")
public class SlPeriod implements java.io.Serializable {

	/** 
	 * 预售期编号
	 */
	private String salePeriodId;

	/** 
	 * 票种编号
	 */
	private String ticketTypeId;

	/** 
	 * 预售期名称
	 */
	private String salePeriodName;

	/** 
	 * 预售期开始日期
	 */
	private Date beginDt;

	/** 
	 * 预售期结束日期
	 */
	private Date endDt;

	/** 
	 * 折后票价（元）
	 */
	private Long discountPrice;

	/** 
	 * 版本号
	 */
	private Date versionNo;

	public SlPeriod() {
	}

	public SlPeriod(String salePeriodId) {
		this.salePeriodId = salePeriodId;
	}
	public SlPeriod(String salePeriodId, String ticketTypeId, String salePeriodName, Date beginDt, Date endDt, Long discountPrice, Date versionNo) {
		this.salePeriodId = salePeriodId;
		this.ticketTypeId = ticketTypeId;
		this.salePeriodName = salePeriodName;
		this.beginDt = beginDt;
		this.endDt = endDt;
		this.discountPrice = discountPrice;
		this.versionNo = versionNo;
	}

	/** 
	 * 预售期编号.
	 */
	@Id
	@Column(name = "SALE_PERIOD_ID", unique = true, nullable = false, length = 60)
	public String getSalePeriodId() {
		return this.salePeriodId;
	}
	/** 
	 * 预售期编号.
	 */
	public void setSalePeriodId(String salePeriodId) {
		this.salePeriodId = salePeriodId;
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
	 * 预售期名称.
	 */
	@Column(name = "SALE_PERIOD_NAME", length = 100)
	public String getSalePeriodName() {
		return this.salePeriodName;
	}
	/** 
	 * 预售期名称.
	 */
	public void setSalePeriodName(String salePeriodName) {
		this.salePeriodName = salePeriodName;
	}

	/** 
	 * 预售期开始日期.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "BEGIN_DT", length = 7)
	@JsonSerialize(using = DateSerializer.class)
	public Date getBeginDt() {
		return this.beginDt;
	}
	/** 
	 * 预售期开始日期.
	 */
	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}

	/** 
	 * 预售期结束日期.
	 */
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = DateSerializer.class)
	@Column(name = "END_DT", length = 7)
	public Date getEndDt() {
		return this.endDt;
	}
	/** 
	 * 预售期结束日期.
	 */
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	/** 
	 * 折后票价（元）.
	 */
	@Column(name = "DISCOUNT_PRICE", precision = 5, scale = 0)
	public Long getDiscountPrice() {
		return this.discountPrice;
	}
	/** 
	 * 折后票价（元）.
	 */
	public void setDiscountPrice(Long discountPrice) {
		this.discountPrice = discountPrice;
	}

	/** 
	 * 版本号.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VERSION_NO", length = 7)
	public Date getVersionNo() {
		return this.versionNo;
	}
	/** 
	 * 版本号.
	 */
	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}

}
