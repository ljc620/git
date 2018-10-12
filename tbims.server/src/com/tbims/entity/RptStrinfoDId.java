package com.tbims.entity;
// Generated 2017-7-11 9:37:01 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhming.support.common.DateSerializer;

/**
 * Entity: 每日票务中心库存汇总表
 */
@Embeddable
public class RptStrinfoDId implements java.io.Serializable {

	/** 
	 * 统计日期
	 */
	private Date rptDate;

	/** 
	 * 票种编号
	 */
	private String ticketTypeId;

	public RptStrinfoDId() {
	}

	public RptStrinfoDId(Date rptDate, String ticketTypeId) {
		this.rptDate = rptDate;
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 统计日期.
	 */
	@Column(name = "RPT_DATE", nullable = false, length = 7)
	@JsonSerialize(using = DateSerializer.class)
	public Date getRptDate() {
		return this.rptDate;
	}
	/** 
	 * 统计日期.
	 */
	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
	}

	/** 
	 * 票种编号.
	 */
	@Column(name = "TICKET_TYPE_ID", nullable = false, length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}
	/** 
	 * 票种编号.
	 */
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RptStrinfoDId))
			return false;
		RptStrinfoDId castOther = (RptStrinfoDId) other;

		return ((this.getRptDate() == castOther.getRptDate()) || (this.getRptDate() != null && castOther.getRptDate() != null && this.getRptDate().equals(castOther.getRptDate())))
				&& ((this.getTicketTypeId() == castOther.getTicketTypeId()) || (this.getTicketTypeId() != null && castOther.getTicketTypeId() != null && this.getTicketTypeId().equals(castOther.getTicketTypeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRptDate() == null ? 0 : this.getRptDate().hashCode());
		result = 37 * result + (getTicketTypeId() == null ? 0 : this.getTicketTypeId().hashCode());
		return result;
	}

}
