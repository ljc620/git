package com.tbims.bean;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

public class PeriodBean {
	/** 
	 * 预售期编号
	 */
	private String salePeriodId;

	/** 
	 * 票种编号
	 */
	private String ticketTypeId;
	/** 
	 * 票种名称
	 */
	private String ticketTypeName;

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

	public String getSalePeriodId() {
		return salePeriodId;
	}

	public void setSalePeriodId(String salePeriodId) {
		this.salePeriodId = salePeriodId;
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

	public String getSalePeriodName() {
		return salePeriodName;
	}

	public void setSalePeriodName(String salePeriodName) {
		this.salePeriodName = salePeriodName;
	}
	@JsonSerialize(using = DateSerializer.class)
	public Date getBeginDt() {
		return beginDt;
	}

	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}
	@JsonSerialize(using = DateSerializer.class)
	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Long getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Long discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Date getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Date versionNo) {
		this.versionNo = versionNo;
	}
	
}
