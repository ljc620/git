package com.tbims.entity;
// Generated 2017-7-11 9:37:01 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Entity: 每日网点库存汇总表
 */
@Embeddable
public class RptStrinfoOutletDId implements java.io.Serializable {

	/** 
	 * 统计日期
	 */
	private Date rptDate;

	/** 
	 * 网点编号
	 */
	private Long outletId;

	/** 
	 * 票种
	 */
	private String ticketTypeId;

	public RptStrinfoOutletDId() {
	}

	public RptStrinfoOutletDId(Date rptDate, Long outletId, String ticketTypeId) {
		this.rptDate = rptDate;
		this.outletId = outletId;
		this.ticketTypeId = ticketTypeId;
	}

	/** 
	 * 统计日期.
	 */
	@Column(name = "RPT_DATE", nullable = false, length = 7)
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
	 * 网点编号.
	 */
	@Column(name = "OUTLET_ID", nullable = false, precision = 6, scale = 0)
	public Long getOutletId() {
		return this.outletId;
	}
	/** 
	 * 网点编号.
	 */
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	/** 
	 * 票种.
	 */
	@Column(name = "TICKET_TYPE_ID", nullable = false, length = 3)
	public String getTicketTypeId() {
		return this.ticketTypeId;
	}
	/** 
	 * 票种.
	 */
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RptStrinfoOutletDId))
			return false;
		RptStrinfoOutletDId castOther = (RptStrinfoOutletDId) other;

		return ((this.getRptDate() == castOther.getRptDate()) || (this.getRptDate() != null && castOther.getRptDate() != null && this.getRptDate().equals(castOther.getRptDate())))
				&& ((this.getOutletId() == castOther.getOutletId()) || (this.getOutletId() != null && castOther.getOutletId() != null && this.getOutletId().equals(castOther.getOutletId())))
				&& ((this.getTicketTypeId() == castOther.getTicketTypeId()) || (this.getTicketTypeId() != null && castOther.getTicketTypeId() != null && this.getTicketTypeId().equals(castOther.getTicketTypeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRptDate() == null ? 0 : this.getRptDate().hashCode());
		result = 37 * result + (getOutletId() == null ? 0 : this.getOutletId().hashCode());
		result = 37 * result + (getTicketTypeId() == null ? 0 : this.getTicketTypeId().hashCode());
		return result;
	}

}
