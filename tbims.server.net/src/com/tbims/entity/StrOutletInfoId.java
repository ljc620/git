package com.tbims.entity;
// Generated 2017-7-23 17:23:20 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Entity: 网点库存表
 */
@Embeddable
public class StrOutletInfoId implements java.io.Serializable {

	/** 
	 * 网点编号
	 */
	private Long outletId;

	/** 
	 * 票种编号
	 */
	private String ticketTypeId;

	public StrOutletInfoId() {
	}

	public StrOutletInfoId(Long outletId, String ticketTypeId) {
		this.outletId = outletId;
		this.ticketTypeId = ticketTypeId;
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
		if (!(other instanceof StrOutletInfoId))
			return false;
		StrOutletInfoId castOther = (StrOutletInfoId) other;

		return ((this.getOutletId() == castOther.getOutletId()) || (this.getOutletId() != null && castOther.getOutletId() != null && this.getOutletId().equals(castOther.getOutletId())))
				&& ((this.getTicketTypeId() == castOther.getTicketTypeId()) || (this.getTicketTypeId() != null && castOther.getTicketTypeId() != null && this.getTicketTypeId().equals(castOther.getTicketTypeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getOutletId() == null ? 0 : this.getOutletId().hashCode());
		result = 37 * result + (getTicketTypeId() == null ? 0 : this.getTicketTypeId().hashCode());
		return result;
	}

}
